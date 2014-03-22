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

import javax.faces.application.Application;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.listeners.ApplicationListener;
import org.openntf.xsp.oauth.listeners.SessionListener;
import org.openntf.xsp.oauth.listeners.VFSEvent;
import com.ibm.commons.vfs.VFS;
import com.ibm.domino.xsp.module.nsf.ModuleClassLoader;
import com.ibm.xsp.application.ApplicationEx;
import com.ibm.xsp.application.DesignerApplicationEx;

public class OAuthApplicationEx extends DesignerApplicationEx {
	private final static boolean _debug = Activator._debug;
	private final ApplicationListener _localAppListener = new ApplicationListener();
	private final SessionListener _localSessionListener = new SessionListener();
	private final VFSEvent _vfsEvent = new VFSEvent();
	static {
		if (_debug) {
			System.out.println(OAuthApplicationEx.class.getName() + " loaded");
		}
	}

	protected OAuthApplicationEx(final Application paramApplication) {
		super(paramApplication);
		initListeners();
		if (_debug) {
			System.out.println(getClass().getName() + " created from delegate application " + paramApplication.getClass().getName());
		}
	}

	protected OAuthApplicationEx(final ApplicationEx paramApplication) {
		super(paramApplication);
		initListeners();
		if (_debug) {
			System.out.println(getClass().getName() + " created from ApplicationEx");
		}
	}

	private void initListeners() {
		addApplicationListener(_localAppListener);
		_localAppListener.applicationCreated(this);
		addSessionListener(_localSessionListener);
		final com.ibm.designer.runtime.Application designApp = getDesignerApplication();
		final ModuleClassLoader nsfLoader = (ModuleClassLoader) designApp.getClassLoader();
		final VFS vfs = designApp.getVFS();
		vfs.addVFSEventListener(_vfsEvent);
	}
}
