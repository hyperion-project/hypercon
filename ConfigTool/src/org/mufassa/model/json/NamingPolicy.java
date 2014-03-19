package org.mufassa.model.json;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingStrategy;

public class NamingPolicy implements FieldNamingStrategy {
	@Override
	public String translateName(Field f) {
		String name = f.getName();
		
		// stripping member indicator if present
		if(name.length() > 1 && name.charAt(0) == 'm' && Character.isUpperCase(name.charAt(1))) {
			name = name.substring(1);
		}
		
		// make sure first letter is capital
		if(name.length() > 0) {
			name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}

		// split name and force upper case
	    StringBuilder translation = new StringBuilder();
	    for (int i = 0; i < name.length(); i++) {
	      char character = name.charAt(i);
	      if (Character.isUpperCase(character) && translation.length() != 0) {
	        translation.append(" ");
	      }
	      translation.append(character);
	    }

	    return translation.toString();
	}
}
