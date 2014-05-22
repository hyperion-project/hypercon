package org.hyperion.model;

import java.util.Locale;

public class ParameterInt extends Parameter {
	private int mValue;
	
	private final int mMinimum;
	
	private final int mMaximum;

	public ParameterInt(String pName, int pValue, int pMinimum, int pMaximum) {
		super(pName);
		mValue = pValue;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	public ParameterInt(String pName, String pDescription, int pValue, int pMinimum, int pMaximum) {
		super(pName, pDescription);
		mValue = pValue;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	public int getValue() {
		return mValue;
	}

	synchronized public void setValue(int pValue) {
		if(pValue == mValue) {
			return;
		}
		
		if(mMinimum <= pValue && pValue <= mMaximum)
		{
			int oldValue = mValue;
			mValue = pValue;
			
			fireChanged(oldValue, mValue);
		} else {
			throw new ModelException("Integer value (" + pValue + ") out of bounds for parameter " + this.toString());
		}
	}

	public int getMinimum() {
		return mMinimum;
	}

	public int getMaximum() {
		return mMaximum;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "%s(value=%d, bounds=[%d:%d])", getName(), getValue(), getMinimum(), getMaximum());
	}
}
