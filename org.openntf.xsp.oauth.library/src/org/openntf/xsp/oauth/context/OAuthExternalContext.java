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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.faces.context.ExternalContext;
import org.openntf.xsp.oauth.Activator;
import com.ibm.xsp.context.Conversation;
import com.ibm.xsp.context.ExternalContextEx;
import com.ibm.xsp.domino.context.DominoExternalContext;

public class OAuthExternalContext extends ExternalContextEx {
	private final DominoExternalContext delegate;
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(OAuthExternalContext.class.getName() + " loaded");
		}
	}

	public OAuthExternalContext(final ExternalContext arg0) {
		super(arg0);
		if (arg0 instanceof DominoExternalContext) {
			delegate = (DominoExternalContext) arg0;
		} else {
			throw new IllegalStateException();
		}
		if (_debug) {
			System.out.println(getClass().getName() + " created from " + arg0.getClass().getName());
		}
	}

	/**
	 * @param paramString
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#changeParameters(java.lang.String)
	 */
	@Override
	public void changeParameters(final String paramString) {
		delegate.changeParameters(paramString);
	}

	/**
	 * @param paramString
	 * @throws IOException
	 * @see com.ibm.xsp.context.ExternalContextEx#dispatch(java.lang.String)
	 */
	@Override
	public void dispatch(final String paramString) throws IOException {
		delegate.dispatch(paramString);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#dominoDocumentUrls()
	 */
	public boolean dominoDocumentUrls() {
		return delegate.dominoDocumentUrls();
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#encodeActionURL(java.lang.String)
	 */
	@Override
	public String encodeActionURL(final String paramString) {
		return delegate.encodeActionURL(paramString);
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#encodeNamespace(java.lang.String)
	 */
	@Override
	public String encodeNamespace(final String paramString) {
		return delegate.encodeNamespace(paramString);
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#encodeResourceURL(java.lang.String)
	 */
	@Override
	public String encodeResourceURL(final String paramString) {
		return delegate.encodeResourceURL(paramString);
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		return delegate.equals(obj);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getApplicationMap()
	 */
	@Override
	public Map getApplicationMap() {
		return delegate.getApplicationMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getAuthType()
	 */
	@Override
	public String getAuthType() {
		return delegate.getAuthType();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getContext()
	 */
	@Override
	public Object getContext() {
		return delegate.getContext();
	}

	/**
	 * @return
	 * @deprecated
	 * @see com.ibm.xsp.context.ExternalContextEx#getConversation()
	 */
	@Deprecated
	@Override
	public Conversation getConversation() {
		return delegate.getConversation();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getDelegate()
	 */
	@Override
	public ExternalContext getDelegate() {
		return delegate.getDelegate();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#getHomePage()
	 */
	@Override
	public String getHomePage() {
		return delegate.getHomePage();
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getInitParameter(java.lang.String)
	 */
	@Override
	public String getInitParameter(final String paramString) {
		return delegate.getInitParameter(paramString);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getInitParameterMap()
	 */
	@Override
	public Map getInitParameterMap() {
		return delegate.getInitParameterMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRemoteUser()
	 */
	@Override
	public String getRemoteUser() {
		return delegate.getRemoteUser();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequest()
	 */
	@Override
	public Object getRequest() {
		return delegate.getRequest();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestContextPath()
	 */
	@Override
	public String getRequestContextPath() {
		return delegate.getRequestContextPath();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestCookieMap()
	 */
	@Override
	public Map getRequestCookieMap() {
		return delegate.getRequestCookieMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestHeaderMap()
	 */
	@Override
	public Map getRequestHeaderMap() {
		return delegate.getRequestHeaderMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestHeaderValuesMap()
	 */
	@Override
	public Map getRequestHeaderValuesMap() {
		return delegate.getRequestHeaderValuesMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestLocale()
	 */
	@Override
	public Locale getRequestLocale() {
		return delegate.getRequestLocale();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestLocales()
	 */
	@Override
	public Iterator getRequestLocales() {
		return delegate.getRequestLocales();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestMap()
	 */
	@Override
	public Map getRequestMap() {
		return delegate.getRequestMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestParameterMap()
	 */
	@Override
	public Map getRequestParameterMap() {
		return delegate.getRequestParameterMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestParameterNames()
	 */
	@Override
	public Iterator getRequestParameterNames() {
		return delegate.getRequestParameterNames();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestParameterValuesMap()
	 */
	@Override
	public Map getRequestParameterValuesMap() {
		return delegate.getRequestParameterValuesMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestPathInfo()
	 */
	@Override
	public String getRequestPathInfo() {
		return delegate.getRequestPathInfo();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getRequestServletPath()
	 */
	@Override
	public String getRequestServletPath() {
		return delegate.getRequestServletPath();
	}

	/**
	 * @param paramString
	 * @return
	 * @throws MalformedURLException
	 * @see com.ibm.xsp.context.ExternalContextEx#getResource(java.lang.String)
	 */
	@Override
	public URL getResource(final String paramString) throws MalformedURLException {
		return delegate.getResource(paramString);
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(final String paramString) {
		return delegate.getResourceAsStream(paramString);
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getResourcePaths(java.lang.String)
	 */
	@Override
	public Set getResourcePaths(final String paramString) {
		return delegate.getResourcePaths(paramString);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getResponse()
	 */
	@Override
	public Object getResponse() {
		return delegate.getResponse();
	}

	/**
	 * @param paramBoolean
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getSession(boolean)
	 */
	@Override
	public Object getSession(final boolean paramBoolean) {
		return delegate.getSession(paramBoolean);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getSessionMap()
	 */
	@Override
	public Map getSessionMap() {
		return delegate.getSessionMap();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#getUserPrincipal()
	 */
	@Override
	public Principal getUserPrincipal() {
		return delegate.getUserPrincipal();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	/**
	 * @param paramString
	 * @return
	 * @see com.ibm.xsp.context.ExternalContextEx#isUserInRole(java.lang.String)
	 */
	@Override
	public boolean isUserInRole(final String paramString) {
		return delegate.isUserInRole(paramString);
	}

	/**
	 * @param paramString
	 * @see com.ibm.xsp.context.ExternalContextEx#log(java.lang.String)
	 */
	@Override
	public void log(final String paramString) {
		delegate.log(paramString);
	}

	/**
	 * @param paramString
	 * @param paramThrowable
	 * @see com.ibm.xsp.context.ExternalContextEx#log(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(final String paramString, final Throwable paramThrowable) {
		delegate.log(paramString, paramThrowable);
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#optimizeForDominoServer()
	 */
	public boolean optimizeForDominoServer() {
		return delegate.optimizeForDominoServer();
	}

	/**
	 * @return
	 * @see com.ibm.xsp.domino.context.DominoExternalContext#optimizeForNotesClient()
	 */
	public boolean optimizeForNotesClient() {
		return delegate.optimizeForNotesClient();
	}

	/**
	 * @param paramString
	 * @throws IOException
	 * @see com.ibm.xsp.context.ExternalContextEx#redirect(java.lang.String)
	 */
	@Override
	public void redirect(final String paramString) throws IOException {
		delegate.redirect(paramString);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return delegate.toString();
	}
}
