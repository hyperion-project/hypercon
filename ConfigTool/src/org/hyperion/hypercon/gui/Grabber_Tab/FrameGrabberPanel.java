package org.hyperion.hypercon.gui.Grabber_Tab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.MiscConfig;

public class FrameGrabberPanel extends JPanel {

	private final MiscConfig mMiscConfig;
	
	private JCheckBox mFrameGrabberCheck;
	private JLabel mWidthLabel;
	private JSpinner mWidthSpinner;
	private JLabel mHeightLabel;
	private JSpinner mHeightSpinner;
	private JLabel mIntervalLabel;
	private JSpinner mIntervalSpinner;
	private JLabel mPriorityLabel;
	private JSpinner mPrioritySpinner;
	
	public FrameGrabberPanel(final MiscConfig pMiscConfig) {
		super();
		
		mMiscConfig = pMiscConfig;
		
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
		setBorder(BorderFactory.createTitledBorder(language.getString("grabber.intframegrabber.title"))); //$NON-NLS-1$
		
		mFrameGrabberCheck = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mFrameGrabberCheck.setSelected(mMiscConfig.mFrameGrabberEnabled);
		mFrameGrabberCheck.addActionListener(mActionListener);
		add(mFrameGrabberCheck);
		
		mWidthLabel = new JLabel(language.getString("grabber.intframegrabber.widthlabel")); //$NON-NLS-1$
		add(mWidthLabel);
		
		mWidthSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mFrameGrabberWidth, 16, 1024, 1));
		mWidthSpinner.addChangeListener(mChangeListener);
		add(mWidthSpinner);
		
		mHeightLabel = new JLabel(language.getString("grabber.intframegrabber.heightlabel")); //$NON-NLS-1$
		add(mHeightLabel);
		
		mHeightSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mFrameGrabberHeight, 16, 1024, 1));
		mHeightSpinner.addChangeListener(mChangeListener);
		add(mHeightSpinner);
		
		mIntervalLabel = new JLabel(language.getString("grabber.intframegrabber.intervallabel")); //$NON-NLS-1$
		add(mIntervalLabel);
		
		mIntervalSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mFrameGrabberInterval_ms, 10, 60000, 10));
		mIntervalSpinner.addChangeListener(mChangeListener);
		add(mIntervalSpinner);

		mPriorityLabel = new JLabel(language.getString("general.phrase.prioritylabel")); //$NON-NLS-1$
		add(mPriorityLabel);
		
		mPrioritySpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mFrameGrabberPriority, 0, 5000, 1));
		mPrioritySpinner.setToolTipText(language.getString("grabber.intframegrabber.prioritytooltip"));
		mPrioritySpinner.addChangeListener(mChangeListener);
		add(mPrioritySpinner);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mFrameGrabberCheck)
						.addComponent(mWidthLabel)
						.addComponent(mHeightLabel)
						.addComponent(mIntervalLabel)
						.addComponent(mPriorityLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mFrameGrabberCheck)
						.addComponent(mWidthSpinner)
						.addComponent(mHeightSpinner)
						.addComponent(mIntervalSpinner)
						.addComponent(mPrioritySpinner)
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mFrameGrabberCheck)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mWidthLabel)
						.addComponent(mWidthSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHeightLabel)
						.addComponent(mHeightSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mIntervalLabel)
						.addComponent(mIntervalSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mPriorityLabel)
						.addComponent(mPrioritySpinner)
						));

		toggleEnabled(mMiscConfig.mFrameGrabberEnabled);
	}
	
	private void toggleEnabled(boolean pEnabled) {
		mWidthLabel.setEnabled(pEnabled);
		mWidthSpinner.setEnabled(pEnabled);
		mHeightLabel.setEnabled(pEnabled);
		mHeightSpinner.setEnabled(pEnabled);
		mIntervalLabel.setEnabled(pEnabled);
		mIntervalSpinner.setEnabled(pEnabled);
		mPriorityLabel.setEnabled(pEnabled);
		mPrioritySpinner.setEnabled(pEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mMiscConfig.mFrameGrabberEnabled = mFrameGrabberCheck.isSelected();
			
			toggleEnabled(mMiscConfig.mFrameGrabberEnabled);
		}
	}; 
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mMiscConfig.mFrameGrabberWidth = (Integer)mWidthSpinner.getValue();
			mMiscConfig.mFrameGrabberHeight = (Integer)mHeightSpinner.getValue();
			mMiscConfig.mFrameGrabberInterval_ms = (Integer)mIntervalSpinner.getValue();
			mMiscConfig.mFrameGrabberPriority = (Integer)mPrioritySpinner.getValue();
		}
	};
}
