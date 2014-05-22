package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.spec.device.LightPackDeviceModel;
import org.hyperion.hypercon.spec.device.Ws2801DeviceModel;
import org.hyperion.model.AbstractModel;
import org.hyperion.model.Event;
import org.hyperion.model.OptionalModel;
import org.hyperion.model.ParameterString;
import org.hyperion.model.ParameterStringEnum;
import org.hyperion.model.json.JsonComment;
import org.hyperion.model.json.Jsonizer;

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
	
	public static final String DEVICE_TYPE_EVENT = "deviceTypeEvent";
	
	public DeviceConfigModel() {
		super();
		initialize();
	}
	
	/** The name of the device */
	public final ParameterString mName = new ParameterString("name", "MyPi");
	
	/** The type specification of the device */
	@Event(DEVICE_TYPE_EVENT)
	public final ParameterStringEnum mType = new ParameterStringEnum("type", "The type of the device or leds", DeviceType.ws2801);
	public final OptionalModel<AbstractModel> mDeviceConfig = new OptionalModel<AbstractModel>(new Ws2801DeviceModel());
	
	/** The order of the color bytes */
	public final ParameterStringEnum mColorByteOrder = new ParameterStringEnum("colorByteOrder", "The order of the color bytes", ColorByteOrder.RGB);
	
	public static void main(String[] pArgs) {
		DeviceConfigModel model = new DeviceConfigModel();
		model.mDeviceConfig.set(new LightPackDeviceModel());
		System.out.println(Jsonizer.serialize(model));
		DeviceConfigModel model2 = (DeviceConfigModel) Jsonizer.serialisedCopy(model);
		System.out.println(Jsonizer.serialize(model2));
		
	}
}
