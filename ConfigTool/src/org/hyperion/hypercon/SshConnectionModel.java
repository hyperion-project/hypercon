package org.hyperion.hypercon;

import java.util.Observable;

import org.hyperion.ssh.ConnectionAdapter;
import org.hyperion.ssh.ConnectionListener;
import org.hyperion.ssh.PiSshConnection;

import com.jcraft.jsch.JSchException;

/**
 * @author Fabian Hertwig
 * A model between the Gui and the SShConnection
 */
public class SshConnectionModel extends Observable {

	private static String hyperionRemoteCall = "hyperion-remote ";
	private static boolean printTraffic = true;

	private static SshConnectionModel instance = null;
	final private PiSshConnection mSshConnection;
	

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
	public boolean sendLedColor(int red, int green, int blue) throws IllegalArgumentException {
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
	public boolean sendLedColor(String hexValues) throws IllegalArgumentException {

		if (hexValues.length() != 6) {
			throw new IllegalArgumentException();
		}
		
		if (!isConnected())  {
			return false;
		}

		mSshConnection.execute(hyperionRemoteCall + " -c " + hexValues);
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
			float hsvSaturation) throws IllegalArgumentException{

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
		mSshConnection.execute(hyperionRemoteCall + "-s " + hsvSaturation + " -v " + hsvGain + " -g " + floatArrayToArgsString(rgbGamma) + 
				" -t " + floatArrayToArgsString(rgbThreshold) + " -b " + floatArrayToArgsString(rgbBlacklevel) + " -w " + floatArrayToArgsString(rgbWhitelevel));
		return true;
		
	}

	/**Sends the clearall command
	 * @return false if there is no connection, true after the command was executed
	 */
	public boolean sendClear(){
		if(isConnected()){
			mSshConnection.execute(hyperionRemoteCall + "--clearall");
			return true;
		}
		return false;
	}

	/**Get connection status
	 * @return
	 */
	public boolean isConnected() {
		return mSshConnection.isConnected();
	}

	private final ConnectionListener mConnectionConsoleListener = new ConnectionAdapter() {
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
	
	/**
	 * array to a String in the format "a1 a2 a3 ... an" with quotes
	 * @param array
	 * @return
	 */
	private static String floatArrayToArgsString(float[] array){
		StringBuffer buffer = new StringBuffer("\"");
		
		for (float f : array) {
			buffer.append(f);
			buffer.append(" ");
			
		}
		buffer.deleteCharAt(buffer.lastIndexOf(" "));
		buffer.append("\"");
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
			hex.insert(0, "0");
		}
		return hex.toString();
	}

}
