package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.JsonStringBuffer;
import org.hyperion.hypercon.spec.ColorConfig;

/**
 * Miscellaneous configuration items for the Hyperion daemon.
 */
public class MiscConfig {
// Blackborder
	public boolean mBlackBorderEnabled = true;
	public double mBlackBorderThreshold = 0.00;
	public int mBlackBorderuFrameCnt = 600;
	public int mBlackBorderbFrameCnt = 50;	
	public int mBlackBordermInconsistentCnt = 10;	
	public int mBlackBorderbRemoveCnt = 1;	
	public BlackBorderStandard mBlackbordermCombo = BlackBorderStandard.defaultt;

//Effect Engine
	/** declare all paths **/
	public String mPathOE = "/storage/hyperion/effects" ;
	public String mPathGEN = "/usr/share/hyperion/effects" ;
	/** Flag indicating that the boot sequence is enabled(true) or not(false) */
	public boolean mBootEffectEnabled = true;
	/** The effect selected for the boot sequence */
	public EffectStandard mEffectsetCombo = EffectStandard.rainbowswirlfast;
	/** The (maximum) length of the boot-sequence */
	public int mEffectDurationspinner = 3000;
	/** The Priority of booteffect engine*/
	public int mEffectPriorityspinner = 700;
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
	/** Flag indicating that the frame-grabber is on at player state pause */
	public boolean mPauseOn = false;
	/** Flag indicating that the frame-grabber is on when xbmc is on screensaver */
	public boolean mScreensaverOn = true;
	/** Flag indicating that the frame-grabber is should take actions when a 3D file is playing */
	public boolean m3DCheckingEnabled = true;

//Proto/Json Forwarder
	public boolean mforwardEnabled = false;
	public String mProtofield = "\"127.0.0.1:19447\"";
	public String mJsonfield = "\"127.0.0.1:19446\"";

//Proto/Boblight/Json Server
	/** The TCP port at which the JSON server is listening for incoming connections */
	public int mJsonPort = 19444;

	/** The TCP port at which the Protobuf server is listening for incoming connections */
	public int mProtoPort = 19445;

	/** Flag indicating that the PROTO interface is enabled */
	public boolean mBoblightInterfaceEnabled = false;
	/** The TCP port at which the Protobuf server is listening for incoming connections */
	public int mBoblightPort = 19333;
	public int mBoblightPriority = 900;
	
