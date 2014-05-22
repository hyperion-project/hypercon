package org.hyperion.model;

public class ParameterString extends Parameter {

	private String mValue;
	
	public ParameterString(String pName, String pValue) {
		super(pName);
		
		mValue = pValue;
	}

	public ParameterString(String pName, String pDescription, String pValue) {
		super(pName, pDescription);
		
		mValue = pValue;
	}
	
	public void setValue(String pValue) {
		if ((mValue == null && pValue == null) || (mValue != null && mValue.equals(pValue))) {
			return;
		}
		
		String oldValue = mValue;
		mValue = pValue;
		fireChanged(oldValue, mValue);
	}
	
	public String getValue() {
		return mValue;
	}
}
