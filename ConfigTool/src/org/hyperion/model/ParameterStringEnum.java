package org.hyperion.model;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * The ParameterEnum allows the configuration of a specified number of values. 
 */
public class ParameterStringEnum extends Parameter {

	/** The log4j Logger instance */
	private static final Logger LOGGER = Logger.getLogger(ParameterStringEnum.class);
	
	/** The array with allowed values */
	private final String[] mValues; 
	
	/** The current selected value */
	private String mSelectedValue;

	/**
	 * Constructs the enumeration
	 * 
	 * @param pName The name of the parameter
	 * @param pValues The allowed values of the enumeration (can not be null or empty)
	 * @param pSelectedValue The current selected value (can not be null)
	 */
	public ParameterStringEnum(String pName, String[] pValues, String pSelectedValue) {
		super(pName);
		
		mValues = pValues;
		if (!isValidValue(pSelectedValue)) {
			mSelectedValue = pValues[0];
		} else {
			mSelectedValue = pSelectedValue;
		}
	}
	
	/**
	 * Constructs the enumeration
	 * 
	 * @param pName The name of the parameter
	 * @param pDescription The description of the parameter
	 * @param pValues The allowed values of the enumeration (can not be null or empty)
	 * @param pSelectedValue The current selected value (can not be null)
	 */
	public ParameterStringEnum(String pName, String pDescription, List<String> pValues, String pSelectedValue) {
		this(pName, pDescription, pValues.toArray(new String[0]), pSelectedValue);
	}
	
	public ParameterStringEnum(String pName, Object pSelectedValue) {
		this(pName, pName, pSelectedValue);
	}

	public ParameterStringEnum(String pName, String pDescription, Object pSelectedValue) {
		super(pName);
		
		Object[] enumValues = pSelectedValue.getClass().getEnumConstants();
		mValues = new String[enumValues.length];
		for (int i=0; i<enumValues.length; ++i) {
			mValues[i] = enumValues[i].toString();
		}
		
		if (!isValidValue(pSelectedValue.toString())) {
			mSelectedValue = mValues[0];
		} else {
			mSelectedValue = pSelectedValue.toString();
		}
	}
	
	/**
	 * Constructs the enumeration
	 * 
	 * @param pName The name of the parameter
	 * @param pDescription The description of the parameter
	 * @param pValues The allowed values of the enumeration (can not be null or empty)
	 * @param pSelectedValue The current selected value (can not be null)
	 */
	public ParameterStringEnum(String pName, String pDescription, String[] pValues, String pSelectedValue) {
		super(pName, pDescription);
		
		mValues = pValues;
		if (!isValidValue(pSelectedValue)) {
			mSelectedValue = mValues[0];
		} else {
			mSelectedValue = pSelectedValue;
		}
}

	/**
	 * Returns the selected value of the enumeration
	 * 
	 * @return The selected value
	 */
	public String getValue() {
		return mSelectedValue;
	}
	
	public void setValue(String pValue) {
		if (!isValidValue(pValue)) {
			LOGGER.warn("Attempting to set ParameterEnum(" + getName() + ") to unknown value: " + pValue);
			return;
		}

		if (!mSelectedValue.equals(pValue)) {
			String oldValue = mSelectedValue;
			mSelectedValue = pValue;
			fireChanged(oldValue, mSelectedValue);
		}
	}
	
	private boolean isValidValue(String pValue) {
		for (String value : mValues) {
			if (value.equals(pValue)) {
				return true;
			}
		}
		return false;
	}
	
	public String[] values() {
		return mValues;
	}
}
