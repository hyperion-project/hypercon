package org.hyperion.ssh;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class PiSshConnection {
	final JSch mJsch;
	Session mSession;

	public PiSshConnection() {
		mJsch = new JSch();
	}

	public void connect(String pHostName, int pTcpPort, String pUsername, String pPassword) throws JSchException {
		if (mSession != null) {
			close();
		}

		try {
			mSession = mJsch.getSession(pUsername, pHostName, pTcpPort);
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
			e.printStackTrace();
			mSession = null;
		}

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

	public void execute(String pCommand) {
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

			while (!channel.isClosed()) {
				Thread.sleep(100);
			}
			channel.disconnect();

			for (ConnectionListener conLin : mConnectionListeners) {
				conLin.commandFinished(pCommand);
			}
		} catch (JSchException jscExc) {
			jscExc.printStackTrace();
		} catch (InterruptedException intExc) {
			intExc.printStackTrace();
		}
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
}
