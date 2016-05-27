package org.hyperion.hypercon.gui.Hardware_Tab;

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
import org.hyperion.hypercon.spec.MiscConfig;
import org.hyperion.hypercon.spec.BlackBorderStandard;

public class BlackBorderPanel extends JPanel {
	
	private final MiscConfig mBlackBorderConfig;
	
	private JCheckBox mBlackBorderEnable;
	
	private JLabel mBlackBorderuFrameCntLabel;
	private JSpinner mBlackBorderuFrameCntSpinner;
	
	private JLabel mBlackBorderbFrameCntLabel;
	private JSpinner mBlackBorderbFrameCntSpinner;
	
	private JLabel mBlackBordermInconsistentCntLabel;
	private JSpinner mBlackBordermInconsistentCntSpinner;
	
	private JLabel mBlackBorderbRemoveCntLabel;
	private JSpinner mBlackBorderbRemoveCntSpinner;
		
	private JLabel mBlackBordermLabel;
	private JComboBox<BlackBorderStandard> mBlackBordermCombo;
	
	private JLabel mBlackBorderThresholdLabel;
	private JSpinner mBlackBorderThresholdSpinner;

	public BlackBorderPanel(final MiscConfig config) {

		super();
		mBlackBorderConfig = config;

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
		setBorder(BorderFactory.createTitledBorder(language.getString("hardware.blackborder.title"))); //$NON-NLS-1$
	
		mBlackBorderEnable = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mBlackBorderEnable.setSelected(mBlackBorderConfig.mBlackBorderEnabled);
		mBlackBorderEnable.setToolTipText(language.getString("hardware.blackborder.enabledtooltip")); //$NON-NLS-1$
		mBlackBorderEnable.addActionListener(mActionListener);
		add(mBlackBorderEnable);

		mBlackBorderThresholdLabel = new JLabel(language.getString("hardware.blackborder.treshlabel")); //$NON-NLS-1$
		add(mBlackBorderThresholdLabel);
		
		mBlackBorderThresholdSpinner = new JSpinner(new SpinnerNumberModel(mBlackBorderConfig.mBlackBorderThreshold*100.0, -100.0, 100.0, 1.0));
		mBlackBorderThresholdSpinner.setToolTipText(language.getString("hardware.blackborder.treshtooltip")); //$NON-NLS-1$
		mBlackBorderThresholdSpinner.addChangeListener(mChangeListener);
		add(mBlackBorderThresholdSpinner);

		mBlackBorderuFrameCntLabel = new JLabel(language.getString("hardware.blackborder.uframecntlabel")); //$NON-NLS-1$
		add(mBlackBorderuFrameCntLabel);
		
		mBlackBorderuFrameCntSpinner = new JSpinner(new SpinnerNumberModel(mBlackBorderConfig.mBlackBorderuFrameCnt*1, 300, 900, 1));
		mBlackBorderuFrameCntSpinner.setToolTipText(language.getString("hardware.blackborder.uframecnttooltip")); //$NON-NLS-1$
		mBlackBorderuFrameCntSpinner.addChangeListener(mChangeListener);
		add(mBlackBorderuFrameCntSpinner);
		
		mBlackBorderbFrameCntLabel = new JLabel(language.getString("hardware.blackborder.bframecntlabel")); //$NON-NLS-1$
		add(mBlackBorderbFrameCntLabel);		
		
		mBlackBorderbFrameCntSpinner = new JSpinner(new SpinnerNumberModel(mBlackBorderConfig.mBlackBorderbFrameCnt*1, 25, 100, 1));
		mBlackBorderuFrameCntSpinner.setToolTipText(language.getString("hardware.blackborder.bframecnttooltip")); //$NON-NLS-1$
		mBlackBorderbFrameCntSpinner.addChangeListener(mChangeListener);
		add(mBlackBorderbFrameCntSpinner);
		
		mBlackBordermInconsistentCntLabel = new JLabel(language.getString("hardware.blackborder.maxincostcntlabel")); //$NON-NLS-1$
		add(mBlackBordermInconsistentCntLabel);		
		
		mBlackBordermInconsistentCntSpinner = new JSpinner(new SpinnerNumberModel(mBlackBorderConfig.mBlackBordermInconsistentCnt*1, 0, 100, 1));
		mBlackBordermInconsistentCntSpinner.setToolTipText(language.getString("hardware.blackborder.maxincistcnttooltip")); //$NON-NLS-1$
		mBlackBordermInconsistentCntSpinner.addChangeListener(mChangeListener);
		add(mBlackBordermInconsistentCntSpinner);
		
		mBlackBorderbRemoveCntLabel = new JLabel(language.getString("hardware.blackborder.bremovecntlabel")); //$NON-NLS-1$
		add(mBlackBorderbRemoveCntLabel);
		
		mBlackBorderbRemoveCntSpinner = new JSpinner(new SpinnerNumberModel(mBlackBorderConfig.mBlackBorderbRemoveCnt*1, 0, 10, 1));
		mBlackBorderbRemoveCntSpinner.setToolTipText(language.getString("hardware.blackborder.bremovecnttooltip")); //$NON-NLS-1$
		mBlackBorderbRemoveCntSpinner.addChangeListener(mChangeListener);
		add(mBlackBorderbRemoveCntSpinner);
		
		mBlackBordermLabel = new JLabel(language.getString("hardware.blackborder.modelabel")); //$NON-NLS-1$
		add(mBlackBordermLabel);

		mBlackBordermCombo = new JComboBox<>(BlackBorderStandard.values());
		mBlackBordermCombo.setSelectedItem(mBlackBorderConfig.mBlackbordermCombo);
		mBlackBordermCombo.setToolTipText(language.getString("hardware.blackborder.modetooltip")); //$NON-NLS-1$
		mBlackBordermCombo.addActionListener(mActionListener);
		add(mBlackBordermCombo);
	
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mBlackBorderEnable)
							.addComponent(mBlackBorderThresholdLabel)
//							.addComponent(mBlackBorderuFrameCntLabel)
//							.addComponent(mBlackBorderbFrameCntLabel)
//							.addComponent(mBlackBordermInconsistentCntLabel)
//							.addComponent(mBlackBorderbRemoveCntLabel)
							.addComponent(mBlackBordermLabel)
						)
					.addGroup(layout.createParallelGroup()
							.addComponent(mBlackBorderEnable)
							.addComponent(mBlackBorderThresholdSpinner)
//							.addComponent(mBlackBorderuFrameCntSpinner)
//							.addComponent(mBlackBorderbFrameCntSpinner)
//							.addComponent(mBlackBordermInconsistentCntSpinner)
//							.addComponent(mBlackBorderbRemoveCntSpinner)
							.addComponent(mBlackBordermCombo)
						)
						);
		layout.setVerticalGroup(layout
				.createSequentialGroup()
					.addComponent(mBlackBorderEnable)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mBlackBorderThresholdLabel)
								.addComponent(mBlackBorderThresholdSpinner)
								)
