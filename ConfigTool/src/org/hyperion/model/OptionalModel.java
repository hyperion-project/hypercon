package org.hyperion.model;

import java.util.Collections;

public class OptionalModel<T extends AbstractModel> extends AbstractModel {

	public static final String OPTION_SET     = "OptionSet";
	public static final String OPTION_UNSET   = "OptionUnset";
	public static final String OPTION_CHANGED = "OptionChanged";
	
	private T mOption;
	
	public OptionalModel() {
		super();
		
		mOption = null;
		
		initialize();
	}
	
	public OptionalModel(final T pOption) {
		super();
		
		mOption = pOption;
		
		initialize();
	}
	
	public T get() {
		return mOption;
	}

	@SuppressWarnings("unchecked")
	public void set(Object pOption) {
		set((T)pOption);
	}

	public void set(T pOption) {
		if (mOption == pOption) {
			return;
		}
		
		mCurrentEvents.add(new ModelEvent(this, OPTION_SET, Collections.singletonList(pOption)));
		mCurrentEvents.add(new ModelEvent(this, OPTION_CHANGED));
		mCurrentEvents.add(new ModelEvent(this, MODEL_TREE_CHANGE_EVENT));

		if (mOption != null) {
			unset();
		}
		
		if (pOption == null) {
			return;
		}
		mOption = pOption;
		
		// register the submodel
		mSubModels.add(mOption);
		
		// set the parent of the sub model to this
		mOption.setParent(this);
		
		// register the generic model event for propagation to this
		mOption.addObserver(mSubModelObserver, MODEL_TREE_CHANGE_EVENT);
	}
	
	public void unset() {
		mCurrentEvents.add(new ModelEvent(this, OPTION_SET, Collections.singletonList(mOption)));
		mCurrentEvents.add(new ModelEvent(this, OPTION_CHANGED));
		mCurrentEvents.add(new ModelEvent(this, MODEL_TREE_CHANGE_EVENT));

		if (mOption == null) {
			// Nothing to do anymore
			return;
		}
		
		mOption.removeObserver(mSubModelObserver);
		mOption.setParent(null);
		mSubModels.remove(mOption);
		mOption = null;
	}
	
}
