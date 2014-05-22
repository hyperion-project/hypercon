package org.hyperion.model.json;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * This adapter can be used for simple cases of inheritance.
 * It invokes the default serialization of the object and adds a class-tag to the serialized object making it possible to
 * create the derived object before invoking the default deserialization.
 * 
 * Do not register this adapter for an entire type hierarchy! This will result in loops...
 *
 * @param <T>
 */
public class InheritanceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	private final static Logger LOGGER = Logger.getLogger(InheritanceAdapter.class);
	
	private final static String CLASS = "_class_";
	
	@Override
	public JsonElement serialize(T pSrc, Type pTypeOfSrc, JsonSerializationContext pContext) {
		// We could just run the default serialization and add the CLASS property, but this would put the CLASS 
		// property at the end and I want the CLASS tag as the first member...
		JsonObject resultObject = new JsonObject();
		resultObject.addProperty(CLASS, pSrc.getClass().getName());
		
		JsonObject jsonObject = (JsonObject) pContext.serialize(pSrc);
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			resultObject.add(entry.getKey(), entry.getValue());
		}
		
		return resultObject;
	}

	@Override
	public T deserialize(JsonElement pJson, Type pTypeOfT, JsonDeserializationContext pContext) throws JsonParseException {
		if (!pJson.isJsonObject()) {
			LOGGER.error("Expected a json object");
			return null;
		}
		JsonObject jsonObject = pJson.getAsJsonObject();

		JsonElement classElement = jsonObject.get(CLASS);
		if (classElement == null || !classElement.isJsonPrimitive()) {
			LOGGER.error("Expected a string element with the class type");
			return null;
		}
		JsonPrimitive classPrimative = classElement.getAsJsonPrimitive();
		if (!classPrimative.isString()) {
			LOGGER.error("Expected a string element with the class type");
			return null;
		}

		String className = classPrimative.getAsString();
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Unable to load class for type: " + className, e);
			return null;
		}

		// return the default deserialization
		return pContext.deserialize(pJson, clazz);
	}
}
