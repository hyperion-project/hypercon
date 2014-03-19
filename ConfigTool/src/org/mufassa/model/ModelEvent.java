package org.mufassa.model;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ModelEvent {
	private final AbstractModel mSource;
	private final String mEvent;
	private final List<? extends Object> mArgs;
	
	public ModelEvent(AbstractModel pSource, String pEvent, List<? extends Object> pArgs) {
		super();
		mSource = pSource;
		mEvent = pEvent;
		mArgs = pArgs;
	}

	public ModelEvent(AbstractModel pSource, String pEvent, Object pArg) {
		this(pSource, pEvent, Collections.singletonList(pArg));
	}

	public ModelEvent(AbstractModel pSource, String pEvent) {
		this(pSource, pEvent, (Object) null);
	}

	public AbstractModel getSource() {
		return mSource;
	}

	public String getEvent() {
		return mEvent;
	}

	public List<? extends Object> getArgs() {
		return mArgs;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mArgs == null) ? 0 : mArgs.hashCode());
		result = prime * result + ((mEvent == null) ? 0 : mEvent.hashCode());
		result = prime * result + ((mSource == null) ? 0 : mSource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		ModelEvent other = (ModelEvent) obj;

		// for the source just compare the references
		if (mSource != other.mSource)
			return false;
		
		if (mEvent == null) {
			if (other.mEvent != null)
				return false;
		} else if (!mEvent.equals(other.mEvent))
			return false;

		if (mArgs == null) {
			if (other.mArgs != null)
				return false;
		} else if (!mArgs.equals(other.mArgs))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return String.format(Locale.ROOT, "ModelEvent(source=%s, event=%s, args=%s)", mSource, mEvent, mArgs);
	}
}
