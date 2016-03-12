package org.hyperion.hypercon.gui.Hardware_Tab.device;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.DeviceConfig;

/**
 * Panel for configuring Ws2801 specific settings
 */
public class SerialPanel extends DeviceTypePanel {

	public static final String[] KnownOutputs = { "/dev/ttyS0", "/dev/ttyUSB0", "/dev/ttyAMA0", "/dev/ttyACM0", "/dev/null"};

	private JLabel mOutputLabel;
	private JComboBox<String> mOutputCombo;
	
	private JLabel mBaudrateLabel;
	private JSpinner mBaudrateSpinner;

	private JLabel mDelayLabel;
	private JSpinner mDelaySpinner;
	

	public SerialPanel() {
		super();
		
		initialise();
	}

	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		String output = getValue("output", KnownOutputs[0]);
		int baudrate  = getValue("rate", 100000);
		int delay     = getValue("delayAfterConnect", 0);
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output", output);
		mDeviceConfig.mDeviceProperties.put("rate",   baudrate);
		mDeviceConfig.mDeviceProperties.put("delayAfterConnect", delay);
		
		mOutputCombo.setSelectedItem(output);
		((SpinnerNumberModel)mBaudrateSpinner.getModel()).setValue(baudrate);
		((SpinnerNumberModel)mDelaySpinner.getModel()).setValue(delay);
	}
	
	private void initialise() {
		mOutputLabel = new JLabel(language.getString("hardware.leddevice.output"));
		mOutputLabel.setMinimumSize(firstColMinDim);
		add(mOutputLabel);
		
		mOutputCombo = new JComboBox<>(KnownOutputs);
		mOutputCombo.setMaximumSize(maxDim);
		mOutputCombo.setEditable(true);
		mOutputCombo.addActionListener(mActionListener);
		add(mOutputCombo);
		
		mBaudrateLabel = new JLabel(language.getString("hardware.leddevice.baudrate"));
		mBaudrateLabel.setMinimumSize(firstColMinDim);
		add(mBaudrateLabel);
		
		mBaudrateSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000000, 128));
		mBaudrateSpinner.setMaximumSize(maxDim);
		mBaudrateSpinner.addChangeListener(mChangeListener);
		add(mBaudrateSpinner);
	
		mDelayLabel = new JLabel(language.getString("hardware.leddevice.delayinms"));
		mDelayLabel.setMinimumSize(firstColMinDim);
		add(mDelayLabel);

		mDelaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 100));
		mDelaySpinner.setMaximumSize(maxDim);
		mDelaySpinner.addChangeListener(mChangeListener);
		add(mDelaySpinner);
		

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mOutputLabel)
						.addComponent(mBaudrateLabel)
						.addComponent(mDelayLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mOutputCombo)
						.addComponent(mBaudrateSpinner)
						.addComponent(mDelaySpinner))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mOutputLabel)
						.addComponent(mOutputCombo))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mBaudrateLabel)
						.addComponent(mBaudrateSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mDelayLabel)
						.addComponent(mDelaySpinner))
				);		
	}
	
	private ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mOutputCombo) {
				mDeviceConfig.mDeviceProperties.put("output", mOutputCombo.getSelectedItem());
			} else if (e.getSource() == mBaudrateSpinner) {
				mDeviceConfig.mDeviceProperties.put("rate", mBaudrateSpinner.getValue());
			} else if (e.getSource() == mDelaySpinner) {
				mDeviceConfig.mDeviceProperties.put("delayAfterConnect", mDelaySpinner.getValue());
			}
			
		}
	};
	
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mDeviceConfig.mDeviceProperties.put("rate", mBaudrateSpinner.getValue());
		}
	};
}
