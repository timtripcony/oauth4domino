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
package org.openntf.xsp.oauth.renderkit;

import java.io.OutputStream;
import java.io.Writer;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;
import org.openntf.xsp.oauth.Activator;
import com.sun.faces.renderkit.RenderKitImpl;

public class OpenNTFRenderKit extends RenderKitImpl {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(OpenNTFRenderKit.class.getName() + " loaded");
		}
	}

	public OpenNTFRenderKit() {
		super();
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public void addRenderer(final String arg0, final String arg1, final Renderer arg2) {
		super.addRenderer(arg0, arg1, arg2);
	}

	@Override
	public ResponseStream createResponseStream(final OutputStream arg0) {
		return super.createResponseStream(arg0);
	}

	@Override
	public ResponseWriter createResponseWriter(final Writer arg0, final String arg1, final String arg2) {
		return super.createResponseWriter(arg0, arg1, arg2);
	}

	@Override
	public Renderer getRenderer(final String arg0, final String arg1) {
		return super.getRenderer(arg0, arg1);
	}

	@Override
	public ResponseStateManager getResponseStateManager() {
		return super.getResponseStateManager();
	}
}
