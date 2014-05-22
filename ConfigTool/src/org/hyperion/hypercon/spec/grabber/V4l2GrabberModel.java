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
"  * device          : V4L2 Device to use [default=\"/dev/video0\"]\n" +
"  * input           : V4L2 input to use [default=0]\n" +
"  * standard        : Video standard (no-change/PAL/NTSC) [default=\"no-change\"]\n" +
"  * width                : V4L2 width to set [default=-1]\n" +
"  * height               : V4L2 height to set [default=-1]\n" +
"  * frameDecimation      : Frame decimation factor [default=2]\n" +
"  * sizeDecimation       : Size decimation factor [default=8]\n" +
"  * priority             : Hyperion priority channel [default=800]\n" +
"  * mode                 : 3D mode to use 2D/3DSBS/3DTAB (note: no autodetection) [default=\"2D\"]\n" +
"  * cropLeft             : Cropping from the left [default=0]\n" +
"  * cropRight            : Cropping from the right [default=0]\n" +
"  * cropTop              : Cropping from the top [default=0]\n" +
"  * cropBottom           : Cropping from the bottom [default=0]\n" +
"  * redSignalThreshold   : Signal threshold for the red channel between 0.0 and 1.0 [default=0.0]\n" +
"  * greenSignalThreshold : Signal threshold for the green channel between 0.0 and 1.0 [default=0.0]\n" +      
"  * blueSignalThreshold  : Signal threshold for the blue channel between 0.0 and 1.0 [default=0.0]")
public class V4l2GrabberModel extends AbstractModel {
	
	public V4l2GrabberModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that the Frame Grabber is enabled */
	public final ParameterBool enabled = new ParameterBool("Enabled", true);
	
	@Suggestions("/dev/video0")
	public final ParameterString device = new ParameterString("device", "/dev/video0");
	public final ParameterInt input = new ParameterInt("input", 0, 0, 16);
	
	public final ParameterStringEnum standard = new ParameterStringEnum("standard", new String[]{"no-change", "PAL", "NTSC"}, "no-change");
	public final ParameterInt width = new ParameterInt("width", 64, 1, 3840);
	public final ParameterInt height = new ParameterInt("height", 64, 1, 2160);
	public final ParameterInt frameDecimation = new ParameterInt("frameDecimation", 2, 1, 1024);
	
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
