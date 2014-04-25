package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

public class BootsequenceModel extends AbstractModel {
	
	public BootsequenceModel() {
		super();
		initialize();
	}
	
	/** Flag indicating that the boot sequence is enabled(true) or not(false) */
	public final ParameterBool enabled = new ParameterBool("enabled", true);
	/** The effect selected for the boot sequence */
	public final ParameterObject<String> effect = new ParameterObject<String>("effect", "Rainbow swirl fast");
	/** The (maximum) length of the boot-sequence */
	public final ParameterInt duration_ms = new ParameterInt("duration_ms", 3000, 100, 3600000);

}
