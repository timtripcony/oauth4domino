package org.openntf.xsp.oauth.model;

import java.io.Serializable;
import java.util.Set;

public class OAuthAcessToken implements Serializable {
	private static final long serialVersionUID = 1L;

	public static OAuthAcessToken valueOf(final String token) {
		final OAuthAcessToken result = new OAuthAcessToken();
		result.setToken(token);
		return result;
	}

	private OAuthResourceContainer resourceContainer;
	private OAuthResourceOwner resourceOwner;
	private Set<AbstractOAuthPermission> permissions;
	private String token;

	protected OAuthAcessToken() {
	}

	public Set<AbstractOAuthPermission> getPermissions() {
		return permissions;
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

	public String getToken() {
		return token;
	}

	public boolean isPermitted(final AbstractOAuthPermission permission) {
		return getPermissions().contains(permission);
	}

	public boolean isPermitted(final String permissionId) {
		return getPermissions().contains(permissionId);
	}

	public boolean isValid() {
		return getResourceOwner() != null;
	}

	public void setPermissions(final Set<AbstractOAuthPermission> permissions) {
		this.permissions = permissions;
	}

	public void setResourceContainer(final OAuthResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}

	public void setResourceOwner(final OAuthResourceOwner resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	private void setToken(final String token) {
		this.token = token;
	}
}
