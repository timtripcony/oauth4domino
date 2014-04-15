package org.acme.xsp.oauth.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lotus.domino.Session;

import org.openntf.xsp.oauth.controller.OAuthProvider;
import org.openntf.xsp.oauth.service.AbstractCustomServiceBean;

import com.ibm.commons.util.io.json.JsonException;
import com.ibm.commons.util.io.json.JsonGenerator;
import com.ibm.commons.util.io.json.JsonJavaFactory;
import com.ibm.xsp.application.ApplicationEx;

public class OAuthTokenTest extends AbstractCustomServiceBean {
	@Override
	protected void doDelete(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app)
			throws IOException {
		// NOOP
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app) throws IOException {
		helloWorld(response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app) throws IOException {
		helloWorld(response);
	}

	@Override
	protected void doPut(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app) throws IOException {
		// NOOP
	}

	protected void helloWorld(final HttpServletResponse response) throws IOException {
		final Map<String, Object> responseData = new HashMap<String, Object>();
		print("Instantiated response Map");
		try {
			Session session = OAuthProvider.getCurrentInstance().getSessionFromAccessToken();
			print("Session is a " + session == null ? " null" : session.getClass().getName());
			final String userName = session.getUserName();
			print("User is " + userName);
			responseData.put("userName", userName);
			if (!"anonymous".equalsIgnoreCase(userName)) {
				response.setStatus(200);
			} else {
				response.setStatus(401);
			}
		} catch (Throwable t) {
			print("Boom: " + t.getClass().getSimpleName() + " " + t.getMessage());
			response.setStatus(500);
		} finally {
		}
		try {
			response.getWriter().write(JsonGenerator.toJson(JsonJavaFactory.instance, responseData));
			response.setContentType("application/json");
		} catch (JsonException e) {
			print("Boom: " + e.getMessage());
			e.printStackTrace();
		}
	}

	protected void print(final String message) {
		System.out.println("OAuthTokenTest: " + message);
	}
}
