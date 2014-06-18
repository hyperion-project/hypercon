package org.hyperion.hypercon.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;
import org.hyperion.ssh.PiSshConnection;

import com.jcraft.jsch.JSchException;

/**
 * @author Fabian Hertwig
 * 
 */
public class SshConnectionPanel extends JPanel implements Observer {

	/**
	 * Here the connection data is saved, eg Ip, Username,...
	 */
	private final SshAndColorPickerConfig mSshConfig;

	/**
	 * the model to handle the connection
	 */
	private final SshConnectionModel sshConnection;

	/**
	 * this is this class to get acces in the Actionlistener subclass. TODO: make acces to this class more beautiful in Actionlistener
	 * 
	 */
	private final SshConnectionPanel self;

	// Gui objects
	private JLabel mAddressLabel;
	private JTextField mAddressField;

	private JLabel mPortLabel;
	private JSpinner mTcpPortSpinner;

	private JLabel mUsernameLabel;
	private JTextField mUsernameField;

	private JLabel mPasswordLabel;
	private JPasswordField mPasswordField;

	private JButton connectBut;

	/**
	 * Constructor
	 * 
	 * @param pSshConfig
	 */
	public SshConnectionPanel(final SshAndColorPickerConfig pSshConfig) {
		super();

		self = this;
		mSshConfig = pSshConfig;
		sshConnection = SshConnectionModel.getInstance();
		// Observe the connection status to enable or disable the buttons
		// accordingly
		sshConnection.addObserver(this);

		initialise();
	}

	/**
	 * to set the Guielements sizes
	 */
	@Override
	@Transient
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension prefSize = super.getPreferredSize();
		return new Dimension(maxSize.width, prefSize.height);
	}
	
	/**
	 * Create, add and layout Gui elements
	 */
	private void initialise() {
		setBorder(BorderFactory.createTitledBorder("Ssh Connection"));

		mAddressLabel = new JLabel("Raspberry IP:");
		add(mAddressLabel);

		mAddressField = new JTextField(mSshConfig.ipAdress);
		mAddressField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mSshConfig.ipAdress = mAddressField.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				mSshConfig.ipAdress = mAddressField.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mSshConfig.ipAdress = mAddressField.getText();
			}
		});
		add(mAddressField);

		mPortLabel = new JLabel("Port:");
		add(mPortLabel);

		mTcpPortSpinner = new JSpinner(new SpinnerNumberModel(mSshConfig.port, 1, 65535, 1));
		mTcpPortSpinner.addChangeListener(mChangeListener);
		add(mTcpPortSpinner);

		mUsernameLabel = new JLabel("Username:");
		add(mUsernameLabel);

		mUsernameField = new JTextField(mSshConfig.username);
		mUsernameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mSshConfig.username = mUsernameField.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				mSshConfig.username = mUsernameField.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mSshConfig.username = mUsernameField.getText();
			}
		});
		add(mUsernameField);

		mPasswordLabel = new JLabel("Password:");
		add(mPasswordLabel);

		mPasswordField = new JPasswordField(mSshConfig.password);
		mPasswordField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mSshConfig.password = new String(mPasswordField.getPassword());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				mSshConfig.password = new String(mPasswordField.getPassword());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mSshConfig.password = new String(mPasswordField.getPassword());
			}
		});
		add(mPasswordField);

		connectBut = new JButton("Connect");
		connectBut.addActionListener(mActionListener);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mAddressLabel)
						.addComponent(mPortLabel)
						.addComponent(mUsernameLabel)
						.addComponent(mPasswordLabel)
						.addComponent(connectBut))
				.addGroup(layout.createParallelGroup()
						.addComponent(mAddressField)
						.addComponent(mTcpPortSpinner)
						.addComponent(mUsernameField)
						.addComponent(mPasswordField)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mAddressLabel)
						.addComponent(mAddressField))
				.addGroup(layout.createParallelGroup()
						.addComponent(mPortLabel)
						.addComponent(mTcpPortSpinner))
				.addGroup(layout.createParallelGroup()
						.addComponent(mUsernameLabel)
						.addComponent(mUsernameField))
				.addGroup(layout.createParallelGroup()
						.addComponent(mPasswordLabel)
						.addComponent(mPasswordField))
						.addGap(10)
				.addGroup(layout.createParallelGroup()
						.addComponent(connectBut))

		);

	}

	/**
	 * Save selection changes in the config file
	 */
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mSshConfig.port = (Integer) mTcpPortSpinner.getValue();
		}
	};

	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == connectBut) {
				if (!sshConnection.isConnected()) {
					setConnectionFieldsAcces(false);
					connectBut.setEnabled(false);

					try {
						if (!sshConnection.connect(mAddressField.getText(), (Integer) mTcpPortSpinner.getValue(), mUsernameField.getText(),
								mPasswordField.getPassword())) {
							JOptionPane.showMessageDialog(self, "Can't connect. Is all data correct?");
							setConnectionFieldsAcces(true);
						}
						connectBut.setEnabled(true);
					} catch (com.jcraft.jsch.JSchException e1) {
						System.out.println("caught the JSchException");
						// FIXME: somehow this com.jcraft.jsch.JSchException
						// cant be caught
					}
				} else {
					sshConnection.disconnect();

				}
			}

		}
	};

	/**
	 * Enable or disable the Gui elememnts which depend on a shh connection
	 * @param setEnabled
	 */
	private void setConnectionFieldsAcces(boolean setEnabled) {
		mAddressField.setEnabled(setEnabled);
		mTcpPortSpinner.setEnabled(setEnabled);
		mUsernameField.setEnabled(setEnabled);
		mPasswordField.setEnabled(setEnabled);

	}

	@Override
	public void update(Observable o, Object arg) {
		if (SshConnectionModel.getInstance().isConnected()) {
			setConnectionFieldsAcces(false);
			connectBut.setText("Disconnect");
		} else {
			setConnectionFieldsAcces(true);
			connectBut.setText("Connect");
		}

	}
}
