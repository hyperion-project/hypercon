package org.hyperion.model;

import java.util.Locale;

public class ParameterObject<ObjectType extends Object> extends Parameter {
	private ObjectType mValue;
	
	public ParameterObject(String pName, ObjectType pValue) {
		super(pName);
		mValue = pValue;
	}

	public ParameterObject(String pName, String pDescription, ObjectType pValue) {
		super(pName, pDescription);
		mValue = pValue;
	}

	public ObjectType getValue() {
		return mValue;
	}

	synchronized public void setValue(ObjectType pValue) {
		if(pValue != mValue) {
			ObjectType oldValue = mValue;
			mValue = pValue;
			
			fireChanged(oldValue, mValue);
		}
	}

	@Override
	public String toString() {
		return String.format(Locale.ROOT, "%s(value=%s)", getName(), getValue());
	}
}
