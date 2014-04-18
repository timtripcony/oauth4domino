package org.openntf.xsp.oauth.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import javax.faces.context.FacesContext;
import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.Session;
import org.openntf.xsp.oauth.model.OAuthSession;
import org.openntf.xsp.oauth.model.OAuthTemporaryCode;
import org.openntf.xsp.oauth.util.OAuthDominoUtils;
import org.openntf.xsp.oauth.util.XspUtils;
import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.extlib.util.ExtLibUtil;
import com.ibm.xsp.model.domino.DominoUtils;

public class OAuthProvider implements Serializable {
	private static final long serialVersionUID = 1L;

	public static OAuthProvider getCurrentInstance() {
		return (OAuthProvider) ExtLibUtil.resolveVariable(FacesContext.getCurrentInstance(), "OAuthProvider");
	}

	public static OAuthSession getSession() {
		return getCurrentInstance().getSessionFromAccessToken();
	}

	private String tokenStorePath;
	private String registryPath;

	public void authorize() {
		Document codeDocument = null;
		boolean codeCreated = false;
		final OAuthTemporaryCode codeScope = OAuthTemporaryCode.getCurrentInstance();
		try {
			codeDocument = getTokenStore().createDocument();
			codeDocument.replaceItemValue("form", "code");
			codeDocument.replaceItemValue("id", codeScope.getCode());
			codeDocument.replaceItemValue("clientId", codeScope.getClientId());
			codeDocument.replaceItemValue("state", codeScope.getState());
			codeDocument.replaceItemValue("scope", codeScope.getScope());
			codeDocument.replaceItemValue("redirectURI", codeScope.getRedirectURI());
			codeDocument.replaceItemValue("resourceOwner", ExtLibUtil.getXspContext().getUser().getDistinguishedName());
			codeCreated = codeDocument.save();
		} catch (final Throwable t) {
			t.printStackTrace();
		} finally {
			OAuthDominoUtils.incinerate(codeDocument);
		}
		if (codeCreated) {
			final FacesContext context = FacesContext.getCurrentInstance();
			final StringBuilder sb = new StringBuilder();
			sb.append(codeScope.getRedirectURI());
			sb.append(codeScope.getRedirectURI().contains("?") ? "&" : "?");
			sb.append("code=" + codeScope.getCode());
			if (StringUtil.isNotEmpty(codeScope.getState())) {
				sb.append("&state=" + encodeURI(codeScope.getState()));
			}
			try {
				context.getExternalContext().redirect(sb.toString());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void denyAccess() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final StringBuilder sb = new StringBuilder();
		final OAuthTemporaryCode codeScope = OAuthTemporaryCode.getCurrentInstance();
		sb.append(codeScope.getRedirectURI());
		sb.append(codeScope.getRedirectURI().contains("?") ? "&" : "?");
		sb.append("error=access_denied");
		try {
			context.getExternalContext().redirect(sb.toString());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	protected String encodeURI(final String original) {
		String result = "";
		try {
			result = URLEncoder.encode(original, "utf-8");
		} catch (final Throwable t) {
		}
		return result;
	}

	public Database getRegistry() {
		Database result = null;
		try {
			final Session signerSession = ExtLibUtil.getCurrentSessionAsSigner();
			result = DominoUtils.openDatabaseByName(signerSession, getRegistryPath());
		} catch (final Throwable t) {
		}
		return result;
	}

	public String getRegistryPath() {
		if (registryPath == null) {
			registryPath = XspUtils.getXspApplicationProperty("xsp.oauth.registry", "oauth.nsf");
		}
		return registryPath;
	}

	public OAuthSession getSessionFromAccessToken() {
		return OAuthSession.fromFacesContext(FacesContext.getCurrentInstance());
	}

	public Database getTokenStore() {
		Database result = null;
		try {
			final Session signerSession = ExtLibUtil.getCurrentSessionAsSigner();
			result = DominoUtils.openDatabaseByName(signerSession, getTokenStorePath());
		} catch (final Throwable t) {
		}
		return result;
	}

	public String getTokenStorePath() {
		if (tokenStorePath == null) {
			tokenStorePath = XspUtils.getXspApplicationProperty("xsp.oauth.tokenstore", "oauth.nsf");
		}
		return tokenStorePath;
	}

	public void setRegistryPath(final String registryPath) {
		this.registryPath = registryPath;
	}

	public void setTokenStorePath(final String tokenStorePath) {
		this.tokenStorePath = tokenStorePath;
	}

	public void validateCodeRequest() {
		final OAuthTemporaryCode codeRequest = OAuthTemporaryCode.getCurrentInstance();
		if (!"code".equals(codeRequest.getResponseType())) {
			final FacesContext context = FacesContext.getCurrentInstance();
			final StringBuilder sb = new StringBuilder();
			sb.append(codeRequest.getRedirectURI());
			sb.append(codeRequest.getRedirectURI().contains("?") ? "&" : "?");
			sb.append("error=unsupported_response_type");
			sb.append("&error_description=" + encodeURI("response_type parameter must be \"code\""));
			try {
				context.getExternalContext().redirect(sb.toString());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}
