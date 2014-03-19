package org.hyperion.model;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.Event;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;

@Event(LedFrameModel.LEDFRAME_EVENT)
public class LedFrameModel extends AbstractModel {
	
	public final static String LEDFRAME_EVENT = "LedFrameEvent";
	
	/** True if the leds are organised clockwise else false (counter clockwise) */
	public final ParameterBool clockwiseDirection = new ParameterBool("Clockwise", true);
	
	/** True if the top corners have a led else false */
	public final ParameterBool topCorners = new ParameterBool("TopCorners", true);

	/** True if the bottom corners have a led else false */
	public final ParameterBool bottomCorners = new ParameterBool("BottomCorners", true);
	
	/** The number of leds between the top-left corner and the top-right corner of the screen 
		(excluding the corner leds) */
	public final ParameterInt topLedCnt = new ParameterInt("TopLedCnt", 16, 0, Integer.MAX_VALUE);

	/** The number of leds between the bottom-left corner and the bottom-right corner of the screen
		(excluding the corner leds) */
	public final ParameterInt bottomLedCnt = new ParameterInt("BottomLedCnt", 16, 0, Integer.MAX_VALUE);
	
	/** The number of leds between the top-left corner and the bottom-left corner of the screen
		(excluding the corner leds) */
	public final ParameterInt leftLedCnt = new ParameterInt("LeftLedCnt", 7, 0, Integer.MAX_VALUE);

	/** The number of leds between the top-right corner and the bottom-right corner of the screen
		(excluding the corner leds) */
	public final ParameterInt rightLedCnt = new ParameterInt("RightLedCnt", 7, 0, Integer.MAX_VALUE);
	
	/** The offset (in leds) of the starting led counted clockwise from the top-left corner */
	public final ParameterInt firstLedOffset = new ParameterInt("FirstLedOffset", -16, Integer.MIN_VALUE, Integer.MAX_VALUE);

	/**
	 * Returns the total number of leds
	 * 
	 * @return The total number of leds
	 */
	public int getTotalLedCount() {
		int cornerLedCnt = 0;
		if (topCorners.getValue())     cornerLedCnt+=2;
		if (bottomCorners.getValue())  cornerLedCnt+=2;
		
		return topLedCnt.getValue() + bottomLedCnt.getValue() + leftLedCnt.getValue() + rightLedCnt.getValue() + cornerLedCnt;
	}
}
