package org.openntf.xsp.oauth.util;

import java.util.ArrayList;

public class RecycleBin extends ArrayList<Object> {
	private static final long serialVersionUID = 1L;

	public void empty() {
		OAuthDominoUtils.incinerate(this);
		clear();
	}
}
