package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.JsonComment;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

@JsonComment(
		"The configuration of the XBMC connection used to enable and disable the frame-grabber. Contains the following fields:\n" + 
		" * xbmcAddress       : The IP address of the XBMC-host\n" +
		" * xbmcTcpPort       : The TCP-port of the XBMC-server\n" +
		" * grabVideo         : Flag indicating that the frame-grabber is on(true) during video playback\n" +
		" * grabPictures      : Flag indicating that the frame-grabber is on(true) during picture show\n" +
		" * grabAudio         : Flag indicating that the frame-grabber is on(true) during audio playback\n" +
		" * grabMenu          : Flag indicating that the frame-grabber is on(true) in the XBMC menu\n" +
		" * grabScreensaver   : Flag indicating that the frame-grabber is on(true) when XBMC is on screensaver\n" +
		" * enable3DDetection : Flag indicating that the frame-grabber should switch to a 3D compatible modus if a 3D video is playing")
public class XbmcVideoCheckerModel extends AbstractModel {
	
	public XbmcVideoCheckerModel() {
		super();
		initialize();
	}

	/** Flag indicating that the XBMC checker is enabled */
	public final ParameterBool mEnabled = new ParameterBool("Enabled", true);
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
}
