package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthResourceOwner implements Serializable {
	private static final long serialVersionUID = 1L;
	private String distinguishedName;

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(final String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
}
