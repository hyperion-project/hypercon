package org.hyperion.hypercon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.hyperion.hypercon.SshConnectionModel;

import com.bric.swing.ColorPicker;

public class ColorPickingPanel extends JPanel implements Observer{

	ColorPicker colorPicker;
	JButton setLedColor;
	JButton clearLedColor;
	
	/**Constructor
	 * 
	 */
	public ColorPickingPanel() {
		super();
		
				
		initialise();
	}
	
	@Override
	@Transient
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension prefSize = super.getPreferredSize();
		SshConnectionModel.getInstance().addObserver(this);
		
		return new Dimension(maxSize.width, prefSize.height);
	}

	private void initialise() {
		setBorder(BorderFactory.createTitledBorder("Send Color"));
		
		//TODO: make the color picker expert controls setable by the user
		colorPicker = new ColorPicker(false, false);
		colorPicker.setRGBControlsVisible(true);
		colorPicker.setPreviewSwatchVisible(true);
		colorPicker.setHexControlsVisible(false);
		colorPicker.setHSBControlsVisible(false);

		colorPicker.setMode(ColorPicker.HUE);
		colorPicker.setMaximumSize(getMaximumSize());
		colorPicker.setMinimumSize(new Dimension(150,150));
		//TODO: make the size less static
		colorPicker.setPreferredSize(new Dimension(200, 200));
		add(colorPicker, BorderLayout.CENTER);
		
		setLedColor = new JButton("Set Led Color");
		setLedColor.addActionListener(mActionListener);
		setLedColor.setEnabled(false);
		add(setLedColor);
		
		clearLedColor = new JButton("Clear");
		clearLedColor.addActionListener(mActionListener);
		clearLedColor.setEnabled(false);
		add(clearLedColor);
		

		
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				
						.addComponent(colorPicker)
		
			.addGroup(layout.createSequentialGroup()
					.addComponent(setLedColor)
					.addComponent(clearLedColor)
		));
		layout.setVerticalGroup(layout.createSequentialGroup()
			
						.addComponent(colorPicker)
			
				.addGroup(layout.createParallelGroup()
						.addComponent(setLedColor)
						.addComponent(clearLedColor)));
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == setLedColor){
				int[] chosenColor = colorPicker.getRGB();
				SshConnectionModel.getInstance().sendLedColor(chosenColor[0], chosenColor[1], chosenColor[2]);
			}else if(e.getSource() == clearLedColor){
				SshConnectionModel.getInstance().sendClear();
			}

		
		}
	};

	@Override
	public void update(Observable arg0, Object arg1) {
		if(SshConnectionModel.getInstance().isConnected()){
			setLedColor.setEnabled(true);
			clearLedColor.setEnabled(true);
		}else{
			setLedColor.setEnabled(false);
			clearLedColor.setEnabled(false);
		}
		
	}
}
