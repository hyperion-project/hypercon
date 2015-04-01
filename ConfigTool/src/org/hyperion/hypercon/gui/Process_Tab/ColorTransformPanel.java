package org.hyperion.hypercon.gui.Process_Tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jcraft.jsch.JSchException;
import org.hyperion.hypercon.ErrorHandling;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.spec.TransformConfig;

/**
 * Configuration panel for the ColorConfig.
 * 
 * NB This has not been integrated in the GUI jet!
 */
public class ColorTransformPanel extends JPanel {

	private final Dimension maxDim = new Dimension(1024, 20);
	private final double spinnerChangeValue = 0.01;

	private final TransformConfig mColorConfig;

	private JPanel mIndexPanel;
	private JLabel mIndexLabel;
	private JTextField mIndexField;

	private JPanel mRgbTransformPanel;
	private JLabel mThresholdLabel;
	private JLabel mGammaLabel;
	private JLabel mBlacklevelLabel;
	private JLabel mWhitelevelLabel;
	private JLabel mRedTransformLabel;
	private JSpinner mRedThresholdSpinner;
	private JSpinner mRedGammaSpinner;
	private JSpinner mRedBlacklevelSpinner;
	private JSpinner mRedWhitelevelSpinner;
	private JLabel mGreenTransformLabel;
	private JSpinner mGreenThresholdSpinner;
	private JSpinner mGreenGammaSpinner;
	private JSpinner mGreenBlacklevelSpinner;
	private JSpinner mGreenWhitelevelSpinner;
	private JLabel mBlueTransformLabel;
	private JSpinner mBlueThresholdSpinner;
	private JSpinner mBlueGammaSpinner;
	private JSpinner mBlueBlacklevelSpinner;
	private JSpinner mBlueWhitelevelSpinner;

	private JPanel mHsvTransformPanel;
	private JLabel mSaturationAdjustLabel;
	private JSpinner mSaturationAdjustSpinner;
	private JLabel mValueAdjustLabel;
	private JSpinner mValueAdjustSpinner;

	private SshColorTransformPanel mSshColorTransformPanel;

