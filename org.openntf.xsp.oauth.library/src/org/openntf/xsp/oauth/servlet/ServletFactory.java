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
package org.openntf.xsp.oauth.servlet;

import java.util.Map;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.openntf.xsp.oauth.Activator;
import com.ibm.commons.util.PathUtil;
import com.ibm.designer.runtime.domino.adapter.ComponentModule;
import com.ibm.designer.runtime.domino.adapter.IServletFactory;
import com.ibm.designer.runtime.domino.adapter.ServletMatch;

public class ServletFactory implements IServletFactory {
	/*
	 * NTF - This class loads and constructs, but I have not yet figured out how to make it be the Factory for the primary servlets
	 */
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(ServletFactory.class.getName() + " loaded");
		}
	}
	private final String _pathInfo1;
	private final String _pathInfo2;
	private final String _servletClass;
	private final String _servletName;
	private ComponentModule _module;
	private Servlet _servlet;

	public ServletFactory() {
		if (_debug) {
			System.out.println(getClass().getName() + " created with defaults");
		}
		_servletClass = "org.openntf.xsp.oauth.servlet.OAuthServlet";
		_servletName = "OAuthServlet";
		_pathInfo1 = "/xsp";
		_pathInfo2 = "/xsp/";
	}

	public ServletFactory(String pathInfo, final String servletClass, final String servletName) {
		if (_debug) {
			System.out.println(getClass().getName() + " created with pathInfo " + pathInfo + ", servletClass: " + servletClass
					+ ", servletName: " + servletName);
		}
		pathInfo = PathUtil.concatPath("/xsp", pathInfo, '/'); // $NON-NLS-1$
		_pathInfo1 = pathInfo;
		_pathInfo2 = pathInfo + "/";
		_servletClass = servletClass;
		_servletName = servletName;
	}

	protected Servlet createServlet() throws ServletException {
		if (_debug) {
			System.out.println(getClass().getName() + " creating servlet");
		}
		final Servlet servlet = _module.createServlet(_servletClass, _servletName, (Map<String, String>) null /* params */);
		return servlet;
	}

	public Servlet getServlet() throws ServletException {
		if (_servlet == null) {
			synchronized (this) {
				if (_servlet == null) {
					_servlet = createServlet();
				}
			}
		}
		return _servlet;
	}

	public ServletMatch getServletMatch(final String contextPath, final String path) throws ServletException {
		if (path.equals(_pathInfo1) || path.startsWith(_pathInfo2)) {
			final String servletPath = _pathInfo1;
			final String pathInfo = path.substring(_pathInfo1.length());
			return new ServletMatch(getServlet(), servletPath, pathInfo);
		}
		return null;
	}

	public void init(final ComponentModule paramComponentModule) {
		_module = paramComponentModule;
	}
}
