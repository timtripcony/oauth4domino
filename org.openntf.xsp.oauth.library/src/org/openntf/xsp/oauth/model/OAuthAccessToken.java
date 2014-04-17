package org.openntf.xsp.oauth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import lotus.domino.Database;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import org.openntf.xsp.oauth.Activator;
import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.util.RecycleBin;
import com.ibm.commons.util.StringUtil;

public class OAuthAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static boolean _debug = Activator._debug;

	public static OAuthAccessToken valueOf(final String token) {
		Activator.debug("Factory creating token metadata object for token " + token);
		final OAuthAccessToken result = new OAuthAccessToken();
		result.setToken(token);
		final OAuthProvider provider = new OAuthProvider();
		final Database tokenStore = provider.getTokenStore();
		final Database registry = provider.getRegistry();
		final RecycleBin trash = new RecycleBin();
		try {
			final View tokens = tokenStore.getView("tokens");
			if (tokens != null) {
				trash.add(tokens);
				final ViewEntry tokenEntry = tokens.getEntryByKey(token);
				if (tokenEntry != null) {
					trash.add(tokenEntry);
					final Vector<?> tokenColumnValues = tokenEntry.getColumnValues();
					trash.add(tokenColumnValues);
					final String clientId = tokenColumnValues.get(1).toString();
					Activator.debug("Client ID: " + clientId);
					final String userName = tokenColumnValues.get(3).toString();
					Activator.debug("Acting on behalf of " + userName);
					final OAuthResourceOwner owner = new OAuthResourceOwner();
					owner.setDistinguishedName(userName);
					result.setResourceOwner(owner);
					final View clients = registry.getView("clients");
					if (clients != null) {
						trash.add(clients);
						final ViewEntry clientEntry = clients.getEntryByKey(clientId, true);
						if (clientEntry != null) {
							trash.add(clientEntry);
							final Vector<?> clientColumnValues = clientEntry.getColumnValues();
							final OAuthClient client = new OAuthClient();
							client.setId(clientColumnValues.get(0).toString());
							client.setDisplayName(clientColumnValues.get(1).toString());
							Activator.debug("Client name: " + client.getDisplayName());
							client.setDescription(clientColumnValues.get(2).toString());
							client.setContainerId(clientColumnValues.get(3).toString());
							result.setClient(client);
							final View containers = registry.getView("containers");
							if (containers != null) {
								trash.add(containers);
								final ViewEntry containerEntry = containers.getEntryByKey(client.getContainerId(), true);
								{
									if (containerEntry != null) {
										trash.add(containerEntry);
										final Vector<?> containerColumnValues = containerEntry.getColumnValues();
										trash.add(containerColumnValues);
										final OAuthResourceContainer container = new OAuthResourceContainer();
										container.setId(containerColumnValues.get(0).toString());
										container.setDisplayName(containerColumnValues.get(1).toString());
										Activator.debug("Accessing resource container " + container.getDisplayName());
										client.setDescription(clientColumnValues.get(2).toString());
										result.setResourceContainer(container);
										final String scope = tokenColumnValues.get(2).toString();
										Activator.debug("Token scope: " + scope);
										final Set<OAuthPermission> permissions = new HashSet<OAuthPermission>();
										final View permissionKeys = registry.getView("permissionKeys");
										if (permissionKeys != null) {
											trash.add(permissionKeys);
											final String[] splitScope = scope.split(",");
											for (final String permissionId : splitScope) {
												if (StringUtil.isNotEmpty(permissionId)) {
													final ViewEntry permissionEntry = permissionKeys.getEntryByKey(container.getId() + "~"
															+ permissionId, true);
													if (permissionEntry != null) {
														trash.add(permissionEntry);
														final Vector<?> permissionColumnValues = permissionEntry.getColumnValues();
														trash.add(permissionColumnValues);
														final OAuthPermission permission = new OAuthPermission();
														permission.setId(permissionId);
														permission.setDisplayName(permissionColumnValues.get(2).toString());
														permission.setDescription(permissionColumnValues.get(3).toString());
														permissions.add(permission);
													} else {
														Activator.debug(String.format("Permission entry %s~%s not found",
																container.getId(), permissionId));
													}
												}
											}
										}
										permissions.add(OAuthPermission.basicProfileAccess());
										result.setPermissions(permissions);
									}
								}
							}
						}
					}
				}
			}
		} catch (final Throwable t) {
			if (_debug) {
				t.printStackTrace();
			}
		} finally {
			trash.empty();
		}
		return result;
	}

	private OAuthProvider provider;
	private OAuthClient client;
	private OAuthResourceContainer resourceContainer;
	private OAuthResourceOwner resourceOwner;
	private Set<OAuthPermission> permissions;
	private String token;

	protected OAuthAccessToken() {
	}

	public OAuthClient getClient() {
		return client;
	}

	public Set<OAuthPermission> getPermissions() {
		return permissions;
	}

	protected OAuthProvider getProvider() {
		if (provider == null) {
			provider = new OAuthProvider();
		}
		return provider;
	}

	public OAuthResourceContainer getResourceContainer() {
		return resourceContainer;
	}

	public OAuthResourceOwner getResourceOwner() {
		return resourceOwner;
	}

	protected String getToken() {
		return token;
	}

	public boolean isPermitted(final OAuthPermission permission) {
		return getPermissions().contains(permission);
	}

	public boolean isPermitted(final String permissionId) {
		boolean result = false;
		for (final OAuthPermission permission : getPermissions()) {
			if (permission.getId().equalsIgnoreCase(permissionId)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isValid() {
		return getResourceOwner() != null;
	}

	protected void setClient(final OAuthClient client) {
		this.client = client;
	}

	protected void setPermissions(final Set<OAuthPermission> permissions) {
		this.permissions = permissions;
	}

	protected void setProvider(final OAuthProvider provider) {
		this.provider = provider;
	}

	protected void setResourceContainer(final OAuthResourceContainer resourceContainer) {
		this.resourceContainer = resourceContainer;
	}

	protected void setResourceOwner(final OAuthResourceOwner resourceOwner) {
		this.resourceOwner = resourceOwner;
	}

	protected void setToken(final String token) {
		this.token = token;
	}
}
