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
import javax.faces.el.PropertyNotFoundException;
import com.ibm.xsp.binding.ValueBindingEx;

public class OAuthValueBinding extends ValueBindingEx {
	private String _expression;

	public OAuthValueBinding() {
		super();
	}

	public OAuthValueBinding(final String expression) {
		super();
		_expression = expression;
	}

	@Override
	public Class<?> getType(final FacesContext arg0) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that would determine the class to be returned
		// In this sample, we'll just always return a string
		return String.class;
	}

	@Override
	public Object getValue(final FacesContext arg0) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that would generate the value to return
		// In this sample, we simply reflect the original expression...
		return _expression;
	}

	@Override
	public boolean isReadOnly(final FacesContext arg0) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that determines whether the binding is readonly.
		// In this sample, we are always readonly
		return true;
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

	@Override
	public void setValue(final FacesContext arg0, final Object arg1) throws EvaluationException, PropertyNotFoundException {
		// TODO Insert your code that does whatever you want from an active set on the value
		// In this sample, we do nothing
	}
}
