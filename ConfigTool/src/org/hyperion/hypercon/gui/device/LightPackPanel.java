package org.hyperion.hypercon.gui.device;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.spec.DeviceConfigModel;

public class LightPackPanel extends DeviceTypePanel {

	private JLabel mSerialNoLabel;
	private JTextField mSerialNoField;
	
	public LightPackPanel() {
		super();
		
		initialise();
	}
	
	@Override
	public void setDeviceConfig(DeviceConfigModel pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);
		
		mSerialNoField.setText(mDeviceConfig.mOutput.getValue());
	}
	
	private void initialise() {
		mSerialNoLabel = new JLabel("Serial #: ");
		mSerialNoLabel.setMinimumSize(firstColMinDim);
		add(mSerialNoLabel);
		
		mSerialNoField = new JTextField();
		mSerialNoField.setToolTipText("The serial number of the lightpack. Leave empty if just one hyperion will sort it out.");
		mSerialNoField.setMaximumSize(maxDim);
		mSerialNoField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mOutput.setValue(mSerialNoField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mOutput.setValue(mSerialNoField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mOutput.setValue(mSerialNoField.getText());
			}
		});
		add(mSerialNoField);

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(mSerialNoLabel)
				.addComponent(mSerialNoField));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(mSerialNoLabel)
				.addComponent(mSerialNoField));
	}
	
}
