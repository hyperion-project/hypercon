package org.hyperion.ssh;

import com.jcraft.jsch.JSchException;
import org.hyperion.hypercon.ErrorHandling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HyperionSshPanel extends JPanel {

	private final PiSshConnection mSshCon = new PiSshConnection();
	private final ConnectionListener mConnectionListener = new ConnectionAdapter() {
		@Override
		public void disconnected() {
			addConsoleLine("DISCONNECTED", "black");
			
			mIpLabel.setEnabled(true);
			mIpField.setEnabled(true);
			mUsernameLabel.setEnabled(true);
			mUsernameField.setEnabled(true);
			mPasswordLabel.setEnabled(true);
			mPasswordField.setEnabled(true);
			mTestConnectionButton.setAction(mConnectAction);
			
			for (Component c : mConfigPanel.getComponents()) {
				c.setEnabled(false);
			}
			for (Component c : mTestPanel.getComponents()) {
				c.setEnabled(false);
			}
		}

		@Override
		public void sendConfigFile(String dstPath, String srcPath, String fileName) {
			// TODO: implement
		}

		@Override
		public void connected() {
			addConsoleLine("CONNECTED", "black");

			mIpLabel.setEnabled(false);
			mIpField.setEnabled(false);
			mUsernameLabel.setEnabled(false);
			mUsernameField.setEnabled(false);
			mPasswordLabel.setEnabled(false);
			mPasswordField.setEnabled(false);
			mTestConnectionButton.setAction(mDisconnectAction);
			
			for (Component c : mConfigPanel.getComponents()) {
				c.setEnabled(true);
			}
			for (Component c : mTestPanel.getComponents()) {
				c.setEnabled(true);
			}
		}
	};
	
	private final Action mConnectAction = new AbstractAction("Connect") {
		@Override
		public void actionPerformed(ActionEvent e) {
			String hostName = mIpField.getText();
			int sshPort     = 22;
			String username = mUsernameField.getText();
			String password = new String(mPasswordField.getPassword());
			
			try {
				mSshCon.connect(hostName, sshPort, username, password);
			} catch (JSchException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	private final Action mDisconnectAction = new AbstractAction("Disconnect") {
		@Override
		public void actionPerformed(ActionEvent e) {
			mSshCon.close();
		}
	};
	
	private JPanel mConnectionPanel;
	private JLabel mIpLabel;
	private JTextField mIpField;
	private JLabel mUsernameLabel;
	private JTextField mUsernameField;
	private JLabel mPasswordLabel;
	private JPasswordField mPasswordField;
	private JButton mTestConnectionButton;
	
	private JPanel mConfigPanel;
	private JLabel mInstallLabel;
	private JButton mInstallButton;
	private JLabel mUpdateLabel;
	private JButton mUpdateButton;
	private JLabel mUploadConfigLabel;
	private JButton mUploadConfigButton;
	
	private JPanel mTestPanel;
	private JButton mVersionButton;
	private JLabel mVersionLabel;
	private JButton mDaemonCheckButton;
	private JLabel mDaemonCheckLabel;
	private JButton mJsonCheck;
	private JLabel mJsonCheckLabel;
	private JButton mProtoCheckButton;
	private JLabel mProtoCheckLabel;
	private JButton mBobCheckButton;
	private JLabel mBobCheckLabel;
	private JButton mVerifyButton;
	private JLabel mVerifyLabel;
	
	private JPanel mConsolePanel;
	private StringBuffer mConsoleBuffer = new StringBuffer("<html><body><font face=\"Courier\" size=\"2\">");
	private JEditorPane mConsolePane;
	
	public HyperionSshPanel() {
		super();
		
		initialise();
		
		for (Component c : mConfigPanel.getComponents()) {
			c.setEnabled(false);
		}
		for (Component c : mTestPanel.getComponents()) {
			c.setEnabled(false);
		}
		mSshCon.addConnectionListener(mConnectionListener);
	}
	
	private void initialise() {
		setLayout(new BorderLayout());
		
		JPanel mCentralPanel = new JPanel();
		mCentralPanel.setLayout(new BoxLayout(mCentralPanel, BoxLayout.PAGE_AXIS));
		mCentralPanel.add(getConnectionPanel());
		mCentralPanel.add(getConfigPanel());
		mCentralPanel.add(getTestPanel());
		
		add(mCentralPanel, BorderLayout.CENTER);
		add(getConsolePanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getConnectionPanel() {
		if (mConnectionPanel == null) {
			mConnectionPanel = new JPanel();
			mConnectionPanel.setLayout(new GridLayout(0, 2));
			mConnectionPanel.setBorder(BorderFactory.createTitledBorder("SSH Connection"));
			
			mIpLabel = new JLabel("IP address: ");
			mConnectionPanel.add(mIpLabel);
			
			mIpField = new JTextField("192.168.1.17");
			mConnectionPanel.add(mIpField);
			
			mUsernameLabel = new JLabel("Username: ");
			mConnectionPanel.add(mUsernameLabel);
			
			mUsernameField = new JTextField("pi");
			mConnectionPanel.add(mUsernameField);
			
			mPasswordLabel = new JLabel("Password: ");
			mConnectionPanel.add(mPasswordLabel);
			
			mPasswordField = new JPasswordField("raspberry");
			mConnectionPanel.add(mPasswordField);
			
			mConnectionPanel.add(new JLabel(""));
			
			mTestConnectionButton = new JButton(mConnectAction);
			mConnectionPanel.add(mTestConnectionButton);
		}
		return mConnectionPanel;
	}
	
	private JPanel getConfigPanel() {
		if (mConfigPanel == null) {
			mConfigPanel = new JPanel();
			mConfigPanel.setBorder(BorderFactory.createTitledBorder("Installation"));
			mConfigPanel.setLayout(new GridLayout(0, 2, 5, 2));
			
			mInstallButton = new JButton("Install Hyperion");
			mInstallButton.setEnabled(false);
			mConfigPanel.add(mInstallButton);
			
			mInstallLabel = new JLabel("Install Hyperion");
			mInstallLabel.setEnabled(false);
			mConfigPanel.add(mInstallLabel);
			
			mUpdateButton = new JButton("Update Hyperion");
			mUpdateButton.setEnabled(false);
			mConfigPanel.add(mUpdateButton);
			
			mUpdateLabel = new JLabel("Update Hyperion");
			mUpdateLabel.setEnabled(false);
			mConfigPanel.add(mUpdateLabel);
			
			mUploadConfigButton = new JButton("Upload configuration");
			mUploadConfigButton.setEnabled(false);
			mConfigPanel.add(mUploadConfigButton);
			
			mUploadConfigLabel = new JLabel("Upload configuration");
			mUploadConfigLabel.setEnabled(false);
			mConfigPanel.add(mUploadConfigLabel);
		}
		return mConfigPanel;
	}
	
	private JPanel getTestPanel() {
		if (mTestPanel == null) {
			mTestPanel = new JPanel();
			mTestPanel.setBorder(BorderFactory.createTitledBorder("Test"));
			mTestPanel.setLayout(new GridLayout(0, 2, 5, 2));
			
			mVersionButton = new JButton("Get Hyperion version");
			mVersionButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!mSshCon.isConnected()) {
						return;
					}
					ConnectionMessageCollector cmc = new ConnectionMessageCollector();
					mSshCon.addConnectionListener(cmc);
					try {
						mSshCon.execute("hyperiond");
					} catch (JSchException e1) {
						ErrorHandling.ShowException(e1);
					}
					mSshCon.removeConnectionListener(cmc);
					
					if (cmc.mLines.isEmpty()) {
						mVersionLabel.setText("FAILED");
					} else {
						String versionLine = cmc.mLines.get(0);
						if (versionLine.startsWith("Application build time: ")) {
							mVersionLabel.setText(versionLine.substring(24));
						} else {
							mVersionLabel.setText("FAILED");
						}
					}
				}
			});
			mVersionButton.setEnabled(false);
			mTestPanel.add(mVersionButton);
			
			mVersionLabel = new JLabel("-");
			mVersionLabel.setEnabled(false);
			mVersionLabel.setHorizontalAlignment(JLabel.CENTER);
			mTestPanel.add(mVersionLabel);
			
			mDaemonCheckButton = new JButton("Hyperion runnning?");
			mDaemonCheckButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!mSshCon.isConnected()) {
						return;
					}
					ConnectionMessageCollector cmc = new ConnectionMessageCollector();
					mSshCon.addConnectionListener(cmc);
					try {
						mSshCon.execute("ps -e | grep hyperiond");
					} catch (JSchException e1) {
						ErrorHandling.ShowException(e1);
					}
					mSshCon.removeConnectionListener(cmc);
					
					if (cmc.mLines.isEmpty()) {
						mDaemonCheckLabel.setText("FAILED");
					} else {
						mDaemonCheckLabel.setText("PASSED");
						
					}
				}
			});
			mTestPanel.add(mDaemonCheckButton);
			
			mDaemonCheckLabel = new JLabel("-");
			mDaemonCheckLabel.setHorizontalAlignment(JLabel.CENTER);
			mTestPanel.add(mDaemonCheckLabel);
			
			mJsonCheck = new JButton("JSON Server?");
			mJsonCheck.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!mSshCon.isConnected()) {
						return;
					}
					ConnectionMessageCollector cmc = new ConnectionMessageCollector();
					mSshCon.addConnectionListener(cmc);
					try {
						mSshCon.execute("sudo netstat -tlp | grep 19444 | grep hyperiond");
					} catch (JSchException e1) {
						ErrorHandling.ShowException(e1);
					}
					mSshCon.removeConnectionListener(cmc);
					
					if (cmc.mLines.isEmpty()) {
						mJsonCheckLabel.setText("FAILED");
					} else {
						mJsonCheckLabel.setText("PASSED");
					}
				}
			});
			mTestPanel.add(mJsonCheck);
			
			mJsonCheckLabel = new JLabel("-");
			mJsonCheckLabel.setHorizontalAlignment(JLabel.CENTER);
			mTestPanel.add(mJsonCheckLabel);
			
			mProtoCheckButton = new JButton("PROTO Server?");
			mProtoCheckButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!mSshCon.isConnected()) {
						return;
					}
					ConnectionMessageCollector cmc = new ConnectionMessageCollector();
					mSshCon.addConnectionListener(cmc);
					try {
						mSshCon.execute("sudo netstat -tlp | grep 19445 | grep hyperiond");
					} catch (JSchException e1) {
						ErrorHandling.ShowException(e1);
					}
					mSshCon.removeConnectionListener(cmc);
					
					if (cmc.mLines.isEmpty()) {
						mProtoCheckLabel.setText("FAILED");
					} else {
						mProtoCheckLabel.setText("PASSED");
					}
				}
			});
			mTestPanel.add(mProtoCheckButton);
			
			mProtoCheckLabel = new JLabel("-");
			mProtoCheckLabel.setHorizontalAlignment(JLabel.CENTER);
			mTestPanel.add(mProtoCheckLabel);
			
			mBobCheckButton = new JButton("Boblight Server?");
			mBobCheckButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!mSshCon.isConnected()) {
						return;
					}
					ConnectionMessageCollector cmc = new ConnectionMessageCollector();
					mSshCon.addConnectionListener(cmc);
					try {
						mSshCon.execute("sudo netstat -tlp | grep 19333 | grep hyperiond");
					} catch (JSchException e1) {
						ErrorHandling.ShowException(e1);
					}
					mSshCon.removeConnectionListener(cmc);
					
					if (cmc.mLines.isEmpty()) {
						mBobCheckLabel.setText("FAILED");
					} else {
						mBobCheckLabel.setText("PASSED");
					}
				}
			});
			mTestPanel.add(mBobCheckButton);
			
			mBobCheckLabel = new JLabel("-");
			mBobCheckLabel.setHorizontalAlignment(JLabel.CENTER);
			mTestPanel.add(mBobCheckLabel);

			mVerifyButton = new JButton("Verify install");
			mVerifyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!mSshCon.isConnected()) {
						return;
					}
					ConnectionMessageCollector cmc = new ConnectionMessageCollector();
					mSshCon.addConnectionListener(cmc);
					try {
						mSshCon.execute("/sbin/initctl stop hyperion");
						mSshCon.execute("sudo hyperiond /etc/hyperion.config.json");
					} catch (JSchException e1) {
						ErrorHandling.ShowException(e1);
					}

					try { Thread.sleep(5000); } catch (Throwable t) {}
