package org.hyperion.hypercon.spec;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterString;
import org.hyperion.model.json.JsonComment;

@JsonComment(
		"The configuration of the effect engine, contains the following items: \n" +
		" * paths : An array with absolute location(s) of directories with effects")
public class EffectEngineModel extends AbstractModel {

	public EffectEngineModel() {
		super();
		initialize();
	}
	
	/** The absolute location(s) of the effects */
	public final ParameterString mEffectEnginePath = new ParameterString("EffectEnginePath", "An array with absolute location(s) of directories with effects", "/opt/hyperion/effects");

}
