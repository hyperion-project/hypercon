package org.hyperion.hypercon.spec;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;


/**
 * Led specification with fractional location along screen border and fractional-rectangle for 
 * integrating an image into led color  
 */
public class LedModel extends AbstractModel {
	
	public LedModel() {
		super();
		initialize();
	}

	/** The sequence number of the led */
	public final ParameterInt sequenceNr = new ParameterInt("SequenceNr", 0, 0, Integer.MAX_VALUE);
	
	/** The side along which the led is placed */
	public final ParameterObject<BorderSide> side = new ParameterObject<>("Side", BorderSide.top);
	
	/** The fractional location of the led */
	public final ParameterObject<Point2D.Double> location = new ParameterObject<>("Location", new Point2D.Double());

	/** The fractional rectangle for image integration */
	public final ParameterObject<Rectangle2D.Double> imageRectangle = new ParameterObject<>("ImageRectangle", new Rectangle2D.Double());
}
