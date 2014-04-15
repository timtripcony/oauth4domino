package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String containerId;
	private String displayName;
	private String description;
	private String secret;

	public String getContainerId() {
		return containerId;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getId() {
		return id;
	}

	public String getSecret() {
		return secret;
	}

	public void setContainerId(final String containerId) {
		this.containerId = containerId;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setSecret(final String secret) {
		this.secret = secret;
	}
}
