package org.hyperion.hypercon;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.hyperion.ssh.ConnectionAdapter;
import org.hyperion.ssh.ConnectionListener;
import org.hyperion.ssh.PiSshConnection;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabian Hertwig
 * A model between the Gui and the SShConnection
 */
public class SshConnectionModel extends Observable {



    private static String hyperionRemoteCall = "hyperion-remote "; //$NON-NLS-1$
	private static  String hyperionGrabberV4l2Call = "hyperion-v4l2 "; //$NON-NLS-1$
	private static  String hyperionInstallCall = "hyperion-v4l2 "; //$NON-NLS-1$
	private static  String hyperionRemoveCall = "hyperion-v4l2 "; //$NON-NLS-1$
	private static boolean printTraffic = true;

	private static SshConnectionModel instance = null;
	final private PiSshConnection mSshConnection;
	
	private boolean wasConnected;
	

	private SshConnectionModel() {
		mSshConnection = new PiSshConnection();
		mSshConnection.addConnectionListener(mConnectionConsoleListener);
	}

	/** Singleton constructor
	 * @return instance of the connection
	 */
	public static SshConnectionModel getInstance() {
		if (instance == null) {
			instance = new SshConnectionModel();
		}
		return instance;
	}

    public static void setHyperionRemoteCall(String hyperionRemoteCall) {
        SshConnectionModel.hyperionRemoteCall = hyperionRemoteCall;
    }

    public static void setHyperionGrabberV4l2Call(String hyperionGrabberV4l2Call) {
        SshConnectionModel.hyperionGrabberV4l2Call = hyperionGrabberV4l2Call;
    }
    
