package org.mufassa.model.json;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.ModelList;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;
import org.mufassa.model.SelectableModelList;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * This Gson adapter class (de-)serializes an AbstractModel and its Parameter fields to and from Json.
 * 
 */
public class AbstractModelAdapter implements JsonSerializer<AbstractModel>, JsonDeserializer<AbstractModel> {
	private static final Logger LOGGER = Logger.getLogger(AbstractModelAdapter.class);
	
	private static final String mClassProperty = "_class_";

	private static final String mSelectedIndex = "Selected index";
	private static final String mContent = "Content";
	
	private FieldNamingStrategy mNaming = FieldNamingPolicy.IDENTITY;

	@Override
	public JsonElement serialize(AbstractModel pSrc, Type pTypeOfSrc, JsonSerializationContext pContext) {
		JsonObject result = new JsonObject();
		
		// add the class type. This is needed for deserialization
		result.add(mClassProperty, new JsonPrimitive(pSrc.getClass().getName()));
		
		if (pSrc.getClass().isAnnotationPresent(JsonComment.class)) {
			JsonComment classComment = pSrc.getClass().getAnnotation(JsonComment.class);
			result.setComment(classComment.value());
		}
		
		// add all fields
		Field[] fields = pSrc.getClass().getFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(IgnoreInJson.class)) {
				continue;
			}
			
			// Check if the field is commented
			String fieldComment = null;
			if (field.isAnnotationPresent(JsonComment.class)) {
				JsonComment fieldCommentAn = field.getAnnotation(JsonComment.class);
				fieldComment = fieldCommentAn.value();
			}
			
			// Obtain the object
			Object object;
			try {
				object = field.get(pSrc);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Something is seriously wrong in this class ("+pSrc+")", e);
				continue;
			} catch (IllegalAccessException e) {
				LOGGER.error("Something is seriously wrong in this class ("+pSrc+")", e);
				continue;
			}

