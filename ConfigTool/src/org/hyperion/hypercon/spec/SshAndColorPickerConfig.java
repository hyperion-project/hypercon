package org.hyperion.hypercon.spec;

import java.util.Vector;

/**
 * @author Fabian Hertwig
 *
 */
public class SshAndColorPickerConfig {
	
	public String ipAdress;
	public int port;
	public String username;
	public String password;
	public boolean colorPickerInExpertmode;
	public boolean colorPickerShowColorWheel;
	public Vector<SshCommand> sshCommands;
    public HyperionRemoteCalls.SystemTypes selectedSystemType;
    public String srcPath;
	public String FileName;
	
	/**Constructor
	 * 
	 */
	public SshAndColorPickerConfig() {
		ipAdress = "192.168.0.3";
		port = 22;
		username = "pi";
		password = "raspberry";
		colorPickerInExpertmode = false;
		colorPickerShowColorWheel = true;
		srcPath = "";
		FileName = "hyperion.config.json";
		
		sshCommands = new Vector<>();

		sshCommands.add(new SshCommand("sudo service hyperion start"));
		sshCommands.add(new SshCommand("sudo service hyperion stop"));
		sshCommands.add(new SshCommand("sudo service hyperion restart"));
		sshCommands.add(new SshCommand("sudo killall hyperionv4l2"));

        selectedSystemType = HyperionRemoteCalls.SystemTypes.allsystems;

	}

}
