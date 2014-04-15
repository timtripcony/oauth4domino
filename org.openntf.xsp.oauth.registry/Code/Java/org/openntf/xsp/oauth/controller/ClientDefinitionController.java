package org.openntf.xsp.oauth.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import lotus.domino.View;
import lotus.domino.ViewEntry;
import lotus.domino.ViewNavigator;

import org.openntf.xsp.oauth.util.OAuthDominoUtils;

import com.ibm.xsp.extlib.util.ExtLibUtil;

public class ClientDefinitionController implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<SelectItem> resourceContainerOptions;

	public List<SelectItem> getResourceContainerOptions() {
		if (resourceContainerOptions == null) {
			resourceContainerOptions = new ArrayList<SelectItem>();
			View containers = null;
			ViewNavigator containerNavigator = null;
			ViewEntry containerEntry = null;
			try {
				containers = ExtLibUtil.getCurrentDatabase().getView("containers");
				containerNavigator = containers.createViewNav();
				containerEntry = containerNavigator.getFirst();
				while (containerEntry != null) {
					Vector<?> columnValues = containerEntry.getColumnValues();
					SelectItem selectItem = new SelectItem();
					selectItem.setLabel(columnValues.get(1).toString());
					selectItem.setValue(columnValues.get(0).toString());
					resourceContainerOptions.add(selectItem);
					containerEntry = OAuthDominoUtils.next(containerNavigator, containerEntry);
					OAuthDominoUtils.incinerate(columnValues); // just in case
				}
			} catch (Throwable t) {
			} finally {
				OAuthDominoUtils.incinerate(containerEntry, containerNavigator, containers);
			}
			Collections.sort(resourceContainerOptions, new Comparator<SelectItem>() {
				public int compare(final SelectItem o1, final SelectItem o2) {
					return o1.getLabel().compareToIgnoreCase(o2.getLabel());
				}
			});
			SelectItem defaultItem = new SelectItem();
			defaultItem.setLabel("Choose a Container");
			defaultItem.setValue("");
			resourceContainerOptions.add(0, defaultItem);
		}
		return resourceContainerOptions;
	}

	public void setResourceContainerOptions(final List<SelectItem> resourceContainerOptions) {
		this.resourceContainerOptions = resourceContainerOptions;
	}
}
