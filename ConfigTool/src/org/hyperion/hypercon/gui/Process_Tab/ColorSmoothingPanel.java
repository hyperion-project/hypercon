package org.hyperion.hypercon.gui.Process_Tab;

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
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.ColorConfig;
import org.hyperion.hypercon.spec.ColorSmoothingType;

public class ColorSmoothingPanel extends JPanel {

	private final ColorConfig mColorConfig;

	private JCheckBox mEnabledCheck;
	private JLabel mTypeLabel;
	private JComboBox<ColorSmoothingType> mTypeCombo;
	private JLabel mTimeLabel;
	private JSpinner mTimeSpinner;
	private JLabel mUpdateFrequencyLabel;
	private JSpinner mUpdateFrequencySpinner;
	private JLabel mUpdateDelayLabel;
	private JSpinner mUpdateDelaySpinner;

	public ColorSmoothingPanel(final ColorConfig pColorConfig) {
		super();
		
		mColorConfig = pColorConfig;
		
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
		setBorder(BorderFactory.createTitledBorder(language.getString("process.smoothing.title"))); //$NON-NLS-1$
		
		mEnabledCheck = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mEnabledCheck.setSelected(mColorConfig.mSmoothingEnabled);
		mEnabledCheck.addActionListener(mActionListener);
		add(mEnabledCheck);
		
		mTypeLabel = new JLabel(language.getString("process.smoothing.typlabel")); //$NON-NLS-1$
		add(mTypeLabel);
		
		mTypeCombo = new JComboBox<>(ColorSmoothingType.values());
		mTypeCombo.setSelectedItem(mColorConfig.mSmoothingType);
		mTypeCombo.addActionListener(mActionListener);
		add(mTypeCombo);
		
		mTimeLabel = new JLabel(language.getString("process.smoothing.timelabel")); //$NON-NLS-1$
		add(mTimeLabel);
		
		mTimeSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mSmoothingTime_ms, 1, 600, 25));
		mTimeSpinner.addChangeListener(mChangeListener);
		add(mTimeSpinner);
		
		mUpdateFrequencyLabel = new JLabel(language.getString("process.smoothing.updatefreqlabel")); //$NON-NLS-1$
		add(mUpdateFrequencyLabel);
		
		mUpdateFrequencySpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mSmoothingUpdateFrequency_Hz, 1, 100, 1));
		mUpdateFrequencySpinner.addChangeListener(mChangeListener);
		add(mUpdateFrequencySpinner);

		mUpdateDelayLabel = new JLabel(language.getString("process.smoothing.updatedelaylabel")); //$NON-NLS-1$
		add(mUpdateDelayLabel);
		
		mUpdateDelaySpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mUpdateDelay, 0, 2048, 1));
		mUpdateDelaySpinner.setToolTipText(language.getString("process.smoothing.updatedelaytooltip")); //$NON-NLS-1$
		mUpdateDelaySpinner.addChangeListener(mChangeListener);
		add(mUpdateDelaySpinner);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mEnabledCheck)
						.addComponent(mTypeLabel)
						.addComponent(mTimeLabel)
						.addComponent(mUpdateFrequencyLabel)
						.addComponent(mUpdateDelayLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mEnabledCheck)
						.addComponent(mTypeCombo)
						.addComponent(mTimeSpinner)
						.addComponent(mUpdateFrequencySpinner)
						.addComponent(mUpdateDelaySpinner)
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mEnabledCheck)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mTypeLabel)
						.addComponent(mTypeCombo)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mTimeLabel)
						.addComponent(mTimeSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mUpdateFrequencyLabel)
						.addComponent(mUpdateFrequencySpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mUpdateDelayLabel)
						.addComponent(mUpdateDelaySpinner)
						));
		
		toggleEnabled(mColorConfig.mSmoothingEnabled);
	}
	
	private void toggleEnabled(boolean pEnabled) {
		mTypeLabel.setEnabled(pEnabled);
		mTypeCombo.setEnabled(pEnabled);
		mTimeLabel.setEnabled(pEnabled);
		mTimeSpinner.setEnabled(pEnabled);
		mUpdateFrequencyLabel.setEnabled(pEnabled);
		mUpdateFrequencySpinner.setEnabled(pEnabled);
		mUpdateDelayLabel.setEnabled(pEnabled);
		mUpdateDelaySpinner.setEnabled(pEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mColorConfig.mSmoothingEnabled = mEnabledCheck.isSelected();
			mColorConfig.mSmoothingType = (ColorSmoothingType)mTypeCombo.getSelectedItem();
			
			toggleEnabled(mColorConfig.mSmoothingEnabled);
		}
	};
	
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mColorConfig.mSmoothingTime_ms = (Integer)mTimeSpinner.getValue();
			mColorConfig.mSmoothingUpdateFrequency_Hz = (Double)mUpdateFrequencySpinner.getValue();
			mColorConfig.mUpdateDelay = (Integer)mUpdateDelaySpinner.getValue();
		}
	};
}
