package org.hyperion.hypercon.spec;

public class SshConfig {
	
	public String adress;
	public int port;
	public String username;
//	public String password;
	public boolean autoUpdate;
	
	
	public SshConfig() {
		adress = "192.168.0.3";
		port = 22;
		autoUpdate = false;
		username = "pi";
	}

}
