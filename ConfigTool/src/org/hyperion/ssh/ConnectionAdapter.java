package org.hyperion.ssh;

public abstract class ConnectionAdapter implements ConnectionListener {

	@Override
	public void connected() {}

	@Override
	public void disconnected() {}

	@Override
	public void commandExec(String pCommand) {}

	@Override
	public void getFile(String src, String dst) {}

	@Override
	public void commandFinished(String pCommand) {}

	@Override
	public void getFileFinished(String src, String dst){}

	@Override
	public void sendConfigFileFinished(String srcPath, String dstPath, String fileName){}
	
	@Override
	public void addLine(String pLine) {}

	@Override
	public void addError(String pLine) {}

}
