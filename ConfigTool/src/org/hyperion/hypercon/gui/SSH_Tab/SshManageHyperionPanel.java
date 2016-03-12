package org.hyperion.hypercon.gui.SSH_Tab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hyperion.hypercon.ErrorHandling;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;

import com.jcraft.jsch.JSchException;
import javax.swing.GroupLayout.Alignment;


public class SshManageHyperionPanel extends JPanel implements Observer {
	
	private final SshAndColorPickerConfig mSshConfig;
    /**
     * the model to handle the connection
     */
	private final SshConnectionModel sshConnection;	
	
	private JButton mInstallHypButton;
	private JButton mRemoveHypButton;
	
	   public SshManageHyperionPanel(SshAndColorPickerConfig mSshConfig) {
	        super();

	        this.mSshConfig = mSshConfig;

	        sshConnection = SshConnectionModel.getInstance();
	        sshConnection.addObserver(this);

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
     * Create, add and layout Gui elements
     */
    private void initialise() {
        setBorder(BorderFactory.createTitledBorder(language.getString("ssh.sshmanage.title"))); //$NON-NLS-1$
	
        mInstallHypButton = new JButton(language.getString("ssh.sshmanage.InstallHypButton")); //$NON-NLS-1$
        mInstallHypButton.setToolTipText(language.getString("ssh.sshmanage.InstallHypButtontooltip"));
        mInstallHypButton.addActionListener(mActionListener);
        add(mInstallHypButton);
	
        mRemoveHypButton = new JButton(language.getString("ssh.sshmanage.RemoveHypButton")); //$NON-NLS-1$
        mRemoveHypButton.setToolTipText(language.getString("ssh.sshmanage.RemoveHypButtontooltip"));
        mRemoveHypButton.addActionListener(mActionListener);
        add(mRemoveHypButton);
        
    GroupLayout layout = new GroupLayout(this);
    layout.setHorizontalGroup(
    	layout.createParallelGroup()
    		.addGroup(layout.createSequentialGroup()
    			.addComponent(mInstallHypButton)
    			.addComponent(mRemoveHypButton)
    				)
    );
    layout.setVerticalGroup(
    	layout.createParallelGroup()
    		.addGroup(layout.createParallelGroup()
    			.addComponent(mInstallHypButton)
    			.addComponent(mRemoveHypButton))
    );
    layout.setAutoCreateGaps(true);
    setLayout(layout);

    setConnectionFieldsAcces(false);

}

/**
 * Enable or disable the Gui elememnts which depend on a shh connection
 * @param setEnabled
 */
private void setConnectionFieldsAcces(boolean setEnabled) {
	mInstallHypButton.setEnabled(setEnabled);
	mRemoveHypButton.setEnabled(setEnabled);
}
	
	 @Override
	    public void update(Observable o, Object arg) {
	        if (SshConnectionModel.getInstance().isConnected()) {
	            setConnectionFieldsAcces(true);
	        } else {
	            setConnectionFieldsAcces(false);
	        }
}
	  private final ActionListener mActionListener = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        
	        	if(e.getSource() == mInstallHypButton){
	        		SSHTrafficPrinterFrame.getInstance();
	        		try {
	       // 			sshConnection.sendInstallMessage();
	        			sshConnection.sendInstall();
	                } catch (JSchException e1) {
	                    ErrorHandling.ShowException(e1);
	                }	
	        	}
	        	if(e.getSource() == mRemoveHypButton){

	        	    String message=language.getString("ssh.sshmanage.RemoveHyperionButtonWarnMessage");
			        int reply = JOptionPane.showConfirmDialog(new JFrame(), message, language.getString("general.HyperConInformationDialogTitle"),
			        		    JOptionPane.YES_NO_OPTION);
			        	 if (reply == JOptionPane.YES_OPTION) {
			        		 try {
			        			 SSHTrafficPrinterFrame.getInstance();
								sshConnection.sendRemove();
							} catch (JSchException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			        		 
			        	 } 
   		

	        	}
	        }
	  };
}