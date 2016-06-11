package org.hyperion.hypercon.spec;

import java.util.Hashtable;

/**
 * The device specific configuration
 */
public class DeviceConfig {
	
	/** The name of the device */
	public String mNameField     = "MyHyperionConfig";
	/** The type specification of the device */
	public DeviceType mType = DeviceType.ws2801;
	
	/** Device (specific) properties */
	public final Hashtable<String, Object> mDeviceProperties = new Hashtable<String, Object>();

	/** The order of the color bytes */
	public ColorByteOrder mColorByteOrder = ColorByteOrder.RGB;
	
	/**Store Device specific config for .dat here too**/
//	public int FCP_portSpinner=8899;
	
	
	/**
	 * Creates the JSON string of the configuration as used in the Hyperion daemon configfile
	 * 
	 * @return The JSON string of this DeviceConfig
	 */
	public String toJsonString() {
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append("\t// DEVICE CONFIGURATION \n");
		strBuf.append("\t\"device\" :\n");
		strBuf.append("\t{\n");
		
		strBuf.append("\t\t\"name\"       : \"").append(mNameField).append("\",\n");
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
