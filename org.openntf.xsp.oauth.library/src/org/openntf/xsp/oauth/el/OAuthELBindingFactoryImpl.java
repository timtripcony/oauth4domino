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

import javax.faces.application.Application;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import com.ibm.xsp.binding.BindingFactory;
import com.ibm.xsp.util.ValueBindingUtil;

public class OAuthELBindingFactoryImpl implements BindingFactory {
	private final static String _prefix = "oauth";

	public MethodBinding createMethodBinding(final Application arg0, final String arg1, final Class[] arg2) {
		final String str = ValueBindingUtil.parseSimpleExpression(arg1);
		return new OAuthMethodBinding(str);
	}

	public ValueBinding createValueBinding(final Application arg0, final String arg1) {
		final String as[] = ValueBindingUtil.splitFormula(getPrefix(), arg1);
		return new OAuthValueBinding(as[0]);
	}

	public String getPrefix() {
		return _prefix;
	}
}
