package org.hyperion.hypercon.gui.Process_Tab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
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
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.TransformConfig;
import javax.swing.GroupLayout.Alignment;

/**
 * Configuration panel for the ColorConfig.
 * 
 * NB This has not been integrated in the GUI jet!
 */
public class ColorTransformPanel extends JPanel {

	private final Dimension maxDim = new Dimension(1024, 20);
	private final Dimension maxSpinnerDim = new Dimension(1024, 20);
	private final double spinnerChangeValue = 0.01;
	private final int spinnerIntChangeValue = 1;
	
	private final TransformConfig mColorConfig;

	private JLabel mIndexLabel;
	private JLabel mDummyLabel;
	private JLabel mDummy1Label;
	private JLabel mDummy2Label;
	private JLabel mDummy3Label;
	private JTextField mIndexField;

	private JLabel mWhiteLabel;
	private JSpinner mRedChannelRedSpinner;	
	private JSpinner mGreenChannelGreenSpinner;
	private JSpinner mBlueChannelBlueSpinner;
	
	private JLabel mPureRedLabel;
	private JLabel mPureGreenLabel;
	private JLabel mPureBlueLabel;
	private JLabel mGammaLabel;
	private JLabel mTemperatureLabel;
	private JLabel mThresholdLabel;
	
	private JLabel mRedTransformLabel;
	private JSpinner mRedChannelGreenSpinner;
	private JSpinner mRedChannelBlueSpinner;
	private JSpinner mRedGammaSpinner;
	private JSpinner mRedTemperatureSpinner;
	private JSpinner mRedThresholdSpinner;
	
	private JLabel mGreenTransformLabel;
	private JSpinner mGreenChannelRedSpinner;
	private JSpinner mGreenChannelBlueSpinner;
	private JSpinner mGreenGammaSpinner;
	private JSpinner mGreenTemperatureSpinner;
	private JSpinner mGreenThresholdSpinner;
	
	private JLabel mBlueTransformLabel;
	private JSpinner mBlueChannelRedSpinner;
	private JSpinner mBlueChannelGreenSpinner;
	private JSpinner mBlueGammaSpinner;
	private JSpinner mBlueTemperatureSpinner;
	private JSpinner mBlueThresholdSpinner;

//	private JLabel mHSVSaturationAdjustLabel;
//	private JSpinner mHSVSaturationAdjustSpinner;
//	private JLabel mHSVValueAdjustLabel;
//	private JSpinner mHSVValueAdjustSpinner;

	private JLabel mHSLSaturationGainAdjustLabel;
	private JSpinner mHSLSaturationGainAdjustSpinner;
	private JLabel mHSLLuminanceGainAdjustLabel;
	private JSpinner mHSLLuminanceGainAdjustSpinner;
	private JLabel mMinimumLuminanceLabel;
	private JSpinner mMinimumLuminanceSpinner;
	
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
		setBorder(BorderFactory.createTitledBorder(language.getString("process.transform.title") + " [" + mColorConfig.mId + "]")); //$NON-NLS-1$ //$NON-NLS-2$

		//sshColorTransformPanel and Actionlistener for its button
		mSshColorTransformPanel = new SshColorTransformPanel();
		mSshColorTransformPanel.sendTransform.addActionListener(mActionListener);
		add(mSshColorTransformPanel);

			mIndexLabel = new JLabel(language.getString("process.transform.indicieslabel")); //$NON-NLS-1$
			add(mIndexLabel);

			mIndexField = new JTextField(mColorConfig.mLedIndexString);
			mIndexField.setToolTipText(language.getString("process.transform.indiciestooltip")); //$NON-NLS-1$
			mIndexField.setMaximumSize(maxDim);
			mIndexField.getDocument().addDocumentListener(mDocumentListener);
			add(mIndexField);

