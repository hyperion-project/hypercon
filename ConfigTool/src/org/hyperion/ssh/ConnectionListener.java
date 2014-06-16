package org.hyperion.ssh;

public interface ConnectionListener {

	public void connected();
	
	public void disconnected();
	
	public void commandExec(String pCommand);
	
	public void commandFinished(String pCommand);
	
	public void addLine(String pLine);
	
	public void addError(String pLine);
}
