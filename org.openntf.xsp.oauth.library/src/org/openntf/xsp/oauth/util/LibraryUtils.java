/*
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package org.openntf.xsp.oauth.util;

import java.net.URL;
import java.util.Enumeration;
import javax.faces.context.FacesContext;
import org.osgi.framework.Bundle;

public class LibraryUtils {
	// methods copied mostly from ExtLibUtils to avoid dependency
	public static URL getResourceURL(final Bundle bundle, String path) {
		final int fileNameIndex = path.lastIndexOf('/');
		final String fileName = path.substring(fileNameIndex + 1);
		path = path.substring(0, fileNameIndex + 1);
		// see http://www.osgi.org/javadoc/r4v42/org/osgi/framework/Bundle.html
		// #findEntries%28java.lang.String,%20java.lang.String,%20boolean%29
		final Enumeration<?> urls = bundle.findEntries(path, fileName, false/* recursive */);
		if (null != urls && urls.hasMoreElements()) {
			final URL url = (URL) urls.nextElement();
			if (null != url) {
				return url;
			}
		}
		return null; // no match, 404 not found.
	}

	public static Object resolveVariable(final FacesContext facesContext, final String name) {
		final Object value = facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, name);
		return value;
	}
}
