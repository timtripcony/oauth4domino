package org.openntf.xsp.oauth.service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.domino.services.ServiceException;
import com.ibm.domino.services.rest.RestServiceEngine;
import com.ibm.xsp.application.ApplicationEx;
import com.ibm.xsp.extlib.component.rest.CustomService;
import com.ibm.xsp.extlib.component.rest.CustomServiceBean;

public abstract class AbstractCustomServiceBean extends CustomServiceBean {
	protected abstract void doDelete(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app)
			throws IOException;

	protected abstract void doGet(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app)
			throws IOException;

	protected abstract void doPost(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app)
			throws IOException;

	protected abstract void doPut(final HttpServletRequest request, final HttpServletResponse response, final ApplicationEx app)
			throws IOException;

	@Override
	public void renderService(final CustomService paramCustomService, final RestServiceEngine engine) throws ServiceException {
		String pathInfo = engine.getHttpRequest().getPathInfo();
		if (pathInfo.contains("?")) {
			pathInfo = pathInfo.substring(0, pathInfo.indexOf("?"));
		}
		try {
			final HttpServletRequest req = engine.getHttpRequest();
			final HttpServletResponse resp = engine.getHttpResponse();
			final String currentMethod = req.getMethod();
			final ApplicationEx app = ApplicationEx.getInstance();
			try {
				if (currentMethod.equals("GET")) {
					doGet(req, resp, app);
				} else if (currentMethod.equals("POST")) {
					doPost(req, resp, app);
				} else if (currentMethod.equals("PUT")) {
					doPut(req, resp, app);
				} else if (currentMethod.equals("DELETE")) {
					doDelete(req, resp, app);
				}
			} catch (final Throwable t) {
				try {
					resp.setStatus(500);
					engine.getHttpResponse().getWriter().write(t.getMessage());
				} catch (final IOException ioException) {
					ioException.printStackTrace();
				}
			}
		} finally {
			try {
				engine.getHttpResponse().getWriter().close();
			} catch (final IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}
