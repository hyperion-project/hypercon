package org.hyperion.model;

import org.mufassa.model.AbstractModelExtension;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;

public class DispmanxGrabberModel extends AbstractModelExtension {

	/** Flag indicating that the Frame Grabber is enabled */
	public final ParameterBool enabled = new ParameterBool("Enabled", true); 
	
	/** The width of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt width = new ParameterInt("Width", 64, 1, 1024);
	
	/** The height of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt height = new ParameterInt("Height", 64, 1, 1024);
	
	/** The interval of frame grabs (screen shots) [ms] */
	public final ParameterInt interval_ms = new ParameterInt("Interval_ms", 100, 10, 10000);
}