			// Dummy Labels are used to fill space
			mDummyLabel = new JLabel("");
			add(mDummyLabel);
			mDummy1Label = new JLabel("255");
			add(mDummy1Label);
			mDummy2Label = new JLabel("255");
			add(mDummy2Label);
			mDummy3Label = new JLabel("255");
			add(mDummy3Label);
			
			mPureRedLabel = new JLabel(language.getString("process.transform.PureRedlabel")); //$NON-NLS-1$
			mPureRedLabel.setToolTipText(language.getString("process.transform.PureRedlabeltooltip"));			
			add(mPureRedLabel);

			mPureGreenLabel = new JLabel(language.getString("process.transform.PureGreenlabel")); //$NON-NLS-1$
			mPureGreenLabel.setToolTipText(language.getString("process.transform.PureGreenlabeltooltip"));		
			add(mPureGreenLabel);

			mPureBlueLabel = new JLabel(language.getString("process.transform.PureBluelabel")); //$NON-NLS-1$
			mPureBlueLabel.setToolTipText(language.getString("process.transform.PureBluelabeltooltip"));
			add(mPureBlueLabel);
			
			mGammaLabel = new JLabel(language.getString("process.transform.gammalabel")); //$NON-NLS-1$
			mGammaLabel.setToolTipText(language.getString("process.transform.gammalabeltooltip"));
			add(mGammaLabel);
			
			mTemperatureLabel = new JLabel(language.getString("process.transform.temperaturelabel")); //$NON-NLS-1$
			mTemperatureLabel.setToolTipText(language.getString("process.transform.temperaturelabeltooltip"));
			add(mTemperatureLabel);
			
			mThresholdLabel = new JLabel(language.getString("process.transform.treshlabel")); //$NON-NLS-1$
			mThresholdLabel.setToolTipText(language.getString("process.transform.treshlabeltooltip"));
			add(mThresholdLabel);
			
			mWhiteLabel = new JLabel(language.getString("process.transform.whitelabel")); //$NON-NLS-1$
			mWhiteLabel.setToolTipText(language.getString("process.transform.ChannelWhitetooltip"));
			add(mWhiteLabel);
			
			mRedChannelRedSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedChannelRedSpinner, 0, 255, spinnerIntChangeValue));
			mRedChannelRedSpinner.setToolTipText(language.getString("process.transform.ChannelWhitetooltip"));		
			mRedChannelRedSpinner.setMaximumSize(maxSpinnerDim);
			mRedChannelRedSpinner.addChangeListener(mChangeListener);
			add(mRedChannelRedSpinner);
		
			mGreenChannelGreenSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenChannelGreenSpinner, 0, 255, spinnerIntChangeValue));
			mGreenChannelGreenSpinner.setToolTipText(language.getString("process.transform.ChannelWhitetooltip"));	
			mGreenChannelGreenSpinner.setMaximumSize(maxSpinnerDim);
			mGreenChannelGreenSpinner.addChangeListener(mChangeListener);
			add(mGreenChannelGreenSpinner);			
			
			mBlueChannelBlueSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueChannelBlueSpinner, 0, 255, spinnerIntChangeValue));
			mBlueChannelBlueSpinner.setToolTipText(language.getString("process.transform.ChannelWhitetooltip"));
			mBlueChannelBlueSpinner.setMaximumSize(maxSpinnerDim);
			mBlueChannelBlueSpinner.addChangeListener(mChangeListener);
			add(mBlueChannelBlueSpinner);
			
			mRedTransformLabel = new JLabel(language.getString("process.transform.redlabel")); //$NON-NLS-1$
			add(mRedTransformLabel);			
			
			mRedChannelGreenSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedChannelGreenSpinner, 0, 255, spinnerIntChangeValue));
			mRedChannelGreenSpinner.setToolTipText(language.getString("process.transform.ChannelAdjustmenttooltip"));		
			mRedChannelGreenSpinner.setMaximumSize(maxSpinnerDim);
			mRedChannelGreenSpinner.addChangeListener(mChangeListener);
			add(mRedChannelGreenSpinner);
			mRedChannelBlueSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedChannelBlueSpinner, 0, 255, spinnerIntChangeValue));
			mRedChannelBlueSpinner.setToolTipText(language.getString("process.transform.ChannelAdjustmenttooltip"));
			mRedChannelBlueSpinner.setMaximumSize(maxSpinnerDim);
			mRedChannelBlueSpinner.addChangeListener(mChangeListener);
			add(mRedChannelBlueSpinner);
			mRedGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedGamma, 0.0, 100.0, spinnerChangeValue));
			mRedGammaSpinner.setToolTipText(language.getString("process.transform.gammalabeltooltip"));
			mRedGammaSpinner.setMaximumSize(maxSpinnerDim);
			mRedGammaSpinner.addChangeListener(mChangeListener);
			add(mRedGammaSpinner);
			mRedTemperatureSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedTemperature, 0, 255, spinnerIntChangeValue));
			mRedTemperatureSpinner.setToolTipText(language.getString("process.transform.temperaturelabeltooltip"));
			mRedTemperatureSpinner.setMaximumSize(maxSpinnerDim);
			mRedTemperatureSpinner.addChangeListener(mChangeListener);
			add(mRedTemperatureSpinner);
			mRedThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedThreshold, 0.0, 1.0, spinnerChangeValue));
			mRedThresholdSpinner.setToolTipText(language.getString("process.transform.treshlabeltooltip"));
			mRedThresholdSpinner.setMaximumSize(maxSpinnerDim);
			mRedThresholdSpinner.addChangeListener(mChangeListener);
			add(mRedThresholdSpinner);

			mGreenTransformLabel = new JLabel(language.getString("process.transform.greenlabel")); //$NON-NLS-1$
			add(mGreenTransformLabel);
			
			mGreenChannelRedSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenChannelRedSpinner, 0, 255, spinnerIntChangeValue));
			mGreenChannelRedSpinner.setToolTipText(language.getString("process.transform.ChannelAdjustmenttooltip"));		
			mGreenChannelRedSpinner.setMaximumSize(maxSpinnerDim);
			mGreenChannelRedSpinner.addChangeListener(mChangeListener);
			add(mGreenChannelRedSpinner);
			mGreenChannelBlueSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenChannelBlueSpinner, 0, 255, spinnerIntChangeValue));
			mGreenChannelBlueSpinner.setToolTipText(language.getString("process.transform.ChannelAdjustmenttooltip"));
			mGreenChannelBlueSpinner.setMaximumSize(maxSpinnerDim);
			mGreenChannelBlueSpinner.addChangeListener(mChangeListener);
			add(mGreenChannelBlueSpinner);
			mGreenGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenGamma, 0.0, 100.0, spinnerChangeValue));
			mGreenGammaSpinner.setToolTipText(language.getString("process.transform.gammalabeltooltip"));
			mGreenGammaSpinner.setMaximumSize(maxSpinnerDim);
			mGreenGammaSpinner.addChangeListener(mChangeListener);
			add(mGreenGammaSpinner);
			mGreenTemperatureSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenTemperature, 0, 255, spinnerIntChangeValue));
			mGreenTemperatureSpinner.setToolTipText(language.getString("process.transform.temperaturelabeltooltip"));
			mGreenTemperatureSpinner.setMaximumSize(maxSpinnerDim);
			mGreenTemperatureSpinner.addChangeListener(mChangeListener);
			add(mGreenTemperatureSpinner);
			mGreenThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenThreshold, 0.0, 1.0, spinnerChangeValue));
			mGreenThresholdSpinner.setToolTipText(language.getString("process.transform.treshlabeltooltip"));
			mGreenThresholdSpinner.setMaximumSize(maxSpinnerDim);
			mGreenThresholdSpinner.addChangeListener(mChangeListener);
			add(mGreenThresholdSpinner);

			mBlueTransformLabel = new JLabel(language.getString("process.transform.bluelabel")); //$NON-NLS-1$
			add(mBlueTransformLabel);
			
			mBlueChannelRedSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueChannelRedSpinner, 0, 255, spinnerIntChangeValue));
			mBlueChannelRedSpinner.setToolTipText(language.getString("process.transform.ChannelAdjustmenttooltip"));		
			mBlueChannelRedSpinner.setMaximumSize(maxSpinnerDim);
			mBlueChannelRedSpinner.addChangeListener(mChangeListener);
			add(mBlueChannelRedSpinner);
			mBlueChannelGreenSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueChannelGreenSpinner, 0, 255, spinnerIntChangeValue));
			mBlueChannelGreenSpinner.setToolTipText(language.getString("process.transform.ChannelAdjustmenttooltip"));	
			mBlueChannelGreenSpinner.setMaximumSize(maxSpinnerDim);
			mBlueChannelGreenSpinner.addChangeListener(mChangeListener);
			add(mBlueChannelGreenSpinner);
			mBlueGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueGamma, 0.0, 100.0, spinnerChangeValue));
			mBlueGammaSpinner.setToolTipText(language.getString("process.transform.gammalabeltooltip"));
			mBlueGammaSpinner.setMaximumSize(maxSpinnerDim);
			mBlueGammaSpinner.addChangeListener(mChangeListener);
			add(mBlueGammaSpinner);
			mBlueTemperatureSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueTemperature, 0, 255, spinnerIntChangeValue));
			mBlueTemperatureSpinner.setToolTipText(language.getString("process.transform.temperaturelabeltooltip"));
			mBlueTemperatureSpinner.setMaximumSize(maxSpinnerDim);
			mBlueTemperatureSpinner.addChangeListener(mChangeListener);
			add(mBlueTemperatureSpinner);
			mBlueThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueThreshold, 0.0, 1.0, spinnerChangeValue));
			mBlueThresholdSpinner.setToolTipText(language.getString("process.transform.treshlabeltooltip"));
			mBlueThresholdSpinner.setMaximumSize(maxSpinnerDim);
			mBlueThresholdSpinner.addChangeListener(mChangeListener);
			add(mBlueThresholdSpinner);

			mHSLSaturationGainAdjustLabel = new JLabel(language.getString("process.transform.HSLSaturationGainlabel")); //$NON-NLS-1$
			add(mHSLSaturationGainAdjustLabel);
			mHSLSaturationGainAdjustSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mHSLSaturationGainAdjustSpinner, 0.0, 1024.0, 0.01));
			mHSLSaturationGainAdjustSpinner.setMaximumSize(maxDim);
			mHSLSaturationGainAdjustSpinner.setToolTipText(language.getString("process.transform.hslsaturationlabeltooltip"));
			mHSLSaturationGainAdjustSpinner.addChangeListener(mChangeListener);
			add(mHSLSaturationGainAdjustSpinner);

			mHSLLuminanceGainAdjustLabel = new JLabel(language.getString("process.transform.HSLluminanceGainlabel")); //$NON-NLS-1$
			add(mHSLLuminanceGainAdjustLabel);
			mHSLLuminanceGainAdjustSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mHSLLuminanceGainAdjustSpinner, 0.0, 1024.0, 0.01));
			mHSLLuminanceGainAdjustSpinner.setMaximumSize(maxDim);
			mHSLLuminanceGainAdjustSpinner.setToolTipText(language.getString("process.transform.hslluminancelabeltooltip"));
			mHSLLuminanceGainAdjustSpinner.addChangeListener(mChangeListener);
			add(mHSLLuminanceGainAdjustSpinner);

			mMinimumLuminanceLabel = new JLabel(language.getString("process.transform.MinimumLuminanceLabel")); //$NON-NLS-1$
			add(mMinimumLuminanceLabel);
			mMinimumLuminanceSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mluminanceMinimumSpinner, 0.0, 1.0, 0.01));
			mMinimumLuminanceSpinner.setMaximumSize(maxDim);
			mMinimumLuminanceSpinner.setToolTipText(language.getString("process.transform.MinimumLuminanceLabeltooltip"));
			mMinimumLuminanceSpinner.addChangeListener(mChangeListener);
			add(mMinimumLuminanceSpinner);
			
			GroupLayout layout = new GroupLayout(this);
			layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
									.addGroup(layout.createSequentialGroup()
										.addComponent(mIndexLabel)
										.addComponent(mIndexField))
											.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup()
													.addComponent(mWhiteLabel)
													.addComponent(mPureBlueLabel)
													.addComponent(mTemperatureLabel)
													.addComponent(mThresholdLabel)
													.addComponent(mDummyLabel)
													.addComponent(mGammaLabel)
													.addComponent(mPureRedLabel)	
													.addComponent(mPureGreenLabel)
														)
												.addGroup(layout.createParallelGroup(Alignment.CENTER)
													.addComponent(mGreenChannelRedSpinner)
													.addComponent(mRedTemperatureSpinner)
													.addComponent(mRedThresholdSpinner)
													.addComponent(mRedTransformLabel)
													.addComponent(mRedGammaSpinner)
													.addComponent(mRedChannelRedSpinner)
													.addComponent(mBlueChannelRedSpinner)
													.addComponent(mDummy1Label)
														)
												.addGroup(layout.createParallelGroup(Alignment.CENTER)
													.addComponent(mRedChannelGreenSpinner)
													.addComponent(mGreenTemperatureSpinner)
													.addComponent(mGreenTransformLabel)
													.addComponent(mGreenThresholdSpinner)
													.addComponent(mGreenGammaSpinner)
													.addComponent(mBlueChannelGreenSpinner)
													.addComponent(mGreenChannelGreenSpinner)
													.addComponent(mDummy2Label)
														)
												.addGroup(layout.createParallelGroup(Alignment.CENTER)
													.addComponent(mBlueChannelBlueSpinner)
													.addComponent(mBlueTemperatureSpinner)
													.addComponent(mBlueTransformLabel)
													.addComponent(mBlueThresholdSpinner)
													.addComponent(mBlueGammaSpinner)
													.addComponent(mGreenChannelBlueSpinner)
													.addComponent(mRedChannelBlueSpinner)
													.addComponent(mDummy3Label)
													)
												)	
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
									.addComponent(mHSLSaturationGainAdjustLabel)
									.addComponent(mHSLLuminanceGainAdjustLabel)
									.addComponent(mMinimumLuminanceLabel)
									.addComponent(mSshColorTransformPanel))
								.addGroup(layout.createParallelGroup()
									.addComponent(mHSLSaturationGainAdjustSpinner)
									.addComponent(mHSLLuminanceGainAdjustSpinner)
									.addComponent(mMinimumLuminanceSpinner))
									))
						)
			);
			layout.setVerticalGroup(
				layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
							.addGroup(layout.createSequentialGroup()
								.addComponent(mIndexField)
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mDummyLabel)
									.addComponent(mRedTransformLabel)
									.addComponent(mGreenTransformLabel)
									.addComponent(mBlueTransformLabel))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mWhiteLabel)
									.addComponent(mRedChannelRedSpinner)
									.addComponent(mGreenChannelGreenSpinner)
									.addComponent(mBlueChannelBlueSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mGammaLabel)
									.addComponent(mRedGammaSpinner)
									.addComponent(mGreenGammaSpinner)
									.addComponent(mBlueGammaSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mPureRedLabel)
									.addComponent(mDummy1Label)
									.addComponent(mRedChannelGreenSpinner)
									.addComponent(mRedChannelBlueSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mPureGreenLabel)
									.addComponent(mGreenChannelRedSpinner)
									.addComponent(mDummy2Label)
									.addComponent(mGreenChannelBlueSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mPureBlueLabel)
									.addComponent(mBlueChannelRedSpinner)
									.addComponent(mBlueChannelGreenSpinner)
									.addComponent(mDummy3Label))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mTemperatureLabel)
									.addComponent(mRedTemperatureSpinner)
									.addComponent(mGreenTemperatureSpinner)
									.addComponent(mBlueTemperatureSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mThresholdLabel)
									.addComponent(mRedThresholdSpinner)
									.addComponent(mGreenThresholdSpinner)
									.addComponent(mBlueThresholdSpinner))
									)
							.addComponent(mIndexLabel))
						.addGap(12)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mHSLSaturationGainAdjustLabel)
							.addComponent(mHSLSaturationGainAdjustSpinner))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mHSLLuminanceGainAdjustLabel)
							.addComponent(mHSLLuminanceGainAdjustSpinner))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mMinimumLuminanceLabel)
								.addComponent(mMinimumLuminanceSpinner))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mSshColorTransformPanel))
							)
			);
			layout.setAutoCreateGaps(true);
			setLayout(layout);
			mIndexLabel.setEnabled(false);
			mIndexField.setEnabled(false);
			EnableFields(false);
	
	}
    private void EnableFields(boolean setEnabled) {
    	mDummy1Label.setEnabled(setEnabled);
    	mDummy2Label.setEnabled(setEnabled);
    	mDummy3Label.setEnabled(setEnabled);
    }
	
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
//			mColorConfig.mDummy1Label = (String) mRedChannelRedSpinner.getValue().toString();
//			mColorConfig.mDummy2Label = (String) mGreenChannelGreenSpinner.getValue().toString();
//			mColorConfig.mDummy3Label = (String) mBlueChannelBlueSpinner.getValue().toString();
			
			mDummy1Label.setText((String) mRedChannelRedSpinner.getValue().toString());
			mDummy2Label.setText((String) mGreenChannelGreenSpinner.getValue().toString());
			mDummy3Label.setText((String) mBlueChannelBlueSpinner.getValue().toString());
			
			mColorConfig.mRedChannelRedSpinner = (Integer) mRedChannelRedSpinner.getValue();
			mColorConfig.mRedChannelGreenSpinner = (Integer) mRedChannelGreenSpinner.getValue();
			mColorConfig.mRedChannelBlueSpinner =  (Integer) mRedChannelBlueSpinner.getValue();
			mColorConfig.mRedGamma = (Double) mRedGammaSpinner.getValue();
			mColorConfig.mRedTemperature = (Integer) mRedTemperatureSpinner.getValue();
			mColorConfig.mRedThreshold = (Double) mRedThresholdSpinner.getValue();

			mColorConfig.mGreenChannelRedSpinner = (Integer) mGreenChannelRedSpinner.getValue();
			mColorConfig.mGreenChannelGreenSpinner = (Integer) mGreenChannelGreenSpinner.getValue();
			mColorConfig.mGreenChannelBlueSpinner = (Integer) mGreenChannelBlueSpinner.getValue();
			mColorConfig.mGreenGamma = (Double) mGreenGammaSpinner.getValue();
			mColorConfig.mGreenTemperature = (Integer) mGreenTemperatureSpinner.getValue();
			mColorConfig.mGreenThreshold = (Double) mGreenThresholdSpinner.getValue();

			mColorConfig.mBlueChannelRedSpinner = (Integer) mBlueChannelRedSpinner.getValue();
			mColorConfig.mBlueChannelGreenSpinner = (Integer) mBlueChannelGreenSpinner.getValue();
			mColorConfig.mBlueChannelBlueSpinner = (Integer) mBlueChannelBlueSpinner.getValue();
			mColorConfig.mBlueGamma = (Double) mBlueGammaSpinner.getValue();
			mColorConfig.mBlueTemperature = (Integer) mBlueTemperatureSpinner.getValue();
			mColorConfig.mBlueThreshold = (Double) mBlueThresholdSpinner.getValue();

			mColorConfig.mHSLSaturationGainAdjustSpinner = (Double) mHSLSaturationGainAdjustSpinner.getValue();
			mColorConfig.mHSLLuminanceGainAdjustSpinner = (Double) mHSLLuminanceGainAdjustSpinner.getValue();
			mColorConfig.mluminanceMinimumSpinner = (Double) mMinimumLuminanceSpinner.getValue();
			
			if (mSshColorTransformPanel.sendTransformContinuousCheckBox.isSelected()){
			sendTransforms();
			}
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
		
			String IndexName;
			float[] rgbThreshold = new float[3];
			float[] rgbGamma = new float[3];
			int[] channelPureRed = new int[3];
			int[] channelPureGreen = new int[3];
			int[] channelPureBlue = new int[3];
			int[] rgbTemperaturelevel = new int[3];
			float hslGain;
			float hslSaturation;
			float hsluminanceMinimum;
			
			IndexName = mColorConfig.mId.toString();
			
			channelPureRed[0] = ((Integer) mRedChannelRedSpinner.getValue()).intValue();
			channelPureRed[1] = ((Integer) mRedChannelGreenSpinner.getValue()).intValue();
			channelPureRed[2] = ((Integer) mRedChannelBlueSpinner.getValue()).intValue();
			
			channelPureGreen[0] = ((Integer) mGreenChannelRedSpinner.getValue()).intValue();
			channelPureGreen[1] = ((Integer) mGreenChannelGreenSpinner.getValue()).intValue();
			channelPureGreen[2] = ((Integer) mGreenChannelBlueSpinner.getValue()).intValue();
			
			channelPureBlue[0] = ((Integer) mBlueChannelRedSpinner.getValue()).intValue();
			channelPureBlue[1] = ((Integer) mBlueChannelGreenSpinner.getValue()).intValue();
			channelPureBlue[2] = ((Integer) mBlueChannelBlueSpinner.getValue()).intValue();
			
			rgbTemperaturelevel[0] = ((Integer) mRedTemperatureSpinner.getValue()).intValue();
			rgbTemperaturelevel[1] = ((Integer) mGreenTemperatureSpinner.getValue()).intValue();
			rgbTemperaturelevel[2] = ((Integer) mBlueTemperatureSpinner.getValue()).intValue();

			rgbGamma[0] = ((Double) mRedGammaSpinner.getValue()).floatValue();
			rgbGamma[1] = ((Double) mGreenGammaSpinner.getValue()).floatValue();
			rgbGamma[2] = ((Double) mBlueGammaSpinner.getValue()).floatValue();
			
			rgbThreshold[0] = ((Double) mRedThresholdSpinner.getValue()).floatValue();
			rgbThreshold[1] = ((Double) mGreenThresholdSpinner.getValue()).floatValue();
			rgbThreshold[2] = ((Double) mBlueThresholdSpinner.getValue()).floatValue();

			hslGain = ((Double) mHSLLuminanceGainAdjustSpinner.getValue()).floatValue();
			hslSaturation = ((Double) mHSLSaturationGainAdjustSpinner.getValue()).floatValue();
			hsluminanceMinimum = ((Double) mMinimumLuminanceSpinner.getValue()).floatValue();
			
		try {
			SshConnectionModel.getInstance().sendColorTransformValues(IndexName, rgbThreshold, rgbGamma, channelPureRed, channelPureGreen, channelPureBlue, rgbTemperaturelevel, hslGain, hslSaturation, hsluminanceMinimum);
		} catch (JSchException e) {
			ErrorHandling.ShowException(e);
		}

	}

}
