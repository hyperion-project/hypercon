package org.hyperion.hypercon.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.hyperion.hypercon.LedFrameFactory;
import org.hyperion.hypercon.LedString;
import org.hyperion.hypercon.gui.LoadSaveCreatePanel;
import org.hyperion.hypercon.gui.External_Tab.BootEffectPanel;
import org.hyperion.hypercon.gui.External_Tab.InterfacePanel;
import org.hyperion.hypercon.gui.External_Tab.XbmcPanel;
import org.hyperion.hypercon.gui.External_Tab.ForwardPanel;
import org.hyperion.hypercon.gui.Grabber_Tab.FrameGrabberPanel;
import org.hyperion.hypercon.gui.Grabber_Tab.Grabberv4l2Panel;
import org.hyperion.hypercon.gui.Hardware_Tab.DevicePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.ImageProcessPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.BlackBorderPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.LedFramePanel;
import org.hyperion.hypercon.gui.LedSimulation.LedSimulationComponent;
import org.hyperion.hypercon.gui.Process_Tab.ColorSmoothingPanel;
import org.hyperion.hypercon.gui.Process_Tab.ColorsPanel;
import org.hyperion.hypercon.gui.SSH_Tab.SshColorPickingPanel;
import org.hyperion.hypercon.gui.SSH_Tab.SshCommandSenderPanel;
import org.hyperion.hypercon.gui.SSH_Tab.SshConnectionPanel;
import org.hyperion.hypercon.gui.SSH_Tab.SshManageHyperionPanel;
import org.hyperion.hypercon.gui.SSH_Tab.SshSendConfigPanel;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;
/**
 * The main-config panel of HyperCon. Includes the configuration and the panels to edit and 
 * write-out the configuration. This can be placed on JFrame, JDialog or JApplet as required.
 */
public class ConfigPanel extends JPanel {

	/** The LED configuration information*/
//	final LedString leddString = new LedString();
	private final LedString ledString;
	private final SshAndColorPickerConfig sshConfig;
		
	/** The panel for containing the example 'Hyperion TV' */
	private JPanel mTvPanel;
	/** The simulated 'Hyperion TV' */
	private LedSimulationComponent mHyperionTv;
	
	private JTabbedPane mSpecificationTabs = null;
	/** The left (WEST) side panel containing the different configuration panels */
	private JPanel mLoadSaveCreatePanel = null;
	private JPanel mHardwarePanel = null;
	private JPanel mProcessPanel = null;
	private JPanel mExternalPanel = null;
	private JPanel mTestingPanel = null;
	private JPanel mGrabberPanel = null;


