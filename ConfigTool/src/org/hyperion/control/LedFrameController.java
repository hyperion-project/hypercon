package org.hyperion.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.model.LedFrameModel;
import org.mufassa.model.IModelObserver;
import org.mufassa.model.ModelEvent;

public class LedFrameController {

	private final LedFrameModel mFrameModel;
	private final IModelObserver mFrameModelObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			update();
		}
	};
	
	private JPanel mControllerPanel;

	private JLabel mHorizontalCountLabel;
	private JSpinner mHorizontalCountSpinner;
	private JLabel mBottomGapCountLabel;
	private JSpinner mBottomGapCountSpinner;
	
	private JLabel mVerticalCountLabel;
	private JSpinner mVerticalCountSpinner;

	private JLabel mTopCornerLabel;
	private JComboBox<Boolean> mTopCornerCombo;
	private JLabel mBottomCornerLabel;
	private JComboBox<Boolean> mBottomCornerCombo;
	
	private JLabel mDirectionLabel;
	private JComboBox<Boolean> mDirectionCombo;
	
	private JLabel mOffsetLabel;
	private JSpinner mOffsetSpinner;
	
	public LedFrameController(final LedFrameModel pFrameModel) {
		super();
		
		mFrameModel = pFrameModel;
		
		mFrameModel.addObserver(mFrameModelObserver, LedFrameModel.LEDFRAME_EVENT);
	}
	
	private JPanel getControllerPanel() {
		if (mControllerPanel == null) {
			mControllerPanel = new JPanel();
			
			mControllerPanel.setBorder(BorderFactory.createTitledBorder("Construction"));
			
			mTopCornerLabel = new JLabel("Led in top corners");
			mControllerPanel.add(mTopCornerLabel);
			mTopCornerCombo = new JComboBox<>(new Boolean[] {true, false});
			mTopCornerCombo.addActionListener(mActionListener);
			mControllerPanel.add(mTopCornerCombo);
			
			mBottomCornerLabel = new JLabel("Led in bottom corners");
			mControllerPanel.add(mBottomCornerLabel);
			mBottomCornerCombo = new JComboBox<>(new Boolean[] {true, false});
			mBottomCornerCombo.addActionListener(mActionListener);
			mControllerPanel.add(mBottomCornerCombo);
			
			mDirectionLabel = new JLabel("Clockwise:");
			mControllerPanel.add(mDirectionLabel);
			mDirectionCombo = new JComboBox<>(new Boolean[] {true, false});
			mDirectionCombo.addActionListener(mActionListener);
			mControllerPanel.add(mDirectionCombo);

			mHorizontalCountLabel = new JLabel("Horizontal #:");
			mControllerPanel.add(mHorizontalCountLabel);
			mHorizontalCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1024, 1));
			mHorizontalCountSpinner.addChangeListener(mChangeListener);
			mControllerPanel.add(mHorizontalCountSpinner);
			
			mBottomGapCountLabel = new JLabel("Bottom Gap #:");
			mControllerPanel.add(mBottomGapCountLabel);
			mBottomGapCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1024, 1));
			mBottomGapCountSpinner.addChangeListener(mChangeListener);
			mControllerPanel.add(mBottomGapCountSpinner);

			mVerticalCountLabel = new JLabel("Vertical #:");
			mControllerPanel.add(mVerticalCountLabel);
			mVerticalCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1024, 1));
			mVerticalCountSpinner.addChangeListener(mChangeListener);
			mControllerPanel.add(mVerticalCountSpinner);

			mOffsetLabel = new JLabel("1st LED offset");
			mControllerPanel.add(mOffsetLabel);
			mOffsetSpinner = new JSpinner(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
			mOffsetSpinner.addChangeListener(mChangeListener);
			mControllerPanel.add(mOffsetSpinner);

			GroupLayout layout = new GroupLayout(mControllerPanel);
			layout.setAutoCreateGaps(true);
			mControllerPanel.setLayout(layout);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mDirectionLabel)
							.addComponent(mTopCornerLabel)
							.addComponent(mBottomCornerLabel)
							.addComponent(mHorizontalCountLabel)
							.addComponent(mBottomGapCountLabel)
							.addComponent(mVerticalCountLabel)
							.addComponent(mOffsetLabel))
					.addGroup(layout.createParallelGroup()
							.addComponent(mDirectionCombo)
							.addComponent(mTopCornerCombo)
							.addComponent(mBottomCornerCombo)
							.addComponent(mHorizontalCountSpinner)
							.addComponent(mBottomGapCountSpinner)
							.addComponent(mVerticalCountSpinner)
							.addComponent(mOffsetSpinner))
					);
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mDirectionLabel)
							.addComponent(mDirectionCombo))
					.addGroup(layout.createParallelGroup()
							.addComponent(mTopCornerLabel)
							.addComponent(mTopCornerCombo))
					.addGroup(layout.createParallelGroup()
							.addComponent(mBottomCornerLabel)
							.addComponent(mBottomCornerCombo))
					.addGroup(layout.createParallelGroup()
							.addComponent(mHorizontalCountLabel)
							.addComponent(mHorizontalCountSpinner))
					.addGroup(layout.createParallelGroup()
							.addComponent(mVerticalCountLabel)
							.addComponent(mVerticalCountSpinner))
					.addGroup(layout.createParallelGroup()
							.addComponent(mBottomGapCountLabel)
							.addComponent(mBottomGapCountSpinner))
					.addGroup(layout.createParallelGroup()
							.addComponent(mOffsetLabel)
							.addComponent(mOffsetSpinner)));
			
		}
		
		update();
		
		return mControllerPanel;
	}
	
	private boolean mUpdating = false;
	
	private void update() {
		if (mUpdating) {
			return;
		}
		mUpdating = true;
		
		mTopCornerCombo.setSelectedItem(mFrameModel.topCorners.getValue());
		mBottomCornerCombo.setSelectedItem(mFrameModel.bottomCorners.getValue());
		mHorizontalCountSpinner.setValue(mFrameModel.topLedCnt.getValue());
		mBottomGapCountSpinner.setValue(mFrameModel.topLedCnt.getValue()-mFrameModel.bottomLedCnt.getValue());
		mVerticalCountSpinner.setValue(mFrameModel.leftLedCnt.getValue());
		mDirectionCombo.setSelectedItem(mFrameModel.clockwiseDirection.getValue());
		
		mUpdating = false;
	}
	
	private void commit() {
		if (mUpdating) {
			return;
		}
		mUpdating = true;
		
		mFrameModel.topCorners.setValue((boolean) mTopCornerCombo.getSelectedItem());
		mFrameModel.bottomCorners.setValue((boolean)mBottomCornerCombo.getSelectedItem());
		mFrameModel.topLedCnt.setValue((int)mHorizontalCountSpinner.getValue());
		mFrameModel.bottomLedCnt.setValue((int)mHorizontalCountSpinner.getValue() - (int)mBottomGapCountSpinner.getValue());
		mFrameModel.leftLedCnt.setValue((int)mVerticalCountSpinner.getValue());
		mFrameModel.rightLedCnt.setValue((int)mVerticalCountSpinner.getValue());

		mFrameModel.commitEvents();
		
		mUpdating = false;
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			commit();
		}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			commit();
		}
	};
}
