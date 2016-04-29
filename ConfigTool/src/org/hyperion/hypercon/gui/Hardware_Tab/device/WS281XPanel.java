package org.hyperion.hypercon.gui.Hardware_Tab.device;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.DeviceConfig;

public class WS281XPanel extends DeviceTypePanel{

	private JLabel mledCountLabel;
	private JSpinner mledCountSpinner;
	
	
//	private JComboBox<String> mOutputCombo;
	
	private JLabel mBaudrateLabel;
	private JSpinner mBaudrateSpinner;
	

	public WS281XPanel() {
		super();
		
		initialise();
	}
	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		int ledcount			 = getValue("leds", 256);

		
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("leds",   ledcount);
		
		mledCountSpinner.setValue(ledcount);
	}
	
	private void initialise() {
		
		mledCountLabel = new JLabel(language.getString("hardware.leddevice.numLedsLabel"));
		mledCountLabel.setMinimumSize(firstColMinDim);
		add(mledCountLabel);

		mledCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		mledCountSpinner.setMaximumSize(maxDim);
		mledCountSpinner.addChangeListener(mChangeListener);
		add(mledCountSpinner);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mledCountLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mledCountSpinner)
						)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mledCountLabel)
						.addComponent(mledCountSpinner))
				);		
	}
	
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == mledCountSpinner) {
				mDeviceConfig.mDeviceProperties.put("leds", mledCountSpinner.getValue());
			}
		}
	};
}