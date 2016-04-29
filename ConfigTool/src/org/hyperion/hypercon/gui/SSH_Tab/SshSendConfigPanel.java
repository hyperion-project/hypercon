package org.hyperion.hypercon.gui.SSH_Tab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.hyperion.hypercon.ErrorHandling;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class SshSendConfigPanel extends JPanel implements Observer {
    /**
     * Here the commands are saved
     */
    private final SshAndColorPickerConfig mSshConfig;

    /**
     * the model to handle the connection
     */
    private final SshConnectionModel sshConnection;
           
    private JButton SourceFileButton;
    private JFileChooser SourceFileChooser;
    
    private JButton sendConfigButton;


    public SshSendConfigPanel(SshAndColorPickerConfig mSshConfig) {
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

        setBorder(BorderFactory.createTitledBorder(language.getString("ssh.sshsendconfig.title"))); 

        sendConfigButton = new JButton(language.getString("ssh.sshsendconfig.sendbutton")); 
        sendConfigButton.setToolTipText(language.getString("ssh.sshsendconfig.sendbuttontooltip"));
        sendConfigButton.addActionListener(mActionListener);
        add(sendConfigButton);

        SourceFileButton = new JButton(language.getString("ssh.sshsendconfig.sourcefilebutton")); 
        SourceFileButton.setToolTipText(language.getString("ssh.sshsendconfig.sourcefilebuttontooltip"));
        SourceFileButton.addActionListener(mActionListener);
        add(SourceFileButton);
        
		SourceFileChooser = new JFileChooser();
		SourceFileChooser.setCurrentDirectory(new java.io.File("."));
		SourceFileChooser.setSelectedFile(new File("hyperion.config.json"));
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("(Hyperion) .json", "json");
		SourceFileChooser.setFileFilter(filter1);
		SourceFileChooser.addActionListener(mActionListener);
        
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(SourceFileButton)
                                        .addComponent(sendConfigButton))
                ));

        layout.setVerticalGroup(layout.createSequentialGroup()

                        .addGroup(layout.createParallelGroup()
                                .addComponent(SourceFileButton)
                        		.addComponent(sendConfigButton))
        		);

        setConnectionFieldsAcces(false);

    }

    /**
     * Enable or disable the Gui elememnts which depend on a shh connection
     * @param setEnabled
     */
    private void setConnectionFieldsAcces(boolean setEnabled) {
        sendConfigButton.setEnabled(setEnabled);
        SourceFileButton.setEnabled(setEnabled);


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
        	
        	if(e.getSource() == sendConfigButton){
        		SSHTrafficPrinterFrame.getInstance();
        		try {
        			sshConnection.sendConfig(mSshConfig.srcPath,mSshConfig.FileName);
        		} 
        		catch (SftpException | JSchException e1) {
		  //      	JOptionPane.showMessageDialog(new JFrame(), language.getString("ssh.sshsendconfig.confuploadfailedMessage"), language.getString("general.HyperConErrorDialogTitle"),
		  //TODO:Move to lower stage for error exception      		        JOptionPane.ERROR_MESSAGE);
                }	
        	}

		       if(e.getSource() == SourceFileButton){
		       int returnVal = SourceFileChooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION){
		        	try {
		        			String rawsrcPath = SourceFileChooser.getSelectedFile().getAbsolutePath();
		        			mSshConfig.FileName = SourceFileChooser.getSelectedFile().getName();
		        			String raw1srcPath = rawsrcPath.substring(0,rawsrcPath.lastIndexOf(File.separator));
		        			mSshConfig.srcPath = raw1srcPath.replace("\\","/");
		        					} catch (Throwable t) {
		        						}

		        					}
		        }
	
        }
    };




}
