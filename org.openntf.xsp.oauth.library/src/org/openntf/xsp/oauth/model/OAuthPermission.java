package org.openntf.xsp.oauth.model;

import java.io.Serializable;

public class OAuthPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String displayName;
	private String description;

	@Override
	public boolean equals(final Object other) {
		return null != other && (other == this || other instanceof OAuthPermission && toString().equalsIgnoreCase(other.toString()));
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

	@Override
	public int hashCode() {
		return toString().hashCode();
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

	@Override
	public String toString() {
		return (getId() == null ? "" : getId()).toLowerCase();
	}
}
