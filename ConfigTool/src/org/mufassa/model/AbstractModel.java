package org.mufassa.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Classes who derive from AbstractModel should call initialize in their constructor!
 */
public abstract class AbstractModel {
	/** The log4j logger */
	private static final Logger LOGGER = Logger.getLogger(AbstractModel.class);
	
	/**
	 * Event which is triggered when the model changes. This event is propagated to the parents of a submodel 
	 */
	public static final String MODEL_TREE_CHANGE_EVENT = "ModelChangeEvent";
	
	/**
	 * Observable support
	 */
	private final Map<IModelObserver, Set<String>> mObservers = Collections.synchronizedMap(new LinkedHashMap<IModelObserver, Set<String>>());
	
	/**
	 * List with all sub models of this class
	 */
	protected final List<AbstractModel> mSubModels = Collections.synchronizedList(new ArrayList<AbstractModel>());
	
	/**
	 * Mapping of fields from the model to the event they generate. All ModelEvents are pre-generated which 
	 * is possible as long as the events they throw have no arguments. 
	 */
	private final Map<Object, Set<ModelEvent>> mEventMap = new LinkedHashMap<Object, Set<ModelEvent>>();
	
	/**
	 * List with all outstanding events
	 */
	protected final Set<ModelEvent> mCurrentEvents = Collections.synchronizedSet(new LinkedHashSet<ModelEvent>());
	
	/**
	 * The parent of this model
	 */
	private AbstractModel mParentModel = null;
	
	/**
	 * Boolean indicating if this class is initialized. The initialize() functions has to be called by the constructor 
	 * of a derived class. Some people have a hard time remembering this. Therefore a check has been added.
	 */
	private boolean isInitialized = false;
	
