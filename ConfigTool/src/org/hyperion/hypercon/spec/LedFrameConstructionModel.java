package org.hyperion.hypercon.spec;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.Event;
import org.hyperion.model.ParameterBool;
import org.hyperion.model.ParameterInt;



/**
 * The LedFrame describes the construction of leds along the sides of the TV screen.
 */
@Event(LedFrameConstructionModel.LEDFRAMECONSTRUCTION_EVENT)
public class LedFrameConstructionModel extends AbstractModel {
	public static final String LEDFRAMECONSTRUCTION_EVENT = "LedFrameConstructionEvent";
	
	/**
	 * Enumeration of the led configuration direction 
	 */
	public enum Direction {
		/** Clockwise led configuration */
		clockwise,
		/** Counter Clockwise led configuration */
		counter_clockwise;
	}
	
	public LedFrameConstructionModel() {
		super();
		initialize();
	}
	
	/** True if the leds are organised clockwise else false (counter clockwise) */
	public final ParameterBool clockwiseDirection = new ParameterBool("ClockwiseDirection", true);
	
	/** True if the top corners have a led else false */
	public final ParameterBool topCorners = new ParameterBool("TopCorners", true);
	/** True if the bottom corners have a led else false */
	public final ParameterBool bottomCorners = new ParameterBool("BottomCorners", true);
	
	/** The number of leds between the top-left corner and the top-right corner of the screen 
		(excluding the corner leds) */
	public final ParameterInt topLedCnt = new ParameterInt("TopLedCnt", 16, 0, 1024);
	/** The number of leds between the bottom-left corner and the bottom-right corner of the screen
		(excluding the corner leds) */
	public final ParameterInt bottomLedCnt = new ParameterInt("BottomLedCnt", 16, 0, 1024);
	
	/** The number of leds between the top-left corner and the bottom-left corner of the screen
		(excluding the corner leds) */
	public final ParameterInt leftLedCnt = new ParameterInt("LeftLedCnt", 7, 0, 1024);
	/** The number of leds between the top-right corner and the bottom-right corner of the screen
		(excluding the corner leds) */
	public final ParameterInt rightLedCnt = new ParameterInt("RightLedCnt", 7, 0, 1024);
	
	/** The offset (in leds) of the starting led counted clockwise from the top-left corner */
	public final ParameterInt firstLedOffset = new ParameterInt("FirstLedOffset", -16, -1024, 1024);
	
	/**
	 * Returns the total number of leds
	 * 
	 * @return The total number of leds
	 */
	public int getLedCount() {
		int cornerLedCnt = 0;
		if (topCorners.getValue())     cornerLedCnt+=2;
		if (bottomCorners.getValue())  cornerLedCnt+=2;
		
		return topLedCnt.getValue() + bottomLedCnt.getValue() + leftLedCnt.getValue() + rightLedCnt.getValue() + cornerLedCnt;
	}
}