	public ColorTransformPanel(TransformConfig pTransformConfig) {
		super();

		mColorConfig = pTransformConfig;

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
		setBorder(BorderFactory.createTitledBorder("Transform [" + mColorConfig.mId + "]"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(getIndexPanel());
		add(Box.createVerticalStrut(10));
		add(getRgbPanel());
		add(Box.createVerticalStrut(10));
		add(getHsvPanel());
		add(Box.createVerticalGlue());

		//sshColorTransformPanel and Actionlistener for its button
		mSshColorTransformPanel = new SshColorTransformPanel();
		mSshColorTransformPanel.sendTransform.addActionListener(mActionListener);
		add(mSshColorTransformPanel);
	}

	private JPanel getIndexPanel() {
		if (mIndexPanel == null) {
			mIndexPanel = new JPanel();
			mIndexPanel.setMaximumSize(new Dimension(1024, 25));
			mIndexPanel.setLayout(new BorderLayout(10, 10));

			mIndexLabel = new JLabel("Indices:");
			mIndexPanel.add(mIndexLabel, BorderLayout.WEST);

			mIndexField = new JTextField(mColorConfig.mLedIndexString);
			mIndexField.setToolTipText("Comma seperated indices or index ranges (eg '1-10, 13, 14, 17-19'); Special case '*', which means all leds");
			mIndexField.getDocument().addDocumentListener(mDocumentListener);
			mIndexPanel.add(mIndexField, BorderLayout.CENTER);
		}
		return mIndexPanel;
	}

	private JPanel getRgbPanel() {
		if (mRgbTransformPanel == null) {
			mRgbTransformPanel = new JPanel();

			GridLayout layout = new GridLayout(0, 5);
			// GroupLayout layout = new GroupLayout(mRgbTransformPanel);
			mRgbTransformPanel.setLayout(layout);

			mRgbTransformPanel.add(Box.createHorizontalBox());

			mThresholdLabel = new JLabel("Thres.");
			mRgbTransformPanel.add(mThresholdLabel);

			mGammaLabel = new JLabel("Gamma");
			mRgbTransformPanel.add(mGammaLabel);

			mBlacklevelLabel = new JLabel("Blacklvl");
			mRgbTransformPanel.add(mBlacklevelLabel);

			mWhitelevelLabel = new JLabel("Whitelvl");
			mRgbTransformPanel.add(mWhitelevelLabel);

			mRedTransformLabel = new JLabel("RED");
			mRgbTransformPanel.add(mRedTransformLabel);
			mRedThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedThreshold, 0.0, 1.0, spinnerChangeValue));
			mRedThresholdSpinner.setMaximumSize(maxDim);
			mRedThresholdSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mRedThresholdSpinner);
			mRedGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedGamma, 0.0, 100.0, spinnerChangeValue));
			mRedGammaSpinner.setMaximumSize(maxDim);
			mRedGammaSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mRedGammaSpinner);
			mRedBlacklevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedBlacklevel, 0.0, 1.0, spinnerChangeValue));
			mRedBlacklevelSpinner.setMaximumSize(maxDim);
			mRedBlacklevelSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mRedBlacklevelSpinner);
			mRedWhitelevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedWhitelevel, 0.0, 1.0, spinnerChangeValue));
			mRedWhitelevelSpinner.setMaximumSize(maxDim);
			mRedWhitelevelSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mRedWhitelevelSpinner);

			mGreenTransformLabel = new JLabel("GREEN");
			mRgbTransformPanel.add(mGreenTransformLabel);
			mGreenThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenThreshold, 0.0, 1.0, spinnerChangeValue));
			mGreenThresholdSpinner.setMaximumSize(maxDim);
			mGreenThresholdSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mGreenThresholdSpinner);
			mGreenGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenGamma, 0.0, 100.0, spinnerChangeValue));
			mGreenGammaSpinner.setMaximumSize(maxDim);
			mGreenGammaSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mGreenGammaSpinner);
			mGreenBlacklevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenBlacklevel, 0.0, 1.0, spinnerChangeValue));
			mGreenBlacklevelSpinner.setMaximumSize(maxDim);
			mGreenBlacklevelSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mGreenBlacklevelSpinner);
			mGreenWhitelevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenWhitelevel, 0.0, 1.0, spinnerChangeValue));
			mGreenWhitelevelSpinner.setMaximumSize(maxDim);
			mGreenWhitelevelSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mGreenWhitelevelSpinner);

			mBlueTransformLabel = new JLabel("BLUE");
			mRgbTransformPanel.add(mBlueTransformLabel);
			mBlueThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueThreshold, 0.0, 1.0, spinnerChangeValue));
			mBlueThresholdSpinner.setMaximumSize(maxDim);
			mBlueThresholdSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mBlueThresholdSpinner);
			mBlueGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueGamma, 0.0, 100.0, spinnerChangeValue));
			mBlueGammaSpinner.setMaximumSize(maxDim);
			mBlueGammaSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mBlueGammaSpinner);
			mBlueBlacklevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueBlacklevel, 0.0, 1.0, spinnerChangeValue));
			mBlueBlacklevelSpinner.setMaximumSize(maxDim);
			mBlueBlacklevelSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mBlueBlacklevelSpinner);
			mBlueWhitelevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueWhitelevel, 0.0, 1.0, spinnerChangeValue));
			mBlueWhitelevelSpinner.setMaximumSize(maxDim);
			mBlueWhitelevelSpinner.addChangeListener(mChangeListener);
			mRgbTransformPanel.add(mBlueWhitelevelSpinner);
		}
		return mRgbTransformPanel;
	}

	private JPanel getHsvPanel() {
		if (mHsvTransformPanel == null) {
			mHsvTransformPanel = new JPanel();

			GroupLayout layout = new GroupLayout(mHsvTransformPanel);
			mHsvTransformPanel.setLayout(layout);

			mSaturationAdjustLabel = new JLabel("HSV Saturation gain");
			mHsvTransformPanel.add(mSaturationAdjustLabel);

			mSaturationAdjustSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mSaturationGain, 0.0, 1024.0, 0.01));

			mSaturationAdjustSpinner.setMaximumSize(maxDim);
			mSaturationAdjustSpinner.addChangeListener(mChangeListener);
			mHsvTransformPanel.add(mSaturationAdjustSpinner);

			mValueAdjustLabel = new JLabel("HSV Value gain");
			mHsvTransformPanel.add(mValueAdjustLabel);

			mValueAdjustSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mValueGain, 0.0, 1024.0, 0.01));
			mValueAdjustSpinner.setMaximumSize(maxDim);
			mValueAdjustSpinner.addChangeListener(mChangeListener);
			mHsvTransformPanel.add(mValueAdjustSpinner);

			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup().addComponent(mSaturationAdjustLabel).addComponent(mValueAdjustLabel))
					.addGroup(layout.createParallelGroup().addComponent(mSaturationAdjustSpinner).addComponent(mValueAdjustSpinner)));

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup().addComponent(mSaturationAdjustLabel).addComponent(mSaturationAdjustSpinner))
					.addGroup(layout.createParallelGroup().addComponent(mValueAdjustLabel).addComponent(mValueAdjustSpinner)));
		}
		return mHsvTransformPanel;
	}

	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mColorConfig.mRedThreshold = (Double) mRedThresholdSpinner.getValue();
			mColorConfig.mRedGamma = (Double) mRedGammaSpinner.getValue();
			mColorConfig.mRedBlacklevel = (Double) mRedBlacklevelSpinner.getValue();
			mColorConfig.mRedWhitelevel = (Double) mRedWhitelevelSpinner.getValue();

			mColorConfig.mGreenThreshold = (Double) mGreenThresholdSpinner.getValue();
			mColorConfig.mGreenGamma = (Double) mGreenGammaSpinner.getValue();
			mColorConfig.mGreenBlacklevel = (Double) mGreenBlacklevelSpinner.getValue();
			mColorConfig.mGreenWhitelevel = (Double) mGreenWhitelevelSpinner.getValue();

			mColorConfig.mBlueThreshold = (Double) mBlueThresholdSpinner.getValue();
			mColorConfig.mBlueGamma = (Double) mBlueGammaSpinner.getValue();
			mColorConfig.mBlueBlacklevel = (Double) mBlueBlacklevelSpinner.getValue();
			mColorConfig.mBlueWhitelevel = (Double) mBlueWhitelevelSpinner.getValue();

			mColorConfig.mSaturationGain = (Double) mSaturationAdjustSpinner.getValue();
			mColorConfig.mValueGain = (Double) mValueAdjustSpinner.getValue();
			
		}
	};
	private final DocumentListener mDocumentListener = new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			mColorConfig.mLedIndexString = mIndexField.getText();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			mColorConfig.mLedIndexString = mIndexField.getText();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			mColorConfig.mLedIndexString = mIndexField.getText();
		}
	};

	/**
	 * get the Action of the button in the SShColorTransformPanel
	 */
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == mSshColorTransformPanel.sendTransform){
				sendTransforms();
			}

		}
	};
	
	/**
	 * get the selected color transform values and send them via the SshConnectionModel
	 */
	private void sendTransforms(){
		
			float[] rgbThreshold = new float[3];
			float[] rgbGamma = new float[3];
			float[] rgbBlacklevel = new float[3];
			float[] rgbWhitelevel = new float[3];
			float hsvGain;
			float hsvSaturation;

			rgbThreshold[0] = ((Double) mRedThresholdSpinner.getValue()).floatValue();
			rgbThreshold[1] = ((Double) mGreenThresholdSpinner.getValue()).floatValue();
			rgbThreshold[2] = ((Double) mBlueThresholdSpinner.getValue()).floatValue();

			rgbGamma[0] = ((Double) mRedGammaSpinner.getValue()).floatValue();
			rgbGamma[1] = ((Double) mGreenGammaSpinner.getValue()).floatValue();
			rgbGamma[2] = ((Double) mBlueGammaSpinner.getValue()).floatValue();

			rgbBlacklevel[0] = ((Double) mRedBlacklevelSpinner.getValue()).floatValue();
			rgbBlacklevel[1] = ((Double) mGreenBlacklevelSpinner.getValue()).floatValue();
			rgbBlacklevel[2] = ((Double) mBlueBlacklevelSpinner.getValue()).floatValue();

			rgbWhitelevel[0] = ((Double) mRedWhitelevelSpinner.getValue()).floatValue();
			rgbWhitelevel[1] = ((Double) mGreenWhitelevelSpinner.getValue()).floatValue();
			rgbWhitelevel[2] = ((Double) mBlueWhitelevelSpinner.getValue()).floatValue();

			hsvGain = ((Double) mValueAdjustSpinner.getValue()).floatValue();
			hsvSaturation = ((Double) mSaturationAdjustSpinner.getValue()).floatValue();

		try {
			SshConnectionModel.getInstance().sendColorTransformValues(rgbThreshold, rgbGamma, rgbBlacklevel, rgbWhitelevel, hsvGain, hsvSaturation);
		} catch (JSchException e) {
			ErrorHandling.ShowException(e);
		}

	}

}
