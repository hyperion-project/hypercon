package org.hyperion.model;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.Event;
import org.mufassa.model.ParameterDouble;

@Event(LedProcessModel.LEDPROCESSMODEL_EVENT)
public class LedProcessModel extends AbstractModel {
	public static final String LEDPROCESSMODEL_EVENT = "LedProcessEvent";
	
	/** The 'integration depth' of the leds along the horizontal axis of the tv */
	public ParameterDouble horizontalDepth = new ParameterDouble("HorizontalDepth", 0.08, 0.0, 1.0);
	/** The 'integration depth' of the leds along the vertical axis of the tv */
	public ParameterDouble verticalDepth   = new ParameterDouble("VerticalDepth", 0.05, 0.0, 1.0);
	
	/** The gap between the border integration area for the horizontal leds */
	public ParameterDouble horizontalGap = new ParameterDouble("HorizontalGap", 0.0, 0.0, 1.0);
	/** The gap between the border integration area for the vertical leds */
	public ParameterDouble verticalGap = new ParameterDouble("VertcialGap", 0.0, 0.0, 1.0);

	/** The fraction of overlap from one to another led */
	public ParameterDouble overlapFraction = new ParameterDouble("OverlapFraction", 0.0, 0.0, 1.0);
}
