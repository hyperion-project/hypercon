package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.gui.Hardware_Tab.device.DeviceTypePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.LightPackPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.PhilipsHuePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.PiBlasterPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.SerialPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.SpiDevPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.TestDevicePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.TinkerForgePanel;

/**
 * Enumeration of known device types
 */
public enum DeviceType {
	apa102("APA102"),
	/** Adalight device */
	adalight("Adalight"),
	ambiled("AmbiLed"),
	atmo("Atmo"),
	hyperion_usbasp_ws2801("Hyperion-USBASP-WS2801"),
	hyperion_usbasp_ws2812("Hyperion-USBASP-WS2812"),
	/** Lightberry device */
	lightberry("Lightberry"),
	/** Lightpack USB led device */
	lightpack("Lightpack"),
	/** LDP6803 Led String device with one continuous shift-register (5 bits per color channel)*/
	lpd6803("LPD6803"),
	/** LDP8806 Led String device with one continuous shift-register (1 + 7 bits per color channel)*/
	lpd8806("LPD8806"),
	multi_lightpack("Multi-Lightpack"),
	p9813("P9813"),
	/** Paintpack USB led device */
	paintpack("Paintpack"),
	philipshue("PhilipsHUE"),
	piblaster("PiBlaster"),
	/** SEDU LED device */
	sedu("SEDU"),
	/** Test device for writing color values to file-output */
	test("Test"),
	tinkerforge("ThinkerForge"),
	/** tpm2 protocol serial device */
	tpm2("TPM2"),
	/** WS2801 Led String device with one continuous shift-register (1 byte per color-channel) */
	ws2801("WS2801"),
	/** WS2801 Led String device with one continuous shift-register (1 byte per color-channel) */
	ws2812b("WS2812b"),
	
	/** No device, no output is generated */
	none("None");
	
	/** The 'pretty' name of the device type */
	private final String mName;
	
	/** The device specific configuration panel */
	private DeviceTypePanel mConfigPanel;
	
	/**
	 * Constructs the DeviceType
	 * 
	 * @param name The 'pretty' name of the device type
	 * @param pConfigPanel The panel for device type specific configuration
	 */
	private DeviceType(final String name) {
		mName        = name;
	}

	/**
	 * Returns the type identifier as used by hyperion to determine the device type
	 * @return
	 */
	public String getTypeId() {
		if (this == hyperion_usbasp_ws2801) {
			return "hyperion-usbasp-ws2801";
		} else if (this == hyperion_usbasp_ws2812) {
			return "hyperion-usbasp-ws2812";
		}
		return super.name();
	}
	
	/**
	 * Returns the configuration panel for the this device-type (or null if no configuration is required)
	 * 
	 * @return The panel for configuring this device type
	 */
	public DeviceTypePanel getConfigPanel(DeviceConfig pDeviceConfig) {
		if (mConfigPanel == null) {
			switch (this) {
			case apa102:
			case lightberry:
			case lpd6803:
			case lpd8806:
			case p9813:
			case ws2801:
				mConfigPanel = new SpiDevPanel();
				break;
			case test:
				mConfigPanel = new TestDevicePanel();
				break;
			case adalight:
			case ambiled:
			case atmo:
			case sedu:
			case tpm2:
				mConfigPanel = new SerialPanel();
				break;
			case lightpack:
				mConfigPanel = new LightPackPanel();
				break;
			case piblaster:
				mConfigPanel = new PiBlasterPanel();
				break;
			case hyperion_usbasp_ws2801:
			case hyperion_usbasp_ws2812:
			case multi_lightpack:
			case paintpack:
			case ws2812b:
			case none:
				break;
			case philipshue:
				mConfigPanel = new PhilipsHuePanel();
				break;
			case tinkerforge:
				mConfigPanel = new TinkerForgePanel();
				break;
			default:
				break;
			}
		}
		if (mConfigPanel != null) {
			mConfigPanel.setDeviceConfig(pDeviceConfig);
		} else {
			pDeviceConfig.mDeviceProperties.clear();
		}
		return mConfigPanel;
	}
	
	@Override
	public String toString() {
		return mName;
	}
	
	public static String listTypes() {
		StringBuilder sb = new StringBuilder();
		for (DeviceType type : DeviceType.values()) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(type.toString());
		}
		return sb.toString();
	}
}
