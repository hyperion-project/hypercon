package org.hyperion.control;

import java.util.Set;

import org.hyperion.model.LedProcessModel;
import org.mufassa.model.IModelObserver;
import org.mufassa.model.ModelEvent;

public class LedProcessController {
	private final LedProcessModel mProcessModel;
	private final IModelObserver mProcessModelObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			update();
		}
	};
	
	public LedProcessController(final LedProcessModel pProcessModel) {
		super();
		
		mProcessModel = pProcessModel;
		mProcessModel.addObserver(mProcessModelObserver, LedProcessModel.LEDPROCESSMODEL_EVENT);
	}
	
	private void update() {
		
	}
	
	private void commit() {
		
	}
}
