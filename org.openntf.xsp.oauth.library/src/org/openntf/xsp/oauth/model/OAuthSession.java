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
package org.openntf.xsp.oauth.model;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.util.OAuthDominoUtils;
import org.openntf.xsp.oauth.util.SudoUtils;
import org.openntf.xsp.oauth.util.XspUtils;
import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class OAuthSession extends SudoSession {
	public static OAuthSession fromFacesContext(final FacesContext context) {
		OAuthSession result = null;
		String userName = "Anonymous";
		OAuthAccessToken authorization = null;
		final String accessToken = ExtLibUtil.readParameter(context, "accessToken");
		Activator.debug("Access Token: " + accessToken);
		final HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		if (StringUtil.isNotEmpty(accessToken)) {
			View tokenView = null;
			ViewEntry tokenEntry = null;
			Document tokenDocument = null;
			try {
				final OAuthProvider provider = new OAuthProvider();
				tokenView = provider.getTokenStore().getView("tokens");
				tokenEntry = tokenView.getEntryByKey(accessToken, true);
				if (tokenEntry != null) {
					tokenDocument = tokenEntry.getDocument();
					final String resourceOwner = tokenDocument.getItemValueString("resourceOwner");
					if (StringUtil.isNotEmpty(resourceOwner)) {
						userName = resourceOwner;
						authorization = OAuthAccessToken.valueOf(accessToken);
					}
				} else {
					response.setStatus(401);
				}
			} catch (final NotesException e) {
				throw new FacesException(e);
			} finally {
				OAuthDominoUtils.incinerate(tokenDocument, tokenEntry, tokenView);
			}
			// TODO: log authentication if enabled in xsp.properties and resource container profile
		}
		result = new OAuthSession(SudoUtils.getSessionAs(userName));
		result.setAuthorization(authorization);
		Activator.debug("User name: " + userName);
		return result;
	}

	private OAuthAccessToken authorization;

	protected OAuthSession(final Session delegate) {
		super(delegate);
	}

	public OAuthAccessToken getAuthorization() {
		return authorization;
	}

	public String getTokenStorePath() {
		return XspUtils.getXspApplicationProperty("xsp.oauth.tokenstore", "oauth.nsf");
	}

	public boolean isGrantedPermission(final String permissionId) {
		boolean result = false;
		if (getAuthorization() != null) {
			result = getAuthorization().isPermitted(permissionId);
		}
		return result;
	}

	protected void setAuthorization(final OAuthAccessToken authorization) {
		this.authorization = authorization;
	}
}
