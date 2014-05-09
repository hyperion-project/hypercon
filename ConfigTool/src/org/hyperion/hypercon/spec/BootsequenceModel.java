package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterString;

public class BootsequenceModel extends AbstractModel {
	
	public BootsequenceModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that the boot sequence is enabled(true) or not(false) */
	public final ParameterBool enabled = new ParameterBool("enabled", true);
	/** The effect selected for the boot sequence */
	public final ParameterString effect = new ParameterString("effect", "Rainbow swirl fast");
	/** The (maximum) length of the boot-sequence */
	public final ParameterInt duration_ms = new ParameterInt("duration_ms", 3000, 100, 3600000);

}
