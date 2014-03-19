package org.hyperion.control;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.hyperion.hypercon.spec.BorderSide;
import org.hyperion.model.HyperionModel;
import org.hyperion.model.LedConfigModel;
import org.hyperion.model.LedFrameModel;
import org.hyperion.model.LedProcessModel;
import org.mufassa.model.IModelObserver;
import org.mufassa.model.ModelEvent;

public class LedConfigController {

	private final HyperionModel mModel;
	private final IModelObserver mObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			updateLeds();
		}
	};
	
	
	public LedConfigController(final HyperionModel pModel) {
		super();
		
		mModel = pModel;
		
		mModel.ledFrameModel.addObserver(mObserver, LedFrameModel.LEDFRAME_EVENT);
		mModel.ledProcessModel.addObserver(mObserver, LedProcessModel.LEDPROCESSMODEL_EVENT);
	}
	
	/**
	 * Translate a 'frame' and picture integration specification to per-led specification
	 */
	private void updateLeds() { 

		// Remove existing leds
		mModel.ledConfigurations.clear();
		
		int totalLedCount = mModel.ledFrameModel.getTotalLedCount();
		if (totalLedCount <= 0) {
			// If there are no leds, we have nothing to do anymore but process the clear event
			mModel.ledConfigurations.commitEvents();
			return;
		}
		
		// Determine the led-number of the top-left led
		int iLed = (totalLedCount - mModel.ledFrameModel.firstLedOffset.getValue())%totalLedCount;
		if (iLed < 0) {
			iLed += totalLedCount;
		}
		
		// Construct the top-left led (if top-left is enabled)
		if (mModel.ledFrameModel.topCorners.getValue()) {
			mModel.ledConfigurations.add(createLed(iLed, 0.0, 0.0, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.top_left));
			iLed = increase(iLed);
		}
		
		// Construct all leds along the top of the screen (if any)
		if (mModel.ledFrameModel.topLedCnt.getValue() > 0) {
			// Determine the led-spacing
			int ledCnt = mModel.ledFrameModel.topLedCnt.getValue();
			double ledSpacing = (double)1.0/(ledCnt);

			for (int iTop=0; iTop<ledCnt; ++iTop) {
				// Compute the location of this led
				double led_x = ledSpacing/2.0 + iTop * ledSpacing;
				double led_y = 0;

				// Construct and add the single led specification to the list of leds
				mModel.ledConfigurations.add(createLed(iLed, led_x, led_y, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.top));
				iLed = increase(iLed);
			}
		}
		
		// Construct the top-right led (if top-right is enabled)
		if (mModel.ledFrameModel.topCorners.getValue()) {
			mModel.ledConfigurations.add(createLed(iLed, 1.0, 0.0, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.top_right));
			iLed = increase(iLed);
		}
		
		// Construct all leds along the right of the screen (if any)
		if (mModel.ledFrameModel.rightLedCnt.getValue() > 0) {
			// Determine the led-spacing
			int ledCnt = mModel.ledFrameModel.rightLedCnt.getValue();
			double ledSpacing = 1.0/ledCnt;

			for (int iRight=0; iRight<ledCnt; ++iRight) {
				// Compute the location of this led
				double led_x = 1.0;
				double led_y = ledSpacing/2.0 + iRight * ledSpacing;

				// Construct and add the single led specification to the list of leds
				mModel.ledConfigurations.add(createLed(iLed, led_x, led_y, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.right));
				iLed = increase(iLed);
			}
		}
		
		// Construct the bottom-right led (if bottom-right is enabled)
		if (mModel.ledFrameModel.bottomCorners.getValue()) {
			mModel.ledConfigurations.add(createLed(iLed, 1.0, 1.0, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.bottom_right));
			iLed = increase(iLed);
		}
		
		// Construct all leds along the bottom of the screen (if any)
		if (mModel.ledFrameModel.bottomLedCnt.getValue() > 0) {
			// Determine the led-spacing (based on top-leds [=bottom leds + gap size])
			int ledCnt = mModel.ledFrameModel.topLedCnt.getValue();
			double ledSpacing = (double)1.0/ledCnt;

			for (int iBottom=(ledCnt-1); iBottom>=0; --iBottom) {
				// Special case for the bottom-gap
				if (iBottom > (mModel.ledFrameModel.bottomLedCnt.getValue()-1)/2 && iBottom < ledCnt - mModel.ledFrameModel.bottomLedCnt.getValue()/2) {
					continue;
				}
				// Compute the location of this led
				double led_x = ledSpacing/2.0 + iBottom * ledSpacing;
				double led_y = 1.0;

				// Construct and add the single led specification to the list of leds
				mModel.ledConfigurations.add(createLed(iLed, led_x, led_y, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.bottom));
				iLed = increase(iLed);
			}
		}
		
		// Construct the bottom-left led (if bottom-left is enabled)
		if (mModel.ledFrameModel.bottomCorners.getValue()) {
			mModel.ledConfigurations.add(createLed(iLed, 0.0, 1.0, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.bottom_left));
			iLed = increase(iLed);
		}
		
		// Construct all leds along the left of the screen (if any)
		if (mModel.ledFrameModel.leftLedCnt.getValue() > 0) {
			// Determine the led-spacing
			int ledCnt = mModel.ledFrameModel.leftLedCnt.getValue();
			double ledSpacing = (double)1.0/ledCnt;
			
			for (int iRight=(ledCnt-1); iRight>=0; --iRight) {
				// Compute the location of this led
				double led_x = 0.0;
				double led_y = ledSpacing/2.0 + iRight * ledSpacing;

				// Construct and add the single led specification to the list of leds
				mModel.ledConfigurations.add(createLed(iLed, led_x, led_y, mModel.ledProcessModel.overlapFraction.getValue(), BorderSide.left));
				iLed = increase(iLed);
			}
		}

		Collections.sort(mModel.ledConfigurations, new Comparator<LedConfigModel>() {
			@Override
			public int compare(LedConfigModel o1, LedConfigModel o2) {
				return Integer.compare(o1.sequenceNr.getValue(), o2.sequenceNr.getValue());
			}
		});
		
		// Process all build up events
		mModel.ledConfigurations.commitEvents();
	}
	

	/**
	 * Convenience method for increasing the led counter (it might actually decrease if the frame is 
	 * counter clockwise)
	 * 
	 * @param pLedCounter The current led counter
	 * @return The counter/index of the next led
	 */
	private int increase(int pLedCounter) {
		if (mModel.ledFrameModel.clockwiseDirection.getValue()) {
			return (pLedCounter+1)%mModel.ledFrameModel.getTotalLedCount();
		} else {
			if (pLedCounter == 0) {
				return mModel.ledFrameModel.getTotalLedCount() - 1;
			}
			return pLedCounter -1;
		}
		
	}

	/**
	 * Constructs the specification of a single led
	 * 
	 * @param pFrameSpec The overall led-frame specification
	 * @param pProcessSpec The overall image-processing specification
	 * @param seqNr The number of the led
	 * @param x_frac The x location of the led in fractional range [0.0; 1.0]
	 * @param y_frac The y location of the led in fractional range [0.0; 1.0]
	 * @param overlap_frac The fractional overlap of the led integration with its neighbor
	 * @param pBorderSide The side on which the led is located
	 * 
	 * @return The image integration specifications of the single led
	 */
	private LedConfigModel createLed(int seqNr, double x_frac, double y_frac, double overlap_frac, BorderSide pBorderSide) {
		LedConfigModel led = new LedConfigModel();
		led.sequenceNr.setValue(seqNr);
		
		final double verticalGap = mModel.ledProcessModel.verticalGap.getValue();
		final double horizontalGap = mModel.ledProcessModel.horizontalGap.getValue();
		
		final int topLedCnt  = mModel.ledFrameModel.topLedCnt.getValue();
		final int leftLedCnt = mModel.ledFrameModel.leftLedCnt.getValue();
		
		double xFrac      = verticalGap   + (1.0 - 2.0*verticalGap)   * x_frac;	
		double yFrac      = horizontalGap + (1.0 - 2.0*horizontalGap) * y_frac;	
		double widthFrac  = ((1.0 - 2.0*verticalGap)  /topLedCnt  * (1.0 + overlap_frac))/2.0;
		double heightFrac = ((1.0 - 2.0*horizontalGap)/leftLedCnt * (1.0 + overlap_frac))/2.0;
		
		double horizontalDepth = Math.min(1.0 - horizontalGap, mModel.ledProcessModel.horizontalDepth.getValue());
		double verticalDepth   = Math.min(1.0 - verticalGap,   mModel.ledProcessModel.verticalDepth.getValue());
		
		Rectangle2D.Double ledArea = null;
		switch (pBorderSide) {
		case top_left: {
			ledArea = new Rectangle2D.Double(
					verticalGap, 
					horizontalGap,
					verticalDepth, 
					horizontalDepth);
			break;
		}
		case top_right: {
			ledArea = new Rectangle2D.Double(
					1.0-verticalGap-verticalDepth,
					horizontalGap,
					verticalDepth, 
					horizontalDepth);
			break;
		}
		case bottom_left: {
			ledArea = new Rectangle2D.Double(
					verticalGap,
					1.0-horizontalGap-horizontalDepth,
					verticalDepth,
					horizontalDepth);
			break;
		}
		case bottom_right: {
			ledArea = new Rectangle2D.Double(
					1.0-verticalGap-verticalDepth, 
					1.0-horizontalGap-horizontalDepth,
					verticalDepth,
					horizontalDepth);
			break;
		}
		case top:{
			double intXmin_frac = Math.max(0.0, xFrac-widthFrac);
			double intXmax_frac = Math.min(xFrac+widthFrac, 1.0);
			ledArea = new Rectangle2D.Double(
					intXmin_frac, 
					horizontalGap, 
					intXmax_frac-intXmin_frac, 
					horizontalDepth);
			
			break;
		}
		case bottom:
		{
			double intXmin_frac = Math.max(0.0, xFrac-widthFrac);
			double intXmax_frac = Math.min(xFrac+widthFrac, 1.0);
			
			ledArea = new Rectangle2D.Double(
					intXmin_frac, 
					1.0-horizontalGap-horizontalDepth, 
					intXmax_frac-intXmin_frac, 
					horizontalDepth);
			break;
		}
		case left: {
			double intYmin_frac = Math.max(0.0, yFrac-heightFrac);
			double intYmax_frac = Math.min(yFrac+heightFrac, 1.0);
			ledArea = new Rectangle2D.Double(
					verticalGap, 
					intYmin_frac, 
					verticalDepth, 
					intYmax_frac-intYmin_frac);
			break;
		}
		case right:
			double intYmin_frac = Math.max(0.0, yFrac-heightFrac);
			double intYmax_frac = Math.min(yFrac+heightFrac, 1.0);
			ledArea = new Rectangle2D.Double(
					1.0-verticalGap-verticalDepth, 
					intYmin_frac, 
					verticalDepth, 
					intYmax_frac-intYmin_frac);
			break;
		}
		led.imageArea.setValue(ledArea);
		
		// Make sure all events are processed before returning
		led.commitEvents();
		
		return led;
	}
	
}
