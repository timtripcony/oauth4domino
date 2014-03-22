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
package org.openntf.xsp.oauth.el;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodNotFoundException;
import com.ibm.xsp.binding.MethodBindingEx;

public class OAuthMethodBinding extends MethodBindingEx {
	private String _expression;

	public OAuthMethodBinding() {
		super();
	}

	public OAuthMethodBinding(final String expression) {
		super();
		_expression = expression;
	}

	@Override
	public Class<?> getType(final FacesContext arg0) throws MethodNotFoundException {
		// TODO Determine what your resulting type for this method binding is
		return null;
	}

	@Override
	public Object invoke(final FacesContext arg0, final Object[] arg1) throws EvaluationException, MethodNotFoundException {
		// TODO Whatever execution behavior you want with a return of whatever you want
		return null;
	}

	@Override
	public void restoreState(final FacesContext paramFacesContext, final Object paramObject) {
		final Object[] arrayOfObject = (Object[]) paramObject;
		super.restoreState(paramFacesContext, arrayOfObject[0]);
		_expression = (String) arrayOfObject[1];
	}

	@Override
	public Object saveState(final FacesContext paramFacesContext) {
		final Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = super.saveState(paramFacesContext);
		arrayOfObject[1] = _expression;
		return arrayOfObject;
	}
}
