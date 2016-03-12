package org.hyperion.hypercon.spec;

import java.util.Hashtable;

/**
 * The device specific configuration
 */
public class DeviceConfig {
	
	/** The name of the device */
	public String mName     = "MyPi";
	/** The type specification of the device */
	public DeviceType mType = DeviceType.ws2801;
	
	/** Device (specific) properties */
	public final Hashtable<String, Object> mDeviceProperties = new Hashtable<String, Object>();

	/** The order of the color bytes */
	public ColorByteOrder mColorByteOrder = ColorByteOrder.RGB;
	
	/**
	 * Creates the JSON string of the configuration as used in the Hyperion daemon configfile
	 * 
	 * @return The JSON string of this DeviceConfig
	 */
	public String toJsonString() {
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append("\t/// Device configuration contains the following fields: \n");
		strBuf.append("\t/// * 'name'       : The user friendly name of the device (only used for display purposes)\n");
		strBuf.append("\t/// * 'type'       : The type of the device or leds (known types for now are\n"
				+ "\t/// " + DeviceType.listTypes() + ")\n");
		strBuf.append("\t/// * [device type specific configuration]\n");
		strBuf.append("\t/// * 'colorOrder' : The order of the color bytes ('rgb', 'rbg', 'bgr', etc.).\n");
		strBuf.append("\t///\n");
		strBuf.append("\t/// * 'Specific of Philips Hue:\n");
		strBuf.append("\t/// * 'username'  		 : The name of user registred on the Philips Hue Bridge \n");
		strBuf.append("\t/// * 'switchOffOnBlack': Define if Hue light switch off when black is detected \n");
		strBuf.append("\t/// * 'transitiontime'	 : Set the time of transition between color of Hue light \n");
		
		strBuf.append("\t\"device\" :\n");
		strBuf.append("\t{\n");
		
		strBuf.append("\t\t\"name\"       : \"").append(mName).append("\",\n");
		strBuf.append("\t\t\"type\"       : \"").append(mType.getTypeId()).append("\",\n");
		for (Object key : mDeviceProperties.keySet()) {
			Object value = mDeviceProperties.get(key);
			if (value instanceof String)
			{
				strBuf.append(String.format("\t\t\"%s\"     : \"%s\",\n", key, value));
			}
			else
			{
				strBuf.append(String.format("\t\t\"%s\"     : %s,\n", key, value.toString()));
			}
		}
		strBuf.append("\t\t\"colorOrder\" : \"").append(mColorByteOrder.name().toLowerCase()).append("\"\n");
		
		strBuf.append("\t}");
		
		return strBuf.toString();
	}
}
