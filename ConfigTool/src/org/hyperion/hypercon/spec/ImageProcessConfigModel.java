package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.LedFrameFactory;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;

/**
 * Configuration parameters for the image processing. These settings are translated using the 
 * {@link LedFrameFactory} to configuration items used in the Hyperion daemon configfile.
 *
 */
public final class ImageProcessConfigModel extends AbstractModel {
	
	/** The 'integration depth' of the leds along the horizontal axis of the tv */
	public final ParameterDouble mHorizontalDepth = new ParameterDouble("HorizontalDepth", 0.08, 0.01, 1.0);
	/** The 'integration depth' of the leds along the vertical axis of the tv */
	public final ParameterDouble mVerticalDepth   = new ParameterDouble("VerticalDepth", 0.05, 0.01, 1.0);
	
	/** The gap between the border integration area for the horizontal leds */
	public final ParameterDouble mHorizontalGap = new ParameterDouble("HorizontalGap", 0.0, 0.0, 1.0);
	/** The gap between the border integration area for the vertical leds */
	public final ParameterDouble mVerticalGap = new ParameterDouble("VerticalGap", 0.0, 0.0, 1.0);

	/** The fraction of overlap from one to another led */
	public final ParameterDouble mOverlapFraction = new ParameterDouble("OverlapFraction", 0.0, -1000.0, 1000.0);
	
	/** Flag indicating that black borders are excluded in the image processing */
	public final ParameterBool mBlackBorderRemoval = new ParameterBool("BlackBorderRemoval", true);
	/** Threshold for the blackborder detector */
	public final ParameterDouble mBlackBorderThreshold = new ParameterDouble("BlackBorderThreshold", 0.01, 0.00, 1.0);
	
}
