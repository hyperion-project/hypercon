package org.hyperion.hypercon.gui.Grabber_Tab;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.DimensionModes;
import org.hyperion.hypercon.spec.Grabberv4l2Config;
import org.hyperion.hypercon.spec.VideoStandard;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

/**
 * Created by Fabian on 14.02.2015.
 */
public class Grabberv4l2Panel extends JPanel {

	private final Grabberv4l2Config mGrabberV4l2Config;

	private final double thresholdSpinnerIncrease = 0.05;
	private final int cropSpinnerIncrease = 1;
	private final int widthHeightSpinnerIncrease = 1;

	private JCheckBox mGrabberV4l2Enable;

	private JLabel mDeviceLabel;
	private JTextField mDevice;

	private JLabel mInputLabel;
	private JSpinner mInput;

	private JLabel mStandardLabel;
	private JComboBox<VideoStandard> mStandard;

	private JLabel mWidthLabel;
	private JSpinner mWidth;

	private JLabel mHeightLabel;
	private JSpinner mHeight;

	private JLabel mFrameDecimationLabel;
	private JSpinner mFrameDecimation;

	private JLabel mSizeDecimationLabel;
	private JSpinner mSizeDecimation;

	private JLabel mPriorityLabel;
	private JSpinner mPriority;

	private JLabel mModeLabel;
	private JComboBox<DimensionModes> mMode;

	private JLabel mCropLeftLabel;
	private JSpinner mCropLeft;

	private JLabel mCropRightLabel;
	private JSpinner mCropRight;

	private JLabel mCropTopLabel;
	private JSpinner mCropTop;

	private JLabel mCropBottomLabelLabel;
	private JSpinner mCropBottom;

	private JLabel mRedSignalThresholdLabel;
	private JSpinner mRedSignalThreshold;

	private JLabel mGreenSignalThresholdLabel;
	private JSpinner mGreenSignalThreshold;

	private JLabel mBlueSignalThresholdLabel;
	private JSpinner mBlueSignalThreshold;