	/**
	 * Constructs the configuration panel with a default initialised led-frame and configuration
	 */
	public ConfigPanel(final LedString pLedString, final SshAndColorPickerConfig pSshConfig) {
		super();
		
		ledString = pLedString;
		sshConfig = pSshConfig;
		initialise();
		
		// Compute the individual leds for the current configuration
		ledString.leds = LedFrameFactory.construct(ledString.mLedFrameConfig, ledString.mProcessConfig);
		mHyperionTv.setLeds(ledString.leds);
		
		// Add Observer to update the individual leds if the configuration changes
		final Observer observer = new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				ledString.leds = LedFrameFactory.construct(ledString.mLedFrameConfig, ledString.mProcessConfig);
				mHyperionTv.setLeds(ledString.leds);
				mHyperionTv.repaint();
			}
		};
		ledString.mLedFrameConfig.addObserver(observer);
		ledString.mProcessConfig.addObserver(observer);
	}
	
	/**
	 * Initialises the config-panel 
	 */
	private void initialise() {
		setLayout(new BorderLayout());
		
		add(getTvPanel(), BorderLayout.CENTER);
		add(getWestPanel(), BorderLayout.WEST);
		
	}
	private JPanel getWestPanel() {
		JPanel mWestPanel = new JPanel();
		mWestPanel.setLayout(new BorderLayout());
		
		mWestPanel.add(getSpecificationTabs(), BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));		
		
		mWestPanel.add(panel, BorderLayout.SOUTH);
		mWestPanel.add(getSaveLoadCreatePanel(), BorderLayout.SOUTH);			
		
		return mWestPanel;
	}
	private JTabbedPane getSpecificationTabs() {
		if (mSpecificationTabs == null) {
			mSpecificationTabs = new JTabbedPane();
			mSpecificationTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			
			mSpecificationTabs.addTab(language.getString("general.tab.hardware"), new JScrollPane(getHardwarePanel())); //$NON-NLS-1$
			mSpecificationTabs.addTab(language.getString("general.tab.process"),  new JScrollPane(getProcessPanel())); //$NON-NLS-1$
			mSpecificationTabs.addTab(language.getString("general.tab.grabber"),  new JScrollPane(getGrabberPanel())); //$NON-NLS-1$
			mSpecificationTabs.addTab(language.getString("general.tab.external"), new JScrollPane(getExternalPanel())); //$NON-NLS-1$
			mSpecificationTabs.addTab(language.getString("general.tab.ssh"),      new JScrollPane(getTestingPanel())); //$NON-NLS-1$
		}
		return mSpecificationTabs;
	}
	
	/* Add save/load/create config to main panel*/
	private JPanel getSaveLoadCreatePanel() {
		if (mLoadSaveCreatePanel == null) {
			mLoadSaveCreatePanel = new JPanel();
			mLoadSaveCreatePanel.setLayout(new BoxLayout(mLoadSaveCreatePanel, BoxLayout.Y_AXIS));
			
			mLoadSaveCreatePanel.add(new LoadSaveCreatePanel(ledString));
			mLoadSaveCreatePanel.add(Box.createVerticalGlue());
		}
		return mLoadSaveCreatePanel;
	}
	
	
	/**
	 * Created, if not exists, and returns the panel holding the simulated 'Hyperion TV'
	 * 
	 * @return The Tv panel
	 */
	private JPanel getTvPanel() {
		if (mTvPanel == null) {
			mTvPanel = new JPanel();
			mTvPanel.setLayout(new BorderLayout());
				
			mHyperionTv = new LedSimulationComponent(ledString.leds, ledString.mGrabberv4l2Config);
			mTvPanel.add(mHyperionTv, BorderLayout.CENTER);
		}
		return mTvPanel;
	}
	
	private JPanel getHardwarePanel() {
		if (mHardwarePanel == null) {
			mHardwarePanel = new JPanel();
			mHardwarePanel.setLayout(new BoxLayout(mHardwarePanel, BoxLayout.Y_AXIS));
			
			mHardwarePanel.add(new DevicePanel(ledString.mDeviceConfig));
			mHardwarePanel.add(new LedFramePanel(ledString.mLedFrameConfig));
			mHardwarePanel.add(new ImageProcessPanel(ledString.mProcessConfig));
			mHardwarePanel.add(new BlackBorderPanel(ledString.mMiscConfig));
			mHardwarePanel.add(Box.createVerticalGlue());
		}
		return mHardwarePanel;
	}
	
	private JPanel getProcessPanel() {
		if (mProcessPanel == null) {
			mProcessPanel = new JPanel();
			mProcessPanel.setLayout(new BoxLayout(mProcessPanel, BoxLayout.Y_AXIS));
			

			mProcessPanel.add(new ColorSmoothingPanel(ledString.mColorConfig));
			mProcessPanel.add(new ColorsPanel(ledString.mColorConfig));
	//		mProcessPanel.add(new SshColorTransformPanel
			mProcessPanel.add(Box.createVerticalGlue());
		}
		return mProcessPanel;
	}
	
	private JPanel getExternalPanel() {
		if (mExternalPanel == null) {
			mExternalPanel = new JPanel();
			mExternalPanel.setLayout(new BoxLayout(mExternalPanel, BoxLayout.Y_AXIS));
			
			mExternalPanel.add(new XbmcPanel(ledString.mMiscConfig));
			mExternalPanel.add(new InterfacePanel(ledString.mMiscConfig));
			mExternalPanel.add(new BootEffectPanel(ledString.mMiscConfig));
			mExternalPanel.add(new ForwardPanel(ledString.mMiscConfig));
			mExternalPanel.add(Box.createVerticalGlue());
		}
		return mExternalPanel;
	}
	
	private JPanel getTestingPanel(){
		if( mTestingPanel == null){
			mTestingPanel = new JPanel();
			mTestingPanel.setLayout(new BoxLayout(mTestingPanel, BoxLayout.Y_AXIS));
			mTestingPanel.add(new SshConnectionPanel(sshConfig));
			mTestingPanel.add(new SshManageHyperionPanel(sshConfig));
			mTestingPanel.add(new SshSendConfigPanel(sshConfig));
			mTestingPanel.add(new SshColorPickingPanel(sshConfig));
//			mTestingPanel.add(new SshCommandSenderPanel(sshConfig));			
			mTestingPanel.add(Box.createVerticalGlue());

		}
		
		return mTestingPanel;
	}

	private JPanel getGrabberPanel(){
		if( mGrabberPanel == null){
			mGrabberPanel = new JPanel();
			mGrabberPanel.setLayout(new BoxLayout(mGrabberPanel, BoxLayout.Y_AXIS));

			mGrabberPanel.add(new FrameGrabberPanel(ledString.mMiscConfig));
			mGrabberPanel.add(new Grabberv4l2Panel(ledString.mGrabberv4l2Config));
			mGrabberPanel.add(Box.createVerticalGlue());

		}

		return mGrabberPanel;
	}
}
