package org.hyperion.hypercon.spec.device;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterString;
import org.mufassa.model.Suggestions;

public class SerialDeviceModel extends AbstractModel {
	/** The device 'file' name */
	@Suggestions({"/dev/ttyUSB0", "/dev/ttyAMA0"})
	public final ParameterString mOutput   = new ParameterString("output", "/dev/ttyUSB0");
	
	/** The baudrate of the device */
	public final ParameterInt mBaudrate = new ParameterInt("baudrate", 250000, 48000, 2000000);

	public SerialDeviceModel() {
		super();
		
		initialize();
	}
}
