package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

/**
 * Miscellaneous configuration items for the Hyperion daemon.
 */
public final class MiscConfigModel extends AbstractModel {

	/** The absolute location(s) of the effects */
	public final ParameterObject<String> mEffectEnginePath = new ParameterObject<>("EffectEnginePath", "/opt/hyperion/effects");
	
	/** Flag indicating that the boot sequence is enabled(true) or not(false) */
	public final ParameterBool mBootSequenceEnabled = new ParameterBool("BootSequenceEnabled", true);
	/** The effect selected for the boot sequence */
	public final ParameterObject<String> mBootSequenceEffect = new ParameterObject<>("BootSequenceEffect", "Rainbow swirl fast");
	/** The (maximum) length of the boot-sequence */
	public final ParameterInt mBootSequenceLength_ms = new ParameterInt("BootSequenceLength_ms", 3000, 100, 3600000);
	
	/** Flag indicating that the Frame Grabber is enabled */
	public final ParameterBool mFrameGrabberEnabled = new ParameterBool("FrameGrabberEnabled", true);
	/** The width of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt mFrameGrabberWidth = new ParameterInt("FrameGrabberWidth", 64, 1, 3840);
	/** The height of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt mFrameGrabberHeight = new ParameterInt("FrameGrabberHeight", 64, 1, 2160);
	/** The interval of frame grabs (screen shots) [ms] */
	public final ParameterInt mFrameGrabberInterval_ms = new ParameterInt("FrameGrabberInterval_ms", 100, 10, 10000);

	/** Flag indicating that the XBMC checker is enabled */
	public final ParameterBool mXbmcCheckerEnabled = new ParameterBool("XbmcCheckerEnabled", true);
	/** The IP-address of XBMC */
	public final ParameterObject<String> mXbmcAddress  = new ParameterObject<>("XbmcAddress", "127.0.0.1");
	/** The TCP JSON-Port of XBMC */
	public final ParameterInt mXbmcTcpPort = new ParameterInt("XbmcTcpPort", 9090, 1, 65535);
	/** Flag indicating that the frame-grabber is on during video playback */
	public final ParameterBool mVideoOn = new ParameterBool("VideoOn", true);
	/** Flag indicating that the frame-grabber is on during XBMC menu */
	public final ParameterBool mMenuOn = new ParameterBool("MenuOn", false);
	/** Flag indicating that the frame-grabber is on during picture slideshow */
	public final ParameterBool mPictureOn = new ParameterBool("PictureOn", true);
	/** Flag indicating that the frame-grabber is on during audio playback */
	public final ParameterBool mAudioOn = new ParameterBool("AudioOn", true);
	/** Flag indicating that the frame-grabber is on when xbmc is on screensaver */
	public final ParameterBool mScreensaverOn = new ParameterBool("ScreensaveOn", true);
	/** Flag indicating that the frame-grabber is should take actions when a 3D file is playing */
	public final ParameterBool m3DCheckingEnabled = new ParameterBool("3DCheckEnabled", true);

	/** Flag indicating that the JSON interface is enabled */
	public final ParameterBool mJsonInterfaceEnabled = new ParameterBool("JsonInterfaceEnabled", true);
	/** The TCP port at which the JSON server is listening for incoming connections */
	public final ParameterInt mJsonPort = new ParameterInt("JsonPort", 19444, 1, 65535);

	/** Flag indicating that the PROTO interface is enabled */
	public final ParameterBool mProtoInterfaceEnabled = new ParameterBool("ProtoInterfaceEnabled", true);
	/** The TCP port at which the Protobuf server is listening for incoming connections */
	public final ParameterInt mProtoPort = new ParameterInt("ProtoPort", 19445, 1, 65535);

	/** Flag indicating that the PROTO interface is enabled */
	public final ParameterBool mBoblightInterfaceEnabled = new ParameterBool("BoblightInterfaceEnabled", false);
	/** The TCP port at which the Protobuf server is listening for incoming connections */
	public final ParameterInt mBoblightPort = new ParameterInt("BoblightPort", 19333, 1, 65535);	
}