	/**
	 * Change listener which is called after one of the member parameters changes its value
	 */
	private PropertyChangeListener mPropertyChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent pEvt) {
			//System.out.println(String.format(Locale.ROOT, "change!: %s (%s)", mEventMap.get(pEvt.getSource()), pEvt.getSource()));

			// add events from the property change
			mCurrentEvents.addAll(mEventMap.get(pEvt.getSource()));
			
			// add events from this model being updated
			mCurrentEvents.addAll(mEventMap.get(AbstractModel.this));
			
			// also add the generic model change event to trigger parents of this model
			mCurrentEvents.add(new ModelEvent(AbstractModel.this, MODEL_TREE_CHANGE_EVENT));
		}
	};
	
	/**
	 * Observer which is used to catch a MODEL_TREE_CHANGE_EVENT from sub models
	 */
	protected IModelObserver mSubModelObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			if(mCurrentEvents.isEmpty()) {
				// immediately commit this event without propagating to sub models 
				// when it is the only waiting event.
				// Otherwise the commit can wait until the other local events are
				// committed to prevent an overload of MODEL_TREE_CHANGE_EVENT commits.
				// commit changes from this model
				
				//System.out.println("Commit: " + MODEL_TREE_CHANGE_EVENT);
				// make a copy of the observer map otherwise a concurrent modification exception can
				// occur when an observer unsubscribes itself during the call-back
				Map<IModelObserver, Set<String>> observers = new LinkedHashMap<IModelObserver, Set<String>>(mObservers);
				for(Entry<IModelObserver, Set<String>> entry : observers.entrySet()) {
					Set<String> events = new LinkedHashSet<String>(entry.getValue());
					if(events.contains(MODEL_TREE_CHANGE_EVENT)) {
						entry.getKey().modelUpdate(Collections.singleton(new ModelEvent(AbstractModel.this, MODEL_TREE_CHANGE_EVENT)));						
					}
				}
			} else {
				mCurrentEvents.add(new ModelEvent(AbstractModel.this, MODEL_TREE_CHANGE_EVENT));
			}
		}
	};
	
	/**
	 * Initialize the model dynamics by registering at all Parameters
	 */
	protected void initialize() {
		Field fields[] = this.getClass().getFields();
		for(Field field : fields) {
			// Obtain the object
			Object object;
			try {
				object = field.get(this);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}

			// handle the object
			if(object instanceof Parameter) {
				// add a change listener to the parameter
				Parameter parameter = (Parameter) object;
				parameter.addPropertyChangeListener(mPropertyChangeListener);

				// register the event of this field in the event map
				registerEvents(field, parameter);
			} else if (object instanceof AbstractModel) {
				AbstractModel model = (AbstractModel) object;
				// register the submodel
				mSubModels.add(model);
				
				// set the parent of the sub model to this
				model.setParent(this);
				
				// register the event of this field in the event map
				registerEvents(field, model);
				
				// register the generic model event for propagation to this
				model.addObserver(mSubModelObserver, MODEL_TREE_CHANGE_EVENT);
			}
		}

		// register class events for this
		registerEvents(this.getClass(), this);
		
		isInitialized = true;
	}
	
	/**
	 * Register the events of a field in the eventMap
	 * @param pElement
	 * @param pObject
	 */
	private void registerEvents(AnnotatedElement pElement, Object pObject) {
		// retrieve the event set.
		Set<ModelEvent> eventSet = mEventMap.get(pObject);
		if(eventSet == null) {
			eventSet = new LinkedHashSet<ModelEvent>();
			mEventMap.put(pObject, eventSet);
		}
		
		// handle @Event annotation
		Event eventType = pElement.getAnnotation(Event.class);
		if(eventType != null) {
			String event = eventType.value();
			eventSet.add(new ModelEvent(this, event));
		}
		
		// handle @Events annotation
		Events eventsType = pElement.getAnnotation(Events.class);
		if(eventsType != null) {
			for (String event : eventsType.value()) {
				eventSet.add(new ModelEvent(this, event));
			}
		}
	}
	
	/**
	 * Commit the events in the current event set to the subscribers
	 */
	public void commitEvents() {
		// commit changes from the sub models
		for (AbstractModel model : mSubModels) {
			model.commitEvents();
		}

		Map<IModelObserver, Set<ModelEvent>> toBeCommitted = new LinkedHashMap<IModelObserver, Set<ModelEvent>>();
		
		// first obtain a list with events which need to be committed. 
		// While doing this, the list of events and observers must not change
		synchronized(mCurrentEvents) {
			synchronized (mObservers) {
				// commit changes from this model
				//System.out.println(this + " commit: " + mCurrentEvents);
				for(Entry<IModelObserver, Set<String>> entry : mObservers.entrySet()) {
					IModelObserver observer = entry.getKey();
					Set<String> observerEvents = entry.getValue();
					Set<ModelEvent> events = new LinkedHashSet<ModelEvent>();
					
					for (ModelEvent event : mCurrentEvents) {
						if(observerEvents.contains(event.getEvent())) {
							events.add(event);
						}
					}
					
					if(!events.isEmpty()) {
						toBeCommitted.put(observer, events);
					}
				}
			}
	
			// clear the event list
			mCurrentEvents.clear();
		}
		
		// now do the commit
		for (Entry<IModelObserver, Set<ModelEvent>> entry : toBeCommitted.entrySet()) {
			entry.getKey().modelUpdate(entry.getValue());		
		}
	}
	
	/**
	 * Add observer for the specified events
	 * @param pObserver
	 * @param pEvents
	 */
	public void addObserver(IModelObserver pObserver, String ... pEvents) {
		if (!isInitialized) {
			LOGGER.error("AbstractModel has not been initialized. Has the initialize() call been " +
					"forgotten in the constructor " + getClass().getCanonicalName());
		}

		Set<String> events = mObservers.get(pObserver);
		if (events == null) {
			events = new LinkedHashSet<String>();
			mObservers.put(pObserver, events);
		}
		Collections.addAll(events, pEvents);
	}
	
	/**
	 * Add observer for the specified events
	 * @param pObserver
	 * @param pEvents
	 */
	public void addObserver(IModelObserver pObserver, Set<String> pEvents) {
		if (!isInitialized) {
			LOGGER.error("AbstractModel has not been initialized. Has the initialize() call been " +
					"forgotten in the constructor " + getClass().getCanonicalName());
		}
		
		Set<String> events = mObservers.get(pObserver);
		if (events == null) {
			events = new LinkedHashSet<String>();
			mObservers.put(pObserver, events);
		}
		events.addAll(pEvents);
	}

	/**
	 * Remove observer from all events it was subscribed at
	 * @param pObserver
	 */
	public void removeObserver(IModelObserver pObserver) {
		mObservers.remove(pObserver);
	}

	/**
	 * Get the direct parent of this model
	 * @return the direct parent
	 */
	public AbstractModel getParent() {
		return mParentModel;
	}
	
	/**
	 * Get the parent of this model of the requested type. 
	 * 
	 * This function will traverse over all parents until it has found a parent of the requested class type.
	 * When a null parent is encountered (top model) then a null is returned (no such parent)
	 * 
	 * @param clazz Type of the requested parent
	 * @return the parent which is of the requested type or null when not found
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParent(Class<T> clazz) {
		// the list in which this platform resides
		AbstractModel parent = mParentModel;

		// propagate over all parents until the parent is eiher null or a
		// Scenario
		while (parent != null && !clazz.isInstance(parent)) {
			parent = parent.getParent();
		}

		// parent is null or a Scenario. cast and return.
		return (T) parent;
	}

	protected void setParent(AbstractModel pParent) {
		mParentModel = pParent;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
