package org.hyperion.hypercon.gui.SSH_Tab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jcraft.jsch.JSchException;
import org.hyperion.hypercon.ErrorHandling;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.HyperionRemoteCalls;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;

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



	// Gui objects
    private JLabel mSystemLabel;
    private JComboBox<String> mSystemCB;

	private JLabel mAddressLabel;
	private JTextField mAddressField;

	private JLabel mPortLabel;
	private JSpinner mTcpPortSpinner;

	private JLabel mUsernameLabel;
	private JTextField mUsernameField;

	private JLabel mPasswordLabel;
	private JPasswordField mPasswordField;

	private JButton connectBut;
	private JButton mTrafficBut;

	/**
	 * Constructor
	 * 
	 * @param pSshConfig
	 */
	public SshConnectionPanel(final SshAndColorPickerConfig pSshConfig) {
		super();

		mSshConfig = pSshConfig;
		sshConnection = SshConnectionModel.getInstance();
		// Observe the connection status to enable or disable the buttons
		// accordingly
		sshConnection.addObserver(this);

        SSHTrafficPrinterFrame.getInstance();

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
		setBorder(BorderFactory.createTitledBorder(language.getString("ssh.sshconnection.title"))); //$NON-NLS-1$

        String systemTooltip = language.getString("ssh.sshconnection.systemtooltip"); //$NON-NLS-1$
        mSystemLabel = new JLabel(language.getString("ssh.sshconnection.systemlabel")); //$NON-NLS-1$
        mSystemLabel.setToolTipText(systemTooltip);
        mSystemCB = new JComboBox<>(HyperionRemoteCalls.getSystemTypesAsVecor());
        mSystemCB.addActionListener(mActionListener);
        mSystemCB.setSelectedItem(mSshConfig.selectedSystemType.toString());
        mSystemCB.setToolTipText(systemTooltip);

		mAddressLabel = new JLabel(language.getString("ssh.sshconnection.rpiiplabel")); //$NON-NLS-1$
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

		mPortLabel = new JLabel(language.getString("ssh.sshconnection.portlabel")); //$NON-NLS-1$
		add(mPortLabel);

		mTcpPortSpinner = new JSpinner(new SpinnerNumberModel(mSshConfig.port, 1, 65535, 1));
		mTcpPortSpinner.addChangeListener(mChangeListener);
		add(mTcpPortSpinner);

		mUsernameLabel = new JLabel(language.getString("ssh.sshconnection.usernamelabel")); //$NON-NLS-1$
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

		mPasswordLabel = new JLabel(language.getString("ssh.sshconnection.passwordlabel")); //$NON-NLS-1$
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
		add(connectBut);

		mTrafficBut = new JButton(language.getString("ssh.sshconnection.showtrafficbutton")); //$NON-NLS-1$
		mTrafficBut.addActionListener(mActionListener);
		add(mTrafficBut);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);

		layout.setHorizontalGroup(layout
                .createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(mSystemLabel)
                        .addComponent(mAddressLabel)
                        .addComponent(mPortLabel)
                        .addComponent(mUsernameLabel)
                        .addComponent(mPasswordLabel)
                        .addComponent(connectBut))
                .addGroup(layout.createParallelGroup()
                        .addComponent(mSystemCB)
                        .addComponent(mAddressField)
                        .addComponent(mTcpPortSpinner)
                        .addComponent(mUsernameField)
                        .addComponent(mPasswordField)
                        .addComponent(mTrafficBut)
                ));

		layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(mSystemLabel)
                        .addComponent(mSystemCB))
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
						.addComponent(connectBut)
						.addComponent(mTrafficBut))

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
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if (connectBut.getText().equals("Connect") && !sshConnection.isConnected()) { //$NON-NLS-1$
					connectBut.setEnabled(false);

					try {
						sshConnection.connect(mAddressField.getText(), (Integer) mTcpPortSpinner.getValue(), mUsernameField.getText(),
								mPasswordField.getPassword());
					} catch (JSchException e1) {

						if(e1.getMessage().contains("Auth cancel")){ //$NON-NLS-1$
							ErrorHandling.ShowMessage(language.getString("ssh.sshconnection.wrongusernameorpwdmessage")); //$NON-NLS-1$
						}
						else if(e1.getMessage().contains("Connection refused: connect")){ //$NON-NLS-1$
							ErrorHandling.ShowMessage(language.getString("ssh.sshconnection.connectionrefusedmessage")); //$NON-NLS-1$
						}else if(e1.getMessage().contains("timeout: socket is not established")){ //$NON-NLS-1$
							ErrorHandling.ShowMessage(language.getString("ssh.sshconnection.timeoutwrongipmessage")); //$NON-NLS-1$
						}else{
							ErrorHandling.ShowException(e1);
						}

					}
					connectBut.setEnabled(true);
				} else {
					sshConnection.disconnect();

				}
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}else if(e.getSource().equals(mTrafficBut)){
					SSHTrafficPrinterFrame.getInstance();
			}
            else if(e.getSource().equals(mSystemCB)){
                try {
                    mSshConfig.selectedSystemType = HyperionRemoteCalls.fromString(mSystemCB.getSelectedItem().toString());
                    SshConnectionModel.setHyperionRemoteCall(HyperionRemoteCalls.getHyperionRemoteCallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionGrabberV4l2Call(HyperionRemoteCalls.getGrabberv4l2CallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionInstallCall(HyperionRemoteCalls.getHyperionInstallCallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionRemoveCall(HyperionRemoteCalls.getHyperionRemoveCallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionConfigTargetCall(HyperionRemoteCalls.getConfigTargetCallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionStartServiceCall(HyperionRemoteCalls.getHyperionStartServiceCallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionStopServiceCall(HyperionRemoteCalls.getHyperionStopServiceCallForSystemType(mSshConfig.selectedSystemType));
                    SshConnectionModel.setHyperionLogCall(HyperionRemoteCalls.getHyperionLogCallForSystemType(mSshConfig.selectedSystemType));
                } catch (Exception e1) {
                    ErrorHandling.ShowException(e1);
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
