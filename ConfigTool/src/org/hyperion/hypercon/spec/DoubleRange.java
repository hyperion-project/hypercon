package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterDouble;

public class DoubleRange extends AbstractModel {

	public final ParameterDouble minimum;
	public final ParameterDouble maximum;

	public DoubleRange(double pLowerBound, double pUpperBound, double pMinimum, double pMaximum) {
		super();
		
		minimum = new ParameterDouble("minimum", pMinimum, pLowerBound, pUpperBound);
		maximum = new ParameterDouble("maximum", pMaximum, pLowerBound, pUpperBound);
		
		initialize();
	}
}
