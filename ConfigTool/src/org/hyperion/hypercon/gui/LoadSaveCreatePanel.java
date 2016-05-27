package org.hyperion.hypercon.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hyperion.hypercon.ConfigurationFile;
import org.hyperion.hypercon.HyperConConfig;
import org.hyperion.hypercon.LedString;
import org.hyperion.hypercon.Main;
import org.hyperion.hypercon.Restart;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;
import org.hyperion.hypercon.spec.TransformConfig;

public class LoadSaveCreatePanel extends JPanel {
	
	final SshAndColorPickerConfig  sshConfig = new SshAndColorPickerConfig();
	public static final HyperConConfig HyperConConfig = new HyperConConfig();
	final static boolean shouldFill = true;
	
	private final LedString ledString;
	int returnVal;
	
	private JButton mSaveConfigButton;
	private JFileChooser mconfigfileChooser;
	
	private JButton mSaveSettingButton;
	private JFileChooser msavesettingfileChooser;
	
	private JButton mLoadSettingButton;
	private JFileChooser mloadsettingfileChooser;
	
	private JButton mhelpButton;
	
	public LoadSaveCreatePanel(final LedString pledString) {
		super();
		
		ledString = pledString;
		
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
		setBorder(BorderFactory.createEmptyBorder(5,5,8,5));

		mhelpButton = new JButton(language.getString("general.helpbutton"));
		mhelpButton.setToolTipText(language.getString("general.helptooltip")); //$NON-NLS-1$		
		mhelpButton.addActionListener(mActionListener);
		add(mhelpButton);
		
		mSaveConfigButton = new JButton(language.getString("general.createconfigbutton"));
		mSaveConfigButton.setToolTipText(language.getString("general.createconfigtooltip")); //$NON-NLS-1$
		mSaveConfigButton.setFont(mSaveConfigButton.getFont().deriveFont(14.0f));
		mSaveConfigButton.addActionListener(mActionListener);
		add(mSaveConfigButton);
		
		mconfigfileChooser = new JFileChooser();
		mconfigfileChooser.setCurrentDirectory(new java.io.File("."));
		mconfigfileChooser.setSelectedFile(new File("hyperion.config.json"));
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("(Hyperion) .json", "json");
		mconfigfileChooser.setFileFilter(filter2);
		mconfigfileChooser.addActionListener(mActionListener);

		mSaveSettingButton = new JButton(language.getString("general.savesettingsbutton"));
		mSaveSettingButton.setToolTipText(language.getString("general.savesettingstooltip")); //$NON-NLS-1$
		mSaveSettingButton.addActionListener(mActionListener);
		add(mSaveSettingButton);
		
		msavesettingfileChooser = new JFileChooser();
		msavesettingfileChooser.setCurrentDirectory(new java.io.File("."));
		msavesettingfileChooser.setSelectedFile(new File("HyperCon_settings.dat"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("(HyperCon) .dat", "dat");
		msavesettingfileChooser.setFileFilter(filter);
		msavesettingfileChooser.addActionListener(mActionListener);		
		
		mLoadSettingButton = new JButton(language.getString("general.loadsettingsbutton"));
		mLoadSettingButton.setToolTipText(language.getString("general.loadsettingstooltip")); //$NON-NLS-1$
		mLoadSettingButton.addActionListener(mActionListener);
		add(mLoadSettingButton);
		
		mloadsettingfileChooser = new JFileChooser();
		mloadsettingfileChooser.setCurrentDirectory(new java.io.File("."));
		mloadsettingfileChooser.setSelectedFile(new File("HyperCon_settings.dat"));
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("(HyperCon) .dat", "dat");
		mloadsettingfileChooser.setFileFilter(filter1);
		mloadsettingfileChooser.addActionListener(mActionListener);		

		
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()

						.addComponent(mSaveSettingButton)
						.addComponent(mLoadSettingButton)	
						.addComponent(mhelpButton)
						)					
				.addGroup(layout.createSequentialGroup()	
						.addComponent(mSaveConfigButton)	
						)					
					
						
						);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(mSaveSettingButton)
						.addComponent(mLoadSettingButton)
						.addComponent(mhelpButton))
					.addGap(7)
					.addGroup(layout.createSequentialGroup()
						.addComponent(mSaveConfigButton))
						)
		);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
	}

	private final ActionListener mActionListener = new ActionListener() {	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mSaveConfigButton){
			returnVal = mconfigfileChooser.showSaveDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				try {
					ledString.saveConfigFile(mconfigfileChooser.getSelectedFile().getAbsolutePath());
				} catch (IOException e1) {
		        	String message=language.getString("general.failedtosaveconfigMessage");
		        	JOptionPane.showMessageDialog(new JFrame(), message, language.getString("general.HyperConErrorDialogTitle"),
		        		        JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
				ConfigurationFile configFile = new ConfigurationFile();
				configFile.store(ledString.mDeviceConfig);
				configFile.store(ledString.mLedFrameConfig);
				configFile.store(ledString.mProcessConfig);
				configFile.store(ledString.mColorConfig);
				configFile.store(ledString.mMiscConfig);
				configFile.save(Main.configFilename);
			}
			}

		if(e.getSource() == mSaveSettingButton){
			returnVal = msavesettingfileChooser.showSaveDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				try {
					
					String path = msavesettingfileChooser.getSelectedFile().getAbsolutePath();
					ConfigurationFile configFile = new ConfigurationFile();
					configFile.store(Main.HyperConConfig);
					configFile.store(ledString.mDeviceConfig);
					configFile.store(ledString.mLedFrameConfig);
					configFile.store(ledString.mProcessConfig);						
					configFile.store(ledString.mColorConfig);
					configFile.store(ledString.mMiscConfig);
					configFile.store(sshConfig);
					configFile.store(ledString.mGrabberv4l2Config);
					configFile.save(path);
					
		        	String message=language.getString("general.successfulsavedsettingMessage");
		        	JOptionPane.showMessageDialog(new JFrame(), message, language.getString("general.HyperConInformationDialogTitle"),
		        		        JOptionPane.INFORMATION_MESSAGE);
		        	
				} catch (Throwable t) {
		        	String message=language.getString("general.failedtosavesettingMessage");
		        	JOptionPane.showMessageDialog(new JFrame(), message, language.getString("general.HyperConErrorDialogTitle"),
		        		        JOptionPane.ERROR_MESSAGE);
					System.err.println("failed to save settings (button)" /*+ configFilename*/); //$NON-NLS-1$
					}
			}
			}
	
		if(e.getSource() == mLoadSettingButton){
			returnVal = mloadsettingfileChooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				try {
					String path = mloadsettingfileChooser.getSelectedFile().getAbsolutePath();
					ConfigurationFile configFile = new ConfigurationFile();
					configFile.load(path);
					configFile.restore(Main.HyperConConfig);
					configFile.restore(ledString.mDeviceConfig);
					configFile.restore(ledString.mLedFrameConfig);
					configFile.restore(ledString.mProcessConfig);
					configFile.restore(ledString.mColorConfig);
					configFile.restore(ledString.mMiscConfig);
					configFile.restore(sshConfig);
					configFile.restore(ledString.mGrabberv4l2Config);
		        	String message=language.getString("general.successfulloadsettingMessage");
		        	JOptionPane.showMessageDialog(new JFrame(), message, language.getString("general.HyperConInformationDialogTitle"),
		        		        JOptionPane.INFORMATION_MESSAGE);
		        	Restart.main(null);
				} catch (Throwable t) {
		        	String message=language.getString("general.failedtoloadsettingMessage");
		        	JOptionPane.showMessageDialog(new JFrame(), message, language.getString("general.HyperConErrorDialogTitle"),
		        		        JOptionPane.ERROR_MESSAGE);
					System.err.println("failed to load settings (button)" /*+ configFilename*/); //$NON-NLS-1$

					}
				if (ledString.mColorConfig.mTransforms.isEmpty()) {
					ledString.mColorConfig.mTransforms.add(new TransformConfig());
				}
			}
			}
	
		 if(e.getSource() == mhelpButton){
		
			try {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
						if (desktop.isSupported(Desktop.Action.BROWSE)) {
						desktop.browse(new URI("https://wiki.hyperion-project.org"));
		        }
		    }
		} catch (Exception e1) {
		        e1.printStackTrace();
		}
		 }
		
		
	}


};

}

