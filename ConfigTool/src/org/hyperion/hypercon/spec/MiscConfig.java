package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.JsonStringBuffer;

/**
 * Miscellaneous configuration items for the Hyperion daemon.
 */
public class MiscConfig {

//Effect Engine
	/** The absolute location(s) of the effects */
	public EffectPathStandard mEffectPathCombo = EffectPathStandard.allsystems;
	
	/** Flag indicating that the boot sequence is enabled(true) or not(false) */
	public boolean mBootEffectEnabled = true;
	/** The effect selected for the boot sequence */
	public EffectStandard mEffectsetCombo = EffectStandard.rainbowswirlfast;
	/** The (maximum) length of the boot-sequence */
	public int mEffectDurationspinner = 3000;
	/** The Priority of booteffect engine*/
	public int mEffectPriorityspinner = 990;
	/** Booteffect - StaticColor R value*/
	public int mEffectColorRspinner = 0;
	/** Booteffect - StaticColor G value*/
	public int mEffectColorGspinner = 0;
	/** Booteffect - StaticColor B value*/
	public int mEffectColorBspinner = 0;	

//Internal Grabber	
	/** Flag indicating that the Frame Grabber is enabled */
	public boolean mFrameGrabberEnabled = true;
	/** The width of 'grabbed' frames (screen shots) [pixels] */
	public int mFrameGrabberWidth = 64;
	/** The height of 'grabbed' frames (screen shots) [pixels] */
	public int mFrameGrabberHeight = 64;
	/** The interval of frame grabs (screen shots) [ms] */
	public int mFrameGrabberInterval_ms = 100;
	/** The Priority of the Internal Frame Grabber */
	public int mFrameGrabberPriority = 890;

//Kodi	
	/** Flag indicating that the XBMC checker is enabled */
	public boolean mXbmcCheckerEnabled = false;
	/** The IP-address of XBMC */
	public String mXbmcAddress  = "127.0.0.1";
	/** The TCP JSON-Port of XBMC */
	public int mXbmcTcpPort     = 9090;
	/** Flag indicating that the frame-grabber is on during video playback */
	public boolean mVideoOn = true;
	/** Flag indicating that the frame-grabber is on during XBMC menu */
	public boolean mMenuOn = false;
	/** Flag indicating that the frame-grabber is on during picture slideshow */
	public boolean mPictureOn = true;
	/** Flag indicating that the frame-grabber is on during audio playback */
	public boolean mAudioOn = true;
	/** Flag indicating that the frame-grabber is on when xbmc is on screensaver */
	public boolean mScreensaverOn = true;
	/** Flag indicating that the frame-grabber is should take actions when a 3D file is playing */
	public boolean m3DCheckingEnabled = true;

//Proto/Json Forwarder
	public boolean mforwardEnabled = false;
	public String mProtofield = "\"127.0.0.1:19447\"";
	public String mJsonfield = "\"127.0.0.1:19446\"";

//Proto/Boblight/Json Server
	/** Flag indicating that the JSON interface is enabled */
	public boolean mJsonInterfaceEnabled = true;
	/** The TCP port at which the JSON server is listening for incoming connections */
	public int mJsonPort = 19444;

	/** Flag indicating that the PROTO interface is enabled */
	public boolean mProtoInterfaceEnabled = true;
	/** The TCP port at which the Protobuf server is listening for incoming connections */
	public int mProtoPort = 19445;

	/** Flag indicating that the PROTO interface is enabled */
	public boolean mBoblightInterfaceEnabled = false;
	/** The TCP port at which the Protobuf server is listening for incoming connections */
	public int mBoblightPort = 19333;
	public int mBoblightPriority = 900;
	
