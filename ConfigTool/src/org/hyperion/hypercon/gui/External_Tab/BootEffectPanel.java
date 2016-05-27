package org.hyperion.hypercon.gui.External_Tab;

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
import javax.swing.GroupLayout.Alignment;

import org.hyperion.hypercon.spec.MiscConfig;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.EffectStandard;

public class BootEffectPanel extends JPanel {
	
	private final MiscConfig mMiscConfig;
	
	private JCheckBox mBootEffectEnable;
	
	private JLabel mEffectSetLabel;
	private JComboBox<EffectStandard> mEffectSetCombo;
	
	private JLabel mEffectDurationLabel;
	private JSpinner mEffectDurationSpinner;
	
	private JLabel mEffectPriorityLabel;
	private JSpinner mEffectPrioritySpinner;
	
	private JLabel mEffectColorLabel;
	private JSpinner mEffectColorRSpinner;	
	private JSpinner mEffectColorGSpinner;		
	private JSpinner mEffectColorBSpinner;		
	
	public BootEffectPanel(final MiscConfig config) {

		super();
		mMiscConfig = config;

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
		setBorder(BorderFactory.createTitledBorder(language.getString("external.effect.booteffect.title"))); //$NON-NLS-1$

		mBootEffectEnable = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mBootEffectEnable.setSelected(mMiscConfig.mBootEffectEnabled);
		mBootEffectEnable.addActionListener(mActionListener);
		add(mBootEffectEnable);
	
		mEffectSetLabel = new JLabel(language.getString("external.effect.booteffect.effectlabel")); //$NON-NLS-1$
		add(mEffectSetLabel);
		
		mEffectSetCombo = new JComboBox<>(EffectStandard.values());
		mEffectSetCombo.setSelectedItem(mMiscConfig.mEffectsetCombo);
		mEffectSetCombo.setToolTipText(language.getString("external.effect.booteffect.effecttooltip")); //$NON-NLS-1$
		mEffectSetCombo.addActionListener(mActionListener);
		add(mEffectSetCombo);
	
		mEffectDurationLabel = new JLabel(language.getString("external.effect.booteffect.durationlabel")); //$NON-NLS-1$
		add(mEffectDurationLabel);	

		mEffectDurationSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mEffectDurationspinner*1, 0, null, 100));
		mEffectDurationSpinner.setToolTipText(language.getString("external.effect.booteffect.durationtooltip")); //$NON-NLS-1$
		mEffectDurationSpinner.addChangeListener(mChangeListener);
		add(mEffectDurationSpinner);
		
		mEffectPriorityLabel = new JLabel(language.getString("external.effect.booteffect.prioritylabel")); //$NON-NLS-1$
		add(mEffectPriorityLabel);	
		
		mEffectPrioritySpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mEffectPriorityspinner*1, 0, null, 25));
		mEffectPrioritySpinner.setToolTipText(language.getString("external.effect.booteffect.prioritytooltip")); //$NON-NLS-1$
		mEffectPrioritySpinner.addChangeListener(mChangeListener);
		add(mEffectPrioritySpinner);
		
		mEffectColorLabel = new JLabel(language.getString("external.effect.booteffect.staticcolorlabel")); //$NON-NLS-1$
		add(mEffectColorLabel);	
		
		mEffectColorRSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mEffectColorRspinner*1, 0, 255, 1));
		mEffectColorRSpinner.setToolTipText(language.getString("external.effect.booteffect.staticcolorRtooltip")); //$NON-NLS-1$
		mEffectColorRSpinner.addChangeListener(mChangeListener);
		
		mEffectColorGSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mEffectColorGspinner*1, 0, 255, 1));
		mEffectColorGSpinner.setToolTipText(language.getString("external.effect.booteffect.staticcolorGtooltip")); //$NON-NLS-1$
		mEffectColorGSpinner.addChangeListener(mChangeListener);
		
		mEffectColorBSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mEffectColorBspinner*1, 0, 255, 1));
		mEffectColorBSpinner.setToolTipText(language.getString("external.effect.booteffect.staticcolorBtooltip")); //$NON-NLS-1$
		mEffectColorBSpinner.addChangeListener(mChangeListener);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout
		.createSequentialGroup()					
			.addGroup(layout.createParallelGroup()
					.addComponent(mBootEffectEnable)
					.addComponent(mEffectColorLabel)
					.addComponent(mEffectSetLabel)
					.addComponent(mEffectDurationLabel)
					.addComponent(mEffectPriorityLabel)
					)
			.addGroup(layout.createParallelGroup()
					.addComponent(mBootEffectEnable)
					.addComponent(mEffectSetCombo)
					.addComponent(mEffectDurationSpinner)
					.addComponent(mEffectPrioritySpinner)
						.addGroup(layout.createSequentialGroup()
								.addComponent(mEffectColorRSpinner)
								.addComponent(mEffectColorGSpinner)
								.addComponent(mEffectColorBSpinner)
						)
					)

		);
layout.setVerticalGroup(layout
		.createSequentialGroup()
			.addComponent(mBootEffectEnable)

				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mEffectColorLabel)
						.addComponent(mEffectColorRSpinner)
						.addComponent(mEffectColorGSpinner)
						.addComponent(mEffectColorBSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mEffectSetLabel)
						.addComponent(mEffectSetCombo)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)		
						.addComponent(mEffectDurationLabel)				
						.addComponent(mEffectDurationSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)	
						.addComponent(mEffectPriorityLabel)				
						.addComponent(mEffectPrioritySpinner)
						)
		);
toggleEnabled(mMiscConfig.mBootEffectEnabled);
}
	
	
	
	private void toggleEnabled(boolean pEnabled) {
	
		mEffectSetLabel.setEnabled(pEnabled);	
		mEffectSetCombo.setEnabled(pEnabled);
		mEffectDurationLabel.setEnabled(pEnabled);
		mEffectDurationSpinner.setEnabled(pEnabled);
		mEffectPriorityLabel.setEnabled(pEnabled);
		mEffectPrioritySpinner.setEnabled(pEnabled);
		mEffectColorLabel.setEnabled(pEnabled);
		mEffectColorRSpinner.setEnabled(pEnabled);
		mEffectColorGSpinner.setEnabled(pEnabled);
		mEffectColorBSpinner.setEnabled(pEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {		
	@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(mBootEffectEnable)) {
				mMiscConfig.mBootEffectEnabled = mBootEffectEnable
				.isSelected();
				toggleEnabled(mMiscConfig.mBootEffectEnabled);
			}
			mMiscConfig.mEffectsetCombo = (EffectStandard) mEffectSetCombo.getSelectedItem();
			}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mMiscConfig.mEffectDurationspinner = (int) mEffectDurationSpinner.getValue();
			mMiscConfig.mEffectPriorityspinner = (int) mEffectPrioritySpinner.getValue();
			mMiscConfig.mEffectColorRspinner = (int) mEffectColorRSpinner.getValue();
			mMiscConfig.mEffectColorGspinner = (int) mEffectColorGSpinner.getValue();
			mMiscConfig.mEffectColorBspinner = (int) mEffectColorBSpinner.getValue();
		}
	};
}
