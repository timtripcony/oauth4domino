package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthResourceContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String displayName;

	public OAuthResourceContainer() {
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getId() {
		return id;
	}

	protected void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	protected void setId(final String id) {
		this.id = id;
	}
}
