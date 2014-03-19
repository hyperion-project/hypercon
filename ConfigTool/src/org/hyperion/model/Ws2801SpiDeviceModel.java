package org.hyperion.model;

import org.mufassa.model.AbstractModelExtension;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;

public class Ws2801SpiDeviceModel extends AbstractModelExtension {
	
	/** The identification of the output device */
	public final ParameterObject<String> outputDevice = new ParameterObject<>("OutputDevice", "/dev/spidev0.0");
	
	/** The indicative baudrate of the spi-device */
	public final ParameterInt baudrate_Hz = new ParameterInt("Baudrate", 250000, 48000, 2000000);
	
}
