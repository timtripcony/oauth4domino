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
package org.openntf.xsp.oauth.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import com.ibm.xsp.registry.FacesDefinition;
import com.ibm.xsp.registry.FacesLibrary;
import com.ibm.xsp.registry.FacesSharableRegistry;
import com.ibm.xsp.registry.config.XspRegistryManager;
import com.ibm.xsp.registry.config.XspRegistryProvider;

public class XspRegistry {
	private final Map<Class<? extends UIComponent>, FacesDefinition> _reverseDefMap;
	private final Map<Class<? extends UIComponent>, String> _reverseTagMap;
	private final Map<String, Class<? extends UIComponent>> _tagMap;

	public XspRegistry() {
		_reverseDefMap = new HashMap<Class<? extends UIComponent>, FacesDefinition>();
		_reverseTagMap = new HashMap<Class<? extends UIComponent>, String>();
		_tagMap = new HashMap<String, Class<? extends UIComponent>>();
		initRegistryMaps();
	}

	public FacesDefinition getDefForClass(final Class<? extends UIComponent> clazz) {
		FacesDefinition result = null;
		if (_reverseDefMap.containsKey(clazz)) {
			return _reverseDefMap.get(clazz);
		} else {
			while (clazz.getSuperclass() != null) {
				result = _reverseDefMap.get(clazz.getSuperclass());
				if (result != null) {
					return result;
				}
			}
		}
		return result;
	}

	public FacesDefinition getTagForClass(final Class<? extends UIComponent> clazz) {
		final FacesDefinition def = getDefForClass(clazz);
		if (def != null) {
			return _reverseDefMap.get(def);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void initRegistryMaps() {
		final XspRegistryManager xrm = XspRegistryManager.getManager();
		final Collection<String> pids = xrm.getRegistryProviderIds();
		for (final String id : pids) {
			final XspRegistryProvider curProv = xrm.getRegistryProvider(id);
			final FacesSharableRegistry curReg = curProv.getRegistry();
			final Collection<String> uris = curReg.getLocalNamespaceUris();
			for (final String curUri : uris) {
				final FacesLibrary fl = curReg.getLocalLibrary(curUri);
				final List<FacesDefinition> defsList = fl.getDefs();
				for (final FacesDefinition curDef : defsList) {
					_reverseDefMap.put((Class<? extends UIComponent>) curDef.getJavaClass(), curDef);
					if (curDef.isTag()) {
						_reverseTagMap.put((Class<? extends UIComponent>) curDef.getJavaClass(), curDef.getTagName());
						_tagMap.put(curDef.getTagName(), (Class<? extends UIComponent>) curDef.getJavaClass());
					}
				}
			}
		}
	}
}
