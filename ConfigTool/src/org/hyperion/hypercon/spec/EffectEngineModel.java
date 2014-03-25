package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.JsonComment;
import org.mufassa.model.ParameterObject;

@JsonComment(
		"The configuration of the effect engine, contains the following items: \n" +
		" * paths : An array with absolute location(s) of directories with effects")
public class EffectEngineModel extends AbstractModel {

	public EffectEngineModel() {
		super();
		initialize();
	}
	
	/** The absolute location(s) of the effects */
	public final ParameterObject<String> mEffectEnginePath = new ParameterObject<>("EffectEnginePath", "/opt/hyperion/effects");

}
