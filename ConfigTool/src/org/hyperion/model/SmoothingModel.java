package org.hyperion.model;

import org.hyperion.hypercon.spec.ColorSmoothingType;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

public class SmoothingModel extends AbstractModel {
	
	public final ParameterBool enabled = new ParameterBool("Enbaled", false);
	
	/** The type of smoothing algorithm */
	public final ParameterObject<ColorSmoothingType> type = new ParameterObject<>("Type", ColorSmoothingType.linear);
	/** The time constant for smoothing algorithm in milliseconds */
	public final ParameterInt time_ms = new ParameterInt("Time_ms", 200, 50, 100000);
	/** The update frequency of the leds in Hz */
	public final ParameterDouble updateFrequency_Hz = new ParameterDouble("", 20.0, 1.0, 100.0);

}
