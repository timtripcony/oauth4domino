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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.util.LibraryUtils;
import com.ibm.xsp.webapp.FacesResourceServlet;
import com.ibm.xsp.webapp.resources.BundleResourceProvider;
import com.ibm.xsp.webapp.resources.URLResourceProvider;

public class ResourceProvider extends BundleResourceProvider {
	protected class CacheableResource extends URLResourceProvider.URLResource {
		protected CacheableResource(final String paramString, final URL paramURL) {
			super(paramString, paramURL);
		}

		@Override
		protected long getLastModificationDate() {
			return ResourceProvider.getLastModificationDate();
		}

		@Override
		protected boolean isResourcesModifiedSince(final long paramLong) {
			final long l = getLastModificationDate();
			if (l >= 0L) {
				return paramLong < l;
			}
			return true;
		}
	}

	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(ResourceProvider.class.getName() + " loaded");
		}
	}
	public static final String BUNDLE_RES_PATH = "/resources/web/";
	public static final String MODULE_PREFIX = "." + ModulePath.NAMESPACE + "/" + Activator.getVersion();
	public static final String RESOURCE_PATH = FacesResourceServlet.RESOURCE_PREFIX // "/.ibmxspres/"
			+ ResourceProvider.MODULE_PREFIX // ".oauth"
			+ "/"; // "/.ibmxspres/.oauth/1.0.1.201112458428/"
	public static final String DOJO_PATH = FacesResourceServlet.RESOURCE_PREFIX // "/.ibmxspres/"
			+ ResourceProvider.MODULE_PREFIX; // ".oauth"
	private static final long LAST_MODDATE = Activator.instance.getBundle().getLastModified();

	protected static long getLastModificationDate() {
		return LAST_MODDATE;
	}

	protected final Map<String, CacheableResource> resources = new HashMap<String, CacheableResource>();

	public ResourceProvider() {
		super(Activator.instance.getBundle(), MODULE_PREFIX);
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public synchronized URLResource addResource(final String paramString, final URL paramURL) {
		final CacheableResource localURLResource = new CacheableResource(paramString, paramURL);
		if (shouldCacheResources()) {
			resources.put(paramString, localURLResource);
		}
		return localURLResource;
	}

	@Override
	protected URL getResourceURL(final HttpServletRequest request, final String name) {
		final String path = BUNDLE_RES_PATH + name;
		final URL resourcePath = LibraryUtils.getResourceURL(getBundle(), path);
		return resourcePath;
	}

	@Override
	protected boolean shouldCacheResources() {
		return true;
	}
}
