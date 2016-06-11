package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.gui.Hardware_Tab.device.DeviceTypePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.LightPackPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.PhilipsHuePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.PiBlasterPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.SerialPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.SpiDevPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.TestDevicePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.TinkerForgePanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.AtmoOrbPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.UDPPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.WS281XPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.FadeCandyPanel;
import org.hyperion.hypercon.gui.Hardware_Tab.device.rawhidPanel;
/**
 * Enumeration of known device types
 */
public enum DeviceType {

/** all non pwm led (stripes) **/
	spileds("---------SPI---------"),
	apa102("APA102"),
	ws2801("WS2801"),
	p9813("P9813"),
	lpd6803("LPD6803"),
	lpd8806("LPD8806"),	

/** all pwm led (stripes) **/
	pwmleds("---------PWM---------"),
	ws2812b("WS2812b (just RPi1)"),
	ws2812spi("WS281X-SPI"),
	ws281x("WS281X (RPi1, RPi2, RPi3)"),
	
/** other devices/controller **/
	otherleddevices("--------OTHER--------"),
	philipshue("PhilipsHUE"),
	atmoorb("AtmoOrb"),
	piblaster("PiBlaster"),
	tinkerforge("Tinkerforge"),
	fadecandy("FadeCandy"),
	udp("UDP"),
	udpraw("UDP (new-imp)"),
	rawhid("RawHID (USB)"),
	sedu("SEDU"),
	adalight("Adalight"),
	AdalightApa102("AdalightAPA102"),
	tpm2("TPM2"),
	hyperion_usbasp_ws2801("USBASP-WS2801"),
	hyperion_usbasp_ws2812("USBASP-WS2812"),
	
/** 3rd party vendors/devices **/
	rdpartydevices("------3rd PARTY------"),
	ambiled("AmbiLed"),
	atmo("Atmo"),
	lightpack("Lightpack"),
	multi_lightpack("Multi-Lightpack"),
	paintpack("Paintpack"),
	file("Test (file)"),
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
		} else if (this == multi_lightpack) {
			return "multi-lightpack";
		}return super.name();
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
			case lpd6803:
			case lpd8806:
			case p9813:
			case ws2801:
				mConfigPanel = new SpiDevPanel();
				break;
			case file:
				mConfigPanel = new TestDevicePanel();
				break;
			case adalight:
			case AdalightApa102:
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
			case rawhid:
				mConfigPanel = new rawhidPanel();
				break;
			case hyperion_usbasp_ws2801:
			case hyperion_usbasp_ws2812:
			case multi_lightpack:
			case paintpack:
			case ws2812b:
			case ws2812spi:
			case none:
				break;
			case ws281x:
				mConfigPanel = new WS281XPanel();
				break;			
			case fadecandy:
				mConfigPanel = new FadeCandyPanel();
				break;
			case philipshue:
				mConfigPanel = new PhilipsHuePanel();
				break;
			case atmoorb:
				mConfigPanel = new AtmoOrbPanel();
				break;
			case udp:
			case udpraw:
				mConfigPanel = new UDPPanel();
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
