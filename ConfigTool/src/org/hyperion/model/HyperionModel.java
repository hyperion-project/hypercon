package org.hyperion.model;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.Event;
import org.mufassa.model.ModelList;

@Event(HyperionModel.HYPERIONMODEL_EVENT)
public class HyperionModel extends AbstractModel {
	public static final String HYPERIONMODEL_EVENT = "HyperionModelEvent";

	public final ModelList<FrameGrabberModel> frameGrabbers = new ModelList<>(); 

	public final ModelList<ColorTransformModel> colorTransforms = new ModelList<>();
	
	public final Ws2801SpiDeviceModel deviceConfigModel = new Ws2801SpiDeviceModel();
	
	public final LedFrameModel ledFrameModel = new LedFrameModel();
	public final LedProcessModel ledProcessModel = new LedProcessModel();
	public final ModelList<LedConfigModel> ledConfigurations = new ModelList<>();

}
