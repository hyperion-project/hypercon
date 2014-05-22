package org.hyperion.model;

import java.util.Locale;

public class ParameterDouble extends Parameter {
	private double mValue;
	
	private final double mMinimum;
	
	private final double mMaximum;

	public ParameterDouble(String pName, double pValue, double pMinimum, double pMaximum) {
		super(pName);
		mValue = pValue;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	public ParameterDouble(String pName, String pDescription, double pValue, double pMinimum, double pMaximum) {
		super(pName, pDescription);
		mValue = pValue;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	public double getValue() {
		return mValue;
	}

	synchronized public void setValue(double pValue) {
		if(pValue == mValue) {
			return;
		}
		
		if(mMinimum <= pValue && pValue <= mMaximum)
		{
			double oldValue = mValue;
			mValue = pValue;
			
			fireChanged(oldValue, mValue);
		} else {
			throw new ModelException("Double value (" + pValue + ") out of bounds for parameter " + this.toString());
		}
	}

	public double getMinimum() {
		return mMinimum;
	}

	public double getMaximum() {
		return mMaximum;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "%s(value=%f, bounds=[%f:%f])", getName(), getValue(), getMinimum(), getMaximum());
	}
}
