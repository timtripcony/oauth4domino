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
package org.openntf.xsp.oauth.implicit;

import java.util.Map;
import javax.faces.context.FacesContext;
import lotus.domino.NotesException;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.model.OAuthSession;
import com.ibm.xsp.context.FacesContextEx;
import com.ibm.xsp.util.TypedUtil;

public class ImplicitObjectFactory implements com.ibm.xsp.el.ImplicitObjectFactory {
	private final String[][] implicitObjectList = {
		{
				"sessionFromAccessToken", OAuthSession.class.getName()
		}
	};
	private final static boolean _debug = Activator._debug;

	public ImplicitObjectFactory() {
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	public void createImplicitObjects(final FacesContextEx context) {
		final Map localMap = TypedUtil.getRequestMap(context.getExternalContext());
		if (!localMap.containsKey("sessionFromAccessToken")) {
			localMap.put("sessionFromAccessToken", OAuthSession.fromFacesContext(context));
		}
	}

	public void destroyImplicitObjects(final FacesContext context) {
		if (_debug) {
			System.out.println(getClass().getName() + " destroying implicit objects");
		}
		final Map localMap = TypedUtil.getRequestMap(context.getExternalContext());
		if (localMap != null) {
			final OAuthSession sudoSession = (OAuthSession) localMap.get("sessionFromAccessToken");
			if (sudoSession != null) {
				try {
					sudoSession.recycle();
				} catch (final NotesException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Object getDynamicImplicitObject(final FacesContextEx context, final String variable) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[][] getImplicitObjectList() {
		return implicitObjectList;
	}
}
