package org.hyperion.hypercon.spec;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterBool;
import org.hyperion.model.ParameterDouble;
import org.hyperion.model.json.JsonComment;

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
