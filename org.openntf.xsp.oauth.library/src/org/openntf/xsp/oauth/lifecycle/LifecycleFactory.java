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
package org.openntf.xsp.oauth.lifecycle;

import java.util.Iterator;
import javax.faces.lifecycle.Lifecycle;
import org.openntf.xsp.oauth.Activator;

public class LifecycleFactory extends javax.faces.lifecycle.LifecycleFactory {
	public static final String LIFECYCLE_ID = "OAuthLifecycle";
	private final javax.faces.lifecycle.LifecycleFactory delegate;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(LifecycleFactory.class.getName() + " loaded");
		}
	}

	public LifecycleFactory(final javax.faces.lifecycle.LifecycleFactory defaultFactory) {
		delegate = defaultFactory;
		final Lifecycle defaultLifecycle = delegate.getLifecycle(javax.faces.lifecycle.LifecycleFactory.DEFAULT_LIFECYCLE);
		addLifecycle(LIFECYCLE_ID, new org.openntf.xsp.oauth.lifecycle.Lifecycle(defaultLifecycle));
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public void addLifecycle(final String lifecycleId, final Lifecycle lifecycle) {
		delegate.addLifecycle(lifecycleId, lifecycle);
	}

	@Override
	public Lifecycle getLifecycle(final String lifecycleId) {
		String localId = lifecycleId;
		if (javax.faces.lifecycle.LifecycleFactory.DEFAULT_LIFECYCLE.equals(lifecycleId)) {
			localId = LIFECYCLE_ID;
		}
		return delegate.getLifecycle(localId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<String> getLifecycleIds() {
		return delegate.getLifecycleIds();
	}
}
