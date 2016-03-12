package org.hyperion.hypercon.gui.Hardware_Tab.device;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
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

public class PhilipsHuePanel extends DeviceTypePanel {

	private JLabel mHostLabel;
	private JTextField mHostField;
	
	private JLabel mUsernameLabel;
	private JTextField mUsernameField;

	private JLabel mSwitchOffOnBlackLabel;
	private JCheckBox mSwitchOffOnBlackBox;
	
	private JLabel mTransitionTimeLabel;
	private JSpinner mTransitionTimeSpinner;

	public PhilipsHuePanel() {
		super();
		
		initialise();
	}

	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		String host              = getValue("output", "127.0.0.1");
		String username          = getValue("username", "newdeveloper");
		boolean switchOffOnBlack = getValue("switchOffOnBlack", true);
		int transitionTime       = getValue("transitiontime", 1);
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output",   host);
		mDeviceConfig.mDeviceProperties.put("username", username);
		mDeviceConfig.mDeviceProperties.put("switchOffOnBlack", switchOffOnBlack);
		mDeviceConfig.mDeviceProperties.put("transitiontime", transitionTime);
		
		mHostField.setText(host);
		mUsernameField.setText(username);
		mSwitchOffOnBlackBox.setSelected(switchOffOnBlack);
		mTransitionTimeSpinner.setValue(transitionTime);
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
	
		mUsernameLabel = new JLabel(language.getString("hardware.leddevice.usernamelabel"));
		mUsernameLabel.setMinimumSize(firstColMinDim);
		add(mUsernameLabel);

		mUsernameField = new JTextField();
		mUsernameField.setMaximumSize(maxDim);
		mUsernameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("username", mUsernameField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("username", mUsernameField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("username", mUsernameField.getText());
			}
		});		
		add(mUsernameField);
		
		mSwitchOffOnBlackLabel = new JLabel(language.getString("hardware.leddevice.switchoffonblack"));
		mSwitchOffOnBlackLabel.setMinimumSize(firstColMinDim);
		add(mSwitchOffOnBlackLabel);
		
		mSwitchOffOnBlackBox = new JCheckBox();
		mSwitchOffOnBlackBox.setMaximumSize(maxDim);
		mSwitchOffOnBlackBox.addChangeListener(mChangeListener);
		add(mSwitchOffOnBlackBox);
		
		mTransitionTimeLabel = new JLabel(language.getString("hardware.leddevice.transitiontime"));
		mTransitionTimeLabel.setMinimumSize(firstColMinDim);
		add(mTransitionTimeLabel);

		mTransitionTimeSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
		mTransitionTimeSpinner.setMaximumSize(maxDim);
		mTransitionTimeSpinner.addChangeListener(mChangeListener);
		add(mTransitionTimeSpinner);

		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostLabel)
						.addComponent(mUsernameLabel)
						.addComponent(mSwitchOffOnBlackLabel)
						.addComponent(mTransitionTimeLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostField)
						.addComponent(mUsernameField)
						.addComponent(mSwitchOffOnBlackBox)
						.addComponent(mTransitionTimeSpinner))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHostLabel)
						.addComponent(mHostField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mUsernameLabel)
						.addComponent(mUsernameField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mSwitchOffOnBlackLabel)
						.addComponent(mSwitchOffOnBlackBox))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mTransitionTimeLabel)
						.addComponent(mTransitionTimeSpinner))
				);		
	}
	
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == mSwitchOffOnBlackBox) {
				mDeviceConfig.mDeviceProperties.put("switchOffOnBlack", mSwitchOffOnBlackBox.isSelected());
			} else if (e.getSource() == mTransitionTimeSpinner) {
				mDeviceConfig.mDeviceProperties.put("transitiontime", mTransitionTimeSpinner.getValue());
			}
		}
	};
}
