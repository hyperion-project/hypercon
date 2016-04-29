package org.hyperion.hypercon.gui.SSH_Tab;

import org.hyperion.hypercon.Main;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.ssh.ConnectionListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Transient;

/**
 * Created by Fabian on 22.02.2015.
 *
 * A frame to print ssh traffic
 */
public class SSHTrafficPrinterFrame extends JFrame implements ConnectionListener{

    /**
     * Singleton object
     */
    private static SSHTrafficPrinterFrame instance;

    /**
     * Singleton
     * @return the only instance of this object
     */
    public static SSHTrafficPrinterFrame getInstance(){
        if(instance == null){
            instance = new SSHTrafficPrinterFrame();
        }else{
            //Show and hide this window on close and open
            instance.setVisible(true);
        }
        return instance;
    }

    /**
     * The Textarea to show the commands
     */
    JTextArea txtArea;

    private SSHTrafficPrinterFrame() {

        super("SSH Traffic");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Show and hide this window on close and open
                setVisible(false);
            }
        });

        try {
            // Configure swing to use the system default look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SshConnectionModel.getInstance().addConnectionListener(this);

        setIconImage(new ImageIcon(Main.class.getResource("HyperConIcon_64.png")).getImage());
        setLayout(new BorderLayout());


        txtArea = new JTextArea("SSH Traffic:\n");
        txtArea.setEditable(false);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtArea);

        //Always scroll to the end
        DefaultCaret caret = (DefaultCaret)txtArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        add(scrollPane, BorderLayout.CENTER);
        setSize(new Dimension(820,600));
    }

    @Override
    @Transient
    public Dimension getMaximumSize() {
        Dimension maxSize = super.getMaximumSize();
        Dimension prefSize = super.getPreferredSize();
        return new Dimension(maxSize.width, prefSize.height);
    }

    /**
     * a static close function so the getInstance().dispose() way wont open the frame for a split second
     */
    public static void close(){
        if(instance != null){
            instance.dispose();
        }
    }

    @Override
    public void connected() {
        txtArea.append("ssh connected\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());
    }

    @Override
    public void disconnected() {
        txtArea.append("ssh disconnected\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());

    }

    @Override
    public void commandExec(String pCommand) {
        txtArea.append("ssh out: \t" + pCommand + "\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());


    }

    @Override
    public void getFile(String src, String dst) {
        txtArea.append("sftp getFile(" + src + ", " + dst + ")\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());

    }
    @Override
    public void sendConfigFile(String srcPath, String FileName, String hyperionConfigTargetCall) {
        txtArea.append("sftp Send Hyperion Config - Sourcepath: " + srcPath + ", Targetpath: " + hyperionConfigTargetCall +", Filename: "+ FileName +"\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());

    }
    @Override
    public void commandFinished(String pCommand) {

    }

    @Override
    public void getFileFinished(String src, String dst) {

    }
    @Override
    public void sendConfigFileFinished(String srcPath, String dstPath, String fileName) {

    }
    @Override
    public void addLine(String pLine) {
        txtArea.append("ssh in: \t" + pLine + "\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());

    }

    @Override
    public void addError(String pLine) {
    	txtArea.append("ssh out: \t" + pLine + "\n");
//      txtArea.append("ssh error: \t" + pLine + "\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());


    }

}
