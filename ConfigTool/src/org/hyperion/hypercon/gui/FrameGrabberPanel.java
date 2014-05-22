package org.hyperion.hypercon.gui;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.spec.grabber.DispmanxGrabberModel;

public class FrameGrabberPanel extends JPanel {

	private final DispmanxGrabberModel mGrabberModel;
	
	private JCheckBox mFrameGrabberCheck;
	private JLabel mWidthLabel;
	private JSpinner mWidthSpinner;
	private JLabel mHeightLabel;
	private JSpinner mHeightSpinner;
	private JLabel mIntervalLabel;
	private JSpinner mIntervalSpinner;
	
	public FrameGrabberPanel(final DispmanxGrabberModel pGrabberModel) {
		super();
		
		mGrabberModel = pGrabberModel;
		
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
		setBorder(BorderFactory.createTitledBorder("Frame Grabber"));
		
		mFrameGrabberCheck = new JCheckBox("Enabled");
		mFrameGrabberCheck.setSelected(mGrabberModel.enabled.getValue());
		mFrameGrabberCheck.addActionListener(mActionListener);
		add(mFrameGrabberCheck);
		
		mWidthLabel = new JLabel("Width: ");
		add(mWidthLabel);
		
		mWidthSpinner = new JSpinner(new SpinnerNumberModel(mGrabberModel.width.getValue(), 16, 1024, 8));
		mWidthSpinner.addChangeListener(mChangeListener);
		add(mWidthSpinner);
		
		mHeightLabel = new JLabel("Heigth: ");
		add(mHeightLabel);
		
		mHeightSpinner = new JSpinner(new SpinnerNumberModel(mGrabberModel.height.getValue(), 16, 1024, 8));
		mHeightSpinner.addChangeListener(mChangeListener);
		add(mHeightSpinner);
		
		mIntervalLabel = new JLabel("Interval [ms]:");
		add(mIntervalLabel);
		
		mIntervalSpinner = new JSpinner(new SpinnerNumberModel(mGrabberModel.interval_ms.getValue(), 10, 60000, 10));
		mIntervalSpinner.addChangeListener(mChangeListener);
		add(mIntervalSpinner);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mFrameGrabberCheck)
						.addComponent(mWidthLabel)
						.addComponent(mHeightLabel)
						.addComponent(mIntervalLabel)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mFrameGrabberCheck)
						.addComponent(mWidthSpinner)
						.addComponent(mHeightSpinner)
						.addComponent(mIntervalSpinner)
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(mFrameGrabberCheck)
				.addGroup(layout.createParallelGroup()
						.addComponent(mWidthLabel)
						.addComponent(mWidthSpinner)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mHeightLabel)
						.addComponent(mHeightSpinner)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(mIntervalLabel)
						.addComponent(mIntervalSpinner)
						));

		toggleEnabled(mGrabberModel.enabled.getValue());
	}
	
	private void toggleEnabled(boolean pEnabled) {
		mWidthLabel.setEnabled(pEnabled);
		mWidthSpinner.setEnabled(pEnabled);
		mHeightLabel.setEnabled(pEnabled);
		mHeightSpinner.setEnabled(pEnabled);
		mIntervalLabel.setEnabled(pEnabled);
		mIntervalSpinner.setEnabled(pEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mGrabberModel.enabled.setValue(mFrameGrabberCheck.isSelected());
			
			toggleEnabled(mGrabberModel.enabled.getValue());
		}
	}; 
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mGrabberModel.width.setValue((Integer)mWidthSpinner.getValue());
			mGrabberModel.height.setValue((Integer)mHeightSpinner.getValue());
			mGrabberModel.interval_ms.setValue((Integer)mIntervalSpinner.getValue());
		}
	};
}
