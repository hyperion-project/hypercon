package org.hyperion.hypercon.spec.device;

import org.hyperion.model.AbstractModel;
import org.hyperion.model.ParameterInt;
import org.hyperion.model.ParameterString;
import org.hyperion.model.Suggestions;

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
