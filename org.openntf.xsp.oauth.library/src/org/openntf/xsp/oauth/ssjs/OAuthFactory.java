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
package org.openntf.xsp.oauth.ssjs;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.ibm.commons.util.io.StreamUtil;
import com.ibm.jscript.ILibraryFactory;
import com.ibm.jscript.InterpretException;

public class OAuthFactory implements ILibraryFactory {
	private final static Map<String, String> _libs = new HashMap<String, String>();
	static {
		_libs.put("OAuth.jss", "/org/openntf/xsp/oauth/ssjs/OAuth.jss");
	}

	public String loadLibrary(final String libName) throws InterpretException {
		String result = null;
		if (_libs.containsKey(libName)) {
			InputStream is = null;
			try {
				is = getClass().getClassLoader().getResourceAsStream(_libs.get(libName));
				result = StreamUtil.readString(is);
			} catch (final IOException ex) {
				throw new InterpretException(ex);
			} finally {
				try {
					is.close();
				} catch (final Exception e) {
				}
			}
		}
		return result;
	}
}
