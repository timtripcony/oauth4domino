package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthResourceContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String databaseName;

	public OAuthResourceContainer() {
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(final String databaseName) {
		this.databaseName = databaseName;
	}
}
