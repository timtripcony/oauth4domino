package org.openntf.xsp.oauth.util;

import com.ibm.xsp.application.ApplicationEx;

public class XspUtils {
	public static String getXspApplicationProperty(final String propertyName, final String defaultValue) {
		String result = defaultValue;
		final ApplicationEx app = ApplicationEx.getInstance();
		if (app != null) {
			result = app.getApplicationProperty(propertyName, defaultValue);
		}
		return result;
	}
}
