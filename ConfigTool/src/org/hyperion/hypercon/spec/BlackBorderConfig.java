package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.JsonStringBuffer;

/**
 * Created by Sascha on 20.02.2016.
 */
public class BlackBorderConfig {
	public boolean mBlackBorderEnabled = true;
	public double mBlackBorderThreshold = 0.00;
	public int mBlackBorderuFrameCnt = 600;
	public int mBlackBorderbFrameCnt = 50;	
	public int mBlackBordermInconsistentCnt = 10;	
	public int mBlackBorderbRemoveCnt = 1;	
	public BlackBorderStandard mBlackbordermCombo = BlackBorderStandard.defaultt;
	
		public void appendTo(JsonStringBuffer pJsonBuf) {
	String blackboardercomment = 
			"The black border configuration, contains the following items: \n" +
			" * enable    			: true if the detector should be activated\n" +
			" * threshold 			: Value below which a pixel is regarded as black (value between 0.0 and 1.0)\n" +
			" * unknownFrameCnt		: Number of frames without any detection before the border is set to 0 (default 600)\n" +
			" * borderFrameCnt		: Number of frames before a consistent detected border gets set (default 50)\n" +
			" * maxInconsistentCnt 	: Number of inconsistent frames that are ignored before a new border gets a chance to proof consistency\n" +
			" * blurRemoveCnt		: Number of pixels that get removed from the detected border to cut away blur (default 1)\n" +
			" * mode 				: Border detection mode (values=default,classic,osd)\n";
	pJsonBuf.writeComment(blackboardercomment);
	
	pJsonBuf.startObject("blackborderdetector");
	pJsonBuf.addValue("enable", mBlackBorderEnabled, false);
	pJsonBuf.addValue("threshold", mBlackBorderThreshold, false);
	pJsonBuf.addValue("unknownFrameCnt", mBlackBorderuFrameCnt, false);
	pJsonBuf.addValue("borderFrameCnt", mBlackBorderbFrameCnt, false);
	pJsonBuf.addValue("maxInconsistentCnt", mBlackBordermInconsistentCnt, false);
	pJsonBuf.addValue("blurRemoveCnt", mBlackBorderbRemoveCnt, false);
	pJsonBuf.addValue("mode", mBlackbordermCombo.toString(), true);
	pJsonBuf.stopObject();	
	}
}