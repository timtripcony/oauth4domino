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
package org.openntf.xsp.oauth.resolver;

import javax.faces.FacesException;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import org.openntf.xsp.oauth.Activator;
import com.ibm.xsp.util.Delegation;

public class PropertyResolver extends javax.faces.el.PropertyResolver {
	protected final javax.faces.el.PropertyResolver _resolver;
	private final static boolean _debug = Activator._debug;

	public PropertyResolver() throws FacesException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		_resolver = (javax.faces.el.PropertyResolver) Delegation.getImplementation("property-resolver");
		if (_debug) {
			System.out.println(getClass().getName() + " created using " + (_resolver != null ? _resolver.getClass().getName() : "null"));
		}
	}

	public PropertyResolver(final javax.faces.el.PropertyResolver resolver) {
		if (_debug) {
			System.out.println(getClass().getName() + " created from delegate " + resolver.getClass().getName());
		}
		_resolver = resolver;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getType(final Object paramObject, final int paramInt) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getType(paramObject, paramInt);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getType(final Object paramObject1, final Object paramObject2) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getType(paramObject1, paramObject2);
	}

	@Override
	public Object getValue(final Object paramObject, final int paramInt) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getValue(paramObject, paramInt);
	}

	@Override
	public Object getValue(final Object paramObject1, final Object paramObject2) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.getValue(paramObject1, paramObject2);
	}

	@Override
	public boolean isReadOnly(final Object paramObject, final int paramInt) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.isReadOnly(paramObject, paramInt);
	}

	@Override
	public boolean isReadOnly(final Object paramObject1, final Object paramObject2) throws EvaluationException, PropertyNotFoundException {
		// your code goes here
		return _resolver.isReadOnly(paramObject1, paramObject2);
	}

	@Override
	public void setValue(final Object paramObject1, final int paramInt, final Object paramObject2) throws EvaluationException,
			PropertyNotFoundException {
		// your code goes here
		_resolver.setValue(paramObject1, paramInt, paramObject2);
	}

	@Override
	public void setValue(final Object paramObject1, final Object paramObject2, final Object paramObject3) throws EvaluationException,
			PropertyNotFoundException {
		// your code goes here
		_resolver.setValue(paramObject1, paramObject2, paramObject3);
	}
}
