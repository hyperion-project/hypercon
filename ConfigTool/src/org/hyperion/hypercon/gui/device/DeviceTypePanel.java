package org.hyperion.hypercon.gui.device;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.hyperion.hypercon.spec.DeviceConfigModel;

public abstract class DeviceTypePanel extends JPanel {

	protected final Dimension firstColMinDim = new Dimension(80, 10);
	protected final Dimension maxDim = new Dimension(1024, 20);
	
	protected DeviceConfigModel mDeviceConfig = null;
	
	public DeviceTypePanel() {
		super();
	}
	
	public void setDeviceConfig(DeviceConfigModel pDeviceConfig) {
		mDeviceConfig = pDeviceConfig;
	}
	
}
