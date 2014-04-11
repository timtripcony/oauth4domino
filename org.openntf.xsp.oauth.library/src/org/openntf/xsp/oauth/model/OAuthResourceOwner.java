package org.openntf.xsp.oauth.model;

import java.io.Serializable;
import lotus.domino.Session;
import org.openntf.xsp.oauth.util.SudoUtils;

public class OAuthResourceOwner implements Serializable {
	private static final long serialVersionUID = 1L;
	private String distinguishedName;

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public Session getSession() {
		return SudoUtils.getSessionAs(getDistinguishedName());
	}

	public void setDistinguishedName(final String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
}
