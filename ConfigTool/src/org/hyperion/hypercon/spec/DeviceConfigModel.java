package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.spec.device.Ws2801DeviceModel;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterString;
import org.mufassa.model.ParameterStringEnum;
import org.mufassa.model.json.JsonComment;

/**
 * The device specific configuration
 */
@JsonComment(
		"Device configuration contains the following fields: \n" +
		" * 'name'       : The user friendly name of the device (only used for display purposes)\n" +
		" * 'type'       : The type of the device or leds (known types for now are 'ws2801', 'ldp8806',\n" +
		"                  'lpd6803', 'sedu', 'adalight', 'lightpack', 'test' and 'none')\n" +
		" * 'output'     : The output specification depends on selected device. This can for example be the\n" +
		"                  device specifier, device serial number, or the output file name\n" +
		" * 'rate'       : The baudrate of the output to the device\n" +
		" * 'colorOrder' : The order of the color bytes ('rgb', 'rbg', 'bgr', etc.).")
public class DeviceConfigModel extends AbstractModel {
	
	public DeviceConfigModel() {
		super();
		initialize();
	}
	
	/** The name of the device */
	public final ParameterString mName = new ParameterString("name", "MyPi");
	
	/** The type specification of the device */
	public final ParameterStringEnum mType = new ParameterStringEnum("type", "The type of the device or leds", DeviceType.ws2801);
	
	public AbstractModel mDeviceConfig = new Ws2801DeviceModel();
	
	/** The order of the color bytes */
	public final ParameterStringEnum mColorByteOrder = new ParameterStringEnum("colorByteOrder", "The order of the color bytes", ColorByteOrder.RGB);
}
