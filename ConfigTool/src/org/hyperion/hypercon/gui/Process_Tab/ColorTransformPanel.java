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

	private final TransformConfig mColorConfig;

//	private JPanel mIndexPanel;
	private JLabel mIndexLabel;
	private JLabel mDummyLabel;
	private JTextField mIndexField;

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

			mDummyLabel = new JLabel("");
			add(mDummyLabel);
			
			mThresholdLabel = new JLabel(language.getString("process.transform.treshlabel")); //$NON-NLS-1$
			mThresholdLabel.setToolTipText(language.getString("process.transform.treshlabeltooltip"));
			add(mThresholdLabel);

			mGammaLabel = new JLabel(language.getString("process.transform.gammalabel")); //$NON-NLS-1$
			mGammaLabel.setToolTipText(language.getString("process.transform.gammalabeltooltip"));
			add(mGammaLabel);

			mBlacklevelLabel = new JLabel(language.getString("process.transform.blacklvllabel")); //$NON-NLS-1$
			mBlacklevelLabel.setToolTipText(language.getString("process.transform.blacklvllabeltooltip"));			
			add(mBlacklevelLabel);

			mWhitelevelLabel = new JLabel(language.getString("process.transform.whitelvllabel")); //$NON-NLS-1$
			mWhitelevelLabel.setToolTipText(language.getString("process.transform.whitelvllabeltooltip"));		
			add(mWhitelevelLabel);

			mRedTransformLabel = new JLabel(language.getString("process.transform.redlabel")); //$NON-NLS-1$
			add(mRedTransformLabel);
			mRedThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedThreshold, 0.0, 1.0, spinnerChangeValue));
			mRedThresholdSpinner.setMaximumSize(maxSpinnerDim);
			mRedThresholdSpinner.addChangeListener(mChangeListener);
			add(mRedThresholdSpinner);
			mRedGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedGamma, 0.0, 100.0, spinnerChangeValue));
			mRedGammaSpinner.setMaximumSize(maxSpinnerDim);
			mRedGammaSpinner.addChangeListener(mChangeListener);
			add(mRedGammaSpinner);
			mRedBlacklevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedBlacklevel, 0.0, 1.0, spinnerChangeValue));
			mRedBlacklevelSpinner.setMaximumSize(maxSpinnerDim);
			mRedBlacklevelSpinner.addChangeListener(mChangeListener);
			add(mRedBlacklevelSpinner);
			mRedWhitelevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mRedWhitelevel, 0.0, 1.0, spinnerChangeValue));
			mRedWhitelevelSpinner.setMaximumSize(maxSpinnerDim);
			mRedWhitelevelSpinner.addChangeListener(mChangeListener);
			add(mRedWhitelevelSpinner);

			mGreenTransformLabel = new JLabel(language.getString("process.transform.greenlabel")); //$NON-NLS-1$
			add(mGreenTransformLabel);
			mGreenThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenThreshold, 0.0, 1.0, spinnerChangeValue));
			mGreenThresholdSpinner.setMaximumSize(maxSpinnerDim);
			mGreenThresholdSpinner.addChangeListener(mChangeListener);
			add(mGreenThresholdSpinner);
			mGreenGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenGamma, 0.0, 100.0, spinnerChangeValue));
			mGreenGammaSpinner.setMaximumSize(maxSpinnerDim);
			mGreenGammaSpinner.addChangeListener(mChangeListener);
			add(mGreenGammaSpinner);
			mGreenBlacklevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenBlacklevel, 0.0, 1.0, spinnerChangeValue));
			mGreenBlacklevelSpinner.setMaximumSize(maxSpinnerDim);
			mGreenBlacklevelSpinner.addChangeListener(mChangeListener);
			add(mGreenBlacklevelSpinner);
			mGreenWhitelevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mGreenWhitelevel, 0.0, 1.0, spinnerChangeValue));
			mGreenWhitelevelSpinner.setMaximumSize(maxSpinnerDim);
			mGreenWhitelevelSpinner.addChangeListener(mChangeListener);
			add(mGreenWhitelevelSpinner);

			mBlueTransformLabel = new JLabel(language.getString("process.transform.bluelabel")); //$NON-NLS-1$
			add(mBlueTransformLabel);
			mBlueThresholdSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueThreshold, 0.0, 1.0, spinnerChangeValue));
			mBlueThresholdSpinner.setMaximumSize(maxSpinnerDim);
			mBlueThresholdSpinner.addChangeListener(mChangeListener);
			add(mBlueThresholdSpinner);
			mBlueGammaSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueGamma, 0.0, 100.0, spinnerChangeValue));
			mBlueGammaSpinner.setMaximumSize(maxSpinnerDim);
			mBlueGammaSpinner.addChangeListener(mChangeListener);
			add(mBlueGammaSpinner);
			mBlueBlacklevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueBlacklevel, 0.0, 1.0, spinnerChangeValue));
			mBlueBlacklevelSpinner.setMaximumSize(maxSpinnerDim);
			mBlueBlacklevelSpinner.addChangeListener(mChangeListener);
			add(mBlueBlacklevelSpinner);
			mBlueWhitelevelSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mBlueWhitelevel, 0.0, 1.0, spinnerChangeValue));
			mBlueWhitelevelSpinner.setMaximumSize(maxSpinnerDim);
			mBlueWhitelevelSpinner.addChangeListener(mChangeListener);
			add(mBlueWhitelevelSpinner);


			mSaturationAdjustLabel = new JLabel(language.getString("process.transform.hsvsaturationlabel")); //$NON-NLS-1$
			add(mSaturationAdjustLabel);

			mSaturationAdjustSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mSaturationGain, 0.0, 1024.0, 0.01));

			mSaturationAdjustSpinner.setMaximumSize(maxDim);
			mSaturationAdjustSpinner.addChangeListener(mChangeListener);
			add(mSaturationAdjustSpinner);

			mValueAdjustLabel = new JLabel(language.getString("process.transform.hsvvaluelabel")); //$NON-NLS-1$
			add(mValueAdjustLabel);

			mValueAdjustSpinner = new JSpinner(new SpinnerNumberModel(mColorConfig.mValueGain, 0.0, 1024.0, 0.01));
			mValueAdjustSpinner.setMaximumSize(maxDim);
			mValueAdjustSpinner.addChangeListener(mChangeListener);
			add(mValueAdjustSpinner);

			
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
													.addComponent(mThresholdLabel)
													.addComponent(mDummyLabel)
													.addComponent(mGammaLabel)
													.addComponent(mBlacklevelLabel)	
													.addComponent(mWhitelevelLabel)
														)
												.addGroup(layout.createParallelGroup(Alignment.CENTER)
													.addComponent(mRedThresholdSpinner)
													.addComponent(mRedTransformLabel)
													.addComponent(mRedGammaSpinner)
													.addComponent(mRedBlacklevelSpinner)
													.addComponent(mRedWhitelevelSpinner)
														)
												.addGroup(layout.createParallelGroup(Alignment.CENTER)
													.addComponent(mGreenTransformLabel)
													.addComponent(mGreenThresholdSpinner)
													.addComponent(mGreenGammaSpinner)
													.addComponent(mGreenBlacklevelSpinner)
													.addComponent(mGreenWhitelevelSpinner)
														)
												.addGroup(layout.createParallelGroup(Alignment.CENTER)
														.addComponent(mBlueTransformLabel)
														.addComponent(mBlueThresholdSpinner)
														.addComponent(mBlueGammaSpinner)
														.addComponent(mBlueBlacklevelSpinner)
														.addComponent(mBlueWhitelevelSpinner)
														)
													)	
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
									.addComponent(mSaturationAdjustLabel)
									.addComponent(mValueAdjustLabel)
									.addComponent(mSshColorTransformPanel))
								.addGroup(layout.createParallelGroup()
									.addComponent(mSaturationAdjustSpinner)
									.addComponent(mValueAdjustSpinner))))
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
									.addComponent(mThresholdLabel)
									.addComponent(mRedThresholdSpinner)
									.addComponent(mGreenThresholdSpinner)
									.addComponent(mBlueThresholdSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mGammaLabel)
									.addComponent(mGreenGammaSpinner)
									.addComponent(mBlueGammaSpinner)
									.addComponent(mRedGammaSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mBlacklevelLabel)
									.addComponent(mBlueBlacklevelSpinner)
									.addComponent(mGreenBlacklevelSpinner)
									.addComponent(mRedBlacklevelSpinner))
								.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(mWhitelevelLabel)
									.addComponent(mGreenWhitelevelSpinner)
									.addComponent(mBlueWhitelevelSpinner)
									.addComponent(mRedWhitelevelSpinner)))
							.addComponent(mIndexLabel))
						.addGap(12)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mSaturationAdjustLabel)
							.addComponent(mSaturationAdjustSpinner))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mValueAdjustLabel)
							.addComponent(mValueAdjustSpinner))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mSshColorTransformPanel))
							)
			);
			layout.setAutoCreateGaps(true);
			setLayout(layout);
		
	
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
