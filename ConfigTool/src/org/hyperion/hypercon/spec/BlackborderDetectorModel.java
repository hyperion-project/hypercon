package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.JsonComment;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;

@JsonComment(
		"The black border configuration, contains the following items: \n" +
		" * enable    : true if the detector should be activated\n" +
		" * threshold : Value below which a pixel is regarded as black (value between 0.0 and 1.0)")
public class BlackborderDetectorModel extends AbstractModel {
	
	public BlackborderDetectorModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that black borders are excluded in the image processing */
	public final ParameterBool enabled = new ParameterBool("enabled", true);
	/** Threshold for the blackborder detector */
	public final ParameterDouble threshold = new ParameterDouble("threshold", 0.01, 0.00, 1.0);

}
