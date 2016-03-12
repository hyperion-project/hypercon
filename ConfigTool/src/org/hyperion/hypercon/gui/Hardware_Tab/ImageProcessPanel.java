package org.hyperion.hypercon.gui.Hardware_Tab;

import java.awt.Dimension;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.ImageProcessConfig;

public class ImageProcessPanel extends JPanel {
	
	private final ImageProcessConfig mProcessConfig;
	
	private JLabel mHorizontalDepthLabel;
	private JSpinner mHorizontalDepthSpinner;
	private JLabel mVerticalDepthLabel;
	private JSpinner mVerticalDepthSpinner;

	private JLabel mHorizontalGapLabel;
	private JSpinner mHorizontalGapSpinner;
	private JLabel mVerticalGapLabel;
	private JSpinner mVerticalGapSpinner;

	private JLabel mOverlapLabel;
	private JSpinner mOverlapSpinner;
	
	public ImageProcessPanel(ImageProcessConfig pProcessConfig) {
		super();
		
		mProcessConfig = pProcessConfig;
		
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
		setBorder(BorderFactory.createTitledBorder(language.getString("hardware.imageprocess.title"))); //$NON-NLS-1$
		
		mHorizontalDepthLabel = new JLabel(language.getString("hardware.imageprocess.horizontaldepthlabel")); //$NON-NLS-1$
		add(mHorizontalDepthLabel);
		
		mHorizontalDepthSpinner = new JSpinner(new SpinnerNumberModel(mProcessConfig.mHorizontalDepth*100.0, 1.0, 100.0, 1.0));
		mHorizontalDepthSpinner.setToolTipText(language.getString("hardware.imageprocess.horizontaldepthtooltip"));
		mHorizontalDepthSpinner.addChangeListener(mChangeListener);
		add(mHorizontalDepthSpinner);

		mVerticalDepthLabel = new JLabel(language.getString("hardware.imageprocess.verticaldepthlabel")); //$NON-NLS-1$
		add(mVerticalDepthLabel);
		
		mVerticalDepthSpinner = new JSpinner(new SpinnerNumberModel(mProcessConfig.mVerticalDepth*100.0, 1.0, 100.0, 1.0));
		mVerticalDepthSpinner.setToolTipText(language.getString("hardware.imageprocess.verticaldepthtooltip"));
		mVerticalDepthSpinner.addChangeListener(mChangeListener);
		add(mVerticalDepthSpinner);

		mHorizontalGapLabel = new JLabel(language.getString("hardware.imageprocess.horizontalgaplabel")); //$NON-NLS-1$
		add(mHorizontalGapLabel);
		
		mHorizontalGapSpinner = new JSpinner(new SpinnerNumberModel(mProcessConfig.mHorizontalGap*100.0, 0.0, 50.0, 1.0));
		mHorizontalGapSpinner.setToolTipText(language.getString("hardware.imageprocess.horizontalgaptooltip"));
		mHorizontalGapSpinner.addChangeListener(mChangeListener);
		add(mHorizontalGapSpinner);

		mVerticalGapLabel = new JLabel(language.getString("hardware.imageprocess.verticalgaplabel")); //$NON-NLS-1$
		add(mVerticalGapLabel);
		
		mVerticalGapSpinner = new JSpinner(new SpinnerNumberModel(mProcessConfig.mVerticalGap*100.0, 0.0, 50.0, 1.0));
		mVerticalGapSpinner.setToolTipText(language.getString("hardware.imageprocess.verticalgaptooltip"));
		mVerticalGapSpinner.addChangeListener(mChangeListener);
		add(mVerticalGapSpinner);

		mOverlapLabel = new JLabel(language.getString("hardware.imageprocess.overlaplabel")); //$NON-NLS-1$
		add(mOverlapLabel);
		
		mOverlapSpinner = new JSpinner(new SpinnerNumberModel(mProcessConfig.mOverlapFraction*100.0, -100.0, 100.0, 1.0));
		mOverlapSpinner.setToolTipText(language.getString("hardware.imageprocess.overlaptooltip"));
		mOverlapSpinner.addChangeListener(mChangeListener);
		add(mOverlapSpinner);
			
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mHorizontalDepthLabel)
						.addComponent(mVerticalDepthLabel)
						.addComponent(mHorizontalGapLabel)
						.addComponent(mVerticalGapLabel)
						.addComponent(mOverlapLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mHorizontalDepthSpinner)
						.addComponent(mVerticalDepthSpinner)
						.addComponent(mHorizontalGapSpinner)
						.addComponent(mVerticalGapSpinner)
						.addComponent(mOverlapSpinner)
						)
						);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHorizontalDepthLabel)
						.addComponent(mHorizontalDepthSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mVerticalDepthLabel)
						.addComponent(mVerticalDepthSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHorizontalGapLabel)
						.addComponent(mHorizontalGapSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mVerticalGapLabel)
						.addComponent(mVerticalGapSpinner)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mOverlapLabel)
						.addComponent(mOverlapSpinner)
						));
	}

	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			// Update the processing configuration
			mProcessConfig.setHorizontalDepth(((Double)mHorizontalDepthSpinner.getValue())/100.0);
			mProcessConfig.setVerticalDepth(((Double)mVerticalDepthSpinner.getValue())/100.0);
			mProcessConfig.setHorizontalGap(((Double)mHorizontalGapSpinner.getValue())/100.0);
			mProcessConfig.setVerticalGap(((Double)mVerticalGapSpinner.getValue())/100.0);
			mProcessConfig.setOverlapFraction(((Double)mOverlapSpinner.getValue())/100.0);	
			// Notify observers
			mProcessConfig.notifyObservers(this);
	}};  
		
}