			// handle the object
			// we only serialize parameters and sub models
			if(object instanceof ParameterBool) {
				ParameterBool parameter = (ParameterBool) object;
				result.add(parameter.getName(), new JsonPrimitive(parameter.getValue()));
			} else if (object instanceof ParameterInt) {
				ParameterInt parameter = (ParameterInt) object;
				result.add(parameter.getName(), new JsonPrimitive(parameter.getValue()));
			} else if (object instanceof ParameterDouble) {
				ParameterDouble parameter = (ParameterDouble) object;
				result.add(parameter.getName(), new JsonPrimitive(parameter.getValue()));
			} else if (object instanceof ParameterObject) {
				ParameterObject<?> parameter = (ParameterObject<?>) object;
				ParameterizedType fieldType = (ParameterizedType) field.getGenericType();
				Type genericType = fieldType.getActualTypeArguments()[0];
				result.add(parameter.getName(), pContext.serialize(parameter.getValue(), genericType));
			} else if (object instanceof ModelList<?>) {
				ModelList<?> list = (ModelList<?>) object;
				JsonArray array = new JsonArray();

				// The object is a ModelList or derived from a model list. we need the generic ModelListType to obtain the element type.
				ParameterizedType listType = null;
				if(object.getClass() == ModelList.class) {
					listType = (ParameterizedType) field.getGenericType();
				} else {
					Class<?> clazz = object.getClass();
					while(clazz.getSuperclass() != ModelList.class) {
						clazz = object.getClass();
					}
					listType = (ParameterizedType) clazz.getGenericSuperclass();
				}
				Type elementType = listType.getActualTypeArguments()[0];
				
				// add all elements of the list
				for (Object obj : list) {
					JsonElement arraryElement = pContext.serialize(obj, elementType);
					array.add(arraryElement);
				}

				// prepend the array with the selected index if this is a SelectableModelList
				if(list instanceof SelectableModelList) {
					Object selectedItem = ((SelectableModelList<?>) list).getSelectedItem();
					int selectedIndex = list.indexOf(selectedItem);
					JsonObject jsonObject = new JsonObject();
					if (fieldComment != null) {
						jsonObject.setComment(fieldComment);
					}
					jsonObject.addProperty(mSelectedIndex, selectedIndex);
					jsonObject.add(mContent, array);
					result.add(mNaming.translateName(field), jsonObject);
				} else {
					if (fieldComment != null) {
						array.setComment(fieldComment);
					}
					result.add(mNaming.translateName(field), array);
				}
			} else if (object instanceof AbstractModel) {
				result.add(mNaming.translateName(field), pContext.serialize(object));
			}
		}
		
		return result;
	}

	@Override
	public AbstractModel deserialize(JsonElement pJson, Type pTypeOfT, JsonDeserializationContext pContext) throws JsonParseException {
		if(!pJson.isJsonObject()) {
			LOGGER.error("Expected a json object when deserializing AbstractModel");
			return null;
		}
		
		JsonObject jsonObject = (JsonObject) pJson;

		// create the model class
		if (!jsonObject.has(mClassProperty)) {
			LOGGER.error("Expected a member \"" + mClassProperty + "\" in json object while deserializing an AbstractModel");
			return null;
		}
		String className = jsonObject.get(mClassProperty).getAsString();
		AbstractModel model = createModelFromClassName(className);
		if(model != null) {
			deserializeModel(jsonObject, model, pContext);
		}
		
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public void deserializeModel(JsonObject pJsonObject, AbstractModel pModel, JsonDeserializationContext pContext) {
		// add all fields
		Field[] fields = pModel.getClass().getFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(IgnoreInJson.class)) {
				continue;
			}

			// Obtain the object
			Object object;
			try {
				object = field.get(pModel);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Something is seriously wrong in this class ("+pModel.getClass()+")", e);
				continue;
			} catch (IllegalAccessException e) {
				LOGGER.error("Something is seriously wrong in this class ("+pModel.getClass()+")", e);
				continue;
			}
			
			// handle the object
			// we only serialize parameters and sub models
			if(object instanceof ParameterBool) {
				ParameterBool parameter = (ParameterBool) object;
				JsonElement element = pJsonObject.get(parameter.getName());
				if (element == null) {
					LOGGER.warn("Missing parameter \"" + parameter.getName() + "\" when deserializing AbstractModel: using default value");
				} else {
					try {
						parameter.setValue(element.getAsBoolean());
					} catch(NumberFormatException e) {
						LOGGER.warn("Expected parameter \"" + parameter.getName() + "\" to be a boolean: using default value");
					}
				}
			} else if(object instanceof ParameterInt) {
				ParameterInt parameter = (ParameterInt) object;
				JsonElement element = pJsonObject.get(parameter.getName());
				if (element == null) {
					LOGGER.warn("Missing parameter \"" + parameter.getName() + "\" when deserializing AbstractModel: using default value");
				} else {
					try {
						parameter.setValue(element.getAsInt());
					} catch(NumberFormatException e) {
						LOGGER.warn("Expected parameter \"" + parameter.getName() + "\" to be an int: using default value");
					}
				}
			} else if(object instanceof ParameterDouble) {
				ParameterDouble parameter = (ParameterDouble) object;
				JsonElement element = pJsonObject.get(parameter.getName());
				if (element == null) {
					LOGGER.warn("Missing parameter \"" + parameter.getName() + "\" when deserializing AbstractModel: using default value");
				} else {
					try {
						parameter.setValue(element.getAsDouble());
					} catch(NumberFormatException e) {
						LOGGER.warn("Expected parameter \"" + parameter.getName() + "\" to be a double: using default value");
					}
				}
			} else if(object instanceof ParameterObject) {
				ParameterObject<Object> parameter = (ParameterObject<Object>) object;
				JsonElement element = pJsonObject.get(parameter.getName());
				if (element == null) {
					LOGGER.warn("Missing parameter \"" + parameter.getName() + "\" when deserializing AbstractModel: using default value");
				} else if(element.isJsonNull()) {
					parameter.setValue(null);
				} else {
					ParameterizedType parameterType = (ParameterizedType) field.getGenericType();
					Type objectType = parameterType.getActualTypeArguments()[0];				
					Object obj = pContext.deserialize(element, objectType);
					if (obj == null) {
						LOGGER.warn("Parameter \"" + parameter.getName() + "\" could not be deserialized as " + objectType + ": using default value");
					} else {
						parameter.setValue(obj);
					}
				}
			} else if (object instanceof ModelList) {
				ModelList<Object> list = (ModelList<Object>) object;
				JsonElement element = pJsonObject.get(mNaming.translateName(field));
				if (element == null) {
					LOGGER.warn("Missing list \"" + mNaming.translateName(field) + "\" when deserializing AbstractModel: using default list");
					continue;
				}
				
				// get the selected info
				int selectedIndex = -1;
				if(list instanceof SelectableModelList) {
					// this is the prepended selected index.
					if(!element.isJsonObject()) {
						LOGGER.warn("SelectableModelList should be an object: using default list");
						continue;
					}
					JsonObject jsonObject = element.getAsJsonObject(); 
					if(!jsonObject.has(mSelectedIndex)) {
						LOGGER.warn("Missing field " + mSelectedIndex + " when deserializing SelectableModelList: using default list");
						continue;
					}
					try {
						selectedIndex = jsonObject.get(mSelectedIndex).getAsInt();
					} catch (Exception ex) {
						LOGGER.warn("Field " + mSelectedIndex + " could not be read as an int: using default list", ex);
						continue;
					}

					if(!jsonObject.has(mContent)) {
						LOGGER.warn("Missing field " + mSelectedIndex + " when deserializing SelectableModelList: using default list");
						continue;
					}
					element = jsonObject.get(mContent);
				}
					
				if (!element.isJsonArray()) {
					LOGGER.warn("Expected parameter \"" + mNaming.translateName(field) + "\" to be an array: using default list");
					continue;
				}
				JsonArray array = element.getAsJsonArray();
				
				// The object is a ModelList or derived from a model list. we need the generic ModelListType to obtain the element type.
				ParameterizedType listType = null;
				if(object.getClass() == ModelList.class || object.getClass() == SelectableModelList.class) {
					listType = (ParameterizedType) field.getGenericType();
				} else {
					Class<?> clazz = object.getClass();
					while(clazz.getSuperclass() != ModelList.class) {
						clazz = object.getClass();
					}
					listType = (ParameterizedType) clazz.getGenericSuperclass();
				}
				Type elementType = listType.getActualTypeArguments()[0];
				
				list.clear(); // delete any elements that may have been set as defaults
				for (int i = 0; i < array.size(); ++i) {
					JsonElement e = array.get(i);
					Object item = pContext.deserialize(e, elementType);
					if(item == null) {
						LOGGER.warn("Unable to deserialize list item: skipping item[" + i + "]");
					} else {
						list.add(item);
						
						// set the selected item
						if(i == selectedIndex) {
							((SelectableModelList<Object>)list).setSelectedItem(item);		
						}
					}
				}
			} else if (object instanceof AbstractModel) {
				AbstractModel model = (AbstractModel) object;
				JsonElement element = pJsonObject.get(mNaming.translateName(field));
				if (element == null) {
					LOGGER.warn("Missing sub model \"" + mNaming.translateName(field) + "\" when deserializing AbstractModel: using default value");
				} else if (!element.isJsonObject()) {
					LOGGER.warn("Expected parameter \"" + mNaming.translateName(field) + "\" to be an object: using default value");
				} else {
					JsonObject obj = element.getAsJsonObject();
					deserializeModel(obj, model, pContext);
				}
			}
		}
	}

	private AbstractModel createModelFromClassName(String className) {
		try {
			return (AbstractModel) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			LOGGER.error("Unable to load and create instance of class: " + className, e);
			return null;
		} catch (IllegalAccessException e) {
			LOGGER.error("Unable to load and create instance of class: " + className, e);
			return null;
		} catch (ClassNotFoundException e) {
			LOGGER.error("Unable to load and create instance of class: " + className, e);
			return null;
		} 
	}
}
