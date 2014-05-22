package org.hyperion.hypercon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.hyperion.gui.ModelPanel;
import org.hyperion.hypercon.LedStringModel;
import org.hyperion.model.json.Jsonizer;

/**
 * The main-config panel of HyperCon. Includes the configuration and the panels to edit and 
 * write-out the configuration. This can be placed on JFrame, JDialog or JApplet as required.
 */
public class ConfigPanel extends JPanel {

	/** The LED configuration information*/
	private final LedStringModel ledString;
	
	/** Action for write the Hyperion deamon configuration file */
	private final Action mSaveConfigAction = new AbstractAction("Create Hyperion Configuration") {
		JFileChooser fileChooser = new JFileChooser();
		{
			fileChooser.setSelectedFile(new File("hyperion.config.json"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showSaveDialog(ConfigPanel.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			try {
				FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
				String jsonStr = Jsonizer.serialize(ledString);
				fw.write(jsonStr);
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	
	/** The panel for containing the example 'Hyperion TV' */
	private JPanel mTvPanel;
	/** The simulated 'Hyperion TV' */
	private LedSimulationComponent mHyperionTv;
	
	private JTabbedPane mSpecificationTabs = null;
	/** The left (WEST) side panel containing the diferent configuration panels */
	private JPanel mHardwarePanel = null;
	private JPanel mProcessPanel = null;
	private JPanel mExternalPanel = null;


	/** The button connected to mSaveConfigAction */
	private JButton mSaveConfigButton;
	
	/**
	 * Constructs the configuration panel with a default initialised led-frame and configuration
	 */
	public ConfigPanel(final LedStringModel pLedString) {
		super();
		
		ledString = pLedString;
		
		initialise();
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
		mSaveConfigButton = new JButton(mSaveConfigAction);
		panel.add(mSaveConfigButton, BorderLayout.SOUTH);
		mWestPanel.add(panel, BorderLayout.SOUTH);

		return mWestPanel;
	}
	private JTabbedPane getSpecificationTabs() {
		if (mSpecificationTabs == null) {
			mSpecificationTabs = new JTabbedPane();

			addTab("Hardware", getHardwarePanel());
			addTab("Image", getImagePanel());
			addTab("Colors", getColorsPanel());
			addTab("Grabbers", getGrabberPanel());
			addTab("External", getExternalPanel());
		}
		return mSpecificationTabs;
	}
	private void addTab(String pName, JComponent pTab) {
		JScrollPane scroll = new JScrollPane(pTab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		mSpecificationTabs.addTab(pName, scroll);
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
				
			mHyperionTv = new LedSimulationComponent(ledString.leds);
			mTvPanel.add(mHyperionTv, BorderLayout.CENTER);
		}
		return mTvPanel;
	}
	private final Dimension firstColDim = new Dimension(100, 20);
	
	private JPanel mGrabberPanel = null;
	
	private final JPanel getGrabberPanel() {
		if (mGrabberPanel == null) {
			mGrabberPanel = new JPanel();
			mGrabberPanel.setLayout(new BoxLayout(mGrabberPanel, BoxLayout.Y_AXIS));

			ModelPanel dispmanxPanel = new ModelPanel(ledString.dispmanxGrabber, firstColDim);
			dispmanxPanel.setBorder(BorderFactory.createTitledBorder("Dispmanx"));
			mGrabberPanel.add(dispmanxPanel);
			
			ModelPanel v4l2Panel = new ModelPanel(ledString.v4l2Grabber, firstColDim);
			v4l2Panel.setBorder(BorderFactory.createTitledBorder("V4L2"));
			mGrabberPanel.add(v4l2Panel);

			ModelPanel x11Panel = new ModelPanel(ledString.x11Grabber, firstColDim);
			x11Panel.setBorder(BorderFactory.createTitledBorder("X11"));
			mGrabberPanel.add(x11Panel);
		}
		return mGrabberPanel;
	}
	
	private JPanel getHardwarePanel() {
		if (mHardwarePanel == null) {
			mHardwarePanel = new JPanel();
			mHardwarePanel.setLayout(new BoxLayout(mHardwarePanel, BoxLayout.Y_AXIS));
			
			//mHardwarePanel.add(new DevicePanel(ledString.device));
			ModelPanel devicePanel = new ModelPanel(ledString.device, firstColDim);
			devicePanel.setBorder(BorderFactory.createTitledBorder("Devices"));
			mHardwarePanel.add(devicePanel);
			
			mHardwarePanel.add(Box.createVerticalGlue());
		}
		return mHardwarePanel;
	}
	private JPanel mImagePanel = null;
	private JPanel getImagePanel() {
		if (mImagePanel == null) {
			mImagePanel = new JPanel();
			mImagePanel.setLayout(new BoxLayout(mImagePanel, BoxLayout.Y_AXIS));
			
			ModelPanel framePanel = new ModelPanel(ledString.ledFrameConfig, firstColDim);
			framePanel.setBorder(BorderFactory.createTitledBorder("Frame"));
			mImagePanel.add(framePanel);
			
			ModelPanel blackborderPanel = new ModelPanel(ledString.blackborderdetector, firstColDim);
			blackborderPanel.setBorder(BorderFactory.createTitledBorder("Blackborder detector"));
			mImagePanel.add(blackborderPanel);
		}
		return mImagePanel;
	}
	
	private JPanel mColorsPanel = null;
	private JPanel getColorsPanel() {
		if (mColorsPanel == null) {
			mColorsPanel = new JPanel();
			mColorsPanel.setLayout(new BoxLayout(mColorsPanel, BoxLayout.Y_AXIS));
			
			ModelPanel transformPanel = new ModelPanel(ledString.color, firstColDim);
			transformPanel.setBorder(BorderFactory.createTitledBorder("Transform"));
			mColorsPanel.add(transformPanel);
		}
		return mColorsPanel;
	}
	
	private JPanel getProcessPanel() {
		if (mProcessPanel == null) {
			mProcessPanel = new JPanel();
			mProcessPanel.setLayout(new BoxLayout(mProcessPanel, BoxLayout.Y_AXIS));
			
			mProcessPanel.add(new ColorSmoothingPanel(ledString.color.smoothing));
			mProcessPanel.add(new ColorsPanel(ledString.color));
			mProcessPanel.add(Box.createVerticalGlue());
		}
		return mProcessPanel;
	}
	
	private JPanel getExternalPanel() {
		if (mExternalPanel == null) {
			mExternalPanel = new JPanel();
			mExternalPanel.setLayout(new BoxLayout(mExternalPanel, BoxLayout.Y_AXIS));
			
			mExternalPanel.add(new XbmcPanel(ledString.xbmcVideoChecker));
			mExternalPanel.add(new InterfacePanel(ledString));
			mExternalPanel.add(new EffectEnginePanel(ledString.effects, ledString.bootSequence));
			mExternalPanel.add(Box.createVerticalGlue());
		}
		return mExternalPanel;
	}
}
