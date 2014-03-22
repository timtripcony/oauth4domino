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

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import org.openntf.xsp.oauth.Activator;
import com.sun.faces.lifecycle.LifecycleImpl;

public class Lifecycle extends LifecycleImpl {
	private final javax.faces.lifecycle.Lifecycle delegate;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(Lifecycle.class.getName() + " loaded");
		}
	}

	public Lifecycle(final javax.faces.lifecycle.Lifecycle standardLifecycle) {
		delegate = standardLifecycle;
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public void addPhaseListener(final PhaseListener listener) {
		getDelegate().addPhaseListener(listener);
	}

	@Override
	public void execute(final FacesContext context) throws FacesException {
		getDelegate().execute(context);
		// try {
		// getDelegate().execute(context);
		// } catch (FacesException intercepted) {
		// intercepted.printStackTrace();
		// FacesContext.getCurrentInstance().renderResponse();
		// throw intercepted;
		// }
	}

	javax.faces.lifecycle.Lifecycle getDelegate() {
		return delegate;
	}

	@Override
	public PhaseListener[] getPhaseListeners() {
		return getDelegate().getPhaseListeners();
	}

	@Override
	public void removePhaseListener(final PhaseListener listener) {
		getDelegate().removePhaseListener(listener);
	}

	@Override
	public void render(final FacesContext context) throws FacesException {
		getDelegate().render(context);
		// try {
		// getDelegate().render(context);
		// } catch (FacesException intercepted) {
		// intercepted.printStackTrace();
		// throw intercepted;
		// }
	}
}
