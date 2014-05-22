package org.hyperion.model;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * ModelList with the ability selecting an element
 */
public class SelectableModelList<ElementType> extends ModelList<ElementType> {
	public static final String SELECTION_CHANGED = "SelectionChangedEvent";
	
	private ElementType mSelectedItem = null;
	
	@Override
	protected void notifyAdded(List<ElementType> pElements) {
		// if we had no selection, select the first one 
		if(mSelectedItem == null) {
			mSelectedItem = get(0);
			mCurrentEvents.add(new ModelEvent(this, SELECTION_CHANGED));
		}
		
		super.notifyAdded(pElements);
	}
	
	@Override
	protected void notifyRemoved(List<ElementType> pElements) {
		// if we remove the selected item, select a new one
		if(pElements.contains(mSelectedItem)) {
			if(size() == 0) {
				mSelectedItem = null;
			} else {
				mSelectedItem = get(0);
			}
			mCurrentEvents.add(new ModelEvent(this, SELECTION_CHANGED));
		}
		
		super.notifyRemoved(pElements);
	}

	public ElementType getSelectedItem() {
		return mSelectedItem;
	}
	
	public void setSelectedItem(ElementType pItem) {
		if(contains(pItem)) {
			if (mSelectedItem != pItem) {
				mSelectedItem = pItem;
				mCurrentEvents.add(new ModelEvent(this, SELECTION_CHANGED));
				mCurrentEvents.add(new ModelEvent(this, MODEL_TREE_CHANGE_EVENT));
			}
		} else {
			throw new NoSuchElementException(pItem.toString());
		}
	}

	public int getSelectedIndex() {
		return indexOf(mSelectedItem);
	}
}
