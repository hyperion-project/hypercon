package org.hyperion.ssh;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.*;

import javax.imageio.ImageIO;



public class PiSshConnection {
	final JSch mJsch;
	static Session mSession;

	public PiSshConnection() {
		mJsch = new JSch();
	}

	public void connect(String pHostName, int pTcpPort, String pUsername, String pPassword, int timeout) throws JSchException{
		if (mSession != null) {
			close();
		}

		try {
			mSession = mJsch.getSession(pUsername, pHostName, pTcpPort);
			mSession.setTimeout(timeout);
			mSession.setPassword(pPassword);
			mSession.setUserInfo(new UserInfo() {
				@Override
				public void showMessage(String message) {
				}

				@Override
				public boolean promptYesNo(String message) {
					return true;
				}

				@Override
				public boolean promptPassword(String message) {
					return true;
				}

				@Override
				public String getPassword() {
					return null;
				}

				@Override
				public boolean promptPassphrase(String message) {
					return true;
				}

				@Override
				public String getPassphrase() {
					return null;
				}
			});
			mSession.connect();

			if (mSession.isConnected()) {
				for (ConnectionListener cl : mConnectionListeners) {
					cl.connected();
				}
			}
		} catch (JSchException e) {
			mSession = null;
			throw e;
		}
	}

	public void connect(String pHostName, int pTcpPort, String pUsername, String pPassword) throws JSchException {
		connect( pHostName,  pTcpPort,  pUsername,  pPassword, 10000);

	}

	public boolean isConnected() {
		return mSession != null && mSession.isConnected();
	}

	public void close() {
		if (isConnected()) {
			mSession.disconnect();
			mSession = null;
		}
		for (ConnectionListener cl : mConnectionListeners) {
			cl.disconnected();
		}
	}

