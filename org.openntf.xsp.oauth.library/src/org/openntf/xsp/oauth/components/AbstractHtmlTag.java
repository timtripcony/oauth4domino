/*
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package org.openntf.xsp.oauth.components;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import com.ibm.xsp.component.UIComponentTag;

public abstract class AbstractHtmlTag extends UIComponentTag {
	public static final String COMPONENT_TYPE = "org.openntf.xsp.HtmlTag";
	public static final String RENDERER_TYPE = "org.openntf.xsp.HtmlTag";
	private String dir;
	private String lang;
	private String onclick;
	private String ondblclick;
	private String onkeydown;
	private String onkeypress;
	private String onkeyup;
	private String onmousedown;
	private String onmousemove;
	private String onmouseout;
	private String onmouseover;
	private String onmouseup;
	private String role;
	private String style;
	private String styleClass;
	private String tagName;
	private String title;

	public String getDir() {
		return getStringProperty("dir", dir);
	}

	public String getLang() {
		return getStringProperty("lang", lang);
	}

	public String getOnclick() {
		return getStringProperty("onclick", onclick);
	}

	public String getOndblclick() {
		return getStringProperty("ondblclick", ondblclick);
	}

	public String getOnkeydown() {
		return getStringProperty("onkeydown", onkeydown);
	}

	public String getOnkeypress() {
		return getStringProperty("onkeypress", onkeypress);
	}

	public String getOnkeyup() {
		return getStringProperty("onkeyup", onkeyup);
	}

	public String getOnmousedown() {
		return getStringProperty("onmousedown", onmousedown);
	}

	public String getOnmousemove() {
		return getStringProperty("onmousemove", onmousemove);
	}

	public String getOnmouseout() {
		return getStringProperty("onmouseout", onmouseout);
	}

	public String getOnmouseover() {
		return getStringProperty("onmouseover", onmouseover);
	}

	public String getOnmouseup() {
		return getStringProperty("onmouseup", onmouseup);
	}

	public String getRole() {
		return getStringProperty("role", role);
	}

	protected String getStringProperty(final String propertyName, final String localValue) {
		if (localValue != null) {
			return localValue;
		}
		final ValueBinding vb = getValueBinding(propertyName);
		if (vb != null) {
			return (String) vb.getValue(getFacesContext());
		}
		return null;
	}

	public String getStyle() {
		return getStringProperty("style", style);
	}

	public String getStyleClass() {
		return getStringProperty("styleClass", styleClass);
	}

	public String getTagname() {
		return getStringProperty("tagName", tagName);
	}

	public String getTagName() {
		return tagName;
	}

	public String getTitle() {
		return getStringProperty("title", title);
	}

	@Override
	public void restoreState(final FacesContext context, final Object state) {
		final Object[] properties = (Object[]) state;
		int idx = 0;
		super.restoreState(context, properties[idx++]);
		dir = (String) properties[idx++];
		lang = (String) properties[idx++];
		onclick = (String) properties[idx++];
		ondblclick = (String) properties[idx++];
		onkeydown = (String) properties[idx++];
		onkeypress = (String) properties[idx++];
		onkeyup = (String) properties[idx++];
		onmousedown = (String) properties[idx++];
		onmousemove = (String) properties[idx++];
		onmouseout = (String) properties[idx++];
		onmouseover = (String) properties[idx++];
		onmouseup = (String) properties[idx++];
		role = (String) properties[idx++];
		style = (String) properties[idx++];
		styleClass = (String) properties[idx++];
		tagName = (String) properties[idx++];
		title = (String) properties[idx++];
	}

	@Override
	public Object saveState(final FacesContext context) {
		final Object[] properties = new Object[18];
		int idx = 0;
		properties[idx++] = super.saveState(context);
		properties[idx++] = dir;
		properties[idx++] = lang;
		properties[idx++] = onclick;
		properties[idx++] = ondblclick;
		properties[idx++] = onkeydown;
		properties[idx++] = onkeypress;
		properties[idx++] = onkeyup;
		properties[idx++] = onmousedown;
		properties[idx++] = onmousemove;
		properties[idx++] = onmouseout;
		properties[idx++] = onmouseover;
		properties[idx++] = onmouseup;
		properties[idx++] = role;
		properties[idx++] = style;
		properties[idx++] = styleClass;
		properties[idx++] = tagName;
		properties[idx++] = title;
		return properties;
	}

	public void setDir(final String value) {
		dir = value;
	}

	public void setLang(final String value) {
		lang = value;
	}

	public void setOnclick(final String value) {
		onclick = value;
	}

	public void setOndblclick(final String value) {
		ondblclick = value;
	}

	public void setOnkeydown(final String value) {
		onkeydown = value;
	}

	public void setOnkeypress(final String value) {
		onkeypress = value;
	}

	public void setOnkeyup(final String value) {
		onkeyup = value;
	}

	public void setOnmousedown(final String value) {
		onmousedown = value;
	}

	public void setOnmousemove(final String value) {
		onmousemove = value;
	}

	public void setOnmouseout(final String value) {
		onmouseout = value;
	}

	public void setOnmouseover(final String value) {
		onmouseover = value;
	}

	public void setOnmouseup(final String value) {
		onmouseup = value;
	}

	public void setRole(final String value) {
		role = value;
	}

	public void setStyle(final String value) {
		style = value;
	}

	public void setStyleClass(final String value) {
		styleClass = value;
	}

	public void setTagName(final String tagName) {
		this.tagName = tagName;
	}

	public void setTitle(final String value) {
		title = value;
	}
}
