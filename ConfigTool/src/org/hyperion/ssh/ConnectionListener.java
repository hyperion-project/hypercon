package org.hyperion.ssh;

public interface ConnectionListener {

	public void connected();
	
	public void disconnected();
	
	public void commandExec(String pCommand);

	public void getFile(String src, String dst);
	
	public void commandFinished(String pCommand);

	public void getFileFinished(String src, String dst);

	public void addLine(String pLine);
	
	public void addError(String pLine);
}
