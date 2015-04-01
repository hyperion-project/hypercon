package org.hyperion.hypercon.gui.Hardware_Tab.device;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.hyperion.hypercon.spec.DeviceConfig;

public abstract class DeviceTypePanel extends JPanel {

	protected final Dimension firstColMinDim = new Dimension(80, 10);
	protected final Dimension maxDim = new Dimension(1024, 20);
	
	protected DeviceConfig mDeviceConfig = null;
	
	public DeviceTypePanel() {
		super();
	}
	
	public void setDeviceConfig(DeviceConfig pDeviceConfig) {
		mDeviceConfig = pDeviceConfig;
	}
	
	public String getValue(String pKey, String pDefault) {
		if (!mDeviceConfig.mDeviceProperties.contains(pKey)) {
			return pDefault;
		}
		return mDeviceConfig.mDeviceProperties.get(pKey).toString();
	}
	
	public int getValue(String pKey, int pDefault) {
		if (!mDeviceConfig.mDeviceProperties.contains(pKey)) {
			return pDefault;
		}
		try {
			return Integer.parseInt(mDeviceConfig.mDeviceProperties.get(pKey).toString());
		} catch (Throwable t) {}
		return pDefault;
	}
	
	public boolean getValue(String pKey, boolean pDefault) {
		if (!mDeviceConfig.mDeviceProperties.contains(pKey)) {
			return pDefault;
		}
		try {
			return Boolean.parseBoolean(mDeviceConfig.mDeviceProperties.get(pKey).toString());
		} catch (Throwable t) {}
		return pDefault;
	}
}
