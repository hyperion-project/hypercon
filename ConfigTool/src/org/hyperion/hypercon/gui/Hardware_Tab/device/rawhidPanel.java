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

public class rawhidPanel extends DeviceTypePanel {

	private JLabel mdelayAfterConnectLabel;
	private JSpinner mdelayAtferConnectSpinner;
	
	private JLabel mPIDLabel;
	private JTextField mPIDField;
	
	private JLabel mVIDLabel;
	private JTextField mVIDField;
	
	public rawhidPanel() {
		super();
		
		initialise();
	}

	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		Integer delayAfterConnect = getValue ("delayAfterConnect", 0);
		String pid      = getValue("PID",     "0x8036");
		String vid = getValue("VID", "0x2341");
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("delayAfterConnect",     delayAfterConnect);
		mDeviceConfig.mDeviceProperties.put("PID",     pid);
		mDeviceConfig.mDeviceProperties.put("VID", vid);
		
		mdelayAtferConnectSpinner.setValue(delayAfterConnect);
		mPIDField.setText(pid);
		mVIDField.setText(vid);
	}
	
	private void initialise() {
		mdelayAfterConnectLabel = new JLabel(language.getString("hardware.leddevice.delayAfterCon"));
		mdelayAfterConnectLabel.setMinimumSize(firstColMinDim);
		add(mdelayAfterConnectLabel);
		
		mdelayAtferConnectSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));
		mdelayAtferConnectSpinner.setMaximumSize(maxDim);
		mdelayAtferConnectSpinner.addChangeListener(mChangeListener);
		add(mdelayAtferConnectSpinner);
	
		mPIDLabel = new JLabel(language.getString("hardware.leddevice.pid"));
		mPIDLabel.setMinimumSize(firstColMinDim);
		add(mPIDLabel);
		
		mPIDField = new JTextField();
		mPIDField.setMaximumSize(maxDim);
		mPIDField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("PID", mPIDField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("PID", mPIDField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("PID", mPIDField.getText());
			}
		});		
		add(mPIDField);
		
		mVIDLabel = new JLabel(language.getString("hardware.leddevice.vid"));
		mVIDLabel.setMinimumSize(firstColMinDim);
		add(mVIDLabel);
		
		mVIDField = new JTextField();
		mVIDField.setMaximumSize(maxDim);
		mVIDField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("VID", mVIDField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("VID", mVIDField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("VID", mVIDField.getText());
			}
		});		
		add(mVIDField);
	
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mPIDLabel)
						.addComponent(mVIDLabel)
						.addComponent(mdelayAfterConnectLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mPIDField)
						.addComponent(mVIDField)
						.addComponent(mdelayAtferConnectSpinner))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mPIDLabel)
						.addComponent(mPIDField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mVIDLabel)
						.addComponent(mVIDField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mdelayAfterConnectLabel)
						.addComponent(mdelayAtferConnectSpinner))
				);		
		}
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			 if (e.getSource() == mdelayAtferConnectSpinner) {
				mDeviceConfig.mDeviceProperties.put("delayAfterConnect", mdelayAtferConnectSpinner.getValue());
			}
		}
	};
}
