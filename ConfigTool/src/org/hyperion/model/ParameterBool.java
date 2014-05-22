package org.hyperion.model;

import java.util.Locale;

public class ParameterBool extends Parameter {
	private boolean mValue;
	
	public ParameterBool(String pName, boolean pValue) {
		super(pName);
		mValue = pValue;
	}

	public ParameterBool(String pName, String pDescription, boolean pValue) {
		super(pName, pDescription);
		mValue = pValue;
	}

	public boolean getValue() {
		return mValue;
	}

	synchronized public void setValue(boolean pValue) {
		if(pValue != mValue) {
			mValue = pValue;
			fireChanged(!mValue, mValue);
		}
	}

	@Override
	public String toString() {
		return String.format(Locale.ROOT, "%s(value=%s)", getName(), getValue());
	}
}
