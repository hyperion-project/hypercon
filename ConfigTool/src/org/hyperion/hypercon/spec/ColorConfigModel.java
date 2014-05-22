package org.hyperion.hypercon.spec;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ModelList;
import org.hyperion.model.json.JsonComment;

/**
 * The color tuning parameters of the different color channels (both in RGB space as in HSV space)
 */
@JsonComment(
		"Color manipulation configuration used to tune the output colors to specific surroundings. \n" +
		"The configuration contains a list of color-transforms. Each transform contains the \n" +
		"following fields:\n" +
		" * 'id'   : The unique identifier of the color transformation (eg 'device_1')	\n" +
		" * 'leds' : The indices (or index ranges) of the leds to which this color transform applies\n" +
		"            (eg '0-5, 9, 11, 12-17'). The indices are zero based.\n" +
		" * 'hsv' : The manipulation in the Hue-Saturation-Value color domain with the following \n" +
		"           tuning parameters:\n" +
		"           - 'saturationGain'  The gain adjustement of the saturation\n" +
		"           - 'valueGain'       The gain adjustement of the value\n" +
		" * 'red'/'green'/'blue' : The manipulation in the Red-Green-Blue color domain with the \n" +
		"                          following tuning parameters for each channel:\n" +
		"           - 'threshold'       The minimum required input value for the channel to be on \n" +
		"                               (else zero)\n" +
		"           - 'gamma'           The gamma-curve correction factor\n" +
		"           - 'blacklevel'      The lowest possible value (when the channel is black)\n" +
		"           - 'whitelevel'      The highest possible value (when the channel is white)\n" +
		"\n" +
		"Next to the list with color transforms there is also a smoothing option.\n" +
		" * 'smoothing' : Smoothing of the colors in the time-domain with the following tuning \n" +
		"                 parameters:\n" +
		"           - 'type'            The type of smoothing algorithm ('linear' or 'none')\n" +
		"           - 'time_ms'         The time constant for smoothing algorithm in milliseconds\n" +
		"           - 'updateFrequency' The update frequency of the leds in Hz")
public class ColorConfigModel extends AbstractModel {
	
	public ColorConfigModel() {
		super();
		initialize();
	}
	
	/** List with color transformations */
	public final ModelList<TransformConfigModel> transform = new ModelList<TransformConfigModel>();
	{
		transform.add(new TransformConfigModel());
	}

	public final SmoothingConfigModel smoothing = new SmoothingConfigModel();
}
