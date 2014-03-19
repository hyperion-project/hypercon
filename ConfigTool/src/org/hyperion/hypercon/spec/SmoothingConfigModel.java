package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

public class SmoothingConfigModel extends AbstractModel {
	
	public final ParameterBool mSmoothingEnabled = new ParameterBool("enabled", false);
	/** The type of smoothing algorithm */
	public final ParameterObject<ColorSmoothingType> mType = new ParameterObject<>("type", ColorSmoothingType.linear);
	/** The time constant for smoothing algorithm in milliseconds */
	public final ParameterInt mTime_ms = new ParameterInt("time_ms", 200, 10, 3600000);
	/** The update frequency of the leds in Hz */
	public final ParameterDouble mUpdateFrequency_Hz = new ParameterDouble("updateFrequency", 20.0, 0.01, 100.0);

}
