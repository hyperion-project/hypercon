package org.mufassa.model.json;

import java.awt.Color;

import javax.swing.JOptionPane;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.mufassa.model.AbstractModel;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class Jsonizer {
	private static Logger LOGGER = Logger.getLogger(Jsonizer.class);
	
	private static Gson getGson() {
		Gson gson = new GsonBuilder()
			.setVersion(1.0)
			.setPrettyPrinting()
			.serializeNulls()
			.serializeSpecialFloatingPointValues()
//			.setFieldNamingStrategy(new NamingPolicy())
			.setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.setExclusionStrategies(new JsonExclusionStrategy())
			.registerTypeHierarchyAdapter(AbstractModel.class, new AbstractModelAdapter())
			.registerTypeAdapter(Color.class, new ColorAdapter())
			.registerTypeAdapter(org.mufassa.model.Parameter.class, new InheritanceAdapter<org.mufassa.model.Parameter>())
			.create();
		return gson;
	}
	
	public static String serialize(AbstractModel model) {
		return getGson().toJson(model);
	}

	public static AbstractModel deserialize(String pJson) {
		// add a log4j appender which catches all message from this package of warning and higher
		JsonizerAppender appender = new JsonizerAppender();
		appender.setLayout(new PatternLayout("- %p: %m"));
		appender.setThreshold(Level.WARN);

		// install the logger for all messages from this package and sub-packages
		Logger baseLogger = Logger.getLogger(Jsonizer.class.getPackage().getName());
		baseLogger.addAppender(appender);

		// try to deserialize the model
		AbstractModel model = null;
		try {
			model = getGson().fromJson(pJson, AbstractModel.class);
	
			// commit any events that might have been set during deserialization
			if(model != null) {
				model.commitEvents();
			}
		} catch (JsonParseException e) {
			LOGGER.error("Error while parsing: " + e.getMessage(), e);
		}
		
		// remove the appender
		baseLogger.removeAppender(appender);
		
		// spam a message box??
		if(appender.hasWarnings()) {
			String message = "";
			for (String msg : appender.getMessages()) {
				message += "\n" + msg;
			}
			
			if(appender.hasErrors()) {
				JOptionPane.showMessageDialog(null, "The file could not be loaded:" + message, "Problems while loading file", JOptionPane.ERROR_MESSAGE);
				return null;
			} else {
				JOptionPane.showMessageDialog(null, "The file has been loaded with problems:" + message, "Problems while loading file", JOptionPane.WARNING_MESSAGE);
			}
		}

		// return the result
		return model;
	}
	
	/**
	 * Creates a deep-copy of an AbstractModel. This will only copy the parts of the model that are 
	 * serialised (and consequently also deserialised). Observers, simple members, etc are not copied 
	 * and will be initialised with defaults.
	 * 
	 * @param pModel The model to deep copy
	 * 
	 * @return THe deep copy of the given model
	 */
	public static AbstractModel serialisedCopy(AbstractModel pModel) {
		return deserialize(serialize(pModel));
	}
}
