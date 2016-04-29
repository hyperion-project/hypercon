package org.hyperion.hypercon.gui.Process_Tab;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.language.language;

public class SshColorTransformPanel extends JPanel implements Observer {
	
	public JButton sendTransform;
	public JCheckBox sendTransformContinuousCheckBox;
	
	public SshColorTransformPanel() {
		super();
		SshConnectionModel.getInstance().addObserver(this);
		initialise();
	}
	
	private void initialise(){
//		setBorder(BorderFactory.createTitledBorder(language.getString("process.ssh.title"))); //$NON-NLS-1$

		sendTransform = new JButton(language.getString("process.ssh.sendcolortransformlabel")); //$NON-NLS-1$
		sendTransform.setToolTipText(language.getString("process.ssh.sendcolortransformtooltip")); //$NON-NLS-1$
		add(sendTransform);
		
		sendTransformContinuousCheckBox = new JCheckBox(language.getString("process.ssh.sendTransContinuouslabel")); //$NON-NLS-1$
		sendTransformContinuousCheckBox.setToolTipText(language.getString("process.ssh.sendTransContinuoustooltip")); //$NON-NLS-1$
		add(sendTransformContinuousCheckBox);
		
		
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout
		.createSequentialGroup()					
			.addGroup(layout.createParallelGroup()
					.addComponent(sendTransform)
					.addComponent(sendTransformContinuousCheckBox)
					)
					);
		layout.setVerticalGroup(layout
			.createSequentialGroup()
				.addComponent(sendTransform)
				.addComponent(sendTransformContinuousCheckBox)
				);

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
		sendTransformContinuousCheckBox.setEnabled(enabled);
	}
}
