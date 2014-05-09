package org.hyperion.hypercon.spec.device;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterString;
import org.mufassa.model.Suggestions;

public class Ws2801DeviceModel extends AbstractModel {
	/** The device 'file' name */
	@Suggestions({ "/dev/spidev0.0", "/dev/spidev0.1", "/dev/null"} )
	public final ParameterString mOutput = new ParameterString("output", "/dev/spidev0.0");
	
	/** The baudrate of the device */
	public final ParameterInt mBaudrate = new ParameterInt("baudrate", 250000, 48000, 2000000);

	public Ws2801DeviceModel() {
		super();
		
		initialize();
	}
}
