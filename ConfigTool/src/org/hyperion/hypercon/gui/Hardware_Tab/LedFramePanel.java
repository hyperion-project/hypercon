package org.hyperion.hypercon.gui.Hardware_Tab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.LedFrameConstruction;
import org.hyperion.hypercon.spec.LedFrameConstruction.Direction;

public class LedFramePanel extends JPanel {
	
	private final LedFrameConstruction mLedFrameSpec;

//	private JLabel mTopCountLabel;
//	private JSpinner mTopCountSpinner;
	
//	private JLabel mBottomCountLabel;
//	private JSpinner mBottomCountSpinner;
	private JLabel mHorizontalCountLabel;
	private JSpinner mHorizontalCountSpinner;
	
	private JLabel mBottomGapCountLabel;
	private JSpinner mBottomGapCountSpinner;
	
	private JLabel mRightCountLabel;
	private JSpinner mRightCountSpinner;

	private JLabel mLeftCountLabel;
	private JSpinner mLeftCountSpinner;	
	
	private JLabel mTopLeftCornerLabel;
	private JCheckBox mTopLeftCornerCheck;
	private JLabel mTopRightCornerLabel;
	private JCheckBox mTopRightCornerCheck;
	private JLabel mBottomLeftCornerLabel;
	private JCheckBox mBottomLeftCornerCheck;
	private JLabel mBottomRightCornerLabel;
	private JCheckBox mBottomRightCornerCheck;	

	
	private JLabel mDirectionLabel;
	private JComboBox<LedFrameConstruction.Direction> mDirectionCombo;
	
	private JLabel mOffsetLabel;
	private JSpinner mOffsetSpinner;
	
	public LedFramePanel(LedFrameConstruction ledFrameSpec) {
		super();
		
		mLedFrameSpec = ledFrameSpec;
		
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
		setBorder(BorderFactory.createTitledBorder(language.getString("hardware.construction.title"))); //$NON-NLS-1$		
		
		mTopLeftCornerLabel = new JLabel(language.getString("hardware.construction.ledtopleftcornerlabel")); //$NON-NLS-1$
		add(mTopLeftCornerLabel);
		mTopLeftCornerCheck = new JCheckBox();
		mTopLeftCornerCheck.setSelected(mLedFrameSpec.topleftCorner);
		mTopLeftCornerCheck.setToolTipText(language.getString("hardware.construction.ledtopleftcornertooltip")); //$NON-NLS-1$
		mTopLeftCornerCheck.addActionListener(mActionListener);
		add(mTopLeftCornerCheck);
		
		mTopRightCornerLabel = new JLabel(language.getString("hardware.construction.ledtoprightcornerlabel")); //$NON-NLS-1$
		add(mTopRightCornerLabel);
		mTopRightCornerCheck = new JCheckBox();
		mTopRightCornerCheck.setSelected(mLedFrameSpec.toprightCorner);
		mTopRightCornerCheck.setToolTipText(language.getString("hardware.construction.ledtoprightcornertooltip")); //$NON-NLS-1$
		mTopRightCornerCheck.addActionListener(mActionListener);
		add(mTopRightCornerCheck);
		
		mBottomLeftCornerLabel = new JLabel(language.getString("hardware.construction.ledbottomleftcornerlabel")); //$NON-NLS-1$
		add(mBottomLeftCornerLabel);
		mBottomLeftCornerCheck = new JCheckBox();
		mBottomLeftCornerCheck.setSelected(mLedFrameSpec.bottomleftCorner);
		mBottomLeftCornerCheck.setToolTipText(language.getString("hardware.construction.ledbottomleftcornertooltip")); //$NON-NLS-1$
		mBottomLeftCornerCheck.addActionListener(mActionListener);
		add(mBottomLeftCornerCheck);

		mBottomRightCornerLabel = new JLabel(language.getString("hardware.construction.ledbottomrightcornerlabel")); //$NON-NLS-1$
		add(mBottomRightCornerLabel);
		mBottomRightCornerCheck = new JCheckBox();
		mBottomRightCornerCheck.setSelected(mLedFrameSpec.bottomrightCorner);
		mBottomRightCornerCheck.setToolTipText(language.getString("hardware.construction.ledbottomrightcornertooltip")); //$NON-NLS-1$
		mBottomRightCornerCheck.addActionListener(mActionListener);
		add(mBottomRightCornerCheck);
		

		
		mDirectionLabel = new JLabel(language.getString("hardware.construction.directionlabel")); //$NON-NLS-1$
		add(mDirectionLabel);
		mDirectionCombo = new JComboBox<>(LedFrameConstruction.Direction.values());
		mDirectionCombo.setSelectedItem(mLedFrameSpec.clockwiseDirection?Direction.clockwise:Direction.counter_clockwise);
		mDirectionCombo.setToolTipText(language.getString("hardware.construction.directiontooltip")); //$NON-NLS-1$
		mDirectionCombo.addActionListener(mActionListener);
		add(mDirectionCombo);

//		mTopCountLabel = new JLabel(language.getString("hardware.construction.ledtoplabel")); //$NON-NLS-1$
//		add(mTopCountLabel);
//		mTopCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.topLedCnt, 0, 1024, 1));
//		mTopCountSpinner.setToolTipText(language.getString("hardware.construction.ledtoptooltip")); //$NON-NLS-1$
//		mTopCountSpinner.addChangeListener(mChangeListener);
//		add(mTopCountSpinner);
		
//		mBottomCountLabel = new JLabel(language.getString("hardware.construction.ledbottomlabel")); //$NON-NLS-1$
//		add(mBottomCountLabel);
//		mBottomCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.bottomLedCnt, 0, 1024, 1));
//		mBottomCountSpinner.setToolTipText(language.getString("hardware.construction.ledbottomtooltip")); //$NON-NLS-1$
//		mBottomCountSpinner.addChangeListener(mChangeListener);
//		add(mBottomCountSpinner);
		
		mBottomGapCountLabel = new JLabel(language.getString("hardware.construction.bottomgaplabel")); //$NON-NLS-1$
		add(mBottomGapCountLabel);
		mBottomGapCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.topLedCnt - mLedFrameSpec.bottomLedCnt, 0, 1024, 1));
