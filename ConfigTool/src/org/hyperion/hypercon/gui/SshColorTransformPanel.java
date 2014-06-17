package org.hyperion.hypercon.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.hyperion.hypercon.SshConnectionModel;

public class SshColorTransformPanel extends JPanel implements Observer {
	
	// the autoupdate checkbox doesnt work right now because it is not correctly set between multiple instances of this class
//	public JCheckBox autoUpdate;
	public JButton sendTransform;

	
	public SshColorTransformPanel() {
		super();
		SshConnectionModel.getInstance().addObserver(this);
		initialise();
	}
	
	private void initialise(){
		setBorder(BorderFactory.createTitledBorder("SSH"));

		
//		autoUpdate = new JCheckBox("Auto Send Config");
//		autoUpdate.setToolTipText("Automatically send the color transform data to hyperiond when any of the data changes in this application,");
////		autoUpdate.addActionListener(mActionListener);
//		autoUpdate.setSelected(SshConnectionModel.getInstance().autoSendColorTransFormEnabled);
		
		sendTransform = new JButton("Send Color Transform");
		sendTransform.setToolTipText("Set the transform values temporarily via SSH. You have to create and copy a configuration file to set them permanently!");
//		sendTransform.addActionListener(mActionListener);
		add(sendTransform);
//		add(autoUpdate);
		
		setGuiElementsEnabled(SshConnectionModel.getInstance().isConnected());
	}

	//the actionlistener is now in the colorTransformPanel class
//	private final ActionListener mActionListener = new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if (e.getSource() == autoUpdate) {
//				SshConnectionModel.getInstance().autoSendColorTransFormEnabled = autoUpdate.isSelected();
//
//			
//			}
//
//		}
//	};


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

	private void setGuiElementsEnabled(boolean enabled) {
//		autoUpdate.setEnabled(enabled);
		sendTransform.setEnabled(enabled);
		
	}
}
