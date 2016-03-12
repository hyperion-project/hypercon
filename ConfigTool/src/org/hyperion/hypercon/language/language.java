package org.hyperion.hypercon.language;

import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class language {
	private static final String BUNDLE_NAME = "org.hyperion.hypercon.language.language"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private language() {
	}

	public static String getString(String key) {
		try {
			return new String(RESOURCE_BUNDLE.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
		catch (UnsupportedEncodingException e) {
			return '!' + key + '!';
		}
	}
}
