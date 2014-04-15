package org.openntf.xsp.oauth.model;

import java.io.Serializable;
import java.util.Set;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.util.OAuthDominoUtils;

public class OAuthAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;

	public static OAuthAccessToken valueOf(final String token) {
		final OAuthAccessToken result = new OAuthAccessToken();
		result.setToken(token);
		View tokens = null;
		ViewEntry tokenEntry = null;
		try {
			tokens = result.getProvider().getTokenStore().getView("tokens");
			tokenEntry = tokens.getEntryByKey(token);
			if (tokenEntry != null) {
				// TODO: populate token metadata so the session can query its client, resource container, permissions, etc.
				// result.setClient(client);
			}
		} catch (final Throwable t) {
		} finally {
			OAuthDominoUtils.incinerate(tokenEntry, tokens);
		}
		return result;
	}

	private OAuthProvider provider;
	private OAuthClient client;
	private OAuthResourceContainer resourceContainer;
	private OAuthResourceOwner resourceOwner;
	private Set<OAuthPermission> permissions;
	private String token;

	protected OAuthAccessToken() {
	}

	public OAuthClient getClient() {
		if (client == null) {
		}
		return client;
	}

	public Set<OAuthPermission> getPermissions() {
		return permissions;
	}

	protected OAuthProvider getProvider() {
		if (provider == null) {
			provider = new OAuthProvider();
		}
		return provider;
	}

	public OAuthResourceContainer getResourceContainer() {
		return resourceContainer;
	}

	public OAuthResourceOwner getResourceOwner() {
		if (resourceOwner == null) {
			// TODO: check token store to see if valid
		}
		return resourceOwner;
	}

	protected String getToken() {
		return token;
	}

	public boolean isPermitted(final OAuthPermission permission) {
		return getPermissions().contains(permission);
	}

	public boolean isPermitted(final String permissionId) {
		return getPermissions().contains(permissionId);
	}

	public boolean isValid() {
		return getResourceOwner() != null;
	}

	protected void setClient(final OAuthClient client) {
		this.client = client;
	}

	protected void setPermissions(final Set<OAuthPermission> permissions) {
		this.permissions = permissions;
	}

	protected void setProvider(final OAuthProvider provider) {
		this.provider = provider;
	}

	protected void setResourceContainer(final OAuthResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}

	protected void setResourceOwner(final OAuthResourceOwner resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	protected void setToken(final String token) {
		this.token = token;
	}
}
