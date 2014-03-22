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

public class XspCanvas extends AbstractHtmlTag {
	public static final String COMPONENT_TYPE = "org.openntf.xsp.Canvas";
	public static final String RENDERER_TYPE = "org.openntf.xsp.Canvas";
	private String ongesturechange;
	private String ongestureend;
	private String ongesturestart;
	private String ontouchcancel;
	private String ontouchend;
	private String ontouchmove;
	private String ontouchstart;

	public XspCanvas() {
		setRendererType(RENDERER_TYPE);
		setTagName("canvas");
	}

	public String getOngesturechange() {
		return getStringProperty("ongesturechange", ongesturechange);
	}

	public String getOngestureend() {
		return getStringProperty("ongestureend", ongestureend);
	}

	public String getOngesturestart() {
		return getStringProperty("ongesturestart", ongesturestart);
	}

	public String getOntouchcancel() {
		return getStringProperty("ontouchcancel", ontouchcancel);
	}

	public String getOntouchend() {
		return getStringProperty("ontouchend", ontouchend);
	}

	public String getOntouchmove() {
		return getStringProperty("ontouchmove", ontouchmove);
	}

	public String getOntouchstart() {
		return getStringProperty("ontouchstart", ontouchstart);
	}

	@Override
	public void restoreState(final FacesContext context, final Object state) {
		final Object[] properties = (Object[]) state;
		int idx = 0;
		super.restoreState(context, properties[idx++]);
		ongesturechange = (String) properties[idx++];
		ongestureend = (String) properties[idx++];
		ongesturestart = (String) properties[idx++];
		ontouchcancel = (String) properties[idx++];
		ontouchend = (String) properties[idx++];
		ontouchmove = (String) properties[idx++];
		ontouchstart = (String) properties[idx++];
	}

	@Override
	public Object saveState(final FacesContext context) {
		final Object[] properties = new Object[8];
		int idx = 0;
		properties[idx++] = super.saveState(context);
		properties[idx++] = ongesturechange;
		properties[idx++] = ongestureend;
		properties[idx++] = ongesturestart;
		properties[idx++] = ontouchcancel;
		properties[idx++] = ontouchend;
		properties[idx++] = ontouchmove;
		properties[idx++] = ontouchstart;
		return properties;
	}

	public void setOngesturechange(final String ongesturechange) {
		this.ongesturechange = ongesturechange;
	}

	public void setOngestureend(final String ongestureend) {
		this.ongestureend = ongestureend;
	}

	public void setOngesturestart(final String ongesturestart) {
		this.ongesturestart = ongesturestart;
	}

	public void setOntouchcancel(final String ontouchcancel) {
		this.ontouchcancel = ontouchcancel;
	}

	public void setOntouchend(final String ontouchend) {
		this.ontouchend = ontouchend;
	}

	public void setOntouchmove(final String ontouchmove) {
		this.ontouchmove = ontouchmove;
	}

	public void setOntouchstart(final String ontouchstart) {
		this.ontouchstart = ontouchstart;
	}
}
