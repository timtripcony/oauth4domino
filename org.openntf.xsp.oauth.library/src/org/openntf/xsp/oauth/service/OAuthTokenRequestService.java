package org.openntf.xsp.oauth.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.View;
import lotus.domino.ViewEntry;
import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.util.RecycleBin;
import com.ibm.commons.util.io.json.JsonException;
import com.ibm.commons.util.io.json.JsonGenerator;
import com.ibm.commons.util.io.json.JsonJavaFactory;
import com.ibm.xsp.application.ApplicationEx;
import com.ibm.xsp.extlib.util.ExtLibUtil;

public class OAuthTokenRequestService extends AbstractCustomServiceBean {
	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app)
			throws IOException {
		// NOOP
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app) throws IOException {
		issueAccessToken(response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app) throws IOException {
		issueAccessToken(response);
	}

	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app) throws IOException {
		// NOOP
	}

	protected void issueAccessToken(final HttpServletResponse response) throws IOException {
		/*
		 * In theory, this service should behave identically whether the method is GET or POST
		 */
		final FacesContext context = FacesContext.getCurrentInstance();
		final String grantType = ExtLibUtil.readParameter(context, "grant_type");
		final String code = ExtLibUtil.readParameter(context, "code");
		final String redirectUri = ExtLibUtil.readParameter(context, "redirect_uri");
		final String clientId = ExtLibUtil.readParameter(context, "client_id");
		final String clientSecret = ExtLibUtil.readParameter(context, "client_secret");
		final String state = ExtLibUtil.readParameter(context, "state");
		// TODO: validate either here or during initial authorization grant that the requested permissions are defined
		View codeView = null;
		ViewEntry codeEntry = null;
		Document codeDocument = null;
		View clientView = null;
		ViewEntry clientEntry = null;
		Document clientDocument = null;
		boolean valid = false;
		final String token = new BigInteger(130, new SecureRandom()).toString(32);
		if ("authorization_token".equals(grantType)) {
			final RecycleBin trash = new RecycleBin();
			try {
				final Map<String, Object> responseData = new HashMap<String, Object>();
				codeView = OAuthProvider.getCurrentInstance().getRegistry().getView("codes");
				trash.add(codeView);
				codeEntry = codeView.getEntryByKey(code, true);
				if (codeEntry != null) {
					trash.add(codeEntry);
					codeDocument = codeEntry.getDocument();
					trash.add(codeDocument);
					if (codeDocument.getItemValueString("clientId").equals(clientId)) {
						if (codeDocument.getItemValueString("state").equals(state)) {
							if (codeDocument.getItemValueString("redirectURI").equals(redirectUri)) {
								clientView = OAuthProvider.getCurrentInstance().getRegistry().getView("clients");
								trash.add(clientView);
								clientEntry = clientView.getEntryByKey(clientId, true);
								if (clientEntry != null) {
									trash.add(clientEntry);
									clientDocument = clientEntry.getDocument();
									trash.add(clientDocument);
									if (clientDocument.getItemValueString("clientSecret").equals(clientSecret)) {
										valid = storeAccessToken(response, token, clientId,
												clientDocument.getItemValueString("containerId"), codeDocument.getItemValueString("scope"),
												codeDocument.getItemValueString("resourceOwner"));
										if (valid) {
											codeDocument.removePermanently(true);
										}
									} else {
										// can't convert an authorization code to an access token without client credentials
										rejectClientSecret(response);
									}
								} else {
									// not a registered client
									rejectClientId(response);
								}
							} else {
								// request does not include the redirect URI used to generate the authorization code
								rejectRedirectionURI(response);
							}
						} else {
							// request does not include the state identifier used to generate the authorization code
							rejectState(response);
						}
					} else {
						// code is valid, but not for this client
						rejectClientId(response);
					}
				} else {
					// code has expired, has already been used, or was never valid
					rejectCode(response);
				}
				// TODO: refactor the above to be less visually hideous
				if (valid) {
					responseData.put("access_token", token);
					response.setStatus(200);
					try {
						response.getWriter().write(JsonGenerator.toJson(JsonJavaFactory.instance, responseData));
						response.setContentType("application/json");
					} catch (final JsonException e) {
						e.printStackTrace();
					}
				}
			} catch (final NotesException e) {
			} finally {
				trash.empty();
			}
		} else {
			rejectRequest(response, "The grant_type parameter must be 'authorization_token'");
		}
	}

	protected void rejectClientId(final HttpServletResponse response) throws IOException {
		sendError("invalid_client", "A valid client ID was not provided", response);
	}

	protected void rejectClientSecret(final HttpServletResponse response) throws IOException {
		sendError("invalid_client", "Valid client credentials were not provided", response);
	}

	protected void rejectCode(final HttpServletResponse response) throws IOException {
		sendError("invalid_grant", "The authorization code is invalid or has expired", response);
	}

	protected void rejectRedirectionURI(final HttpServletResponse response) throws IOException {
		sendError(
				"invalid_client",
				"To protect against cross-site request forgery, the redirection URI provided when requesting a temporary code must also be provided when requesting an access token",
				response);
	}

	protected void rejectRequest(final HttpServletResponse response, final String description) throws IOException {
		sendError("invalid_request", description, response);
	}

	protected void rejectState(final HttpServletResponse response) throws IOException {
		sendError(
				"invalid_client",
				"To protect against cross-site request forgery, the state provided when requesting a temporary code must also be provided when requesting an access token",
				response);
	}

	protected void sendError(final String code, final String description, final HttpServletResponse response) throws IOException {
		sendError(code, description, response, 400);
	}

	protected void sendError(final String code, final String description, final HttpServletResponse response, final int statusCode)
			throws IOException {
		final Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put("error", code);
		responseData.put("error_description", description);
		try {
			response.getWriter().write(JsonGenerator.toJson(JsonJavaFactory.instance, responseData));
			response.setContentType("application/json");
			response.setStatus(statusCode);
		} catch (final JsonException e) {
			e.printStackTrace();
		}
	}

	protected boolean storeAccessToken(final HttpServletResponse response, final String token, final String clientId,
			final String containerId, final String scope, final String resourceOwner) throws IOException {
		boolean result = false;
		Document tokenDocument = null;
		try {
			tokenDocument = OAuthProvider.getCurrentInstance().getTokenStore().createDocument();
			tokenDocument.replaceItemValue("form", "token");
			tokenDocument.replaceItemValue("id", token);
			tokenDocument.replaceItemValue("clientId", clientId);
			tokenDocument.replaceItemValue("containerId", containerId);
			tokenDocument.replaceItemValue("scope", scope);
			tokenDocument.replaceItemValue("resourceOwner", resourceOwner);
			if (tokenDocument.save()) {
				result = true;
			} else {
			}
		} catch (final Throwable t) {
		}
		if (!result) {
			sendError("server_error",
					"Unable to generate a token. The request appears to have been valid, so this problem may be temporary.", response, 500);
		}
		return result;
	}
}
