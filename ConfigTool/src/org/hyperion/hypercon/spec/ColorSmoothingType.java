package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.language.language;

public enum ColorSmoothingType {
	/** Linear smoothing of led data */
	linear(language.getString("process.smoothinglist.linearsmoothing"));
	private final String mName;
	
	private ColorSmoothingType(String name) {
		mName = name;
	}
	/**
	 * Returns the type identifier as used by hyperion effects
	 * @return
	 */
	public String getTypeId() {
		
		return super.name();
	}
	@Override
	public String toString() {
		return mName;
	}
}