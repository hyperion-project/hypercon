package org.hyperion.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.hyperion.model.json.IgnoreInJson;

public abstract class Parameter {
	private final String mName;
	private final String mDescription;
	
    public Parameter(String pName, String pDescription) {
		super();
		mName = pName;
		mDescription = pDescription;
	}
	
	public Parameter(String pName) {
		super();
		mName = pName;
		mDescription = pName;
	}

	public String getName() {
		return mName;
	}

	public String getDescription() {
		return mDescription;
	}

	
	/**
	 * Property change support for letting the model know of the changed value.
	 * It is initialized lazely because of the the json deserialization.
	 */
	@IgnoreInJson
    private PropertyChangeSupport mPropertyChangeSupport = null;

    void addPropertyChangeListener(PropertyChangeListener pListener) {
    	if(mPropertyChangeSupport == null) {
    		mPropertyChangeSupport = new PropertyChangeSupport(this);
    	}
    	
		mPropertyChangeSupport.addPropertyChangeListener(pListener);
	}

	void removePropertyChangeListener(PropertyChangeListener pListener) {
    	if(mPropertyChangeSupport != null) {
    		mPropertyChangeSupport.removePropertyChangeListener(pListener);
    	}
	}

	protected void fireChanged(Object pOldValue, Object pNewValue) {
    	if(mPropertyChangeSupport != null) {
    		mPropertyChangeSupport.firePropertyChange(mName, pOldValue, pNewValue);
    	}
	}
}
