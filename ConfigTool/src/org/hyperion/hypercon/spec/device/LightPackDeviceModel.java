package org.hyperion.hypercon.spec.device;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterObject;

public class LightPackDeviceModel extends AbstractModel {

	public final ParameterObject<String> usbSerialNumber = new ParameterObject<String>("USB Serial Number", "");
	
	public LightPackDeviceModel() {
		super();
		
		initialize();
	}
}
