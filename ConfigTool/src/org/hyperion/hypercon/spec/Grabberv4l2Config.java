package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.JsonStringBuffer;

/**
 * Created by Fabian on 14.02.2015.
 */
public class Grabberv4l2Config {

	public boolean mGrabberv4l2Enabled = false;

	public String mDevice = "/dev/video0";
	public int mInput = 0;
	public VideoStandard mStandard = VideoStandard.noChange;
	public int mWidth = -1;
	public int mHeight = -1;
	public int mFrameDecimation = 2;
	public int mSizeDecimation = 8;
	public int mPriority = 900;
	public DimensionModes mMode = DimensionModes.TwoD;
	public int mCropLeft = 0;
	public int mCropRight = 0;
	public int mCropTop = 0;
	public int mCropBottom = 0;
	public double mRedSignalThreshold = 0.0;
	public double mGreenSignalThreshold = 0.0;
	public double mBlueSignalThreshold = 0.0;


	public void appendTo(JsonStringBuffer strBuf) {
		String grabberv4l2Comment = "Configuration for the embedded V4L2 grabber\n"
				+ " * device          : V4L2 Device to use [default=\"/dev/video0\"]\n"
				+ " * input           : V4L2 input to use [default=0]\n"
				+ " * standard        : Video standard (no-change/PAL/NTSC) [default=\"no-change\"]\n"
				+ " * width                : V4L2 width to set [default=-1]\n"
				+ " * height               : V4L2 height to set [default=-1]\n"
				+ " * frameDecimation      : Frame decimation factor [default=2]\n"
				+ " * sizeDecimation       : Size decimation factor [default=8]\n"
				+ " * priority             : Hyperion priority channel [default=900]\n"
				+ " * mode                 : 3D mode to use 2D/3DSBS/3DTAB (note: no autodetection) [default=\"2D\"]\n"
				+ " * cropLeft             : Cropping from the left [default=0]\n"
				+ " * cropRight            : Cropping from the right [default=0]\n"
				+ " * cropTop              : Cropping from the top [default=0]\n"
				+ " * cropBottom           : Cropping from the bottom [default=0]\n"
				+ " * redSignalThreshold   : Signal threshold for the red channel between 0.0 and 1.0 [default=0.0]\n"
				+ " * greenSignalThreshold : Signal threshold for the green channel between 0.0 and 1.0 [default=0.0]\n"
				+ " * blueSignalThreshold  : Signal threshold for the blue channel between 0.0 and 1.0 [default=0.0]";
		strBuf.writeComment(grabberv4l2Comment);

		strBuf.toggleComment(!mGrabberv4l2Enabled);
		strBuf.startObject("grabber-v4l2");
		strBuf.addValue("device", mDevice, false);
		strBuf.addValue("input", mInput, false);
		strBuf.addValue("standard", mStandard.toString(), false);
		strBuf.addValue("width", mWidth, false);
		strBuf.addValue("height", mHeight, false);
		strBuf.addValue("frameDecimation", mFrameDecimation, false);
		strBuf.addValue("sizeDecimation", mSizeDecimation, false);
		strBuf.addValue("priority", mPriority, false);
		strBuf.addValue("mode", mMode.toString(), false);
		strBuf.addValue("cropLeft", mCropLeft, false);
		strBuf.addValue("cropRight", mCropRight, false);
		strBuf.addValue("cropTop", mCropTop, false);
		strBuf.addValue("cropBottom", mCropBottom, false);
		strBuf.addValue("redSignalThreshold", mRedSignalThreshold, false);
		strBuf.addValue("greenSignalThreshold", mGreenSignalThreshold, false);
		strBuf.addValue("blueSignalThreshold", mBlueSignalThreshold, true);
		strBuf.stopObject();
		strBuf.toggleComment(false);
	}

	/**
	 * Creates the JSON string of the configuration as used in the Hyperion
	 * daemon configfile
	 *
	 * @return The JSON string of this Config
	 */
	public String toJsonString() {
		JsonStringBuffer jsonBuf = new JsonStringBuffer(1);
		appendTo(jsonBuf);
		return jsonBuf.toString();
	}

	public static void main(String[] pArgs) {
		Grabberv4l2Config config = new Grabberv4l2Config();

		JsonStringBuffer jsonBuf = new JsonStringBuffer(1);
		config.appendTo(jsonBuf);

		System.out.println(jsonBuf.toString());
	}
}
