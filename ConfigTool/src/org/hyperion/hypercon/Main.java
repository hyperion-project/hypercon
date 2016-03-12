package org.hyperion.hypercon;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import org.hyperion.hypercon.JavaVersion;
import org.hyperion.hypercon.gui.ConfigPanel;
import org.hyperion.hypercon.gui.SSH_Tab.SSHTrafficPrinterFrame;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;
import org.hyperion.hypercon.spec.TransformConfig;

/**
 * (static) Main-class for starting HyperCon (the Hyperion configuration file builder) as a standard 
 * JAVA application (contains the entry-point).
 */
public class Main {
	public static final String configFilename = "hypercon.dat"; //$NON-NLS-1$
	
	/** Some application settings (for easy/dirty access) */
	public static final HyperConConfig HyperConConfig = new HyperConConfig();

	public static String versionStr = "V1.00.0"; 
	public static String DateStr = "(11.03.2016)";
	/**
	 * Entry point to start HyperCon 
	 * 
	 * @param pArgs HyperCon does not have command line arguments
	 */
	
	public static void main(String[] pArgs) {
	
		final LedString ledString = new LedString();
		final SshAndColorPickerConfig  sshConfig = new SshAndColorPickerConfig();
		try {
			// Configure swing to use the system default look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		
	      //check if the minimum java version is ok
        if (! JavaVersion.isOKJVMVersion()) {
        	String message=language.getString("general.JavaVerionInfoMessage");
        	JOptionPane.showMessageDialog(new JFrame(), message, language.getString("general.HyperConInformationDialogTitle"),
        		        JOptionPane.INFORMATION_MESSAGE);
        }
			
		
		// Create a frame for the configuration panel
		JFrame frame = new JFrame();
		ErrorHandling.mainframe = frame;
		String title = language.getString("general.title") + " -" + ((versionStr != null && !versionStr.isEmpty())? (" (" + versionStr + ")") : "") + " " + DateStr;  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		frame.setTitle(title);
		frame.setSize(1400, 800);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(new ImageIcon(Main.class.getResource("HyperConIcon_64.png")).getImage()); //$NON-NLS-1$
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ConfigurationFile configFile = new ConfigurationFile();
					configFile.store(Main.HyperConConfig);
					configFile.store(ledString.mDeviceConfig);
					configFile.store(ledString.mLedFrameConfig);
					configFile.store(ledString.mProcessConfig);
					configFile.store(ledString.mBlackBorderConfig);
					configFile.store(ledString.mColorConfig);
					configFile.store(ledString.mMiscConfig);
					configFile.store(sshConfig);
					configFile.store(ledString.mGrabberv4l2Config);
					configFile.save(configFilename);
				} catch (Throwable t) {
					System.err.println(language.getString("general.failedtosave") + configFilename); //$NON-NLS-1$
				}
				SshConnectionModel.getInstance().disconnect();
				SSHTrafficPrinterFrame.close();
			}
		});
		
		if (new File(configFilename).exists()) {
			try {
				ConfigurationFile configFile = new ConfigurationFile();
				configFile.load(configFilename);
				configFile.restore(Main.HyperConConfig);
				configFile.restore(ledString.mDeviceConfig);
				configFile.restore(ledString.mLedFrameConfig);
				configFile.restore(ledString.mProcessConfig);
				configFile.restore(ledString.mBlackBorderConfig);
				configFile.restore(ledString.mColorConfig);
				configFile.restore(ledString.mMiscConfig);
				configFile.restore(sshConfig);
				configFile.restore(ledString.mGrabberv4l2Config);
			} catch (Throwable t) {
				System.err.println(language.getString("general.failedtoload") + configFilename); //$NON-NLS-1$
			}
			if (ledString.mColorConfig.mTransforms.isEmpty()) {
				ledString.mColorConfig.mTransforms.add(new TransformConfig());
			}
		}
		
		// Add the HyperCon configuration panel
		frame.setContentPane(new ConfigPanel(ledString, sshConfig));
		
		// Show the frame
		frame.setVisible(true);
	}

	static void ShowError(String message){
		new JOptionPane(message, JOptionPane.ERROR_MESSAGE);

	}
}
