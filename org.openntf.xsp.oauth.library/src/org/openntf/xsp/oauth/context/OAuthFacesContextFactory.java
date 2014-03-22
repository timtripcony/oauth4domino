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
package org.openntf.xsp.oauth.context;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.listeners.ContextListener;
import com.ibm.xsp.FacesExceptionEx;
import com.ibm.xsp.context.FacesContextFactoryImpl;
import com.ibm.xsp.domino.context.DominoFacesContextFactoryImpl;

public class OAuthFacesContextFactory extends FacesContextFactory {
	private final FacesContextFactory _delegate;
	private ContextListener _contextListener;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(OAuthFacesContextFactory.class.getName() + " loaded");
		}
	}

	public OAuthFacesContextFactory() {
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
		Object inst;
		try {
			@SuppressWarnings("rawtypes")
			final Class delegateClass = DominoFacesContextFactoryImpl.class;
			inst = delegateClass.newInstance();
		} catch (final Exception e) {
			e.printStackTrace();
			throw new FacesExceptionEx(e);
		}
		if (inst instanceof FacesContextFactory) {
			_delegate = (FacesContextFactory) inst;
		} else {
			_delegate = null;
		}
	}

	public OAuthFacesContextFactory(final FacesContextFactory delegate) {
		if (_debug) {
			System.out.println(getClass().getName() + " created from delegate");
		}
		if (delegate instanceof FacesContextFactoryImpl) {
			_delegate = ((FacesContextFactoryImpl) delegate).getDelegate();
		} else {
			_delegate = delegate;
		}
	}

	private void attachListener(final OAuthFacesContext ctx) {
		if (ContextListener.ATTACH_LISTENER) {
			if (_contextListener == null) {
				_contextListener = new ContextListener();
			}
			ctx.addRequestListener(_contextListener);
		}
	}

	@Override
	public FacesContext getFacesContext(final Object context, final Object request, final Object response, final Lifecycle lifecycle)
			throws FacesException {
		final FacesContext ctx = _delegate.getFacesContext(context, request, response, lifecycle);
		final OAuthFacesContext localContext = new OAuthFacesContext(ctx);
		attachListener(localContext);
		return localContext;
	}
}
