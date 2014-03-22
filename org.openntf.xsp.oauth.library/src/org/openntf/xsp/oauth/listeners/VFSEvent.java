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
package org.openntf.xsp.oauth.listeners;

import org.openntf.xsp.oauth.Activator;
import com.ibm.commons.vfs.VFS;
import com.ibm.commons.vfs.VFSEventAdapter;

public class VFSEvent extends VFSEventAdapter {
	private final static boolean _debug = Activator._debug;
	static {
		if (_debug) {
			System.out.println(VFSEvent.class.getName() + " loaded");
		}
	}

	public VFSEvent() {
		if (_debug) {
			System.out.println(getClass().getName() + " created");
		}
	}

	@Override
	public void onBeginRefresh(final VFS paramVFS) {
		if (_debug) {
			System.out.println(getClass().getName() + " refresh beginning");
		}
		super.onBeginRefresh(paramVFS);
	}

	@Override
	public void onEndRefresh(final VFS paramVFS) {
		super.onEndRefresh(paramVFS);
		if (_debug) {
			System.out.println(getClass().getName() + " refresh completed");
		}
	}
}
