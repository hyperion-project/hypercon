package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.JsonComment;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;

@JsonComment(
		"///  The configuration for the frame-grabber, contains the following items: \n" +
		"///   * width        : The width of the grabbed frames [pixels]\n" +
		"///   * height       : The height of the grabbed frames [pixels]\n" +
		"///   * frequency_Hz : The frequency of the frame grab [Hz]")
public class VideoGrabberModel extends AbstractModel {
	
	public VideoGrabberModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that the Frame Grabber is enabled */
	public final ParameterBool enabled = new ParameterBool("FrameGrabberEnabled", true);
	/** The width of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt width = new ParameterInt("width", 64, 1, 3840);
	/** The height of 'grabbed' frames (screen shots) [pixels] */
	public final ParameterInt height = new ParameterInt("height", 64, 1, 2160);
	/** The interval of frame grabs (screen shots) [ms] */
	public final ParameterInt interval_ms = new ParameterInt("interval_ms", 100, 10, 10000);

}
