package org.hyperion.hypercon.spec;

import java.util.Locale;

public class TransformConfig {
	/** The identifier of this ColorTransform configuration */
	public String mId = "default";
	
	/** The indices to which this transform applies */
	public String mLedIndexString = "*";
	
	/** The Dummy Labels for the free space on red/green/blue channel config */
	public String mDummy1Label = "255";
	public String mDummy2Label = "255";
	public String mDummy3Label = "255";
	/** The saturation gain (in HSL space) */
	public double mHSLSaturationGainAdjustSpinner = 1.0;
	/** The value gain (in HSL space) */
	public double mHSLLuminanceGainAdjustSpinner = 1.0;
	/** The minimum luminance */
	public double mluminanceMinimumSpinner = 0.0;
	
	/** The RGB Values for RED Channel (in RGB space) */
	public int mRedChannelRedSpinner 	= 255;
	public int mRedChannelGreenSpinner 	= 0;
	public int mRedChannelBlueSpinner 	= 0;
	/** The gamma-curve correct for the RED-value (in RGB space) */
	public double mRedGamma      		= 2.5;
	/** The (warm/cold) temperature of RED Value (in RGB space) */
	public int mRedTemperature 			= 255;
	/** The minimum required RED-value (in RGB space) */
	public double mRedThreshold 		= 0.0;

	/** The RGB Values for GREEN Channel (in RGB space) */
	public int mGreenChannelRedSpinner	= 0;
	public int mGreenChannelGreenSpinner= 255;
	public int mGreenChannelBlueSpinner	= 0;
	/** The gamma-curve correct for the GREEN-value (in RGB space) */
	public double mGreenGamma      		= 2.5;
	/** The (warm/cold) temperature of GREEN Value (in RGB space) */
	public int mGreenTemperature 		= 255;	
	/** The minimum required GREEN-value (in RGB space) */
	public double mGreenThreshold  		= 0.0;

	/** The RGB Values for BLUE Channel (in RGB space) */
	public int mBlueChannelRedSpinner 	= 0;
	public int mBlueChannelGreenSpinner = 0;
	public int mBlueChannelBlueSpinner 	= 255;
	/** The gamma-curve correct for the BLUE-value (in RGB space) */
	public double mBlueGamma      		= 2.5;
	/** The (warm/cold) temperature of BLUE Value (in RGB space) */
	public int mBlueTemperature 		= 255;	
	/** The minimum required BLUE-value (in RGB space) */
	public double mBlueThreshold  		= 0.0;
	
	
	public String toJsonString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("\t\t\"channelAdjustment\" :\n");
		strBuf.append("\t\t[\n");
		strBuf.append("\t\t\t{\n");
		strBuf.append("\t\t\t\t\"id\"   : \"" + mId + "\",\n");
		strBuf.append("\t\t\t\t\"leds\" : \"" + mLedIndexString + "\",\n");
		strBuf.append(channelAdjustmentToJsonString() + "\n");
		strBuf.append("\t\t\t}\n");
		strBuf.append("\t\t],\n");

		strBuf.append("\t\t\"temperature\" :\n");
		strBuf.append("\t\t[\n");
		strBuf.append("\t\t\t{\n");
		strBuf.append("\t\t\t\t\"id\"   : \"" + mId + "\",\n");
		strBuf.append("\t\t\t\t\"leds\" : \"" + mLedIndexString + "\",\n");
		strBuf.append(temperatureToJsonString() + "\n");
		strBuf.append("\t\t\t}\n");
		strBuf.append("\t\t],\n");
		
		strBuf.append("\t\t\"transform\" :\n");
		strBuf.append("\t\t[\n");
		strBuf.append("\t\t\t{\n");
		strBuf.append("\t\t\t\t\"id\"   : \"" + mId + "\",\n");
		strBuf.append("\t\t\t\t\"leds\" : \"" + mLedIndexString + "\",\n");
		strBuf.append(hslToJsonString() + ",\n");
		strBuf.append(rgbToJsonString() + "\n");
		strBuf.append("\t\t\t}\n");
		
		return strBuf.toString();
	}
	/**
	 * Creates the JSON string of the channelAdjustment-subconfiguration as used in the Hyperion deamon configfile
	 * 
	 * @return The JSON string of the channelAdjustment-config
	 */
	private String channelAdjustmentToJsonString() {
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append("\t\t\t\t\"pureRed\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"redChannel\"		: %d,\n", mRedChannelRedSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"greenChannel\"\t	: %d,\n", mRedChannelGreenSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"blueChannel\"		: %d\n", mRedChannelBlueSpinner));
		strBuf.append("\t\t\t\t},\n");

		strBuf.append("\t\t\t\t\"pureGreen\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"redChannel\"		: %d,\n", mGreenChannelRedSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"greenChannel\"\t	: %d,\n", mGreenChannelGreenSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"blueChannel\"		: %d\n", mGreenChannelBlueSpinner));
		strBuf.append("\t\t\t\t},\n");

		strBuf.append("\t\t\t\t\"pureBlue\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"redChannel\"		: %d,\n", mBlueChannelRedSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"greenChannel\"\t	: %d,\n", mBlueChannelGreenSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"blueChannel\"		: %d\n", mBlueChannelBlueSpinner));
		strBuf.append("\t\t\t\t}");
		
		return strBuf.toString();
	}
	/**
	 * Creates the JSON string of the temperature-subconfiguration as used in the Hyperion deamon configfile
	 * 
	 * @return The JSON string of the temperature-config
	 */
	private String temperatureToJsonString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("\t\t\t\t\"correctionValues\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"red\" 	: %d,\n", mRedTemperature));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"green\"	: %d,\n", mGreenTemperature));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"blue\" 	: %d\n", mBlueTemperature));
		strBuf.append("\t\t\t\t}");
		return strBuf.toString();
	}
	/**
	 * Creates the JSON string of the HSL-subconfiguration as used in the Hyperion deamon configfile
	 * 
	 * @return The JSON string of the HSL-config
	 */
	private String hslToJsonString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("\t\t\t\t\"hsl\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"saturationGain\"	: %.4f,\n", mHSLSaturationGainAdjustSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"luminanceGain\"\t	: %.4f,\n", mHSLLuminanceGainAdjustSpinner));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"luminanceMinimum\"\t	: %.4f\n", mluminanceMinimumSpinner));
		strBuf.append("\t\t\t\t}");
		return strBuf.toString();
	}
	/**
	 * Creates the JSON string of the RGB-subconfiguration as used in the Hyperion deamon configfile
	 * 
	 * @return The JSON string of the RGB-config
	 */
	private String rgbToJsonString() {
		StringBuffer strBuf = new StringBuffer();
		
		strBuf.append("\t\t\t\t\"red\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"threshold\" 	: %.4f,\n", mRedThreshold));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"gamma\"     	: %.4f\n", mRedGamma));
		strBuf.append("\t\t\t\t},\n");

		strBuf.append("\t\t\t\t\"green\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"threshold\" 	: %.4f,\n", mGreenThreshold));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"gamma\"     	: %.4f\n", mGreenGamma));
		strBuf.append("\t\t\t\t},\n");

		strBuf.append("\t\t\t\t\"blue\" :\n");
		strBuf.append("\t\t\t\t{\n");
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"threshold\" 	: %.4f,\n", mBlueThreshold));
		strBuf.append(String.format(Locale.ROOT, "\t\t\t\t\t\"gamma\"     	: %.4f\n", mBlueGamma));
		strBuf.append("\t\t\t\t}");
		
		return strBuf.toString();
	}
	
	@Override
	public String toString() {
		return mId;
	}
}
