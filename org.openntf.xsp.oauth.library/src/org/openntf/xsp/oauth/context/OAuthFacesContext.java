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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.openntf.xsp.oauth.Activator;
import com.ibm.xsp.domino.context.DominoFacesContext;

public class OAuthFacesContext extends DominoFacesContext {
	private ExternalContext external;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(OAuthFacesContext.class.getName() + " loaded");
		}
	}

	public OAuthFacesContext(final FacesContext paramFacesContext) {
		super(paramFacesContext);
		if (_debug) {
			try {
				System.out.println(getClass().getName() + " created from delegate of " + paramFacesContext.getClass().getName());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ExternalContext getExternalContext() {
		if (external == null) {
			final ExternalContext ectx = super.getExternalContext();
			external = new OAuthExternalContext(ectx);
		}
		return external;
	}
}
