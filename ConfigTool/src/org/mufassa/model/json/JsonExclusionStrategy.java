/**
 * 
 */
package org.mufassa.model.json;

import java.util.Arrays;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * The strategy to be used for excluding fields when serializing objects to Json format
 */
public class JsonExclusionStrategy implements ExclusionStrategy {
    /**
     * Attributes declared by these classes need to be skipped
     */
    private List<Class<?>> mDeclaringClassesToSkip = null;

    public JsonExclusionStrategy(List <Class<?>> pDeclaringClassesToSkip) {
    	mDeclaringClassesToSkip = pDeclaringClassesToSkip;
    };

    public JsonExclusionStrategy(Class<?> ... pDeclaringClassesToSkip) {
    	mDeclaringClassesToSkip = Arrays.asList(pDeclaringClassesToSkip);
    };

    @Override
	public boolean shouldSkipClass(Class<?> clazz) {
      return false;
    }

    /** 
     * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
     * Ignore fields with IgnoreInJson tag
     */
    @Override
	public boolean shouldSkipField(FieldAttributes f) {
      // Annotated to be ignored
      if (f.getAnnotation(IgnoreInJson.class) != null) {
    	  return true;
      }
      
      // Declared in class that is to be ignored
      Class<?> declaringClass = f.getDeclaringClass();
      if (mDeclaringClassesToSkip.contains(declaringClass)) {
    	return true;
      }
      
      return false;
    }
}
