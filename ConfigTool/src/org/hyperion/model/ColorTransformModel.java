package org.hyperion.model;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterObject;

public class ColorTransformModel extends AbstractModel {

	/** The identifier of this ColorTransform configuration */
	public ParameterObject<String> id = new ParameterObject<>("Id", "default");
	
	/** The indices to which this transform applies */
	public ParameterObject<String> ledIndexString = new ParameterObject<>("LedIndexString", "*");
	
	/** The saturation gain (in HSV space) */
	public ParameterDouble saturationGain = new ParameterDouble("SaturationGain", 1.0, 0.0, 1.0);
	/** The value gain (in HSV space) */
	public ParameterDouble valueGain = new ParameterDouble("ValueGain", 1.0, 0.0, 1.0);
	
	/** The minimum required RED-value (in RGB space) */
	public ParameterDouble redThreshold  = new ParameterDouble("RedThreshold", 0.0, 0.0, 1.0);
	/** The gamma-curve correct for the RED-value (in RGB space) */
	public ParameterDouble redGamma      = new ParameterDouble("RedGamma", 1.0, 0.0, 1.0);
	/** The black-level of the RED-value (in RGB space) */
	public ParameterDouble redBlacklevel = new ParameterDouble("RedBlacklevel", 0.0, 0.0, 1.0);
	/** The white-level of the RED-value (in RGB space) */
	public ParameterDouble redWhitelevel = new ParameterDouble("RedWhitelevel", 1.0, 0.0, 1.0);
	
	/** The minimum required GREEN-value (in RGB space) */
	public ParameterDouble greenThreshold  = new ParameterDouble("GreenThreshold", 0.0, 0.0, 1.0);
	/** The gamma-curve correct for the GREEN-value (in RGB space) */
	public ParameterDouble greenGamma      = new ParameterDouble("GreenGamma", 1.0, 0.0, 1.0);
	/** The black-level of the GREEN-value (in RGB space) */
	public ParameterDouble greenBlacklevel = new ParameterDouble("GreenBlacklevel", 0.0, 0.0, 1.0);
	/** The white-level of the GREEN-value (in RGB space) */
	public ParameterDouble greenWhitelevel = new ParameterDouble("GreenWhitelevel", 1.0, 0.0, 1.0);
	
	/** The minimum required BLUE-value (in RGB space) */
	public ParameterDouble blueThreshold  = new ParameterDouble("BlueThreshold", 0.0, 0.0, 1.0);
	/** The gamma-curve correct for the BLUE-value (in RGB space) */
	public ParameterDouble blueGamma      = new ParameterDouble("BlueGamma", 1.0, 0.0, 1.0);
	/** The black-level of the BLUE-value (in RGB space) */
	public ParameterDouble blueBlacklevel = new ParameterDouble("BlueBlacklevel", 0.0, 0.0, 1.0);
	/** The white-level of the BLUE-value (in RGB space) */
	public ParameterDouble blueWhitelevel = new ParameterDouble("BlueWhitelevel", 1.0, 0.0, 1.0);
	
}
