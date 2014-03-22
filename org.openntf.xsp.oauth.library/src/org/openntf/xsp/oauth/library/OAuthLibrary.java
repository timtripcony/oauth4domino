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
package org.openntf.xsp.oauth.library;

import org.openntf.xsp.oauth.Activator;
import com.ibm.xsp.library.AbstractXspLibrary;

public class OAuthLibrary extends AbstractXspLibrary {
	private final static String LIBRARY_ID = "org.openntf.xsp.oauth.library"; // OAuthLibrary.class.getName();
	public final static String LIBRARY_RESOURCE_NAMESPACE = "oauth";
	public final static String LIBRARY_BEAN_PREFIX = "OAuth";
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(OAuthLibrary.class.getName() + " loaded");
		}
	}

	public OAuthLibrary() {
		if (_debug) {
			System.out.println(OAuthLibrary.class.getName() + " created");
		}
	}

	@Override
	public String[] getDependencies() {
		return new String[] {
				"com.ibm.xsp.core.library", "com.ibm.xsp.extsn.library", "com.ibm.xsp.domino.library", "com.ibm.xsp.designer.library"
		};
	}

	@Override
	public String[] getFacesConfigFiles() {
		final String[] files = new String[] {
			"META-INF/oauth-faces-config.xml"
		};
		return files;
	}

	public String getLibraryId() {
		return LIBRARY_ID;
	}

	@Override
	public String getPluginId() {
		return Activator.PLUGIN_ID;
	}

	@Override
	public String[] getXspConfigFiles() {
		final String[] files = new String[] {
			"META-INF/oauth.xsp-config"
		};
		return files;
	}

	@Override
	public boolean isGlobalScope() {
		return false;
	}
}
