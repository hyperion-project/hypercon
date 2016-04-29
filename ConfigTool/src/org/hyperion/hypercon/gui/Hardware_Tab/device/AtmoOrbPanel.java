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

public class AtmoOrbPanel extends DeviceTypePanel {

	private JLabel mHostLabel;
	private JTextField mHostField;
	
	private JLabel morbIdsLabel;
	private JTextField morbIdsField;

	private JLabel museOrbSmoothingLabel;
	private JCheckBox museOrbSmoothingBox;

	private JLabel mSkipSmoothingDiffLabel;
	private JSpinner mSkipSmoothingDiffSpinner;
	
	private JLabel mTransitionTimeLabel;
	private JSpinner mTransitionTimeSpinner;

	private JLabel mnumLedsLabel;
	private JSpinner mnumLedsSpinner;
	
	private JLabel mPortLabel;
	private JSpinner mPortSpinner;
	
	public AtmoOrbPanel() {
		super();
		
		initialise();
	}
	
	@Override
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		super.setDeviceConfig(pDeviceConfig);

		// Make sure that the device specific configuration (and only device specific) is set
		String host              = getValue("output", "239.15.18.2");
		boolean useOrbSmoothing	 = getValue("useOrbSmoothing", false);
		int skipSmoothingDiff	 = getValue("skipSmoothingDiff", 0);
		int transitionTime       = getValue("transitiontime", 1);
		String orbIds 			 = getValue("orbIds","1");
		int numLeds     	     = getValue("numLeds", 24);
		int port     	    	 = getValue("port", 49692);
		mDeviceConfig.mDeviceProperties.clear();
		mDeviceConfig.mDeviceProperties.put("output",   host);
		mDeviceConfig.mDeviceProperties.put("useOrbSmoothing", useOrbSmoothing);
		mDeviceConfig.mDeviceProperties.put("skipSmoothingDiff", skipSmoothingDiff);
		mDeviceConfig.mDeviceProperties.put("transitiontime", transitionTime);
		mDeviceConfig.mDeviceProperties.put("orbIds",   orbIds);
		mDeviceConfig.mDeviceProperties.put("numLeds",   numLeds);
		mDeviceConfig.mDeviceProperties.put("port",   port);
		
		mHostField.setText(host);
		morbIdsField.setText(orbIds);
		museOrbSmoothingBox.setSelected(useOrbSmoothing);
		mSkipSmoothingDiffSpinner.setValue(skipSmoothingDiff);
		mTransitionTimeSpinner.setValue(transitionTime);
		mnumLedsSpinner.setValue(numLeds);
		mPortSpinner.setValue(port);
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
	
		morbIdsLabel = new JLabel(language.getString("hardware.leddevice.orbIdslabel"));
		morbIdsLabel.setMinimumSize(firstColMinDim);
		add(morbIdsLabel);

		morbIdsField = new JTextField();
		morbIdsField.setMaximumSize(maxDim);
		morbIdsField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("orbIds", morbIdsField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("orbIds", morbIdsField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mDeviceProperties.put("orbIds", morbIdsField.getText());
			}
		});		
		add(morbIdsField);
		
		museOrbSmoothingLabel = new JLabel(language.getString("hardware.leddevice.AOuseOrbSmoothing"));
		museOrbSmoothingLabel.setMinimumSize(firstColMinDim);
		add(museOrbSmoothingLabel);
		
		museOrbSmoothingBox = new JCheckBox();
		museOrbSmoothingBox.setMaximumSize(maxDim);
		museOrbSmoothingBox.addChangeListener(mChangeListener);
		add(museOrbSmoothingBox);
		
		mSkipSmoothingDiffLabel = new JLabel(language.getString("hardware.leddevice.AOskipSmoothingDiffLabel"));
		mSkipSmoothingDiffLabel.setMinimumSize(firstColMinDim);
		add(mSkipSmoothingDiffLabel);

		mSkipSmoothingDiffSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
		mSkipSmoothingDiffSpinner.setMaximumSize(maxDim);
		mSkipSmoothingDiffSpinner.addChangeListener(mChangeListener);
		add(mSkipSmoothingDiffSpinner);
		
		mTransitionTimeLabel = new JLabel(language.getString("hardware.leddevice.transitiontime"));
		mTransitionTimeLabel.setMinimumSize(firstColMinDim);
		add(mTransitionTimeLabel);

		mTransitionTimeSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
		mTransitionTimeSpinner.setMaximumSize(maxDim);
		mTransitionTimeSpinner.addChangeListener(mChangeListener);
		add(mTransitionTimeSpinner);

		mnumLedsLabel = new JLabel(language.getString("hardware.leddevice.numLedsLabel"));
		mnumLedsLabel.setMinimumSize(firstColMinDim);
		add(mnumLedsLabel);
		
		mnumLedsSpinner = new JSpinner(new SpinnerNumberModel(24, 1, 500, 1));
		mnumLedsSpinner.setMaximumSize(maxDim);
		mnumLedsSpinner.addChangeListener(mChangeListener);
		add(mnumLedsSpinner);
		
		mPortLabel = new JLabel(language.getString("hardware.leddevice.portLabel"));
		mPortLabel.setMinimumSize(firstColMinDim);
		add(mPortLabel);
		
		mPortSpinner = new JSpinner(new SpinnerNumberModel(49692, 1, 99999, 1));
		mPortSpinner.setMaximumSize(maxDim);
		mPortSpinner.addChangeListener(mChangeListener);
		add(mPortSpinner);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostLabel)
						.addComponent(morbIdsLabel)
						.addComponent(mnumLedsLabel)
						.addComponent(mPortLabel)
						.addComponent(museOrbSmoothingLabel)
						.addComponent(mSkipSmoothingDiffLabel)
						.addComponent(mTransitionTimeLabel))
				.addGroup(layout.createParallelGroup()
						.addComponent(mHostField)
						.addComponent(morbIdsField)
						.addComponent(mnumLedsSpinner)
						.addComponent(mPortSpinner)
						.addComponent(museOrbSmoothingBox)
						.addComponent(mSkipSmoothingDiffSpinner)
						.addComponent(mTransitionTimeSpinner))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHostLabel)
						.addComponent(mHostField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(morbIdsLabel)
						.addComponent(morbIdsField))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mnumLedsLabel)
						.addComponent(mnumLedsSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mPortLabel)
						.addComponent(mPortSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(museOrbSmoothingLabel)
						.addComponent(museOrbSmoothingBox))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mSkipSmoothingDiffLabel)
						.addComponent(mSkipSmoothingDiffSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mTransitionTimeLabel)
						.addComponent(mTransitionTimeSpinner))
				);		
	}
	
	private ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == museOrbSmoothingBox) {
				mDeviceConfig.mDeviceProperties.put("museOrbSmoothingBox", museOrbSmoothingBox.isSelected());
			} else if (e.getSource() == mSkipSmoothingDiffSpinner) {
				mDeviceConfig.mDeviceProperties.put("skipSmoothingDiff", mSkipSmoothingDiffSpinner.getValue());
			} else if (e.getSource() == mTransitionTimeSpinner) {
				mDeviceConfig.mDeviceProperties.put("transitiontime", mTransitionTimeSpinner.getValue());
			}else if (e.getSource() == mPortSpinner) {
				mDeviceConfig.mDeviceProperties.put("port", mPortSpinner.getValue());
			}else if (e.getSource() == mnumLedsSpinner) {
				mDeviceConfig.mDeviceProperties.put("numLeds", mnumLedsSpinner.getValue());
			}
		}
	};
}
