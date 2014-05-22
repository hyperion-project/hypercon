package org.hyperion.control;

import java.util.Set;

import org.hyperion.hypercon.LedStringModel;
import org.hyperion.hypercon.spec.DeviceConfigModel;
import org.hyperion.hypercon.spec.DeviceType;
import org.hyperion.hypercon.spec.device.LightPackDeviceModel;
import org.hyperion.hypercon.spec.device.SerialDeviceModel;
import org.hyperion.hypercon.spec.device.TestDeviceModel;
import org.hyperion.hypercon.spec.device.Ws2801DeviceModel;
import org.hyperion.model.IModelObserver;
import org.hyperion.model.ModelEvent;
import org.hyperion.model.ModelList;
import org.hyperion.model.OptionalModel;

public class DeviceController {
	
	private final LedStringModel mModel;

	private final IModelObserver mListObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			for (ModelEvent event : pEvents) {
				switch (event.getEvent()) {
				case ModelList.ELEMENT_ADDED:
					for (Object newElem : event.getArgs()) {
						if (!(newElem instanceof DeviceConfigModel)) {
							continue;
						}
						deviceAdded((DeviceConfigModel) newElem);
					}
					break;
				case ModelList.ELEMENT_REMOVED:
					for (Object newElem : event.getArgs()) {
						if (!(newElem instanceof DeviceConfigModel)) {
							continue;
						}
						deviceRemoved((DeviceConfigModel) newElem);
					}
					break;
				default:
					continue;
				}
			}
		}
	};
	private final IModelObserver mDeviceObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			for (ModelEvent event : pEvents) {
				if (!(event.getSource() instanceof DeviceConfigModel)) {
					continue;
				}
				DeviceConfigModel device = (DeviceConfigModel) event.getSource();
				updateDeviceConfig(device);
			}
			mModel.device.commitEvents();
		}
	};
	public DeviceController(final LedStringModel pModel) {
		super();
		
		mModel = pModel;
		
		mModel.device.addObserver(mListObserver, ModelList.ELEMENT_ADDED, ModelList.ELEMENT_REMOVED);
		for (DeviceConfigModel device : mModel.device) {
			deviceAdded(device);
		}
	}
	
	private void deviceAdded(final DeviceConfigModel pDevice) {
		updateDeviceConfig(pDevice);
		pDevice.commitEvents();
		pDevice.addObserver(mDeviceObserver, DeviceConfigModel.DEVICE_TYPE_EVENT);
	}
	private void deviceRemoved(final DeviceConfigModel pDevice) {
		pDevice.removeObserver(mDeviceObserver);
	}
	private void updateDeviceConfig(final DeviceConfigModel pDevice) {
		// Check that the specific configuration is correct
		DeviceType devType = DeviceType.valueOf(pDevice.mType.getValue());
		switch (devType) {
		case ws2801:
		case lightberry:
		case lpd6803:
		case lpd8806:
			if (!(pDevice.mDeviceConfig.get() instanceof Ws2801DeviceModel)) {
				pDevice.mDeviceConfig.set(new Ws2801DeviceModel());
			}
			break;
		case adalight:
		case sedu:
			if (!(pDevice.mDeviceConfig.get() instanceof SerialDeviceModel)) {
				pDevice.mDeviceConfig.set(new SerialDeviceModel());
			}
			break;
		case lightpack:
			if (!(pDevice.mDeviceConfig.get() instanceof LightPackDeviceModel)) {
				pDevice.mDeviceConfig.set(new LightPackDeviceModel());
			}
			break;
		case test:
			if (!(pDevice.mDeviceConfig.get() instanceof TestDeviceModel)) {
				pDevice.mDeviceConfig.set(new TestDeviceModel());
			}
			break;
		case none:
		case paintpack:
		case multi_lightpack:
			pDevice.mDeviceConfig.set(null);
			break;
		default:
			break;
		}
	}
}
