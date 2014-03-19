package org.hyperion.model;

import java.awt.geom.Rectangle2D;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

public class LedConfigModel extends AbstractModel {

	public final ParameterInt sequenceNr = new ParameterInt("Sequence #", 0, 0, Integer.MAX_VALUE);
	
	public final ParameterObject<Rectangle2D.Double> imageArea = new ParameterObject<>("Image Area", new Rectangle2D.Double()); 
}
