package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.JsonStringBuffer;

/**
 * Created by Fabian on 14.02.2015.
 */
public class Grabberv4l2Config {

	public boolean mGrabberv4l2Enabled = false;

	public String mDevice = "/dev/video0";
	public int mInput = 0;
	public VideoStandard mStandard = VideoStandard.PAL;
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
	
	if (mGrabberv4l2Enabled==true){
		String grabberv4l2Comment = "V4L2 GRABBER CONFIG";
		strBuf.writeComment(grabberv4l2Comment);

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

		strBuf.newLine();
	}else{
		String grabComment = "NO V4L2 GRABBER CONFIG";
		strBuf.writeComment(grabComment);
	}

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
