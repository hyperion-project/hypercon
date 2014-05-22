package org.hyperion.model.json;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
	private final static Logger LOGGER = Logger.getLogger(ColorAdapter.class);
	
	@Override
	public Color deserialize(JsonElement pJson, Type pTypeOfT, JsonDeserializationContext pContext)
			throws JsonParseException {
		if (!pJson.isJsonPrimitive() || !pJson.getAsJsonPrimitive().isString()) {
			LOGGER.error("JsonElement did not contain required color string");
			return null;
		}
		
		String colorStr = pJson.getAsString();
		Color color = getColor(colorStr);
		
		if(color == null) {
			LOGGER.error("Unable to decode color string: " + colorStr);
			return null;
		}
		
		return color;
	}

	@Override
	public JsonElement serialize(Color pSrc, Type pTypeOfSrc, JsonSerializationContext pContext) {
		String hexStr = getHexName(pSrc);
		return new JsonPrimitive(hexStr);
	}

	/**
	 * Return the hex name of a specified color.
	 * 
	 * @param color Color to get hex name of.
	 * @return Hex name of color: "aarrggbb".
	 */
	protected static String getHexName(Color color) {
		return String.format("0x%08x", color.getRGB());
	}

	/**
	 * Returns a Color based on 'colorName' which must be one of the predefined
	 * colors in java.awt.Color. Returns null if colorName is not valid.
	 * 
	 * @param colorName String describing the color, e.g. pink
	 * @return The color, or null if the color was not found
	 */
	protected static Color getColor(String colorName) {
		if ((colorName == null) || (colorName.length() <= 0)) {
			return null;
		}

		if (colorName.startsWith("0x")) {
			long rgba = Long.parseLong(colorName.substring(2), 16);
			return new Color((int) rgba, true);
		}

		try {
			// Find the field and value of colorName
			Field field = Color.class.getField(colorName);
			return (Color) field.get(null);
		} catch (Exception e) {
			// empty
		}
		
		return null;
	}

}
