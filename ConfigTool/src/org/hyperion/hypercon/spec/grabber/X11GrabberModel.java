package org.hyperion.hypercon.spec.grabber;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterBool;
import org.hyperion.model.ParameterDouble;
import org.hyperion.model.ParameterInt;
import org.hyperion.model.ParameterString;
import org.hyperion.model.ParameterStringEnum;
import org.hyperion.model.Suggestions;
import org.hyperion.model.json.JsonComment;

@JsonComment(
" Configuration for the embedded V4L2 grabber\n" +
"  * device               : V4L2 Device to use [default=\"/dev/video0\"]\n" +
"  * input                : V4L2 input to use [default=0]\n" +
"  * sizeDecimation       : Size decimation factor [default=8]\n" +
" * interval_ms  : The interval between two frame-grabs [ms]\n" +
"  * mode                 : 3D mode to use 2D/3DSBS/3DTAB (note: no autodetection) [default=\"2D\"]\n" +
"  * cropLeft             : Cropping from the left [default=0]\n" +
"  * cropRight            : Cropping from the right [default=0]\n" +
"  * cropTop              : Cropping from the top [default=0]\n" +
"  * cropBottom           : Cropping from the bottom [default=0]\n" +
"  * priority             : Hyperion priority channel [default=800]\n" +
"  * redSignalThreshold   : Signal threshold for the red channel between 0.0 and 1.0 [default=0.0]\n" +
"  * greenSignalThreshold : Signal threshold for the green channel between 0.0 and 1.0 [default=0.0]\n" +      
"  * blueSignalThreshold  : Signal threshold for the blue channel between 0.0 and 1.0 [default=0.0]")
public class X11GrabberModel extends AbstractModel {
	
	public X11GrabberModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that the Frame Grabber is enabled */
	public final ParameterBool enabled = new ParameterBool("Enabled", true);
	
	/** The interval of frame grabs (screen shots) [ms] */
	public final ParameterInt interval_ms = new ParameterInt("interval_ms", 100, 10, 10000);
	
	public final ParameterInt sizeDecimation = new ParameterInt("sizeDecimation", 8, 1, 1024);
	/** The display priority of the grabber */
	public final ParameterStringEnum mode = new ParameterStringEnum("mode", new String[]{"2D", "3DSBS", "3DTAB"}, "2D");
	
	public final ParameterInt cropLeft = new ParameterInt("cropLeft", 0, 0, Integer.MAX_VALUE-1);
	public final ParameterInt cropRight = new ParameterInt("cropRight", 0, 0, Integer.MAX_VALUE-1);
	public final ParameterInt cropTop = new ParameterInt("cropTop", 0, 0, Integer.MAX_VALUE-1);
	public final ParameterInt cropBottom = new ParameterInt("cropBottom", 0, 0, Integer.MAX_VALUE-1);

	public final ParameterInt priority = new ParameterInt("priority", 800, 0, Integer.MAX_VALUE-1);

	public final ParameterDouble redSignalThreshold = new ParameterDouble("redSignalThreshold", 0.0, 0.0, 1.0);
	public final ParameterDouble greenSignalThreshold = new ParameterDouble("greenSignalThreshold", 0.0, 0.0, 1.0);
	public final ParameterDouble blueSignalThreshold = new ParameterDouble("blueSignalThreshold", 0.0, 0.0, 1.0);
}
