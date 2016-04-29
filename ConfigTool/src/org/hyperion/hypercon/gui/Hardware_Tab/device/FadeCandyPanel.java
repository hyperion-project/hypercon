package org.hyperion.hypercon.gui.Hardware_Tab.device;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.DeviceConfig;

public class FadeCandyPanel extends DeviceTypePanel {


	private JLabel mHostLabel;
	private JTextField mHostField;
	
	private JLabel mPortLabel;
	private JSpinner mPortSpinner;

	private JLabel mChannelLabel;
	private JSpinner mChannelSpinner;

	public FadeCandyPanel() {
		super();
		
		initialise();
	}

	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		String host              = getValue("output", "127.0.0.1");
		int port			 = getValue("port", 7890);
		int channel     			 = getValue("channel", 0);

		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output",   host);
		mDeviceConfig.mDeviceProperties.put("port",   	port);
		mDeviceConfig.mDeviceProperties.put("channel", 	channel);
		
		mHostField.setText(host);
		mPortSpinner.setValue(port);
		mChannelSpinner.setValue(channel);
	}
	
	private void initialise() {
		mHostLabel = new JLabel(language.getString("hardware.leddevice.host"));
		mHostLabel.setMinimumSize(firstColMinDim);
		add(mHostLabel);
		
		mHostField = new JTextField();
		mHostField.setMaximumSize(maxDim);
		mHostField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mHostField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mHostField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("output", mHostField.getText());
			}
		});		
		add(mHostField);
		
		mPortLabel = new JLabel(language.getString("hardware.leddevice.portLabel"));
		mPortLabel.setMinimumSize(firstColMinDim);
		add(mPortLabel);

		mPortSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));
		mPortSpinner.setMaximumSize(maxDim);
		mPortSpinner.addChangeListener(mChangeListener);
		add(mPortSpinner);

		mChannelLabel = new JLabel(language.getString("hardware.leddevice.channellabel"));
		mChannelLabel.setMinimumSize(firstColMinDim);
		add(mChannelLabel);

		mChannelSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));
		mChannelSpinner.setMaximumSize(maxDim);
		mChannelSpinner.addChangeListener(mChangeListener);
		add(mChannelSpinner);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostLabel)
						.addComponent(mPortLabel)
						.addComponent(mChannelLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostField)
						.addComponent(mPortSpinner)
						.addComponent(mChannelSpinner))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHostLabel)
						.addComponent(mHostField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mPortLabel)
						.addComponent(mPortSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mChannelLabel)
						.addComponent(mChannelSpinner))
				);		
	}
	
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == mPortSpinner) {
				mDeviceConfig.mDeviceProperties.put("port", mPortSpinner.getValue());
			} else if (e.getSource() == mChannelSpinner) {
				mDeviceConfig.mDeviceProperties.put("channel", mChannelSpinner.getValue());
			}
		}
	};
}