	public void appendTo(JsonStringBuffer strBuf) {

// BootEffect Engine

		String effectEngineComment = 
				"The configuration of the effect engine, contains the following items: \n" +
				" * paths		: An array with absolute location(s) of directories with effects \n" +
				" * color 		: Set static color after boot -> set effect to \"\" (empty) and input the values [R,G,B] and set duration_ms NOT to 0 (use 1) instead \n" +				
				" * effect 		: The effect selected as 'boot sequence' \n" +
				" * duration_ms	: The duration of the selected effect (0=endless) \n" +
				" * priority 	: The priority of the selected effect/static color (default=990) HINT: lower value result in HIGHER priority! \n";
		strBuf.writeComment(effectEngineComment);
				
		strBuf.startObject("effects");
		strBuf.startArray("paths");
//		for (String effectPath : effectPaths) {
//			strBuf.addArrayElement(effectPath, effectPath == effectPaths[effectPaths.length-1]);
//		}
		strBuf.addPathValue(mEffectPathCombo.getTypeId(), true);
		strBuf.stopArray(true);
		strBuf.stopObject();

		strBuf.newLine();

		strBuf.toggleComment(!mBootEffectEnabled);
		strBuf.startObject("bootsequence");
		strBuf.addArrayRGB("color", mEffectColorRspinner, mEffectColorGspinner, mEffectColorBspinner, false);
		strBuf.addValue("effect", mEffectsetCombo.getTypeId(), false);
		strBuf.addValue("duration_ms", mEffectDurationspinner, false);
		strBuf.addValue("priority", mEffectPriorityspinner, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);

		strBuf.newLine();

// Json/Proto Forward
		
		String JsonforwardComment = 
				"The configuration of the Json/Proto forwarder. Forward messages to multiple instances of Hyperion on same and/or other hosts \n" +
				"'proto' is mostly used for video streams and 'json' for effects \n" +
				" * proto	: Proto server adress and port of your target. Syntax:[IP:PORT] -> [\"127.0.0.1:19447\"] or more instances to forward [\"127.0.0.1:19447\",\"192.168.0.24:19449\"] \n" +
				" * json	: Json server adress and port of your target. Syntax:[IP:PORT] -> [\"127.0.0.1:19446\"] or more instances to forward [\"127.0.0.1:19446\",\"192.168.0.24:19448\"] \n" +
				" HINT:	If you redirect to \"127.0.0.1\" (localhost) you could start a second hyperion with another device/led config!\n" +
				"		Be sure your client(s) is/are listening on the configured ports. The second Hyperion (if used) also needs to be configured! (HyperCon -> External -> Json Server/Proto Server)\n";
		strBuf.writeComment(JsonforwardComment);
		
		strBuf.toggleComment(!mforwardEnabled);
		strBuf.startObject("forwarder");
		strBuf.addArray("proto", mProtofield, false);
		strBuf.addArray("json", mJsonfield, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);		

		strBuf.newLine();

// Internal Grabber		

		String grabComment =
				" The configuration for the frame-grabber, contains the following items: \n" +
				"  * width        : The width of the grabbed frames [pixels]\n" +
				"  * height       : The height of the grabbed frames [pixels]\n" +
				"  * frequency_Hz : The frequency of the frame grab [Hz]\n" +
				"  * priority     : The priority of the frame-gabber (Default=890) HINT: lower value result in HIGHER priority! \n" +
				"  * ATTENTION    : Power-of-Two resolution is not supported and leads to unexpected behaviour! \n";
		strBuf.writeComment(grabComment);
		
		strBuf.toggleComment(!mFrameGrabberEnabled);
		strBuf.startObject("framegrabber");
		strBuf.addValue("width", mFrameGrabberWidth, false);
		strBuf.addValue("height", mFrameGrabberHeight, false);
		strBuf.addValue("frequency_Hz", 1000.0/mFrameGrabberInterval_ms, false);
		strBuf.addValue("priority", mFrameGrabberPriority, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);
		
		strBuf.newLine();
		
// Kodi Detection
		
		String xbmcComment = 
				"The configuration of the Kodi connection used to enable and disable the frame-grabber. Contains the following fields: \n" +
				" * xbmcAddress       : The IP address of the Kodi-host\n" +
				" * xbmcTcpPort       : The TCP-port of the Kodi-server\n" +
				" * grabVideo         : Flag indicating that the frame-grabber is on(true) during video playback\n" +
				" * grabPictures      : Flag indicating that the frame-grabber is on(true) during picture show\n" +
				" * grabAudio         : Flag indicating that the frame-grabber is on(true) during audio playback\n" +
				" * grabMenu          : Flag indicating that the frame-grabber is on(true) at the Kodi menu\n" +
				" * grabScreensaver   : Flag indicating that the frame-grabber is on(true) when Kodi is on screensaver\n" +
				" * enable3DDetection : Flag indicating that the frame-grabber should switch to a 3D compatible modus if a 3D video is playing\n";
		strBuf.writeComment(xbmcComment);
		
		strBuf.toggleComment(!mXbmcCheckerEnabled);
		strBuf.startObject("xbmcVideoChecker");
		strBuf.addValue("xbmcAddress", mXbmcAddress, false);
		strBuf.addValue("xbmcTcpPort", mXbmcTcpPort, false);
		strBuf.addValue("grabVideo", mVideoOn, false);
		strBuf.addValue("grabPictures", mPictureOn, false);
		strBuf.addValue("grabAudio", mAudioOn, false);
		strBuf.addValue("grabMenu", mMenuOn, false);
		strBuf.addValue("grabScreensaver", mScreensaverOn, false);
		strBuf.addValue("enable3DDetection", m3DCheckingEnabled, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);

		strBuf.newLine();

// Json Server
		
		String jsonComment = 
				"The configuration of the Json server which enables the json remote interface\n" +
				" * port : Port at which the json server is started\n";
		strBuf.writeComment(jsonComment);
		
		strBuf.toggleComment(!mJsonInterfaceEnabled);
		strBuf.startObject("jsonServer");
		strBuf.addValue("port", mJsonPort, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);

		strBuf.newLine();
		
// Proto Server
		
		String protoComment =
			    "The configuration of the Proto server which enables the protobuffer remote interface\n" +
			    " * port : Port at which the protobuffer server is started\n";
		strBuf.writeComment(protoComment);
		
		strBuf.toggleComment(!mProtoInterfaceEnabled);
		strBuf.startObject("protoServer");
		strBuf.addValue("port", mProtoPort, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);

		strBuf.newLine();
	    
// Boblight Server
		
		String boblightComment =
			    "The configuration of the boblight server which enables the boblight remote interface\n" +
			    " * port 	: Port at which the boblight server is started\n" +
				" * priority: Priority of the boblight server (Default=900) HINT: lower value result in HIGHER priority! \n";
		strBuf.writeComment(boblightComment);
		
		strBuf.toggleComment(!mBoblightInterfaceEnabled);
		strBuf.startObject("boblightServer");
		strBuf.addValue("port", mBoblightPort, false);
		strBuf.addValue("priority", mBoblightPriority, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);
	}
	/**
	 * Creates the JSON string of the configuration as used in the Hyperion daemon configfile
	 * 
	 * @return The JSON string of this MiscConfig
	 */
	public String toJsonString() {
		JsonStringBuffer jsonBuf = new JsonStringBuffer(1);
		appendTo(jsonBuf);
		return jsonBuf.toString();
	}
	
	public static void main(String[] pArgs) {
		MiscConfig miscConfig = new MiscConfig();
		
		JsonStringBuffer jsonBuf = new JsonStringBuffer(1);
		miscConfig.appendTo(jsonBuf);
		
		System.out.println(jsonBuf.toString());
	}
}
