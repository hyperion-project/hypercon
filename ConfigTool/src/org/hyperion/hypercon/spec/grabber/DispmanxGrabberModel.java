package org.hyperion.hypercon.spec.grabber;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterBool;
import org.hyperion.model.ParameterDouble;
import org.hyperion.model.ParameterInt;
import org.hyperion.model.json.JsonComment;

@JsonComment(
		"The configuration for the dispmanx frame-grabber, contains the following items: \n" +
		" * enabled      : True if the grabber is enabled else false\n" +
		" * priority     : The display priority of the grabber\n" +
		" * width        : The width of the grabbed frames [pixels]\n" +
		" * height       : The height of the grabbed frames [pixels]\n" +
		" * interval_ms  : The interval between two frame-grabs [ms]\n" +
		" * redSignalThreshold   : Signal threshold for the red channel between 0.0 and 1.0 [default=0.0]\n" +
		" * greenSignalThreshold : Signal threshold for the green channel between 0.0 and 1.0 [default=0.0]\n" +      
		" * blueSignalThreshold  : Signal threshold for the blue channel between 0.0 and 1.0 [default=0.0]")
public class DispmanxGrabberModel extends AbstractModel {
	
	public DispmanxGrabberModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that the Frame Grabber is enabled */
	public final ParameterBool enabled = new ParameterBool("Enabled", true);
	
	/** The display priority of the grabber */
	public final ParameterInt priority = new ParameterInt("priority", 1000, 0, Integer.MAX_VALUE-1);
	/** The width of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt width = new ParameterInt("width", 64, 1, 3840);
	/** The height of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt height = new ParameterInt("height", 64, 1, 2160);
	/** The interval of frame grabs (screen shots) [ms] */
	public final ParameterInt interval_ms = new ParameterInt("interval_ms", 100, 10, 10000);

	public final ParameterDouble redSignalThreshold = new ParameterDouble("redSignalThreshold", 0.0, 0.0, 1.0);
	public final ParameterDouble greenSignalThreshold = new ParameterDouble("greenSignalThreshold", 0.0, 0.0, 1.0);
	public final ParameterDouble blueSignalThreshold = new ParameterDouble("blueSignalThreshold", 0.0, 0.0, 1.0);

}
