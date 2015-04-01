package org.hyperion.hypercon.gui.Process_Tab;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.hyperion.hypercon.SshConnectionModel;

public class SshColorTransformPanel extends JPanel implements Observer {
	
	public JButton sendTransform;

	
	public SshColorTransformPanel() {
		super();
		SshConnectionModel.getInstance().addObserver(this);
		initialise();
	}
	
	private void initialise(){
		setBorder(BorderFactory.createTitledBorder("SSH"));

		sendTransform = new JButton("Send Color Transform");
		sendTransform.setToolTipText("Set the transform values temporarily via SSH. You have to create and copy a configuration file to set them permanently!");
		add(sendTransform);
		
		setGuiElementsEnabled(SshConnectionModel.getInstance().isConnected());
	}

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
		sendTransform.setEnabled(enabled);
		
	}
}