    public static void setHyperionInstallCall(String hyperionInstallCall) {
        SshConnectionModel.hyperionInstallCall = hyperionInstallCall;
    }
    public static void setHyperionRemoveCall(String hyperionRemoveCall) {
        SshConnectionModel.hyperionRemoveCall = hyperionRemoveCall;
    }
	/**Tries to establish a connection
	 * @param hostName
	 * @param port
	 * @param username
	 * @param password
	 * @return true if the connection is established
	 * @throws JSchException 
	 */
	public boolean connect(String hostName, int port, String username, char[] password) throws JSchException  {
			mSshConnection.connect(hostName, port, username, String.copyValueOf(password));
			
		
		if(isConnected()){
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	/**
	 * Closes the connection and removes the connectionlistener
	 */
	public void disconnect() {
		if(!isConnected()){
			return;
		}
		mSshConnection.close();
		mSshConnection.removeConnectionListener(mConnectionConsoleListener);
		setChanged();
		notifyObservers();
	}

	/** Sends the hyperion-remote -c command to set the led color
	 * @param red value between 0 and 255
	 * @param green value between 0 and 255
	 * @param blue value between 0 and 255
	 * @return false if there is no connection, true after the command was sent
	 * @throws IllegalArgumentException when the parameters don't fit
	 */
	public boolean sendLedColor(int red, int green, int blue) throws IllegalArgumentException, JSchException {
		if(red < 0 || red > 255 || green < 0 || green > 255 ||blue < 0 || blue > 255){
			throw new IllegalArgumentException();
		}
		

		return sendLedColor(intToTwoValueHex(red) + intToTwoValueHex(green) + intToTwoValueHex(blue));

	}

	/** Sends the hyperion-remote -c command to set the led color
	 * @param hexValues RRGGBB as Hexvalues, eg. FF0000 for 255 0 0
	 * @return false if there is no connection, true after the command was sent
	 * @throws IllegalArgumentException when the parameters don't fit
	 */
	public boolean sendLedColor(String hexValues) throws IllegalArgumentException, JSchException {

		if (hexValues.length() != 6) {
			throw new IllegalArgumentException();
		}
		
		if (!isConnected())  {
			return false;
		}

		mSshConnection.execute(hyperionRemoteCall + " -c " + hexValues); //$NON-NLS-1$
		return true;

	}

	/**Send the color transform values via the hyperion-remote commands -s -v -g -t -b -w
	 	 * @param rgbThreshold 3 values 0.0 - 1.0
	 * @param rgbGamma 3 values
	 * @param rgbBlacklevel 3 values 0.0 - 1.0
	 * @param rgbWhitelevel 3 values 0.0 - 1.0
	 * @param hsvGain
	 * @param hsvSaturation
	 * @return false if there is no connection, true after the command was sent
	 * @throws IllegalArgumentException when the parameters don't fit
	 */
	public boolean sendColorTransformValues(float[] rgbThreshold, float[] rgbGamma, float[] rgbBlacklevel, float[] rgbWhitelevel, float hsvGain,
			float hsvSaturation) throws IllegalArgumentException, JSchException {

		if( rgbBlacklevel.length != 3 || rgbGamma.length != 3 || rgbThreshold.length != 3 || rgbWhitelevel.length != 3){
			throw new IllegalArgumentException();
		}
		if (!isConnected())  {
			return false;
		}
		/*
		 *  -s, --saturation <arg>       Set the HSV saturation gain of the leds
		    -v, --value <arg>            Set the HSV value gain of the leds
		    -g, --gamma <arg>            Set the gamma of the leds (requires 3 space seperated values)
		    -t, --threshold <arg>        Set the threshold of the leds (requires 3 space seperated values between 0.0 and 1.0)
		    -b, --blacklevel <arg>       Set the blacklevel of the leds (requires 3 space seperated values which are normally between 0.0 and 1.0)
		    -w, --whitelevel <arg>       Set the whitelevel of the leds (requires 3 space seperated values which are normally between 0.0 and 1.0)

		 */
		mSshConnection.execute(hyperionRemoteCall + "-s " + hsvSaturation + " -v " + hsvGain + " -g " + floatArrayToArgsString(rgbGamma) +  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				" -t " + floatArrayToArgsString(rgbThreshold) + " -b " + floatArrayToArgsString(rgbBlacklevel) + " -w " + floatArrayToArgsString(rgbWhitelevel)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return true;
		
	}

	/**Sends the clearall command
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendClear() throws JSchException {
		if(isConnected()){
			mSshConnection.execute(hyperionRemoteCall + "--clearall"); //$NON-NLS-1$
			return true;
		}
		return false;
	}

	/**
	 *
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendTakeScreenshot() throws JSchException {
		if(isConnected()){

			mSshConnection.execute(hyperionGrabberV4l2Call + "--screenshot"); //$NON-NLS-1$
			return true;
		}
		return false;
	}

	/**
	 *
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendCommand(String command) throws JSchException {
		if(isConnected()){

			mSshConnection.execute(command);
			return true;
		}
		return false;
	}
	/**
	 *
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendInstallMessage() throws JSchException {
		if(isConnected()){	
			mSshConnection.execute("Install/Update Hyperion, this may take a while. The output is generated, when the script is ready! Just wait!");
			return true;
		}
		return false;
	}
	/**
	 *
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendInstall() throws JSchException {
		if(isConnected()){
			System.out.println("Install/Update Hyperion, this may take a while. The output is generated, when the script is ready! Just wait!"); //$NON-NLS-1$
			
			mSshConnection.execute(hyperionInstallCall);
			return true;
		}
		return false;
	}
	/**
	 *
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendRemove() throws JSchException {
		if(isConnected()){
			mSshConnection.execute(hyperionRemoveCall);
			return true;
		}
		return false;
	}
    /**
     *
     * @return false if there is no connection, true after the command was executed
     */
    public boolean sendCommandInNewThread(String command) throws JSchException {
        if(isConnected()){

            mSshConnection.executeInThread(command);
            return true;
        }
        return false;
    }

	/**
	 *
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendTakeScreenshot(String hyperionV42lArguments) throws JSchException {
		if(isConnected()){

			mSshConnection.execute(hyperionGrabberV4l2Call + hyperionV42lArguments + " --screenshot"); //$NON-NLS-1$
			return true;
		}
		return false;
	}

	public boolean getScreenshot() throws JSchException, SftpException {
		if(isConnected()){

			mSshConnection.getFile("./screenshot.png", "."); //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		}
		return false;

	}

	public Image getScreenshotImage() throws JSchException, SftpException, IOException {
		if(isConnected()){

			return mSshConnection.getImage("./screenshot.png"); //$NON-NLS-1$
		}
		return null;
	}

	/**Get connection status
	 * @return
	 */
	public boolean isConnected() {
		if(wasConnected != mSshConnection.isConnected()){
			wasConnected =  mSshConnection.isConnected();
			setChanged();
			notifyObservers();
		}
		return mSshConnection.isConnected();
	}

	private final ConnectionListener mConnectionConsoleListener = new ConnectionAdapter() {
		@Override
		public void commandExec(String pCommand) {
			if(printTraffic){
				System.out.println("ssh: $ " + pCommand); //$NON-NLS-1$
			}
		}

		@Override
		public void commandFinished(String pCommand) {
			if(printTraffic){
				//Nothing to do
			}
		}

		@Override
		public void getFileFinished(String src, String dst) {
			if(printTraffic){
				System.out.println("sftp getFile(" + src + ", " + dst + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}

		@Override
		public void addLine(String pLine) {
			if(printTraffic){
				System.out.println("ssh: " + pLine); //$NON-NLS-1$
			}
		}

		@Override
		public void addError(String pLine) {
			if(printTraffic){
				System.out.println("ssh Error: " + "\u001B[31m" + pLine); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}

		@Override
		public void connected() {
			if(printTraffic){
				System.out.println("ssh connected"); //$NON-NLS-1$
			}
			super.connected();
		}

		@Override
		public void disconnected() {
			if(printTraffic){
				System.out.println("ssh disconnected"); //$NON-NLS-1$
			}
			super.disconnected();
		}
	};

	public void addConnectionListener(ConnectionListener listener){
		mSshConnection.addConnectionListener(listener);
	}

	public void removeConnectionListener(ConnectionListener listener){
		mSshConnection.removeConnectionListener(listener);
	}
	
	/**
	 * array to a String in the format "a1 a2 a3 ... an" with quotes
	 * @param array
	 * @return
	 */
	private static String floatArrayToArgsString(float[] array){
		StringBuffer buffer = new StringBuffer("\""); //$NON-NLS-1$
		
		for (float f : array) {
			buffer.append(f);
			buffer.append(" "); //$NON-NLS-1$
			
		}
		buffer.deleteCharAt(buffer.lastIndexOf(" ")); //$NON-NLS-1$
		buffer.append("\""); //$NON-NLS-1$
		return buffer.toString();
	}
	
	/**
	 * Convert an int to a hex value as String. Eg. 15 -> 0F , 16 -> 1F
	 * @param i
	 * @return
	 */
	private static String intToTwoValueHex(int i){
		StringBuffer hex = new StringBuffer(Integer.toHexString(i));
		if(hex.length() == 1){
			hex.insert(0, "0"); //$NON-NLS-1$
		}
		return hex.toString();
	}



}
