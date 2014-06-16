package org.hyperion.hypercon.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.spec.SshConfig;

public class SshConnectionPanel extends JPanel{

	private final SshConfig mSshConfig;
	
	
	private JLabel mAddressLabel;
	private JTextField mAddressField;
	
	private JLabel mPortLabel;
	private JSpinner mTcpPortSpinner;

	private JCheckBox autoUpdate;
	
	public SshConnectionPanel(final SshConfig pSshConfig) {
		super();
		
		mSshConfig = pSshConfig;
		
		initialise();
	}
	
	@Override
	@Transient
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension prefSize = super.getPreferredSize();
		return new Dimension(maxSize.width, prefSize.height);
	}

	private void initialise() {
		setBorder(BorderFactory.createTitledBorder("Ssh Connection"));
		
		
		mAddressLabel = new JLabel("Raspberry address:");
		add(mAddressLabel);
		
		mAddressField = new JTextField(mSshConfig.adress);
		mAddressField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mSshConfig.adress = mAddressField.getText();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mSshConfig.adress = mAddressField.getText();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mSshConfig.adress = mAddressField.getText();
			}
		});
		add(mAddressField);
		
		mPortLabel = new JLabel("Port:");
		add(mPortLabel);
		
		mTcpPortSpinner = new JSpinner(new SpinnerNumberModel(mSshConfig.port, 1, 65535, 1));
		mTcpPortSpinner.addChangeListener(mChangeListener);
		add(mTcpPortSpinner);
		
		autoUpdate = new JCheckBox("Auto Update");
		autoUpdate.addActionListener(mActionListener);
		add(autoUpdate);
		
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mAddressLabel)
						.addComponent(mPortLabel)
						.addComponent(autoUpdate)
						
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mAddressField)
						.addComponent(mTcpPortSpinner)
						.addComponent(autoUpdate)
						
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mAddressLabel)
						.addComponent(mAddressField)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mPortLabel)
						.addComponent(mTcpPortSpinner)
						)
		.addComponent(autoUpdate));

	}
	
	
	
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mSshConfig.port = (Integer)mTcpPortSpinner.getValue();
		}	
	};
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mSshConfig.autoUpdate = autoUpdate.isSelected();

		
		}
	};
}