    public void executeInThread(final String command){

        Thread thread = new Thread(){
            public void run(){
                try {
                    execute(command);
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }
	public void execute(String pCommand) throws JSchException {
		if (mSession == null || !mSession.isConnected()) {
			System.err.println("Can not execute on not existing or not connected session");
			return;
		}
		try {
			ChannelExec channel = (ChannelExec) mSession.openChannel("exec");
			channel.setCommand(pCommand);

			channel.setInputStream(null);
			channel.setErrStream(mErrorStream);
			channel.setOutputStream(mOutputStream);

			for (ConnectionListener cl : mConnectionListeners) {
				cl.commandExec(pCommand);
			}
			channel.connect();

            int waitedTimeToCloseChannel = 0;
			while (!channel.isClosed() || !channel.isEOF()) {
				Thread.sleep(100);
                waitedTimeToCloseChannel += 100;

//                if(waitedTimeToCloseChannel > 30000){
//                    break;
//                }

			}
			channel.disconnect();

			for (ConnectionListener conLin : mConnectionListeners) {
				conLin.commandFinished(pCommand);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void sendConfig(String srcPath, String FileName, String hyperionConfigTargetCall) throws JSchException, SftpException {
		if (mSession == null || !mSession.isConnected()) {
			System.err.println("Can not execute on not existing or not connected session");
			return;
		}

			ChannelSftp channel = (ChannelSftp) mSession.openChannel("sftp");


			for (ConnectionListener cl : mConnectionListeners) {
				cl.sendConfigFile(srcPath,FileName, hyperionConfigTargetCall);
			}

			channel.connect();
			
			channel.lcd(srcPath);
			channel.put(FileName, hyperionConfigTargetCall+FileName, channel.OVERWRITE);

			channel.disconnect();

			for (ConnectionListener conLin : mConnectionListeners) {
				conLin.getFileFinished(srcPath, hyperionConfigTargetCall);
			}

	}

	public Image getImage(String srcFile) throws JSchException, SftpException, IOException {
		if (mSession == null || !mSession.isConnected()) {
			System.err.println("Can not execute on not existing or not connected session");
			return null;
		}

		ChannelSftp channel = (ChannelSftp) mSession.openChannel("sftp");


		for (ConnectionListener cl : mConnectionListeners) {
			cl.getFile(srcFile,"java.awt.Image");
		}

		channel.connect();

		Image img = ImageIO.read(channel.get(srcFile));

		channel.disconnect();

		for (ConnectionListener conLin : mConnectionListeners) {
			conLin.getFileFinished(srcFile, "image");
		}
		return img;

	}

	private final OutputStream mOutputStream = new OutputStream() {
		StringBuffer strBuf = new StringBuffer();

		@Override
		public synchronized void write(int b) throws IOException {
			if (b == 10 || b == 13) {
				// Found line end
				if (strBuf.length() == 0) {
					return;
				}
				String line = strBuf.toString();

				for (ConnectionListener cl : mConnectionListeners) {
					cl.addLine(line);
				}

				strBuf = new StringBuffer();
				return;
			}
			strBuf.append((char) b);
		}
	};
	private final OutputStream mErrorStream = new OutputStream() {
		StringBuffer strBuf = new StringBuffer();

		@Override
		public synchronized void write(int b) throws IOException {
			if (b == 10 || b == 13) {
				// Found line end
				if (strBuf.length() == 0) {
					return;
				}
				String line = strBuf.toString();

				for (ConnectionListener cl : mConnectionListeners) {
					cl.addError(line);
				}

				strBuf = new StringBuffer();
				return;
			}
			strBuf.append((char) b);
		}
	};

	private final List<ConnectionListener> mConnectionListeners = new Vector<>();

	public void addConnectionListener(ConnectionListener pListener) {
		mConnectionListeners.add(pListener);
	}

	public void removeConnectionListener(ConnectionListener pListener) {
		mConnectionListeners.remove(pListener);
	}

	public static void main(String[] pArgs) {
		PiSshConnection con = new PiSshConnection();
		try {
			ConnectionAdapter ca = new ConnectionAdapter() {
				boolean printTraffic = true;

				@Override
				public void commandExec(String pCommand) {
					if(printTraffic){
						System.out.println("ssh: $ " + pCommand);
					}
				}

				@Override
				public void commandFinished(String pCommand) {
					if(printTraffic){
						//Nothing to do
					}
				}
				@Override
				public void sendConfigFile(String srcPath, String dstPath, String fileName) {
					if(printTraffic){
						System.out.println("sftp sendConfigFile(" + srcPath + ", " + dstPath + fileName +")");
					}
				}
				@Override
				public void sendConfigFileFinished(String srcPath, String dstPath, String fileName) {
					if(printTraffic){
						System.out.println("sftp sendConfigFile(" + srcPath + ", " + dstPath + fileName +")");
					}
				}

				@Override
				public void addLine(String pLine) {
					if(printTraffic){
						System.out.println("ssh: " + pLine);
					}
				}

				@Override
				public void addError(String pLine) {
					if(printTraffic){
						System.out.println("ssh Error: " + "\u001B[31m" + pLine);
					}

				}

				@Override
				public void connected() {
					if(printTraffic){
						System.out.println("ssh connected");
					}
					super.connected();
				}

				@Override
				public void disconnected() {
					if(printTraffic){
						System.out.println("ssh disconnected");
					}
					super.disconnected();
				}
			};

			con.addConnectionListener(ca);
			con.connect("192.168.178.32", 22, "pi", ".", 10000);

			//con.execute("hyperion-v4l2 --screenshot");
			ChannelSftp channel = (ChannelSftp) mSession.openChannel("sftp");



			channel.connect();
			Image img = ImageIO.read(channel.get("./screenshot.png"));

			channel.disconnect();

			/*
			try {
				con.getFile("./screenshot.png", ".");
			} catch (SftpException e) {
				e.printStackTrace();
			}
			*/
			con.close();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
