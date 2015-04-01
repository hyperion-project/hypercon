package org.hyperion.hypercon.gui.SSH_Tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import com.jcraft.jsch.JSchException;
import org.hyperion.hypercon.ErrorHandling;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;

import com.bric.swing.ColorPicker;

/**
 * @author Fabian Hertwig
 *
 */
public class SshColorPickingPanel extends JPanel implements Observer, PropertyChangeListener{


	private ColorPicker colorPicker;
	private JButton setLedColor;
	private JButton clearLedColor;
	private JCheckBox autoUpdateCB;
	private JCheckBox expertView;
	private JCheckBox showColorWheel;
	private SshAndColorPickerConfig sshConfig;
	
	
	/**Constructor
	 * @param psshConfig 
	 * 
	 */
	public SshColorPickingPanel(SshAndColorPickerConfig psshConfig) {
		super();
		sshConfig = psshConfig;
		
		SshConnectionModel.getInstance().addObserver(this);
				
		initialise();
	}
	
	/**
	 * to set the Guielements sizes
	 */
	@Override
	@Transient
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension prefSize = super.getPreferredSize();
		return new Dimension(maxSize.width, prefSize.height);
	}
	
	/**
	 * Create Gui elements and layout
	 */
	private void initialise() {
		
		//All the Gui elements
		setBorder(BorderFactory.createTitledBorder("Set Led Color"));
		
		expertView = new JCheckBox("Expertview");
		expertView.setSelected(sshConfig.colorPickerInExpertmode);
		expertView.addActionListener(mActionListener);
		add(expertView);
		
		showColorWheel = new JCheckBox("Colorwheel");
		showColorWheel.setSelected(sshConfig.colorPickerShowColorWheel);
		showColorWheel.addActionListener(mActionListener);
		add(showColorWheel);
		
		colorPicker = new ColorPicker(false, false);
		colorPicker.setRGBControlsVisible(true);
		colorPicker.setPreviewSwatchVisible(true);
		colorPicker.setHexControlsVisible(false);
		colorPicker.setHSBControlsVisible(false);

		colorPicker.setMode(ColorPicker.HUE);
		colorPicker.setMinimumSize(new Dimension(150,150));
		colorPicker.addPropertyChangeListener(this);
		//TODO: make the color picker size less static
		colorPicker.setPreferredSize(new Dimension(200, 200));
		colorPicker.setRGB(255, 255, 255);
		if(!showColorWheel.isSelected()){
			colorPicker.setMode(ColorPicker.HUE);
		}else{
			colorPicker.setMode(ColorPicker.BRI);
		}
		add(colorPicker, BorderLayout.CENTER);
		
		autoUpdateCB = new JCheckBox("Auto Update");
		autoUpdateCB.setToolTipText("Automatically send new color selections, this may be a bit slow and laggy!");
		add(autoUpdateCB);
		
		setLedColor = new JButton("Set Led Color");
		setLedColor.addActionListener(mActionListener);
		add(setLedColor);
		
		clearLedColor = new JButton("Clear");
		clearLedColor.addActionListener(mActionListener);
		add(clearLedColor);
		
		setGuiElementsEnabled(false);

		//The Layout
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(showColorWheel)
						.addComponent(expertView)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(colorPicker))
		.addGroup(layout.createSequentialGroup()
					.addComponent(autoUpdateCB))
			.addGroup(layout.createSequentialGroup()
					.addComponent(setLedColor)
					.addComponent(clearLedColor)
		));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(showColorWheel)
						.addComponent(expertView)
						)
				.addGroup(layout.createSequentialGroup()
						.addComponent(colorPicker)
						.addComponent(autoUpdateCB)
						
				)
			
				.addGroup(layout.createParallelGroup()
						.addComponent(setLedColor)
						.addComponent(clearLedColor)));
	}
	
	/**
	 * Listener for the buttons and checkboxes
	 */
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == setLedColor){
				int[] chosenColor = colorPicker.getRGB();
				try {
					SshConnectionModel.getInstance().sendLedColor(chosenColor[0], chosenColor[1], chosenColor[2]);
				} catch (JSchException e1) {
					ErrorHandling.ShowException(e1);
				}
			}else if(e.getSource() == clearLedColor){
				try {
					SshConnectionModel.getInstance().sendClear();
				} catch (JSchException e1) {
					ErrorHandling.ShowException(e1);
				}
			}else if(e.getSource() == expertView){
				colorPicker.setExpertControlsVisible(expertView.isSelected());
				sshConfig.colorPickerInExpertmode = expertView.isSelected();
				
			}else if(e.getSource() == showColorWheel){
				sshConfig.colorPickerShowColorWheel = showColorWheel.isSelected();
				if(!showColorWheel.isSelected()){
					colorPicker.setMode(ColorPicker.HUE);
				}else{
					colorPicker.setMode(ColorPicker.BRI);
				}
			}

		
		}
	};
	
	
	/**
	 * is called when the ssh connection status changes
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(SshConnectionModel.getInstance().isConnected()){
			setGuiElementsEnabled(true);

		}else{
			setGuiElementsEnabled(false);
		}
		
	}

	/**
	 * Is called from the color picker when the color changed
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(autoUpdateCB != null && autoUpdateCB.isSelected() && evt.getPropertyName().equals("selected color")){
			int[] chosenColor = colorPicker.getRGB();
			try {
				SshConnectionModel.getInstance().sendLedColor(chosenColor[0], chosenColor[1], chosenColor[2]);
			} catch (JSchException e) {
				ErrorHandling.ShowException(e);
			}
		}
		
	}

	/**
	 * Enable or disabel all guielements which shouldnt be editable if there is no connection
	 * @param enabled
	 */
	private void setGuiElementsEnabled(boolean enabled){
		setLedColor.setEnabled(enabled);
		clearLedColor.setEnabled(enabled);
		autoUpdateCB.setEnabled(enabled);
		
	}
	
}
