package org.mufassa.model.util;

import java.lang.reflect.Field;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.Parameter;

public class ModelUtil {

	public static Parameter findParameter(AbstractModel pModel, String pParName) {
		for (Field modelField : pModel.getClass().getFields()) {
			Object fieldInstance;
			try {
				fieldInstance = modelField.get(pModel);
			} catch (Exception e) {
				continue;
			}
			
			if (!(fieldInstance instanceof Parameter)) {
				continue;
			}
			String paramName = ((Parameter) fieldInstance).getName();
			if (paramName.equalsIgnoreCase(pParName)) {
				return (Parameter) fieldInstance;
			}
		}
		return null;
	}
}