//		mBottomGapCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.bottomGapCnt, 0, 1024, 2));
		mBottomGapCountSpinner.setToolTipText(language.getString("hardware.construction.bottomgaptooltip")); //$NON-NLS-1$
		mBottomGapCountSpinner.addChangeListener(mChangeListener);
		add(mBottomGapCountSpinner);

		mHorizontalCountLabel = new JLabel(language.getString("hardware.construction.horizontallabel")); //$NON-NLS-1$
		add(mHorizontalCountLabel);
		mHorizontalCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.topLedCnt, 0, 1024, 1));
		mHorizontalCountSpinner.setToolTipText(language.getString("hardware.construction.horizontaltooltip")); //$NON-NLS-1$
		mHorizontalCountSpinner.addChangeListener(mChangeListener);
		add(mHorizontalCountSpinner);
		
		
		mRightCountLabel = new JLabel(language.getString("hardware.construction.ledrightlabel")); //$NON-NLS-1$
		add(mRightCountLabel);
		mRightCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.rightLedCnt, 0, 1024, 1));
		mRightCountSpinner.setToolTipText(language.getString("hardware.construction.ledrighttooltip")); //$NON-NLS-1$
		mRightCountSpinner.addChangeListener(mChangeListener);
		add(mRightCountSpinner);

		mLeftCountLabel = new JLabel(language.getString("hardware.construction.ledleftlabel")); //$NON-NLS-1$
		add(mLeftCountLabel);
		mLeftCountSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.leftLedCnt, 0, 1024, 1));
		mLeftCountSpinner.setToolTipText(language.getString("hardware.construction.ledlefttooltip")); //$NON-NLS-1$
		mLeftCountSpinner.addChangeListener(mChangeListener);
		add(mLeftCountSpinner);
		
		mOffsetLabel = new JLabel(language.getString("hardware.construction.ledoffsetlabel")); //$NON-NLS-1$
		add(mOffsetLabel);
		mOffsetSpinner = new JSpinner(new SpinnerNumberModel(mLedFrameSpec.firstLedOffset, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
		mOffsetSpinner.setToolTipText(language.getString("hardware.construction.ledoffsettooltip")); //$NON-NLS-1$
		mOffsetSpinner.addChangeListener(mChangeListener);
		add(mOffsetSpinner);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);

		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(mTopLeftCornerLabel)
						.addComponent(mBottomLeftCornerLabel)
						)
					.addGroup(layout.createParallelGroup()
						.addComponent(mTopLeftCornerCheck)
						.addComponent(mBottomLeftCornerCheck)
						)
					.addGap(15)
					.addGroup(layout.createParallelGroup()
						.addComponent(mTopRightCornerLabel)
						.addComponent(mBottomRightCornerLabel)
						)
					.addGroup(layout.createParallelGroup()
						.addComponent(mTopRightCornerCheck)
						.addComponent(mBottomRightCornerCheck)
						))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()						
								.addComponent(mDirectionLabel)
								.addComponent(mHorizontalCountLabel)
								.addComponent(mLeftCountLabel)
								.addComponent(mRightCountLabel)
								.addComponent(mBottomGapCountLabel)
								.addComponent(mOffsetLabel))
							.addGroup(layout.createParallelGroup()
								.addComponent(mDirectionCombo)
								.addComponent(mHorizontalCountSpinner)
								.addComponent(mLeftCountSpinner)
								.addComponent(mRightCountSpinner)
								.addComponent(mBottomGapCountSpinner)
								.addComponent(mOffsetSpinner))
				));
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mTopLeftCornerLabel)
						.addComponent(mTopLeftCornerCheck)
						.addComponent(mTopRightCornerLabel)
						.addComponent(mTopRightCornerCheck)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mBottomLeftCornerLabel)
						.addComponent(mBottomLeftCornerCheck)
						.addComponent(mBottomRightCornerLabel)
						.addComponent(mBottomRightCornerCheck)
						)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mDirectionLabel)
						.addComponent(mDirectionCombo))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mHorizontalCountLabel)
						.addComponent(mHorizontalCountSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mLeftCountLabel)
						.addComponent(mLeftCountSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mRightCountLabel)
						.addComponent(mRightCountSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mBottomGapCountLabel)
						.addComponent(mBottomGapCountSpinner))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mOffsetLabel)
						.addComponent(mOffsetSpinner))));
		
	}
	
	void updateLedConstruction() {
		mLedFrameSpec.topleftCorner    = mTopLeftCornerCheck.isSelected();
		mLedFrameSpec.toprightCorner = mTopRightCornerCheck.isSelected();
		mLedFrameSpec.bottomleftCorner = mBottomLeftCornerCheck.isSelected();
		mLedFrameSpec.bottomrightCorner = mBottomRightCornerCheck.isSelected();
		
		mLedFrameSpec.clockwiseDirection = ((LedFrameConstruction.Direction)mDirectionCombo.getSelectedItem()) == LedFrameConstruction.Direction.clockwise;
		mLedFrameSpec.firstLedOffset = (Integer)mOffsetSpinner.getValue();
		
		mLedFrameSpec.topLedCnt = (Integer)mHorizontalCountSpinner.getValue();
		mLedFrameSpec.bottomLedCnt = Math.max(0, mLedFrameSpec.topLedCnt - (Integer)mBottomGapCountSpinner.getValue());
		mLedFrameSpec.rightLedCnt = (Integer)mRightCountSpinner.getValue();
		mLedFrameSpec.leftLedCnt  = (Integer)mLeftCountSpinner.getValue();
		
		mLedFrameSpec.setChanged();
		mLedFrameSpec.notifyObservers();
		mBottomGapCountSpinner.setValue(mLedFrameSpec.topLedCnt - mLedFrameSpec.bottomLedCnt);
//		mBottomGapCountSpinner.setValue(mLedFrameSpec.bottomGapCnt);
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateLedConstruction();
		}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			updateLedConstruction();
		}
	};

}
