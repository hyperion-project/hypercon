package org.hyperion.hypercon.gui.Grabber_Tab;

import org.hyperion.hypercon.spec.DimensionModes;
import org.hyperion.hypercon.spec.Grabberv4l2Config;
import org.hyperion.hypercon.spec.VideoStandard;

import javax.swing.*;
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
public class Grabberv4l2Panel extends JPanel{

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
        setBorder(BorderFactory.createTitledBorder("GrabberV4L2"));

        mGrabberV4l2Enable = new JCheckBox("Enabled");
        mGrabberV4l2Enable.setSelected(mGrabberV4l2Config.mGrabberv4l2Enabled);
        mGrabberV4l2Enable.addActionListener(mActionListener);
        add(mGrabberV4l2Enable);

        mDeviceLabel = new JLabel("Device: ");
        add(mDeviceLabel);
        mDevice = new JTextField(mGrabberV4l2Config.mDevice);
        mDevice.setToolTipText("V4L2 Device to use [default=\"/dev/video0\"]");
        mDevice.getDocument().addDocumentListener(mDocumentListener);
        add(mDevice);

        mInputLabel = new JLabel("Input: ");
        add(mInputLabel);
        mInput = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mInput, 0, Integer.MAX_VALUE, 1));
        mInput.addChangeListener(mChangeListener);
        mInput.setToolTipText("V4L2 input to use [default=0]");
        add(mInput);

        mStandardLabel = new JLabel("Video Standard: ");
        add(mStandardLabel);
        mStandard = new JComboBox<>(VideoStandard.values());
        mStandard.setSelectedItem(mGrabberV4l2Config.mStandard);
        mStandard.addActionListener(mActionListener);
        add(mStandard);

        mWidthLabel= new JLabel("Width: ");
        add(mWidthLabel);
        mWidth = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mWidth, -1, Integer.MAX_VALUE, widthHeightSpinnerIncrease));
        mWidth.addChangeListener(mChangeListener);
        mWidth.setToolTipText("V4L2 width to set [default=-1]");
        add(mWidth);


        mHeightLabel= new JLabel("Height: ");
        add(mHeightLabel);
        mHeight = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mHeight, -1, Integer.MAX_VALUE, widthHeightSpinnerIncrease));
        mHeight.addChangeListener(mChangeListener);
        mHeight.setToolTipText("V4L2 height to set [default=-1]");
        add(mHeight);

        mFrameDecimationLabel= new JLabel("Frame Decimation: ");
        add(mFrameDecimationLabel);
        mFrameDecimation = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mFrameDecimation, 0, Integer.MAX_VALUE, 1));
        mFrameDecimation.addChangeListener(mChangeListener);
        mFrameDecimation.setToolTipText("Frame decimation factor [default=2]");
        add(mFrameDecimation);

        mSizeDecimationLabel= new JLabel("Size Decimation: ");
        add(mSizeDecimationLabel);
        mSizeDecimation = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mSizeDecimation, 0, Integer.MAX_VALUE, 1));
        mSizeDecimation.addChangeListener(mChangeListener);
        mSizeDecimation.setToolTipText("Size decimation factor [default=8]");
        add(mSizeDecimation);

        mPriorityLabel = new JLabel("Priority: ");
        mPriority = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mPriority, 0, Integer.MAX_VALUE, 1));
        mPriority.addChangeListener(mChangeListener);
        mPriority.setToolTipText("Hyperion priority channel [default=800]");
        add(mPriority);

        mModeLabel= new JLabel("3D Mode: ");
        add(mModeLabel);
        mMode = new JComboBox<>(DimensionModes.values());
        mMode.setSelectedItem(mGrabberV4l2Config.mMode);
        mMode.addActionListener(mActionListener);
        add(mMode);

        mCropLeftLabel= new JLabel("Crop Left: ");
        add(mCropLeftLabel);
        mCropLeft = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mCropLeft, 0, 100, cropSpinnerIncrease));
        mCropLeft.addChangeListener(mChangeListener);
        mCropLeft.setToolTipText("Cropping from the left [default=0]");
        add(mCropLeft);

        mCropRightLabel= new JLabel("Crop Right: ");
        add(mCropRightLabel);
        mCropRight = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mCropRight, 0, 100, cropSpinnerIncrease));
        mCropRight.addChangeListener(mChangeListener);
        mCropRight.setToolTipText("Cropping from the right [default=0]");
        add(mCropRight);

        mCropTopLabel= new JLabel("Crop Top: ");
        add(mCropTopLabel);
        mCropTop = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mCropTop, 0, 100, cropSpinnerIncrease));
        mCropTop.addChangeListener(mChangeListener);
        mCropTop.setToolTipText("Cropping from the top [default=0]");
        add(mCropTop);

        mCropBottomLabelLabel= new JLabel("Crop Bottom: ");
        add(mCropBottomLabelLabel);
        mCropBottom = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mCropBottom, 0, 100, cropSpinnerIncrease));
        mCropBottom.addChangeListener(mChangeListener);
        mCropBottom.setToolTipText("Cropping from the bottom [default=0]");
        add(mCropBottom);

        mRedSignalThresholdLabel= new JLabel("Red Signal Threshold: ");
        add(mRedSignalThresholdLabel);
        mRedSignalThreshold = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mRedSignalThreshold, 0, 1, thresholdSpinnerIncrease));
        mRedSignalThreshold.addChangeListener(mChangeListener);
        mRedSignalThreshold.setToolTipText("Signal threshold for the red channel between 0.0 and 1.0 [default=0.0]");
        add(mRedSignalThreshold);

        mGreenSignalThresholdLabel= new JLabel("Green Signal Threshold: ");
        add(mGreenSignalThresholdLabel);
        mGreenSignalThreshold = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mGreenSignalThreshold, 0, 1, thresholdSpinnerIncrease));
        mGreenSignalThreshold.addChangeListener(mChangeListener);
        mGreenSignalThreshold.setToolTipText("Signal threshold for the green channel between 0.0 and 1.0 [default=0.0]");
        add(mGreenSignalThreshold);

        mBlueSignalThresholdLabel= new JLabel("Blue Signal Threshold: ");
        add(mBlueSignalThresholdLabel);
        mBlueSignalThreshold = new JSpinner(new SpinnerNumberModel(mGrabberV4l2Config.mBlueSignalThreshold, 0, 1, thresholdSpinnerIncrease));
        mBlueSignalThreshold.addChangeListener(mChangeListener);
        mBlueSignalThreshold.setToolTipText("Signal threshold for the blue channel between 0.0 and 1.0 [default=0.0]");
        add(mBlueSignalThreshold);

        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
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
                                .addComponent(mBlueSignalThresholdLabel)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mGrabberV4l2Enable)
                                .addComponent(mDevice)
                                .addComponent(mInput)
                                .addComponent(mStandard)
                                .addComponent(mWidth)
                                .addComponent(mHeight)
                                .addComponent(mFrameDecimation)
                                .addComponent(mSizeDecimation)
                                .addComponent(mPriority)
                                .addComponent(mMode)
                                .addComponent(mCropLeft)
                                .addComponent(mCropRight)
                                .addComponent(mCropTop)
                                .addComponent(mCropBottom)
                                .addComponent(mRedSignalThreshold)
                                .addComponent(mGreenSignalThreshold)
                                .addComponent(mBlueSignalThreshold)
                ));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(mGrabberV4l2Enable)
                .addGroup(layout.createParallelGroup()
                                .addComponent(mDeviceLabel)
                                .addComponent(mDevice)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mInputLabel)
                                .addComponent(mInput)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mStandardLabel)
                                .addComponent(mStandard)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mWidthLabel)
                                .addComponent(mWidth)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mHeightLabel)
                                .addComponent(mHeight)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mFrameDecimationLabel)
                                .addComponent(mFrameDecimation)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mSizeDecimationLabel)
                                .addComponent(mSizeDecimation)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mPriorityLabel)
                                .addComponent(mPriority)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mModeLabel)
                                .addComponent(mMode)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mCropLeftLabel)
                                .addComponent(mCropLeft)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mCropRightLabel)
                                .addComponent(mCropRight)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mCropTopLabel)
                                .addComponent(mCropTop)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mCropBottomLabelLabel)
                                .addComponent(mCropBottom)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mRedSignalThresholdLabel)
                                .addComponent(mRedSignalThreshold)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mGreenSignalThresholdLabel)
                                .addComponent(mGreenSignalThreshold)
                )
                .addGroup(layout.createParallelGroup()
                                .addComponent(mBlueSignalThresholdLabel)
                                .addComponent(mBlueSignalThreshold)
                )

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
            if(e.getSource().equals(mGrabberV4l2Enable)) {
                mGrabberV4l2Config.mGrabberv4l2Enabled = mGrabberV4l2Enable.isSelected();
                toggleEnabled(mGrabberV4l2Config.mGrabberv4l2Enabled);
            }

            mGrabberV4l2Config.mStandard = (VideoStandard) mStandard.getSelectedItem();
            mGrabberV4l2Config.mMode = (DimensionModes) mMode.getSelectedItem();

        }
    };
    private final ChangeListener mChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            mGrabberV4l2Config.mInput = (Integer) mInput.getValue();
            mGrabberV4l2Config.mWidth = (Integer)mWidth.getValue();
            mGrabberV4l2Config.mHeight = (Integer) mHeight.getValue();
            mGrabberV4l2Config.mFrameDecimation = (Integer) mFrameDecimation.getValue();
            mGrabberV4l2Config.mSizeDecimation = (Integer) mSizeDecimation.getValue();
            mGrabberV4l2Config.mPriority = (Integer) mPriority.getValue();
            mGrabberV4l2Config.mCropLeft = (Integer) mCropLeft.getValue();
            mGrabberV4l2Config.mCropRight = (Integer) mCropRight.getValue();
            mGrabberV4l2Config.mCropTop = (Integer) mCropTop.getValue();
            mGrabberV4l2Config.mCropBottom = (Integer) mCropBottom.getValue();
            mGrabberV4l2Config.mRedSignalThreshold = (Double) mRedSignalThreshold.getValue();
            mGrabberV4l2Config.mGreenSignalThreshold = (Double) mGreenSignalThreshold.getValue();
            mGrabberV4l2Config.mBlueSignalThreshold = (Double) mBlueSignalThreshold.getValue();
        }
    };

    private final DocumentListener mDocumentListener = new DocumentListener(){

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

        }
    };
}
