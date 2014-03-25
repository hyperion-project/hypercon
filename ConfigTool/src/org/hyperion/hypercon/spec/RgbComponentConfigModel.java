package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterDouble;

public class RgbComponentConfigModel extends AbstractModel {
	
	public RgbComponentConfigModel() {
		super();
		initialize();
	}

	/** The minimum required component-value (in RGB space) */
	public final ParameterDouble threshold  = new ParameterDouble("threshold", 0.0, 0.0, 1.0);
	/** The gamma-curve correct for the component-value (in RGB space) */
	public final ParameterDouble gamma      = new ParameterDouble("gamma", 1.0, 0.0, 1024.0);
	/** The black-level of the component-value (in RGB space) */
	public final ParameterDouble blacklevel = new ParameterDouble("blacklevel", 0.0, 0.0, 1.0);
	/** The white-level of the component-value (in RGB space) */
	public final ParameterDouble whitelevel = new ParameterDouble("whitelevel", 1.0, 0.0, 1.0);

}
