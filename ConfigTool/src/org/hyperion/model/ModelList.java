package org.hyperion.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Event(ModelList.LIST_CHANGED)
public class ModelList<ElementType extends Object> extends AbstractModel implements List<ElementType> {
	public static final String LIST_CHANGED = "ListChanged";
	public static final String ELEMENT_ADDED = "ElementAdded";
	public static final String ELEMENT_REMOVED = "ElementRemoved";

	private List<ElementType> mList = Collections.synchronizedList(new ArrayList<ElementType>());

	/**
	 * Change listener which is called after one of the member parameters changes its value
	 */
	protected PropertyChangeListener mPropertyChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent pEvt) {
			// a parameter in the list changed from value
			mCurrentEvents.add(new ModelEvent(ModelList.this, LIST_CHANGED));

			// also add the generic model change event to trigger parents of this model
			mCurrentEvents.add(new ModelEvent(ModelList.this, MODEL_TREE_CHANGE_EVENT));
		}
	};

	protected void notifyAdded(List<ElementType> elements) {
		mCurrentEvents.add(new ModelEvent(this, ELEMENT_ADDED, elements));
		mCurrentEvents.add(new ModelEvent(this, LIST_CHANGED));
		mCurrentEvents.add(new ModelEvent(this, MODEL_TREE_CHANGE_EVENT));
		
		for (ElementType element : elements) {
			if(element instanceof AbstractModel) {
				AbstractModel model = (AbstractModel) element;
				// register the sub model
				mSubModels.add(model);
				
				// set the parent of the sub model
				model.setParent(this);
				
				// register the generic model event for propagation to this
				model.addObserver(mSubModelObserver, MODEL_TREE_CHANGE_EVENT);
			} else if (element instanceof Parameter) {
				Parameter parameter = (Parameter) element;
				
				// register the generic model event for propagation to this
				parameter.addPropertyChangeListener(mPropertyChangeListener);				
			}
		}
	}
	
	protected void notifyRemoved(List<ElementType> elements) {
		mCurrentEvents.add(new ModelEvent(this, ELEMENT_REMOVED, elements));
		mCurrentEvents.add(new ModelEvent(this, LIST_CHANGED));
		mCurrentEvents.add(new ModelEvent(this, MODEL_TREE_CHANGE_EVENT));

		for(ElementType element : elements) {
			if(element instanceof AbstractModel) {
				AbstractModel model = (AbstractModel) element;
				// unregister the sub model
				mSubModels.remove(model);
				
				// unset the parent of the sub model
				model.setParent(null);

				// unregister the generic model event for propagation to this
				model.removeObserver(mSubModelObserver);
			} else if (element instanceof Parameter) {
				Parameter parameter = (Parameter) element;
				
				// register the generic model event for propagation to this
				parameter.removePropertyChangeListener(mPropertyChangeListener);				
			}
		}
	}
	
	public ModelList() {
		initialize();
	}

	@Override
	public int size() {
		return mList.size();
	}

	@Override
	public boolean isEmpty() {
		return mList.isEmpty();
	}

	@Override
	public boolean contains(Object pO) {
		return mList.contains(pO);
	}

	@Override
	public Iterator<ElementType> iterator() {
		return mList.iterator();
	}

	@Override
	public Object[] toArray() {
		return mList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] pA) {
		return mList.toArray(pA);
	}

	@Override
	public boolean add(ElementType pE) {
		mList.add(pE);
		notifyAdded(Collections.singletonList(pE));
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object pO) {
		boolean rc = mList.remove(pO);
		if(rc)
			notifyRemoved(Collections.singletonList((ElementType) pO));
		return rc;
	}

	@Override
	public boolean containsAll(Collection<?> pC) {
		return mList.containsAll(pC);
	}

	@Override
	public boolean addAll(Collection<? extends ElementType> pC) {
		mList.addAll(pC);
		notifyAdded(new ArrayList<ElementType>(pC));
		return true;
	}

	@Override
	public boolean addAll(int pIndex, Collection<? extends ElementType> pC) {
		mList.addAll(pIndex, pC);
		notifyAdded(new ArrayList<ElementType>(pC));
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> pC) {
		List<?> removed = new ArrayList<Object>(pC);
		
		boolean rc;
		synchronized (mList) {
			removed.retainAll(mList);
			rc = mList.removeAll(pC);
		}
		
		if(rc)
			notifyRemoved((List<ElementType>) removed);
		return rc;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean retainAll(Collection<?> pC) {
		List<?> removed = new ArrayList<Object>(pC);

		boolean rc;
		synchronized (mList) {
			removed.removeAll(mList);
			rc = mList.retainAll(pC);
		}

		if(rc) {
			notifyRemoved((List<ElementType>) removed);
		}
		return rc;
	}

	@Override
	public void clear() {
		List<ElementType> removed;
		synchronized (mList) {
			if(mList.isEmpty()) 
				return;
			
			removed = new ArrayList<ElementType>(mList);
			mList.clear();
		}
		
		notifyRemoved(removed);
	}

	@Override
	public ElementType get(int pIndex) {
		return mList.get(pIndex);
	}

	@Override
	public ElementType set(int pIndex, ElementType pElement) {
		ElementType old = mList.set(pIndex, pElement);
		notifyRemoved(Collections.singletonList(old));
		notifyAdded(Collections.singletonList(pElement));
		return old;
	}

	@Override
	public void add(int pIndex, ElementType pElement) {
		mList.add(pIndex, pElement);
		notifyAdded(Collections.singletonList(pElement));
	}

	@Override
	public ElementType remove(int pIndex) {
		ElementType old = mList.remove(pIndex);
		if(old != null) {
			notifyRemoved(Collections.singletonList(old));
		}
		return old;
	}

	@Override
	public int indexOf(Object pO) {
		return mList.indexOf(pO);
	}

	@Override
	public int lastIndexOf(Object pO) {
		return mList.lastIndexOf(pO);
	}

	@Override
	public ListIterator<ElementType> listIterator() {
		return mList.listIterator();
	}

	@Override
	public ListIterator<ElementType> listIterator(int pIndex) {
		return mList.listIterator(pIndex);
	}

	@Override
	public List<ElementType> subList(int pFromIndex, int pToIndex) {
		return mList.subList(pFromIndex, pToIndex);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + mList;
	}
}
