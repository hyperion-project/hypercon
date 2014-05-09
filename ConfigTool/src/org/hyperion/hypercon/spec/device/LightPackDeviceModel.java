package org.hyperion.hypercon.spec.device;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterObject;

public class LightPackDeviceModel extends AbstractModel {

	public final ParameterObject<String> usbSerialNumber = new ParameterObject<String>("USB Serial Number", "");
	
	public LightPackDeviceModel() {
		super();
		
		initialize();
	}
}
