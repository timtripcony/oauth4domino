package org.openntf.xsp.oauth.util;

import com.ibm.xsp.application.ApplicationEx;

public class XspUtils {
	public static String getXspApplicationProperty(final String propertyName, final String defaultValue) {
		return ApplicationEx.getInstance().getApplicationProperty(propertyName, defaultValue);
	}
}
