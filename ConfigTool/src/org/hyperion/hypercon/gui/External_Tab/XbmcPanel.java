package org.hyperion.hypercon.gui.External_Tab;

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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.GroupLayout.Alignment;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.MiscConfig;


public class XbmcPanel extends JPanel {

	private final MiscConfig mMiscConfig;
	
	private JCheckBox mXbmcCheck;
	
	private JLabel mAddressLabel;
	private JTextField mAddressField;
	
	private JLabel mTcpPortLabel;
	private JSpinner mTcpPortSpinner;
	
	private JLabel mMenuLabel;
	private JCheckBox mMenuCheck;
	private JLabel mVideoLabel;
	private JCheckBox mVideoCheck;
	private JLabel mPictureLabel;
	private JCheckBox mPictureCheck;
	private JLabel mAudioLabel;
	private JCheckBox mAudioCheck;
	private JLabel mPauseLabel;
	private JCheckBox mPauseCheck;
	private JLabel mScreensaverLabel;
	private JCheckBox mScreensaverCheck;
	private JLabel mEnable3DLabel;
	private JCheckBox mEnable3DCeck;
	
	public XbmcPanel(final MiscConfig pMiscConfig) {
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
		setBorder(BorderFactory.createTitledBorder(language.getString("external.kodi.kodititle"))); //$NON-NLS-1$
		
		mXbmcCheck = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mXbmcCheck.setSelected(mMiscConfig.mXbmcCheckerEnabled);
		mXbmcCheck.addActionListener(mActionListener);
		add(mXbmcCheck);
		
		mAddressLabel = new JLabel(language.getString("external.kodi.serveradrlabel")); //$NON-NLS-1$
		add(mAddressLabel);
		
		mAddressField = new JTextField(mMiscConfig.mXbmcAddress);
		mAddressField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mMiscConfig.mXbmcAddress = mAddressField.getText();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mMiscConfig.mXbmcAddress = mAddressField.getText();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mMiscConfig.mXbmcAddress = mAddressField.getText();
			}
		});
		add(mAddressField);
		
		mTcpPortLabel = new JLabel(language.getString("external.kodi.portlabel")); //$NON-NLS-1$
		add(mTcpPortLabel);
		
		mTcpPortSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mXbmcTcpPort, 1, 65535, 1));
		mTcpPortSpinner.addChangeListener(mChangeListener);
		add(mTcpPortSpinner);
		
		
		mMenuLabel = new JLabel(language.getString("external.kodi.menulabel")); //$NON-NLS-1$
		add(mMenuLabel);

		mMenuCheck = new JCheckBox(); //$NON-NLS-1$
		mMenuCheck.setSelected(mMiscConfig.mMenuOn);
		mMenuCheck.setToolTipText(language.getString("external.kodi.menutooltip")); //$NON-NLS-1$
		mMenuCheck.addActionListener(mActionListener);
		add(mMenuCheck);
		
		mVideoLabel = new JLabel(language.getString("external.kodi.videolabel")); //$NON-NLS-1$
		add(mVideoLabel);

		mVideoCheck = new JCheckBox(); //$NON-NLS-1$
		mVideoCheck.setSelected(mMiscConfig.mVideoOn);
		mVideoCheck.setToolTipText(language.getString("external.kodi.videotooltip")); //$NON-NLS-1$
		mVideoCheck.addActionListener(mActionListener);
		add(mVideoCheck);		
		

		mPictureLabel = new JLabel(language.getString("external.kodi.picturelabel")); //$NON-NLS-1$
		add(mPictureLabel);
		

		mPictureCheck = new JCheckBox(); //$NON-NLS-1$
		mPictureCheck.setSelected(mMiscConfig.mPictureOn);
		mPictureCheck.setToolTipText(language.getString("external.kodi.picturetooltip")); //$NON-NLS-1$
		mPictureCheck.addActionListener(mActionListener);
		add(mPictureCheck);	
		
		mAudioLabel = new JLabel(language.getString("external.kodi.audiolabel")); //$NON-NLS-1$
		add(mAudioLabel);
			
		mAudioCheck = new JCheckBox(); //$NON-NLS-1$
		mAudioCheck.setSelected(mMiscConfig.mAudioOn);
		mAudioCheck.setToolTipText(language.getString("external.kodi.audiotooltip")); //$NON-NLS-1$
		mAudioCheck.addActionListener(mActionListener);
		add(mAudioCheck);			

		mPauseLabel = new JLabel(language.getString("external.kodi.pauselabel")); //$NON-NLS-1$
		add(mPauseLabel);
			
		mPauseCheck = new JCheckBox(); //$NON-NLS-1$
		mPauseCheck.setSelected(mMiscConfig.mAudioOn);
		mPauseCheck.setToolTipText(language.getString("external.kodi.pausetooltip")); //$NON-NLS-1$
		mPauseCheck.addActionListener(mActionListener);
		add(mPauseCheck);
		
		mScreensaverLabel = new JLabel(language.getString("external.kodi.screensaverlabel")); //$NON-NLS-1$
		add(mScreensaverLabel);

		mScreensaverCheck = new JCheckBox(); //$NON-NLS-1$
		mScreensaverCheck.setSelected(mMiscConfig.mScreensaverOn);
		mScreensaverCheck.setToolTipText(language.getString("external.kodi.sreensavertooltip")); //$NON-NLS-1$
		mScreensaverCheck.addActionListener(mActionListener);
		add(mScreensaverCheck);					

		mEnable3DLabel = new JLabel(language.getString("external.kodi.3dchecklabel")); //$NON-NLS-1$
		add(mEnable3DLabel);
	
		mEnable3DCeck = new JCheckBox(); //$NON-NLS-1$
		mEnable3DCeck.setSelected(mMiscConfig.m3DCheckingEnabled);
		mEnable3DCeck.setToolTipText(language.getString("external.kodi.3dchecktooltip")); //$NON-NLS-1$
		mEnable3DCeck.addActionListener(mActionListener);
		add(mEnable3DCeck);			

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(mXbmcCheck)
						.addComponent(mAddressLabel)
						.addComponent(mTcpPortLabel))
					.addGroup(layout.createParallelGroup()
//						.addComponent(mXbmcCheck)
						.addComponent(mAddressField)
						.addComponent(mTcpPortSpinner)))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(mMenuLabel)
								.addComponent(mVideoLabel)
								.addComponent(mPictureLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(mMenuCheck)
								.addComponent(mVideoCheck)
								.addComponent(mPictureCheck))
						.addGap(10)
						.addGroup(layout.createParallelGroup()
								.addComponent(mAudioLabel)
								.addComponent(mScreensaverLabel)
								.addComponent(mEnable3DLabel))						
						.addGroup(layout.createParallelGroup()
								.addComponent(mAudioCheck)
								.addComponent(mScreensaverCheck)
								.addComponent(mEnable3DCeck))								
						.addGap(10)
						.addGroup(layout.createParallelGroup()
								.addComponent(mPauseLabel))						
						.addGroup(layout.createParallelGroup()
								.addComponent(mPauseCheck))	
						)						
					
						);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(mXbmcCheck)
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mAddressLabel)
						.addComponent(mAddressField))
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mTcpPortLabel)
						.addComponent(mTcpPortSpinner))
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mMenuLabel)
						.addComponent(mMenuCheck)
						.addComponent(mAudioLabel)
						.addComponent(mAudioCheck)
						.addComponent(mPauseLabel)
						.addComponent(mPauseCheck))
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mVideoLabel)
						.addComponent(mVideoCheck)
						.addComponent(mScreensaverLabel)
						.addComponent(mScreensaverCheck))
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mPictureLabel)
						.addComponent(mPictureCheck)
						.addComponent(mEnable3DLabel)
						.addComponent(mEnable3DCeck))
						)
		);
		layout.setAutoCreateGaps(true);
		setLayout(layout);

		toggleEnabled(mMiscConfig.mXbmcCheckerEnabled);
	}
	
	private void toggleEnabled(boolean pEnabled) {
		mAddressLabel.setEnabled(pEnabled);
		mAddressField.setEnabled(pEnabled);
		
		mTcpPortSpinner.setEnabled(pEnabled);
		mTcpPortLabel.setEnabled(pEnabled);
		
		mMenuLabel.setEnabled(pEnabled);
		mMenuCheck.setEnabled(pEnabled);
		mVideoLabel.setEnabled(pEnabled);
		mVideoCheck.setEnabled(pEnabled);
		mPictureLabel.setEnabled(pEnabled);
		mPictureCheck.setEnabled(pEnabled);
		mAudioLabel.setEnabled(pEnabled);
		mAudioCheck.setEnabled(pEnabled);
		mPauseLabel.setEnabled(pEnabled);
		mPauseCheck.setEnabled(pEnabled);
		mScreensaverLabel.setEnabled(pEnabled);
		mScreensaverCheck.setEnabled(pEnabled);
		mEnable3DLabel.setEnabled(pEnabled);
		mEnable3DCeck.setEnabled(pEnabled);
	}
	
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mMiscConfig.mXbmcTcpPort = (Integer)mTcpPortSpinner.getValue();
		}	
	};
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mMiscConfig.mXbmcCheckerEnabled = mXbmcCheck.isSelected();
			mMiscConfig.mMenuOn = mMenuCheck.isSelected();
			mMiscConfig.mVideoOn = mVideoCheck.isSelected();
			mMiscConfig.mPictureOn = mPictureCheck.isSelected();
			mMiscConfig.mAudioOn = mAudioCheck.isSelected();
			mMiscConfig.mPauseOn = mPauseCheck.isSelected();
			mMiscConfig.mScreensaverOn = mScreensaverCheck.isSelected();
			mMiscConfig.m3DCheckingEnabled = mEnable3DCeck.isSelected();

			toggleEnabled(mMiscConfig.mXbmcCheckerEnabled);
		}
	};
}
