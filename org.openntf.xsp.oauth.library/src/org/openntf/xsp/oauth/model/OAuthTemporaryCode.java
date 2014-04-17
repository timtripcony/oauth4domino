package org.openntf.xsp.oauth.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.util.OAuthDominoUtils;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class OAuthTemporaryCode implements Serializable {
	private static final long serialVersionUID = 1L;

	public static OAuthTemporaryCode getCurrentInstance() {
		return (OAuthTemporaryCode) ExtLibUtil.resolveVariable(FacesContext.getCurrentInstance(), "OAuthTemporaryCode");
	}

	private OAuthClient client;
	private String clientId;
	private String code;
	private String containerId;
	private String redirectURI;
	private List<OAuthPermission> requestedPermissions;
	private OAuthResourceContainer resourceContainer;
	private OAuthResourceOwner resourceOwner;
	private String responseType;
	private String scope;
	private String state;

	public OAuthTemporaryCode() {
		final FacesContext context = FacesContext.getCurrentInstance();
		setClientId(ExtLibUtil.readParameter(context, "client_id"));
		setRedirectURI(ExtLibUtil.readParameter(context, "redirect_uri"));
		setResponseType(ExtLibUtil.readParameter(context, "response_type"));
		setScope(ExtLibUtil.readParameter(context, "scope"));
		setState(ExtLibUtil.readParameter(context, "state"));
	}

	public OAuthClient getClient() {
		if (client == null) {
			View clients = null;
			ViewEntry clientEntry = null;
			Vector<?> columnValues = null;
			try {
				clients = OAuthProvider.getCurrentInstance().getRegistry().getView("clients");
				clientEntry = clients.getEntryByKey(getClientId());
				columnValues = clientEntry.getColumnValues();
				client = new OAuthClient();
				client.setId(columnValues.get(0).toString());
				client.setDisplayName(columnValues.get(1).toString());
				client.setContainerId(columnValues.get(3).toString());
			} catch (final Throwable t) {
				t.printStackTrace();
			} finally {
				OAuthDominoUtils.incinerate(columnValues, clientEntry, clients);
			}
		}
		return client;
	}

	public String getClientId() {
		return clientId;
	}

	public String getCode() {
		if (code == null) {
			code = new BigInteger(130, new SecureRandom()).toString(32);
		}
		return code;
	}

	public String getContainerId() {
		if (containerId == null) {
			containerId = getClient().getContainerId();
		}
		return containerId;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public List<OAuthPermission> getRequestedPermissions() {
		if (requestedPermissions == null) {
			requestedPermissions = new ArrayList<OAuthPermission>();
			final String[] aliases = getScope().split(",");
			View permissionView = null;
			ViewEntry entry = null;
			Vector<?> columnValues = null;
			try {
				permissionView = OAuthProvider.getCurrentInstance().getRegistry().getView("permissionKeys");
				for (final String alias : aliases) {
					entry = permissionView.getEntryByKey(getContainerId() + "~" + alias, true);
					if (entry != null) {
						columnValues = entry.getColumnValues();
						final OAuthPermission permission = new OAuthPermission();
						permission.setId(alias);
						permission.setDisplayName(columnValues.get(2).toString());
						permission.setDescription(columnValues.get(3).toString());
						requestedPermissions.add(permission);
					}
					OAuthDominoUtils.incinerate(columnValues);
				}
			} catch (final Throwable t) {
				t.printStackTrace();
			} finally {
				OAuthDominoUtils.incinerate(permissionView);
			}
		}
		return requestedPermissions;
	}

	public OAuthResourceContainer getResourceContainer() {
		if (resourceContainer == null) {
			View containers = null;
			ViewEntry containerEntry = null;
			Vector<?> columnValues = null;
			try {
				containers = OAuthProvider.getCurrentInstance().getRegistry().getView("containers");
				containerEntry = containers.getEntryByKey(getContainerId(), true);
				columnValues = containerEntry.getColumnValues();
				resourceContainer = new OAuthResourceContainer();
				resourceContainer.setId(getContainerId());
				resourceContainer.setDisplayName(columnValues.get(1).toString());
			} catch (final Throwable t) {
				t.printStackTrace();
			} finally {
				OAuthDominoUtils.incinerate(columnValues, containerEntry, containers);
			}
		}
		return resourceContainer;
	}

	public OAuthResourceOwner getResourceOwner() {
		return resourceOwner;
	}

	public String getResponseType() {
		return responseType;
	}

	public String getScope() {
		return scope;
	}

	public String getState() {
		return state;
	}

	public void setClient(final OAuthClient client) {
		this.client = client;
	}

	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setContainerId(final String containerId) {
		this.containerId = containerId;
	}

	public void setRedirectURI(final String redirectURI) {
		this.redirectURI = redirectURI;
	}

	public void setRequestedPermissions(final List<OAuthPermission> requestedPermissions) {
		this.requestedPermissions = requestedPermissions;
	}

	public void setResourceContainer(final OAuthResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}

	public void setResourceOwner(final OAuthResourceOwner resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	public void setResponseType(final String responseType) {
		this.responseType = responseType;
	}

	public void setScope(final String scope) {
		this.scope = scope;
	}

	public void setState(final String state) {
		this.state = state;
	}
}