	public Grabberv4l2Panel(final Grabberv4l2Config config) {

		super();
		mGrabberV4l2Config = config;

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
		setBorder(BorderFactory.createTitledBorder(language.getString("grabber.extgrabber.title"))); //$NON-NLS-1$

		mGrabberV4l2Enable = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mGrabberV4l2Enable.setSelected(mGrabberV4l2Config.mGrabberv4l2Enabled);
		mGrabberV4l2Enable.addActionListener(mActionListener);
		add(mGrabberV4l2Enable);

		mDeviceLabel = new JLabel(language.getString("grabber.extgrabber.devicelabel")); //$NON-NLS-1$
		add(mDeviceLabel);
		mDevice = new JTextField(mGrabberV4l2Config.mDevice);
		mDevice.setToolTipText(language.getString("grabber.extgrabber.devicetooltip")); //$NON-NLS-1$
		mDevice.getDocument().addDocumentListener(mDocumentListener);
		add(mDevice);

		mInputLabel = new JLabel(language.getString("grabber.extgrabber.inputlabel")); //$NON-NLS-1$
		add(mInputLabel);
		mInput = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mInput,
				0, Integer.MAX_VALUE, 1));
		mInput.addChangeListener(mChangeListener);
		mInput.setToolTipText(language.getString("grabber.extgrabber.inputooltip")); //$NON-NLS-1$
		add(mInput);

		mStandardLabel = new JLabel(language.getString("grabber.extgrabber.videostandardlabel")); //$NON-NLS-1$
		add(mStandardLabel);
		mStandard = new JComboBox<>(VideoStandard.values());
		mStandard.setSelectedItem(mGrabberV4l2Config.mStandard);
		mStandard.addActionListener(mActionListener);
		add(mStandard);

		mWidthLabel = new JLabel(language.getString("grabber.extgrabber.widthlabel")); //$NON-NLS-1$
		add(mWidthLabel);
		mWidth = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mWidth,
				-1, Integer.MAX_VALUE, widthHeightSpinnerIncrease));
		mWidth.addChangeListener(mChangeListener);
		mWidth.setToolTipText(language.getString("grabber.extgrabber.widthtooltip")); //$NON-NLS-1$
		add(mWidth);

		mHeightLabel = new JLabel(language.getString("grabber.extgrabber.heightlabel")); //$NON-NLS-1$
		add(mHeightLabel);
		mHeight = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mHeight, -1, Integer.MAX_VALUE,
				widthHeightSpinnerIncrease));
		mHeight.addChangeListener(mChangeListener);
		mHeight.setToolTipText(language.getString("grabber.extgrabber.heighttooltip")); //$NON-NLS-1$
		add(mHeight);

		mFrameDecimationLabel = new JLabel(language.getString("grabber.extgrabber.framedezilabel")); //$NON-NLS-1$
		add(mFrameDecimationLabel);
		mFrameDecimation = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mFrameDecimation, 0, Integer.MAX_VALUE, 1));
		mFrameDecimation.addChangeListener(mChangeListener);
		mFrameDecimation.setToolTipText(language.getString("grabber.extgrabber.framedezitooltip")); //$NON-NLS-1$
		add(mFrameDecimation);

		mSizeDecimationLabel = new JLabel(language.getString("grabber.extgrabber.sizelabel")); //$NON-NLS-1$
		add(mSizeDecimationLabel);
		mSizeDecimation = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mSizeDecimation, 0, Integer.MAX_VALUE, 1));
		mSizeDecimation.addChangeListener(mChangeListener);
		mSizeDecimation.setToolTipText(language.getString("grabber.extgrabber.sizetooltip")); //$NON-NLS-1$
		add(mSizeDecimation);

		mPriorityLabel = new JLabel(language.getString("grabber.extgrabber.prioritylabel")); //$NON-NLS-1$
		mPriority = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mPriority, 0, Integer.MAX_VALUE, 1));
		mPriority.addChangeListener(mChangeListener);
		mPriority.setToolTipText(language.getString("grabber.extgrabber.prioritytooltip")); //$NON-NLS-1$
		add(mPriority);

		mModeLabel = new JLabel(language.getString("grabber.extgrabber.3dmodelabel")); //$NON-NLS-1$
		add(mModeLabel);
		mMode = new JComboBox<>(DimensionModes.values());
		mMode.setSelectedItem(mGrabberV4l2Config.mMode);
		mMode.addActionListener(mActionListener);
		add(mMode);

		mCropLeftLabel = new JLabel(language.getString("grabber.extgrabber.cropleftlabel")); //$NON-NLS-1$
		add(mCropLeftLabel);
		mCropLeft = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mCropLeft, 0, 100, cropSpinnerIncrease));
		mCropLeft.addChangeListener(mChangeListener);
		mCropLeft.setToolTipText(language.getString("grabber.extgrabber.croplefttooltip")); //$NON-NLS-1$
		add(mCropLeft);

		mCropRightLabel = new JLabel(language.getString("grabber.extgrabber.croprightlabel")); //$NON-NLS-1$
		add(mCropRightLabel);
		mCropRight = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mCropRight, 0, 100, cropSpinnerIncrease));
		mCropRight.addChangeListener(mChangeListener);
		mCropRight.setToolTipText(language.getString("grabber.extgrabber.croprighttooltip")); //$NON-NLS-1$
		add(mCropRight);

		mCropTopLabel = new JLabel(language.getString("grabber.extgrabber.croptoplabel")); //$NON-NLS-1$
		add(mCropTopLabel);
		mCropTop = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mCropTop, 0, 100, cropSpinnerIncrease));
		mCropTop.addChangeListener(mChangeListener);
		mCropTop.setToolTipText(language.getString("grabber.extgrabber.croptoptooltip")); //$NON-NLS-1$
		add(mCropTop);

		mCropBottomLabelLabel = new JLabel(language.getString("grabber.extgrabber.cropbottomlabel")); //$NON-NLS-1$
		add(mCropBottomLabelLabel);
		mCropBottom = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mCropBottom, 0, 100, cropSpinnerIncrease));
		mCropBottom.addChangeListener(mChangeListener);
		mCropBottom.setToolTipText(language.getString("grabber.extgrabber.cropbottomtooltip")); //$NON-NLS-1$
		add(mCropBottom);

		mRedSignalThresholdLabel = new JLabel(language.getString("grabber.extgrabber.redsignaltreshlabel")); //$NON-NLS-1$
		add(mRedSignalThresholdLabel);
		mRedSignalThreshold = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mRedSignalThreshold, 0, 1,
				thresholdSpinnerIncrease));
		mRedSignalThreshold.addChangeListener(mChangeListener);
		mRedSignalThreshold
				.setToolTipText(language.getString("grabber.extgrabber.redsignaltreshtooltip")); //$NON-NLS-1$
		add(mRedSignalThreshold);

		mGreenSignalThresholdLabel = new JLabel(language.getString("grabber.extgrabber.greensignaltreshlabel")); //$NON-NLS-1$
		add(mGreenSignalThresholdLabel);
		mGreenSignalThreshold = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mGreenSignalThreshold, 0, 1,
				thresholdSpinnerIncrease));
		mGreenSignalThreshold.addChangeListener(mChangeListener);
		mGreenSignalThreshold
				.setToolTipText(language.getString("grabber.extgrabber.greensignaltreshtooltip")); //$NON-NLS-1$
		add(mGreenSignalThreshold);

		mBlueSignalThresholdLabel = new JLabel(language.getString("grabber.extgrabber.bluesignaltreshlabel")); //$NON-NLS-1$
		add(mBlueSignalThresholdLabel);
		mBlueSignalThreshold = new JSpinner(new SpinnerNumberModel(
				mGrabberV4l2Config.mBlueSignalThreshold, 0, 1,
				thresholdSpinnerIncrease));
		mBlueSignalThreshold.addChangeListener(mChangeListener);
		mBlueSignalThreshold
				.setToolTipText(language.getString("grabber.extgrabber.bluesignaltreshtooltip")); //$NON-NLS-1$
		add(mBlueSignalThreshold);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup()
								.addComponent(mGrabberV4l2Enable)
								.addComponent(mDeviceLabel)
								.addComponent(mInputLabel)
								.addComponent(mStandardLabel)
								.addComponent(mWidthLabel)
								.addComponent(mHeightLabel)
								.addComponent(mFrameDecimationLabel)
								.addComponent(mSizeDecimationLabel)
								.addComponent(mPriorityLabel)
								.addComponent(mModeLabel)
								.addComponent(mCropLeftLabel)
								.addComponent(mCropRightLabel)
								.addComponent(mCropTopLabel)
								.addComponent(mCropBottomLabelLabel)
								.addComponent(mRedSignalThresholdLabel)
								.addComponent(mGreenSignalThresholdLabel)
								.addComponent(mBlueSignalThresholdLabel))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(mGrabberV4l2Enable)
								.addComponent(mDevice).addComponent(mInput)
								.addComponent(mStandard).addComponent(mWidth)
								.addComponent(mHeight)
								.addComponent(mFrameDecimation)
								.addComponent(mSizeDecimation)
								.addComponent(mPriority).addComponent(mMode)
								.addComponent(mCropLeft)
								.addComponent(mCropRight)
								.addComponent(mCropTop)
								.addComponent(mCropBottom)
								.addComponent(mRedSignalThreshold)
								.addComponent(mGreenSignalThreshold)
								.addComponent(mBlueSignalThreshold)));
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addComponent(mGrabberV4l2Enable)
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mDeviceLabel)
								.addComponent(mDevice))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mInputLabel)
								.addComponent(mInput))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mStandardLabel)
								.addComponent(mStandard))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mWidthLabel)
								.addComponent(mWidth))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mHeightLabel)
								.addComponent(mHeight))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mFrameDecimationLabel)
								.addComponent(mFrameDecimation))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mSizeDecimationLabel)
								.addComponent(mSizeDecimation))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mPriorityLabel)
								.addComponent(mPriority))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mModeLabel)
								.addComponent(mMode))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mCropLeftLabel)
								.addComponent(mCropLeft))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mCropRightLabel)
								.addComponent(mCropRight))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mCropTopLabel)
								.addComponent(mCropTop))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mCropBottomLabelLabel)
								.addComponent(mCropBottom))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mRedSignalThresholdLabel)
								.addComponent(mRedSignalThreshold))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mGreenSignalThresholdLabel)
								.addComponent(mGreenSignalThreshold))
				.addGroup(
						layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mBlueSignalThresholdLabel)
								.addComponent(mBlueSignalThreshold))

		);

		toggleEnabled(mGrabberV4l2Config.mGrabberv4l2Enabled);

	}

	private void toggleEnabled(boolean pEnabled) {
		mDeviceLabel.setEnabled(pEnabled);
		mDevice.setEnabled(pEnabled);
		mInputLabel.setEnabled(pEnabled);
		mInput.setEnabled(pEnabled);
		mStandardLabel.setEnabled(pEnabled);
		mStandard.setEnabled(pEnabled);
		mWidthLabel.setEnabled(pEnabled);
		mWidth.setEnabled(pEnabled);
		mHeightLabel.setEnabled(pEnabled);
		mHeight.setEnabled(pEnabled);
		mFrameDecimationLabel.setEnabled(pEnabled);
		mFrameDecimation.setEnabled(pEnabled);
		mSizeDecimationLabel.setEnabled(pEnabled);
		mSizeDecimation.setEnabled(pEnabled);
		mPriorityLabel.setEnabled(pEnabled);
		mPriority.setEnabled(pEnabled);
		mModeLabel.setEnabled(pEnabled);
		mMode.setEnabled(pEnabled);
		mCropLeftLabel.setEnabled(pEnabled);
		mCropLeft.setEnabled(pEnabled);
		mCropRightLabel.setEnabled(pEnabled);
		mCropRight.setEnabled(pEnabled);
		mCropTopLabel.setEnabled(pEnabled);
		mCropTop.setEnabled(pEnabled);
		mCropBottomLabelLabel.setEnabled(pEnabled);
		mCropBottom.setEnabled(pEnabled);
		mRedSignalThresholdLabel.setEnabled(pEnabled);
		mRedSignalThreshold.setEnabled(pEnabled);
		mGreenSignalThresholdLabel.setEnabled(pEnabled);
		mGreenSignalThreshold.setEnabled(pEnabled);
		mBlueSignalThresholdLabel.setEnabled(pEnabled);
		mBlueSignalThreshold.setEnabled(pEnabled);

	}

	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(mGrabberV4l2Enable)) {
				mGrabberV4l2Config.mGrabberv4l2Enabled = mGrabberV4l2Enable
						.isSelected();
				toggleEnabled(mGrabberV4l2Config.mGrabberv4l2Enabled);
			}

			mGrabberV4l2Config.mStandard = (VideoStandard) mStandard
					.getSelectedItem();
			mGrabberV4l2Config.mMode = (DimensionModes) mMode.getSelectedItem();

		}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mGrabberV4l2Config.mInput = (Integer) mInput.getValue();
			mGrabberV4l2Config.mWidth = (Integer) mWidth.getValue();
			mGrabberV4l2Config.mHeight = (Integer) mHeight.getValue();
			mGrabberV4l2Config.mFrameDecimation = (Integer) mFrameDecimation
					.getValue();
			mGrabberV4l2Config.mSizeDecimation = (Integer) mSizeDecimation
					.getValue();
			mGrabberV4l2Config.mPriority = (Integer) mPriority.getValue();
			mGrabberV4l2Config.mCropLeft = (Integer) mCropLeft.getValue();
			mGrabberV4l2Config.mCropRight = (Integer) mCropRight.getValue();
			mGrabberV4l2Config.mCropTop = (Integer) mCropTop.getValue();
			mGrabberV4l2Config.mCropBottom = (Integer) mCropBottom.getValue();
			mGrabberV4l2Config.mRedSignalThreshold = (Double) mRedSignalThreshold
					.getValue();
			mGrabberV4l2Config.mGreenSignalThreshold = (Double) mGreenSignalThreshold
					.getValue();
			mGrabberV4l2Config.mBlueSignalThreshold = (Double) mBlueSignalThreshold
					.getValue();
		}
	};

	private final DocumentListener mDocumentListener = new DocumentListener() {

		@Override
		public void insertUpdate(DocumentEvent e) {
			mGrabberV4l2Config.mDevice = mDevice.getText();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			mGrabberV4l2Config.mDevice = mDevice.getText();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			mGrabberV4l2Config.mDevice = mDevice.getText();
		}
	};
}
