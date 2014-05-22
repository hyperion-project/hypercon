package org.hyperion.hypercon.spec;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterInt;
import org.hyperion.model.ParameterObject;
import org.hyperion.model.ParameterString;
import org.hyperion.model.json.Jsonizer;


/**
 * Led specification with fractional location along screen border and fractional-rectangle for 
 * integrating an image into led color  
 */
public class LedModel extends AbstractModel {
	
	public LedModel() {
		super();
		initialize();
	}

	public final ParameterString id = new ParameterString("id", "");
	
	/** The sequence number of the led */
	public final ParameterInt index = new ParameterInt("index", 0, 0, Integer.MAX_VALUE);
	
	public final DoubleRange hscan = new DoubleRange(0.0, 1.0, 0.0, 1.0);
	public final DoubleRange vscan = new DoubleRange(0.0, 1.0, 0.0, 1.0);
	
	/** The side along which the led is placed */
	public final ParameterObject<BorderSide> side = new ParameterObject<BorderSide>("Side", BorderSide.top);
	
	/** The fractional location of the led */
	public final ParameterObject<Point2D.Double> location = new ParameterObject<Point2D.Double>("Location", new Point2D.Double());

	/** 
	 * Return the fractional rectangle for image integration 
	 */
	public Rectangle2D getImageRectangle() {
		return new Rectangle2D.Double(hscan.minimum.getValue(), vscan.minimum.getValue(), 
				hscan.maximum.getValue()-hscan.minimum.getValue(), vscan.maximum.getValue()-vscan.minimum.getValue());
	}
	
	public static void main(String[] pArgs) {
		LedModel model = new LedModel();
		
		System.out.println(Jsonizer.serialize(model));
	}
}
