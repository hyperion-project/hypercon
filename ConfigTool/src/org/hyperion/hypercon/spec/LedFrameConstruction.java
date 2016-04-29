package org.hyperion.hypercon.spec;

import java.util.Observable;

import org.hyperion.hypercon.language.language;



/**
 * The LedFrame describes the construction of leds along the sides of the TV screen.
 */
public class LedFrameConstruction extends Observable {
	/**
	 * Enumeration of the led configuration direction 
	 */
//	public enum Direction {
//		/** Clockwise led configuration */
//		clockwise,
//		/** Counter Clockwise led configuration */
//		counter_clockwise;
//	}
	public enum Direction {
		clockwise(language.getString("hardware.directionlist.clockwise")), 
		counter_clockwise(language.getString("hardware.directionlist.counter_clockwise")); 
	
	private final String mtext;

    private Direction(final String name){
        mtext = name;
    }
	 @Override
	    public String toString() {
	        return mtext;
	    }
	}
	/** True if the leds are organised clockwise else false (counter clockwise) */
	public boolean clockwiseDirection = true;
	
	public boolean topleftCorner = false;
	public boolean toprightCorner = false;
	public boolean bottomleftCorner = false;
	public boolean bottomrightCorner = false;
	/** The number of leds between the top-left corner and the top-right corner of the screen 
		(excluding the corner leds) */
	public int topLedCnt = 16;
	/** The number of leds between the bottom-left corner and the bottom-right corner of the screen
		(excluding the corner leds) */
	public int bottomLedCnt = 16;
	
	/** The number of leds between the top-left corner and the bottom-left corner of the screen
		(excluding the corner leds) */
	public int leftLedCnt = 7;
	/** The number of leds between the top-right corner and the bottom-right corner of the screen
		(excluding the corner leds) */
	public int rightLedCnt = 7;
	
	/** The offset (in leds) of the starting led counted clockwise from the top-left corner */
	public int firstLedOffset = -16;
	
	public int bottomGapCnt = 0;
	/**
	 * Returns the total number of leds
	 * 
	 * @return The total number of leds
	 */

	public int getLedCount() {
		int cornerLedCnt = 0;
		if (topleftCorner)     cornerLedCnt+=1;
		if (toprightCorner)  cornerLedCnt+=1;
		if (bottomleftCorner)     cornerLedCnt+=1;
		if (bottomrightCorner)  cornerLedCnt+=1;
		
		return topLedCnt + bottomLedCnt + leftLedCnt + rightLedCnt + cornerLedCnt;
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
	}
}
