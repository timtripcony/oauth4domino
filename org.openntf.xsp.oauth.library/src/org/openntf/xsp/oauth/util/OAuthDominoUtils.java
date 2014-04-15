package org.openntf.xsp.oauth.util;

import java.util.Collection;
import java.util.Map;
import lotus.domino.Base;
import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.ViewEntry;
import lotus.domino.ViewEntryCollection;
import lotus.domino.ViewNavigator;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class OAuthDominoUtils {
	public static Database getSignerDatabase() {
		Database result = null;
		final Database currentDatabase = ExtLibUtil.getCurrentDatabase();
		try {
			final String serverName = currentDatabase.getServer();
			final String filePath = currentDatabase.getFilePath();
			result = ExtLibUtil.getCurrentSessionAsSigner().getDatabase(serverName, filePath);
		} catch (final NotesException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void incinerate(final Object... objects) {
		for (final Object object : objects) {
			if (object != null) {
				if (object instanceof Map<?, ?>) {
					for (final Object eachValue : ((Map<?, ?>) object).values()) {
						incinerate(eachValue);
					}
				} else if (object instanceof Collection<?>) {
					for (final Object eachMember : (Collection<?>) object) {
						incinerate(eachMember);
					}
				} else if (object instanceof Base) {
					try {
						((Base) object).recycle();
					} catch (final Throwable t) {
					}
				}
			}
		}
	}

	public static ViewEntry next(final ViewEntryCollection collection, final ViewEntry current) {
		ViewEntry result = null;
		try {
			result = collection.getNextEntry(current);
			incinerate(current);
		} catch (final Throwable t) {
		}
		return result;
	}

	public static ViewEntry next(final ViewNavigator navigator, final ViewEntry current) {
		ViewEntry result = null;
		try {
			result = navigator.getNext(current);
			incinerate(current);
		} catch (final Throwable t) {
		}
		return result;
	}
}
