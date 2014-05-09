package org.hyperion.hypercon.spec.device;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

public class SerialDeviceModel extends AbstractModel {
	/** The device 'file' name */
	public final ParameterObject<String> mOutput   = new ParameterObject<String>("output", "/dev/ttyUSB0");
	
	/** The baudrate of the device */
	public final ParameterInt mBaudrate = new ParameterInt("baudrate", 250000, 48000, 2000000);

	public SerialDeviceModel() {
		super();
		
		initialize();
	}
}
