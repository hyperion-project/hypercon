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

public class UDPPanel extends DeviceTypePanel {

	private JLabel mHostLabel;
	private JTextField mHostField;
	
	private JLabel mProtocolLabel;
	private JSpinner mProtocolSpinner;

	private JLabel mRateLabel;
	private JSpinner mRateSpinner;
	
	private JLabel mMaxPacketLabel;
	private JSpinner mMaxPacketSpinner;

	public UDPPanel() {
		super();
		
		initialise();
	}

	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		String host              = getValue("output", "192.168.0.10:19446");
		int protocol			 = getValue("protocol", 0);
		int rate     			 = getValue("rate", 1000000);
		int maxpacket			 = getValue("maxpacket", 1450);
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output",   host);
		mDeviceConfig.mDeviceProperties.put("protocol",   protocol);
		mDeviceConfig.mDeviceProperties.put("rate",   rate);
		mDeviceConfig.mDeviceProperties.put("maxpacket", maxpacket);
		
		mHostField.setText(host);
		mProtocolSpinner.setValue(protocol);
		mRateSpinner.setValue(rate);
		mMaxPacketSpinner.setValue(maxpacket);
	}
	
	private void initialise() {
		mHostLabel = new JLabel(language.getString("hardware.leddevice.output"));
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
		
		mProtocolLabel = new JLabel(language.getString("hardware.leddevice.protocol"));
		mProtocolLabel.setMinimumSize(firstColMinDim);
		add(mProtocolLabel);

		mProtocolSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 3, 1));
		mProtocolSpinner.setMaximumSize(maxDim);
		mProtocolSpinner.addChangeListener(mChangeListener);
		add(mProtocolSpinner);

		mRateLabel = new JLabel(language.getString("hardware.leddevice.rate"));
		mRateLabel.setMinimumSize(firstColMinDim);
		add(mRateLabel);

		mRateSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 2000000, 1));
		mRateSpinner.setMaximumSize(maxDim);
		mRateSpinner.addChangeListener(mChangeListener);
		add(mRateSpinner);
		
		mMaxPacketLabel = new JLabel(language.getString("hardware.leddevice.maxpacketlabel"));
		mMaxPacketLabel.setMinimumSize(firstColMinDim);
		add(mMaxPacketLabel);

		mMaxPacketSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		mMaxPacketSpinner.setMaximumSize(maxDim);
		mMaxPacketSpinner.addChangeListener(mChangeListener);
		add(mMaxPacketSpinner);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostLabel)
						.addComponent(mProtocolLabel)
						.addComponent(mRateLabel)
						.addComponent(mMaxPacketLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostField)
						.addComponent(mProtocolSpinner)
						.addComponent(mRateSpinner)
						.addComponent(mMaxPacketSpinner))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHostLabel)
						.addComponent(mHostField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mProtocolLabel)
						.addComponent(mProtocolSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mRateLabel)
						.addComponent(mRateSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mMaxPacketLabel)
						.addComponent(mMaxPacketSpinner))
				);		
	}
	
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == mProtocolSpinner) {
				mDeviceConfig.mDeviceProperties.put("protocol", mProtocolSpinner.getValue());
			} else if (e.getSource() == mRateSpinner) {
				mDeviceConfig.mDeviceProperties.put("rate", mRateSpinner.getValue());
			} else if (e.getSource() == mMaxPacketSpinner) {
				mDeviceConfig.mDeviceProperties.put("maxpacket", mMaxPacketSpinner.getValue());
			}
		}
	};
}