//						.addGroup(layout.createParallelGroup(Alignment.CENTER)		
//								.addComponent(mBlackBorderuFrameCntLabel)				
//								.addComponent(mBlackBorderuFrameCntSpinner)
//								)
//						.addGroup(layout.createParallelGroup(Alignment.CENTER)		
//								.addComponent(mBlackBorderbFrameCntLabel)				
//								.addComponent(mBlackBorderbFrameCntSpinner)
//								)
//						.addGroup(layout.createParallelGroup(Alignment.CENTER)		
//								.addComponent(mBlackBordermInconsistentCntLabel)				
//								.addComponent(mBlackBordermInconsistentCntSpinner)
//								)
//						.addGroup(layout.createParallelGroup(Alignment.CENTER)		
//								.addComponent(mBlackBorderbRemoveCntLabel)				
//								.addComponent(mBlackBorderbRemoveCntSpinner)
//								)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)		
								.addComponent(mBlackBordermLabel)				
								.addComponent(mBlackBordermCombo)
							));
		toggleEnabled(mBlackBorderConfig.mBlackBorderEnabled);
	}
	
	private void toggleEnabled(boolean pEnabled) {
	
		mBlackBorderThresholdLabel.setEnabled(pEnabled);	
		mBlackBorderThresholdSpinner.setEnabled(pEnabled);
		mBlackBorderuFrameCntLabel.setEnabled(pEnabled);
		mBlackBorderuFrameCntSpinner.setEnabled(pEnabled);
		mBlackBorderbFrameCntLabel.setEnabled(pEnabled);
		mBlackBorderbFrameCntSpinner.setEnabled(pEnabled);
		mBlackBordermInconsistentCntLabel.setEnabled(pEnabled);
		mBlackBordermInconsistentCntSpinner.setEnabled(pEnabled);
		mBlackBorderbRemoveCntLabel.setEnabled(pEnabled);
		mBlackBorderbRemoveCntSpinner.setEnabled(pEnabled);
		mBlackBordermLabel.setEnabled(pEnabled);
		mBlackBordermCombo.setEnabled(pEnabled);
		}
	
	private final ActionListener mActionListener = new ActionListener() {		
	@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(mBlackBorderEnable)) {
				mBlackBorderConfig.mBlackBorderEnabled = mBlackBorderEnable
				.isSelected();
				toggleEnabled(mBlackBorderConfig.mBlackBorderEnabled);
			}
			mBlackBorderConfig.mBlackbordermCombo = (BlackBorderStandard) mBlackBordermCombo
					.getSelectedItem();
			}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mBlackBorderConfig.mBlackBorderThreshold = (Double) mBlackBorderThresholdSpinner.getValue()/100.0;
			mBlackBorderConfig.mBlackBorderuFrameCnt = (Integer) mBlackBorderuFrameCntSpinner.getValue();    
			mBlackBorderConfig.mBlackBorderbFrameCnt = (Integer) mBlackBorderbFrameCntSpinner.getValue();
			mBlackBorderConfig.mBlackBordermInconsistentCnt = (Integer) mBlackBordermInconsistentCntSpinner.getValue();
			mBlackBorderConfig.mBlackBorderbRemoveCnt = (Integer) mBlackBorderbRemoveCntSpinner.getValue();
 		

			}
	};
}
