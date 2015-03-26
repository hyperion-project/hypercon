package org.hyperion.hypercon.gui.device;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.spec.DeviceConfig;

public class TestDevicePanel extends DeviceTypePanel {

	private JLabel mFilenameLabel;
	private JTextField mFilenameField;
	
	public TestDevicePanel() {
		super();
		
		initialise();
	}
	
	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);
		
		// Make sure that the device specific configuration (and only device specific) is set
		String output = getValue("output", "/tmp/hyperion.out");
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output",   output);
		
		mFilenameField.setText(output);
	}
	
	private void initialise() {
		mFilenameLabel = new JLabel("Filename: ");
		mFilenameLabel.setMinimumSize(firstColMinDim);
		add(mFilenameLabel);
		
		mFilenameField = new JTextField();
		mFilenameField.setMaximumSize(maxDim);
		mFilenameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mFilenameField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mFilenameField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mFilenameField.getText());
			}
		});
		add(mFilenameField);

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(mFilenameLabel)
				.addComponent(mFilenameField));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(mFilenameLabel)
				.addComponent(mFilenameField));
	}
	
}
