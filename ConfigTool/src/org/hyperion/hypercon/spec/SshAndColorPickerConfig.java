package org.hyperion.hypercon.spec;

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
	}

}
