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
package org.openntf.xsp.oauth.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import com.ibm.designer.runtime.domino.adapter.ComponentModule;
import com.ibm.designer.runtime.domino.adapter.HttpService;
import com.ibm.designer.runtime.domino.adapter.LCDEnvironment;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpServletRequestAdapter;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpServletResponseAdapter;
import com.ibm.designer.runtime.domino.bootstrap.adapter.HttpSessionAdapter;

public class OAuthService extends HttpService {
	public OAuthService(final LCDEnvironment paramLCDEnvironment) {
		super(paramLCDEnvironment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean doService(final String paramString1, final String paramString2, final HttpSessionAdapter paramHttpSessionAdapter,
			final HttpServletRequestAdapter paramHttpServletRequestAdapter, final HttpServletResponseAdapter paramHttpServletResponseAdapter)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getModules(final List<ComponentModule> paramList) {
		// TODO Auto-generated method stub
	}
}
