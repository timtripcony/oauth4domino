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
package org.openntf.xsp.oauth.implicit;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.faces.FacesException;
import javax.servlet.http.HttpServletResponse;
import lotus.domino.AdministrationProcess;
import lotus.domino.AgentContext;
import lotus.domino.Base;
import lotus.domino.ColorObject;
import lotus.domino.Database;
import lotus.domino.DateRange;
import lotus.domino.DateTime;
import lotus.domino.DbDirectory;
import lotus.domino.Directory;
import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.DxlExporter;
import lotus.domino.DxlImporter;
import lotus.domino.International;
import lotus.domino.Log;
import lotus.domino.Name;
import lotus.domino.Newsletter;
import lotus.domino.NotesCalendar;
import lotus.domino.NotesException;
import lotus.domino.PropertyBroker;
import lotus.domino.Registration;
import lotus.domino.RichTextParagraphStyle;
import lotus.domino.RichTextStyle;
import lotus.domino.Session;
import lotus.domino.Stream;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.model.OAuthAccessToken;
import org.openntf.xsp.oauth.util.OAuthDominoUtils;
import org.openntf.xsp.oauth.util.SudoUtils;
import org.openntf.xsp.oauth.util.XspUtils;
import com.ibm.commons.util.StringUtil;
import com.ibm.xsp.context.FacesContextEx;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class SudoSession implements Session {
	private Session delegate;
	private OAuthAccessToken authorization;

	private SudoSession() {
		// to prevent construction from outside
	}

	public SudoSession(final FacesContextEx context) {
		String userName = "Anonymous";
		final String accessToken = ExtLibUtil.readParameter(context, "accessToken");
		Activator.debug("Access Token: " + accessToken);
		final HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		if (StringUtil.isNotEmpty(accessToken)) {
			View tokenView = null;
			ViewEntry tokenEntry = null;
			Document tokenDocument = null;
			try {
				final OAuthProvider provider = new OAuthProvider();
				tokenView = provider.getTokenStore().getView("tokens");
				tokenEntry = tokenView.getEntryByKey(accessToken, true);
				if (tokenEntry != null) {
					tokenDocument = tokenEntry.getDocument();
					final String resourceOwner = tokenDocument.getItemValueString("resourceOwner");
					if (StringUtil.isNotEmpty(resourceOwner)) {
						userName = resourceOwner;
						setAuthorization(OAuthAccessToken.valueOf(accessToken));
					}
				} else {
					response.setStatus(401);
				}
			} catch (final NotesException e) {
				throw new FacesException(e);
			} finally {
				OAuthDominoUtils.incinerate(tokenDocument, tokenEntry, tokenView);
			}
			// TODO: log authentication if enabled in xsp.properties
		}
		Activator.debug("User name: " + userName);
		setDelegate(SudoUtils.getSessionAs(userName));
	}

	public SudoSession(final Session session) {
		setDelegate(session);
	}

	public AdministrationProcess createAdministrationProcess(final String server) throws NotesException {
		return getDelegate().createAdministrationProcess(server);
	}

	public ColorObject createColorObject() throws NotesException {
		return getDelegate().createColorObject();
	}

	public DateRange createDateRange() throws NotesException {
		return getDelegate().createDateRange();
	}

	public DateRange createDateRange(final Date start, final Date end) throws NotesException {
		return getDelegate().createDateRange(start, end);
	}

	public DateRange createDateRange(final DateTime start, final DateTime end) throws NotesException {
		return getDelegate().createDateRange(start, end);
	}

	public DateTime createDateTime(final Calendar date) throws NotesException {
		return getDelegate().createDateTime(date);
	}

	public DateTime createDateTime(final Date date) throws NotesException {
		return getDelegate().createDateTime(date);
	}

	public DateTime createDateTime(final String date) throws NotesException {
		return getDelegate().createDateTime(date);
	}

	public DxlExporter createDxlExporter() throws NotesException {
		return getDelegate().createDxlExporter();
	}

	public DxlImporter createDxlImporter() throws NotesException {
		return getDelegate().createDxlImporter();
	}

	public Log createLog(final String name) throws NotesException {
		return getDelegate().createLog(name);
	}

	public Name createName(final String name) throws NotesException {
		return getDelegate().createName(name);
	}

	public Name createName(final String name, final String language) throws NotesException {
		return getDelegate().createName(name, language);
	}

	public Newsletter createNewsletter(final DocumentCollection collection) throws NotesException {
		return getDelegate().createNewsletter(collection);
	}

	public Registration createRegistration() throws NotesException {
		return getDelegate().createRegistration();
	}

	public RichTextParagraphStyle createRichTextParagraphStyle() throws NotesException {
		return getDelegate().createRichTextParagraphStyle();
	}

	public RichTextStyle createRichTextStyle() throws NotesException {
		return getDelegate().createRichTextStyle();
	}

	public Stream createStream() throws NotesException {
		return getDelegate().createStream();
	}

	public Vector evaluate(final String formula) throws NotesException {
		return getDelegate().evaluate(formula);
	}

	public Vector evaluate(final String formula, final Document documentContext) throws NotesException {
		return getDelegate().evaluate(formula, documentContext);
	}

	public Vector freeResourceSearch(final DateTime start, final DateTime end, final String arg2, final int arg3, final int arg4)
			throws NotesException {
		return getDelegate().freeResourceSearch(start, end, arg2, arg3, arg4);
	}

	public Vector freeResourceSearch(final DateTime start, final DateTime end, final String arg2, final int arg3, final int arg4,
			final String arg5, final int arg6, final String arg7, final String arg8, final int arg9) throws NotesException {
		return getDelegate().freeResourceSearch(start, end, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	public Vector freeTimeSearch(final DateRange window, final int duration, final Object names, final boolean firstfit)
			throws NotesException {
		return getDelegate().freeTimeSearch(window, duration, names, firstfit);
	}

	public Vector getAddressBooks() throws NotesException {
		return getDelegate().getAddressBooks();
	}

	public AgentContext getAgentContext() throws NotesException {
		return getDelegate().getAgentContext();
	}

	public OAuthAccessToken getAuthorization() {
		return authorization;
	}

	public NotesCalendar getCalendar(final Database database) throws NotesException {
		return getDelegate().getCalendar(database);
	}

	public String getCommonUserName() throws NotesException {
		return getUserNameObject().getCommon();
	}

	public Object getCredentials() throws NotesException {
		return getDelegate().getCredentials();
	}

	public Database getCurrentDatabase() throws NotesException {
		final Database actualCurrent = ExtLibUtil.getCurrentDatabase();
		return getDatabase(actualCurrent.getServer(), actualCurrent.getFilePath());
	}

	public Database getDatabase(final String server, final String path) throws NotesException {
		return getDelegate().getDatabase(server, path);
	}

	public Database getDatabase(final String server, final String path, final boolean open) throws NotesException {
		return getDelegate().getDatabase(server, path, open);
	}

	public DbDirectory getDbDirectory(final String server) throws NotesException {
		return getDelegate().getDbDirectory(server);
	}

	protected Session getDelegate() {
		return delegate;
	}

	public Directory getDirectory() throws NotesException {
		return getDelegate().getDirectory();
	}

	public Directory getDirectory(final String server) throws NotesException {
		return getDelegate().getDirectory(server);
	}

	public String getEffectiveUserName() throws NotesException {
		return getDelegate().getEffectiveUserName();
	}

	public String getEnvironmentString(final String variable) throws NotesException {
		return getDelegate().getEnvironmentString(variable);
	}

	public String getEnvironmentString(final String variable, final boolean isSystem) throws NotesException {
		return getDelegate().getEnvironmentString(variable, isSystem);
	}

	public Object getEnvironmentValue(final String variable) throws NotesException {
		return getDelegate().getEnvironmentValue(variable);
	}

	public Object getEnvironmentValue(final String variable, final boolean isSystem) throws NotesException {
		return getDelegate().getEnvironmentValue(variable, isSystem);
	}

	public String getHttpURL() throws NotesException {
		return getDelegate().getHttpURL();
	}

	public International getInternational() throws NotesException {
		return getDelegate().getInternational();
	}

	public String getNotesVersion() throws NotesException {
		return getDelegate().getNotesVersion();
	}

	public String getOrgDirectoryPath() throws NotesException {
		return getDelegate().getOrgDirectoryPath();
	}

	public String getPlatform() throws NotesException {
		return getDelegate().getPlatform();
	}

	public PropertyBroker getPropertyBroker() throws NotesException {
		return getDelegate().getPropertyBroker();
	}

	public String getServerName() throws NotesException {
		return getDelegate().getServerName();
	}

	public String getSessionToken() throws NotesException {
		return getDelegate().getSessionToken();
	}

	public String getSessionToken(final String hostName) throws NotesException {
		return getDelegate().getSessionToken(hostName);
	}

	public String getTokenStorePath() {
		return XspUtils.getXspApplicationProperty("xsp.oauth.tokenstore", "oauth.nsf");
	}

	public String getURL() throws NotesException {
		return getDelegate().getURL();
	}

	public Database getURLDatabase() throws NotesException {
		return getDelegate().getURLDatabase();
	}

	public Vector getUserGroupNameList() throws NotesException {
		return getDelegate().getUserGroupNameList();
	}

	public String getUserName() throws NotesException {
		return getEffectiveUserName();
	}

	public Vector getUserNameList() throws NotesException {
		return getDelegate().getUserNameList();
	}

	public Name getUserNameObject() throws NotesException {
		return createName(getEffectiveUserName());
	}

	public Document getUserPolicySettings(final String server, final String name, final int type) throws NotesException {
		return getDelegate().getUserPolicySettings(server, name, type);
	}

	public Document getUserPolicySettings(final String server, final String name, final int type, final String explicitPolicy)
			throws NotesException {
		return getDelegate().getUserPolicySettings(server, name, type, explicitPolicy);
	}

	public String hashPassword(final String password) throws NotesException {
		return getDelegate().hashPassword(password);
	}

	public boolean isAnonymous() throws NotesException {
		return "anonymous".equalsIgnoreCase(getUserName());
	}

	public boolean isConvertMime() throws NotesException {
		return getDelegate().isConvertMime();
	}

	public boolean isConvertMIME() throws NotesException {
		return getDelegate().isConvertMIME();
	}

	public boolean isOnServer() throws NotesException {
		return getDelegate().isOnServer();
	}

	public boolean isRestricted() throws NotesException {
		return getDelegate().isRestricted();
	}

	public boolean isTrackMillisecInJavaDates() throws NotesException {
		return getDelegate().isTrackMillisecInJavaDates();
	}

	public boolean isTrustedSession() throws NotesException {
		return getDelegate().isTrustedSession();
	}

	public boolean isValid() {
		return getDelegate().isValid();
	}

	public void recycle() throws NotesException {
		getDelegate().recycle();
	}

	public void recycle(final Vector objects) throws NotesException {
		getDelegate().recycle(objects);
	}

	public boolean resetUserPassword(final String serverName, final String userName, final String password) throws NotesException {
		return getDelegate().resetUserPassword(serverName, userName, password);
	}

	public boolean resetUserPassword(final String serverName, final String userName, final String password, final int downloadCount)
			throws NotesException {
		return getDelegate().resetUserPassword(serverName, userName, password, downloadCount);
	}

	public Base resolve(final String url) throws NotesException {
		return getDelegate().resolve(url);
	}

	public String sendConsoleCommand(final String serverName, final String consoleCommand) throws NotesException {
		return getDelegate().sendConsoleCommand(serverName, consoleCommand);
	}

	public void setAllowLoopBack(final boolean option) throws NotesException {
		getDelegate().setAllowLoopBack(option);
	}

	protected void setAuthorization(final OAuthAccessToken authorization) {
		this.authorization = authorization;
	}

	public void setConvertMime(final boolean option) throws NotesException {
		getDelegate().setConvertMime(option);
	}

	public void setConvertMIME(final boolean option) throws NotesException {
		getDelegate().setConvertMIME(option);
	}

	protected void setDelegate(final Session delegate) {
		this.delegate = delegate;
	}

	public void setEnvironmentVar(final String variable, final Object value) throws NotesException {
		getDelegate().setEnvironmentVar(variable, value);
	}

	public void setEnvironmentVar(final String variable, final Object value, final boolean isSystem) throws NotesException {
		getDelegate().setEnvironmentVar(variable, value, isSystem);
	}

	public void setTrackMillisecInJavaDates(final boolean option) throws NotesException {
		getDelegate().setTrackMillisecInJavaDates(option);
	}

	public boolean verifyPassword(final String password, final String hashedPassword) throws NotesException {
		return getDelegate().verifyPassword(password, hashedPassword);
	}
}
