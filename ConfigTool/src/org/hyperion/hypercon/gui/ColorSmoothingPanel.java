package org.hyperion.hypercon.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.spec.ColorSmoothingType;
import org.hyperion.hypercon.spec.SmoothingConfigModel;

public class ColorSmoothingPanel extends JPanel {

	private final SmoothingConfigModel mSmoothConfig;

	private JCheckBox mEnabledCheck;
	private JLabel mTypeLabel;
	private JComboBox<ColorSmoothingType> mTypeCombo;
	private JLabel mTimeLabel;
	private JSpinner mTimeSpinner;
	private JLabel mUpdateFrequencyLabel;
	private JSpinner mUpdateFrequencySpinner;

	public ColorSmoothingPanel(final SmoothingConfigModel pSmoothConfig) {
		super();
		
		mSmoothConfig = pSmoothConfig;
		
		initialise();
	}
	
	@Override
	@Transient
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension prefSize = super.getPreferredSize();
		return new Dimension(maxSize.width, prefSize.height);
	}

	private void initialise() {
		setBorder(BorderFactory.createTitledBorder("Smoothing"));
		
		mEnabledCheck = new JCheckBox("Enabled");
		mEnabledCheck.setSelected(mSmoothConfig.mSmoothingEnabled.getValue());
		mEnabledCheck.addActionListener(mActionListener);
		add(mEnabledCheck);
		
		mTypeLabel = new JLabel("Type: ");
		add(mTypeLabel);
		
		mTypeCombo = new JComboBox<ColorSmoothingType>(ColorSmoothingType.values());
		mTypeCombo.setSelectedItem(mSmoothConfig.mType.getValue());
		mTypeCombo.addActionListener(mActionListener);
		add(mTypeCombo);
		
		mTimeLabel = new JLabel("Time [ms]: ");
		add(mTimeLabel);
		
		mTimeSpinner = new JSpinner(new SpinnerNumberModel(mSmoothConfig.mTime_ms.getValue(), 1, 600, 100));
		mTimeSpinner.addChangeListener(mChangeListener);
		add(mTimeSpinner);
		
		mUpdateFrequencyLabel = new JLabel("Update Freq. [Hz]: ");
		add(mUpdateFrequencyLabel);
		
		mUpdateFrequencySpinner = new JSpinner(new SpinnerNumberModel(mSmoothConfig.mUpdateFrequency_Hz.getValue(), 1, 100, 1));
		mUpdateFrequencySpinner.addChangeListener(mChangeListener);
		add(mUpdateFrequencySpinner);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mEnabledCheck)
						.addComponent(mTypeLabel)
						.addComponent(mTimeLabel)
						.addComponent(mUpdateFrequencyLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mEnabledCheck)
						.addComponent(mTypeCombo)
						.addComponent(mTimeSpinner)
						.addComponent(mUpdateFrequencySpinner)
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mEnabledCheck)
				.addGroup(layout.createParallelGroup()
						.addComponent(mTypeLabel)
						.addComponent(mTypeCombo)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mTimeLabel)
						.addComponent(mTimeSpinner)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mUpdateFrequencyLabel)
						.addComponent(mUpdateFrequencySpinner)
						));
		
		toggleEnabled(mSmoothConfig.mSmoothingEnabled.getValue());
	}
	
	private void toggleEnabled(boolean pEnabled) {
		mTypeLabel.setEnabled(pEnabled);
		mTypeCombo.setEnabled(pEnabled);
		mTimeLabel.setEnabled(pEnabled);
		mTimeSpinner.setEnabled(pEnabled);
		mUpdateFrequencyLabel.setEnabled(pEnabled);
		mUpdateFrequencySpinner.setEnabled(pEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mSmoothConfig.mSmoothingEnabled.setValue(mEnabledCheck.isSelected());
			mSmoothConfig.mType.setValue((ColorSmoothingType)mTypeCombo.getSelectedItem());
			
			toggleEnabled(mSmoothConfig.mSmoothingEnabled.getValue());
		}
	};
	
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mSmoothConfig.mTime_ms.setValue((Integer)mTimeSpinner.getValue());
			mSmoothConfig.mUpdateFrequency_Hz.setValue((Double)mUpdateFrequencySpinner.getValue());
		}
	};
}
