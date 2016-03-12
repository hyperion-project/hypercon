package org.hyperion.hypercon.gui.Hardware_Tab.device;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.DeviceConfig;

public class LightPackPanel extends DeviceTypePanel {

	private JLabel mSerialNoLabel;
	private JTextField mSerialNoField;
	
	public LightPackPanel() {
		super();
		
		initialise();
	}
	
	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);
		
		// Make sure that the device specific configuration (and only device specific) is set
		String output = getValue("output", "");
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output", output);
		
		mSerialNoField.setText(output);
	}
	
	private void initialise() {
		mSerialNoLabel = new JLabel(language.getString("hardware.leddevice.serialno"));
		mSerialNoLabel.setMinimumSize(firstColMinDim);
		add(mSerialNoLabel);
		
		mSerialNoField = new JTextField();
		mSerialNoField.setMaximumSize(maxDim);
		mSerialNoField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mSerialNoField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mSerialNoField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mSerialNoField.getText());
			}
		});
		add(mSerialNoField);

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(mSerialNoLabel)
				.addComponent(mSerialNoField));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(mSerialNoLabel)
				.addComponent(mSerialNoField));
	}
	
}
