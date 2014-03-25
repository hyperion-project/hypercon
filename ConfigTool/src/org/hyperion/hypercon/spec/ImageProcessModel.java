package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.Event;
import org.mufassa.model.ParameterDouble;

/**
 * Configuration parameters for the image processing. These settings are translated using the 
 * {@link LedFrameFactory} to configuration items used in the Hyperion daemon configfile.
 */
@Event(ImageProcessModel.IMAGEPROCESSMODEL_EVENT)
public final class ImageProcessModel extends AbstractModel {
	public static final String IMAGEPROCESSMODEL_EVENT = "ImageProcessModel_Event";

	public ImageProcessModel() {
		super();
		initialize();
	}
	
	/** The 'integration depth' of the leds along the horizontal axis of the tv */
	public final ParameterDouble horizontalDepth = new ParameterDouble("HorizontalDepth", 0.08, 0.01, 1.0);
	/** The 'integration depth' of the leds along the vertical axis of the tv */
	public final ParameterDouble verticalDepth   = new ParameterDouble("VerticalDepth", 0.05, 0.01, 1.0);
	
	/** The gap between the border integration area for the horizontal leds */
	public final ParameterDouble horizontalGap = new ParameterDouble("HorizontalGap", 0.0, 0.0, 1.0);
	/** The gap between the border integration area for the vertical leds */
	public final ParameterDouble verticalGap = new ParameterDouble("VerticalGap", 0.0, 0.0, 1.0);

	/** The fraction of overlap from one to another led */
	public final ParameterDouble overlapFraction = new ParameterDouble("OverlapFraction", 0.0, -1000.0, 1000.0);
	
}
