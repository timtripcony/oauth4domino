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
package org.openntf.xsp.oauth.resources;

import java.util.IdentityHashMap;
import javax.faces.context.FacesContext;
import com.ibm.xsp.component.UIViewRootEx;
import com.ibm.xsp.resource.DojoModuleResource;
import com.ibm.xsp.resource.Resource;
import com.ibm.xsp.resource.ScriptResource;
import com.ibm.xsp.resource.StyleSheetResource;

public class Resources {
	public static final DojoModuleResource oauthDojoModule1 = new DojoModuleResource("oauth.dojo.Module1");
	public static final DojoModuleResource oauthDojoModule2 = new DojoModuleResource("oauth.dojo.Module2");
	public static final StyleSheetResource oauthStyleSheet1 = new StyleSheetResource(ResourceProvider.RESOURCE_PATH
			+ "styles/oauthstyle1.css");
	public static final ScriptResource oauthClientLibrary1 = new ScriptResource(ResourceProvider.RESOURCE_PATH + "js/oauthLibrary1.js",
			true);
	public static final ScriptResource oauthClientLibrary2 = new ScriptResource(ResourceProvider.RESOURCE_PATH + "js/oauthLibrary2.js",
			true);
	public static final Resource[] oauthResourceCollection = {
			oauthDojoModule1, oauthDojoModule2, oauthStyleSheet1, oauthClientLibrary1, oauthClientLibrary2
	};

	public static void addAllEncodeResources() {
	}

	public static void addEncodeResource(final FacesContext context, final Resource resource) {
		final UIViewRootEx rootEx = (UIViewRootEx) context.getViewRoot();
		addEncodeResource(rootEx, resource);
	}

	@SuppressWarnings("unchecked")
	public static void addEncodeResource(final UIViewRootEx rootEx, final Resource resource) {
		IdentityHashMap<Resource, Boolean> m = (IdentityHashMap<Resource, Boolean>) rootEx.getEncodeProperty("genesis.EncodeResource");
		if (m == null) {
			m = new IdentityHashMap<Resource, Boolean>();
		} else {
			if (m.containsKey(resource)) {
				return;
			}
		}
		m.put(resource, Boolean.TRUE);
		rootEx.addEncodeResource(resource);
	}

	public static void addEncodeResources(final FacesContext context, final Resource[] resources) {
		final UIViewRootEx rootEx = (UIViewRootEx) context.getViewRoot();
		addEncodeResources(rootEx, resources);
	}

	public static void addEncodeResources(final UIViewRootEx rootEx, final Resource[] resources) {
		if (resources != null) {
			for (final Resource resource : resources) {
				addEncodeResource(rootEx, resource);
			}
		}
	}
}
