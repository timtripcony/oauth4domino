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
package org.openntf.xsp.oauth.components;

import javax.faces.context.FacesContext;
import com.ibm.xsp.component.UIOutputEx;

public class OpenNTFComponent extends UIOutputEx {
	public static final String COMPONENT_FAMILY = "org.openntf.xsp.oauth";
	public static final String RENDERER_TYPE = "org.openntf.xsp.oauth.renderkit.OpenNTFRenderer";
	public static final String STYLEKIT_FAMILY = "OpenNTF.Starter";

	public OpenNTFComponent() {
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public String getStyleKitFamily() {
		return STYLEKIT_FAMILY;
	}

	@Override
	public void restoreState(final FacesContext context, final Object state) {
		final Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
	}

	@Override
	public Object saveState(final FacesContext context) {
		final Object values[] = new Object[1];
		values[0] = super.saveState(context);
		return values;
	}
}
