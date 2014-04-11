package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String secret;

	public String getId() {
		return id;
	}

	public String getSecret() {
		return secret;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setSecret(final String secret) {
		this.secret = secret;
	}
}
