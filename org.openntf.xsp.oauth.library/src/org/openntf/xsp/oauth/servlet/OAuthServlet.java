package org.openntf.xsp.oauth.servlet;

import org.openntf.xsp.oauth.Activator;

import com.ibm.xsp.webapp.DesignerFacesServlet;

public class OAuthServlet extends DesignerFacesServlet {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug)
			System.out.println(OAuthServlet.class.getName() + " loaded");
	}

	public OAuthServlet() {
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

}
