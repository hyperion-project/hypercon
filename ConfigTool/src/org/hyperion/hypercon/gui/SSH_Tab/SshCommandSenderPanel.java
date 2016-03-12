package org.hyperion.hypercon.gui.SSH_Tab;

import com.jcraft.jsch.JSchException;
import org.hyperion.hypercon.ErrorHandling;
import org.hyperion.hypercon.SshConnectionModel;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.SshAndColorPickerConfig;
import org.hyperion.hypercon.spec.SshCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Fabian on 10.03.2015.
 */
public class SshCommandSenderPanel extends JPanel implements Observer {

    /**
     * Here the commands are saved
     */
    private final SshAndColorPickerConfig mSshConfig;

    /**
     * the model to handle the connection
     */
    private final SshConnectionModel sshConnection;

    private JComboBox<String> commands_cb;
    private JButton send_but;
    private JButton del_but;


    public SshCommandSenderPanel(SshAndColorPickerConfig mSshConfig) {
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

        setBorder(BorderFactory.createTitledBorder(language.getString("ssh.sshcommand.title"))); //$NON-NLS-1$


        commands_cb = new JComboBox<>();
        for (SshCommand sshCommand : mSshConfig.sshCommands) {
            commands_cb.addItem(sshCommand.getCommand());
        }


        commands_cb.setEditable(true);
        commands_cb.addActionListener(mActionListener);
        add(commands_cb);

        send_but = new JButton(language.getString("ssh.sshcommand.sendbutton")); //$NON-NLS-1$
        send_but.addActionListener(mActionListener);
        add(send_but);

        del_but = new JButton(language.getString("ssh.sshcommand.deletebutton")); //$NON-NLS-1$
        del_but.addActionListener(mActionListener);
        add(del_but);

        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(commands_cb)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(send_but)
                                        .addComponent(del_but)
                                )
                ));

        layout.setVerticalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(commands_cb))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(send_but)
                                .addComponent(del_but))
        );

        setConnectionFieldsAcces(false);

    }

    /**
     * Enable or disable the Gui elememnts which depend on a shh connection
     * @param setEnabled
     */
    private void setConnectionFieldsAcces(boolean setEnabled) {
        send_but.setEnabled(setEnabled);
        commands_cb.setEnabled(setEnabled);
        del_but.setEnabled(setEnabled);


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

            if (e.getActionCommand() == "comboBoxEdited") { //$NON-NLS-1$
                if(commands_cb.getSelectedIndex() == -1){
                    commands_cb.addItem(String.valueOf(commands_cb.getSelectedItem()));
                }
            }

            if (e.getSource() == send_but && commands_cb.getSelectedItem() != null) {

                boolean commandAlreadyInList = false;
                for (SshCommand sshCommand : mSshConfig.sshCommands) {
                    if(sshCommand.getCommand().equals(commands_cb.getSelectedItem().toString())){
                        commandAlreadyInList = true;
                    }
                }

                if(!commandAlreadyInList){
                    mSshConfig.sshCommands.add(new SshCommand(commands_cb.getSelectedItem().toString()));
                }
                try {
                    sshConnection.sendCommandInNewThread(commands_cb.getSelectedItem().toString());
                } catch (JSchException e1) {
                    ErrorHandling.ShowException(e1);
                }
            }
            else if (e.getSource() == del_but && commands_cb.getSelectedItem() != null){
                for (SshCommand sshCommand : mSshConfig.sshCommands) {
                    if(sshCommand.getCommand().equals(commands_cb.getSelectedItem().toString())){
                        mSshConfig.sshCommands.removeElement(sshCommand);
                        break;
                    }
                }
                commands_cb.removeItemAt(commands_cb.getSelectedIndex());
                commands_cb.setSelectedIndex(-1);
            }
        }
    };




}
