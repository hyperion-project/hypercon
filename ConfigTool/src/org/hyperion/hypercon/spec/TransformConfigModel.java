package org.hyperion.hypercon.spec;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterDouble;
import org.hyperion.model.ParameterObject;
import org.hyperion.model.ParameterString;

public class TransformConfigModel extends AbstractModel {
	
	public TransformConfigModel() {
		super();
		initialize();
	}

	/** The identifier of this ColorTransform configuration */
	public final ParameterString id = new ParameterString("id", "default");
	
	/** The indices to which this transform applies */
	public final ParameterString ledIndexString = new ParameterString("leds", "*");
	
	/** The saturation gain (in HSV space) */
	public final ParameterDouble saturationGain = new ParameterDouble("saturationGain", 1.0, 0.0, 1024.0);
	/** The value gain (in HSV space) */
	public final ParameterDouble valueGain = new ParameterDouble("valueGain", 1.0, 0.0, 1024.0);
	
	/** The transform configuration of the red component (RGB space) */
	public final RgbComponentConfigModel red = new RgbComponentConfigModel();
	
	/** The transform configuration of the green component (RGB space) */
	public final RgbComponentConfigModel green = new RgbComponentConfigModel();
	
	/** The transform configuration of the blue component (RGB space) */
	public final RgbComponentConfigModel blue = new RgbComponentConfigModel();
}