//					mSshCon.execute("sudo killall hyperiond");
					mSshCon.removeConnectionListener(cmc);
					
					if (cmc.mLines.isEmpty()) {
						mVerifyLabel.setText("FAILED");
					} else {
						mVerifyLabel.setText("PASSED");
					}
				}
			});
			mTestPanel.add(mVerifyButton);
			
			mVerifyLabel = new JLabel("-");
			mVerifyLabel.setHorizontalAlignment(JLabel.CENTER);
			mTestPanel.add(mVerifyLabel);
		}
		return mTestPanel;
	}
	
	private void addConsoleLine(String pLine, String pColor) {
		if (pColor != null) {
			mConsoleBuffer.append("<font \"color\"=\"" + pColor + "\">");
		}
		mConsoleBuffer.append(pLine);
		if (pColor != null) {
			mConsoleBuffer.append("</font>");
		}
		mConsoleBuffer.append("<br>\n");
		mConsolePane.setText(mConsoleBuffer.toString() + "</font></html></body>");
	}
	
	private JPanel getConsolePanel() {
		if (mConsolePanel == null) {
			mConsolePanel = new JPanel();
			mConsolePanel.setPreferredSize(new Dimension(1024, 200));
			mConsolePanel.setBorder(BorderFactory.createTitledBorder(""));
			mConsolePanel.setLayout(new BorderLayout());
			
			mConsolePane = new JEditorPane("text/html", "");
			mConsolePanel.add(new JScrollPane(mConsolePane), BorderLayout.CENTER);
			
			mSshCon.addConnectionListener(mConnectionConsoleListener);
		}
		return mConsolePanel;
	}
	
	private final ConnectionListener mConnectionConsoleListener = new ConnectionAdapter() {
		@Override
		public void commandExec(String pCommand) {
			addConsoleLine("$ " + pCommand, "blue");
		}
		
		@Override
		public void commandFinished(String pCommand) {
			addConsoleLine("", null);
			addConsoleLine("", null);
		}
		
		@Override
		public void addLine(String pLine) {
			addConsoleLine(pLine, "black");
		}
		@Override
		public void addError(String pLine) {
			addConsoleLine(pLine, "red");
		}

		@Override
		public void sendConfigFile(String dstPath, String srcPath, String fileName) {
			// TODO: implement
		}
	};
	
	public static void main(final String[] pArgs) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(640, 640);
		
		frame.setContentPane(new HyperionSshPanel());
		
		frame.setVisible(true);
	}
}
