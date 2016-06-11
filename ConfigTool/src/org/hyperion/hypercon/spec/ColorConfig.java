package org.hyperion.hypercon.spec;

import java.util.Locale;
import java.util.Vector;

/**
 * The color tuning parameters of the different color channels (both in RGB space as in HSV space)
 */
public class ColorConfig {
	
	/** List with color transformations */
	public Vector<TransformConfig> mTransforms = new Vector<>();
	{
		mTransforms.add(new TransformConfig());
	}
	
	public boolean mSmoothingEnabled = true;
	/** The type of smoothing algorithm */
	public ColorSmoothingType mSmoothingType = ColorSmoothingType.linear;
	/** The time constant for smoothing algorithm in milliseconds */
	public int mSmoothingTime_ms = 200;
	/** The update frequency of the leds in Hz */
	public double mSmoothingUpdateFrequency_Hz = 20.0;
	
	/** The number of periods (1/mSmoothingUpdateFrequency_Hz) to delay the update of the leds */
	public int mUpdateDelay = 0;
	
	/**
	 * Creates the JSON string of the configuration as used in the Hyperion daemon configfile
	 * 
	 * @return The JSON string of this ColorConfig
	 */
	public String toJsonString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("\t// COLOR CALIBRATION CONFIG\n");
		strBuf.append("\t\"color\" :\n");
		strBuf.append("\t{\n");
		
		for (int i=0; i<mTransforms.size(); ++i) {
			TransformConfig transform = mTransforms.get(i);
			strBuf.append(transform.toJsonString());
		}

		if(mSmoothingEnabled==true){
			strBuf.append("\t\t],\n");
			strBuf.append("\t// SMOOTHING CONFIG\n");
			strBuf.append(smoothingToString());
		}
		else{
			strBuf.append("\t\t]\n");
			strBuf.append("\t// NO SMOOTHING CONFIG\n");
		}
		strBuf.append("\t}");
		
		return strBuf.toString();
	}
	
	/**
	 * Creates the JSON string of the smoothing subconfiguration as used in the Hyperion deamon configfile
	 * 
	 * @return The JSON string of the HSV-config
	 */
	private String smoothingToString() {
		StringBuffer strBuf = new StringBuffer();
		
		String preamble = "\t\t";
		strBuf.append(preamble).append("\"smoothing\" :\n");
		strBuf.append(preamble).append("{\n");
		strBuf.append(preamble).append(String.format(Locale.ROOT, "\t\"type\"            : \"%s\",\n", (mSmoothingEnabled) ? mSmoothingType.getTypeId() : "none"));
		strBuf.append(preamble).append(String.format(Locale.ROOT, "\t\"time_ms\"         : %d,\n", mSmoothingTime_ms));
		strBuf.append(preamble).append(String.format(Locale.ROOT, "\t\"updateFrequency\" : %.4f,\n", mSmoothingUpdateFrequency_Hz));
		strBuf.append(preamble).append(String.format(Locale.ROOT, "\t\"updateDelay\"     : %d\n", mUpdateDelay));
		
		strBuf.append(preamble).append("}\n");
		return strBuf.toString();
	}
}
