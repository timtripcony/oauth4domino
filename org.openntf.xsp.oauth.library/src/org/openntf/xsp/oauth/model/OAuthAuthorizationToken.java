package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthAuthorizationToken implements Serializable {
	private static final long serialVersionUID = 1L;
	private OAuthResourceContainer resourceContainer;
	private OAuthResourceOwner resourceOwner;

	public OAuthResourceContainer getResourceContainer() {
		return resourceContainer;
	}

	public OAuthResourceOwner getResourceOwner() {
		return resourceOwner;
	}

	public void setResourceContainer(final OAuthResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}

	public void setResourceOwner(final OAuthResourceOwner resourceOwner) {
		this.resourceOwner = resourceOwner;
	}
}
