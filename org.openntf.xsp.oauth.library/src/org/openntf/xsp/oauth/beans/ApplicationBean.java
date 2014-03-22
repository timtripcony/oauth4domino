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
package org.openntf.xsp.oauth.beans;

import java.io.Serializable;
import org.openntf.xsp.oauth.application.XspRegistry;

public class ApplicationBean extends AbstractBean {
	private static final long serialVersionUID = 8889085634851461653L;

	public static ApplicationBean getCurrentInstance() {
		Object result = null;
		result = AbstractBean.getCurrentInstance("Application");
		if (result instanceof ApplicationBean) {
			return (ApplicationBean) result;
		} else {
			return null;
		}
	}

	private XspRegistry _xspRegistry;

	public ApplicationBean() {
	}

	public ServerBean getServerScope() {
		return ServerBean.getCurrentInstance();
	}

	public Serializable getServerVar(final Object key) {
		return getServerScope().get(key);
	}

	public XspRegistry getXspRegistry() {
		if (_xspRegistry == null) {
			_xspRegistry = new XspRegistry();
		}
		return _xspRegistry;
	}

	public Serializable putServerVar(final Object key, final Serializable value) {
		return getServerScope().put(key, value);
	}
}
