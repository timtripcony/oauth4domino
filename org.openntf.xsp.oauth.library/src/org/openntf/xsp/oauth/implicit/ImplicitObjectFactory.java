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
import org.openntf.xsp.oauth.Activator;
import com.ibm.xsp.context.FacesContextEx;
import com.ibm.xsp.util.TypedUtil;

public class ImplicitObjectFactory implements com.ibm.xsp.el.ImplicitObjectFactory {
	private final String[][] implicitObjectList = {
		{
				"OAuthServerBean", ImplicitObject.class.getName()
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
	public void createImplicitObjects(final FacesContextEx paramFacesContextEx) {
		final Map localMap = TypedUtil.getRequestMap(paramFacesContextEx.getExternalContext());
		localMap.put("OAuthServerBean", new ImplicitObject());
	}

	public void destroyImplicitObjects(final FacesContext paramFacesContext) {
		// TODO Auto-generated method stub
	}

	public Object getDynamicImplicitObject(final FacesContextEx paramFacesContextEx, final String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[][] getImplicitObjectList() {
		return implicitObjectList;
	}
}