	public void appendTo(JsonStringBuffer strBuf) {

	// Internal Grabber		
			
		if (mFrameGrabberEnabled==true){
			String grabComment = "FRAME GRABBER CONFIG";
			strBuf.writeComment(grabComment);
					
			strBuf.startObject("framegrabber");
			strBuf.addValue("width", mFrameGrabberWidth, false);
			strBuf.addValue("height", mFrameGrabberHeight, false);	
			strBuf.addValue("frequency_Hz", 1000.0/mFrameGrabberInterval_ms, false);
			strBuf.addValue("priority", mFrameGrabberPriority, true);
			strBuf.stopObject();
				
			strBuf.newLine();
		}

	// Black Border

		if (mBlackBorderEnabled==true){
			String blackboardercomment = "BLACKBORDER CONFIG";
			strBuf.writeComment(blackboardercomment);
			
			strBuf.startObject("blackborderdetector");
			strBuf.addValue("enable", mBlackBorderEnabled, false);
			strBuf.addValue("threshold", mBlackBorderThreshold, false);
			strBuf.addValue("unknownFrameCnt", mBlackBorderuFrameCnt, false);
			strBuf.addValue("borderFrameCnt", mBlackBorderbFrameCnt, false);
			strBuf.addValue("maxInconsistentCnt", mBlackBordermInconsistentCnt, false);
			strBuf.addValue("blurRemoveCnt", mBlackBorderbRemoveCnt, false);
			strBuf.addValue("mode", mBlackbordermCombo.toString(), true);
			strBuf.stopObject();	
			
			strBuf.newLine();
		}
				
	// Kodi Detection
			
		if (mXbmcCheckerEnabled==true){
			String xbmcComment = "KODI CHECK CONFIG";
			strBuf.writeComment(xbmcComment);

			strBuf.startObject("xbmcVideoChecker");
			strBuf.addValue("xbmcAddress", mXbmcAddress, false);
			strBuf.addValue("xbmcTcpPort", mXbmcTcpPort, false);
			strBuf.addValue("grabVideo", mVideoOn, false);
			strBuf.addValue("grabPictures", mPictureOn, false);
			strBuf.addValue("grabAudio", mAudioOn, false);
			strBuf.addValue("grabMenu", mMenuOn, false);
			strBuf.addValue("grabPause", mPauseOn, false);
			strBuf.addValue("grabScreensaver", mScreensaverOn, false);
			strBuf.addValue("enable3DDetection", m3DCheckingEnabled, true);
			strBuf.stopObject();

			strBuf.newLine();
		}
		
	// BootEffect
		
		if (mBootEffectEnabled==true){
			String effectEngineComment = "BOOTEFFECT CONFIG";
			strBuf.writeComment(effectEngineComment);
			
			strBuf.startObject("bootsequence");
			strBuf.addArrayRGB("color", mEffectColorRspinner, mEffectColorGspinner, mEffectColorBspinner, false);
			strBuf.addValue("effect", mEffectsetCombo.getTypeId(), false);
			strBuf.addValue("duration_ms", mEffectDurationspinner, false);
			strBuf.addValue("priority", mEffectPriorityspinner, true);
			strBuf.stopObject();

			strBuf.newLine();
		}	
		
	// Json Server
			
			String jsonComment = "JSON SERVER CONFIG";
			strBuf.writeComment(jsonComment);
			
			strBuf.startObject("jsonServer");
			strBuf.addValue("port", mJsonPort, true);
			strBuf.stopObject();

			strBuf.newLine();
			
	// Proto Server
				
			String protoComment = "PROTO SERVER CONFIG";
			strBuf.writeComment(protoComment);
			
			strBuf.startObject("protoServer");
			strBuf.addValue("port", mProtoPort, true);
			strBuf.stopObject();

			strBuf.newLine();
			
	// Boblight Server

		if (mBoblightInterfaceEnabled==true){
			String boblightComment = "BOBLIGHT SERVER CONFIG";
			strBuf.writeComment(boblightComment);
				
			strBuf.startObject("boblightServer");
			strBuf.addValue("port", mBoblightPort, false);
			strBuf.addValue("priority", mBoblightPriority, true);
			strBuf.stopObject();
		
			strBuf.newLine();
		}
	
	// Json/Proto Forward
		
		if (mforwardEnabled==true){
			String JsonforwardComment = "JSON/PROTO FORWARD CONFIG";
			strBuf.writeComment(JsonforwardComment);
						
			strBuf.startObject("forwarder");
			strBuf.addArray("json", mJsonfield, false);
			strBuf.addArray("proto", mProtofield, true);
			strBuf.stopObject();
				
			strBuf.newLine();	
		}

	// Effect Path

			String effectPathComment = "EFFECT PATH";
			strBuf.writeComment(effectPathComment);
					
			strBuf.startObject("effects");
			strBuf.startArray("paths");
			strBuf.addPathValue(mPathOE, false);
			strBuf.addPathValue(mPathGEN, true);
			strBuf.stopArray(true);
			strBuf.stopObject();

			strBuf.newLine();

		if (mFrameGrabberEnabled==false){
			String addComment = "NO FRAME GRABBER CONFIG";
			strBuf.writeComment(addComment);
		}
		if (mBlackBorderEnabled==false){
			String addComment = "NO BLACKBORDER CONFIG";
			strBuf.writeComment(addComment);
		}
		if (mXbmcCheckerEnabled==false){
			String addComment = "NO KODI CHECK CONFIG";
			strBuf.writeComment(addComment);
		}
		if (mBootEffectEnabled==false){
			String addComment = "NO BOOTEFFECT CONFIG";
			strBuf.writeComment(addComment);
		}
		if (mBoblightInterfaceEnabled==false){
			String addComment = "NO BOBLIGHT SERVER CONFIG";
			strBuf.writeComment(addComment);
		}
		if (mforwardEnabled==false){
			String addComment = "NO JSON/PROTO FORWARD CONFIG";
			strBuf.writeComment(addComment);
		}
		strBuf.newLine();
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
