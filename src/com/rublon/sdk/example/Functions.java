package com.rublon.sdk.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rublon.sdk.core.RublonHttpCallback.CallbackException;
import com.rublon.sdk.core.exception.APIException;
import com.rublon.sdk.core.exception.APIException.UserBypassedException;
import com.rublon.sdk.core.exception.RublonException;
import com.rublon.sdk.core.exception.RublonException.ConfigurationException;
import com.rublon.sdk.twofactor.Rublon;
import com.rublon.sdk.twofactor.RublonCallback;
import org.json.JSONObject;

public class Functions {

	private final String configFileName = "config.cfg";
	private Map<String, String> cfg;
	private Rublon rublon;

	private final String NOTICE  = "alert-primary";
	private final String SUCCESS = "alert-success";
	private final String ERROR   = "alert-danger";
	private final String WARNING = "alert-warning";

	public Functions() throws IOException {
		this.cfg = getConfig();
		this.rublon = new Rublon(
			cfg.get("RUBLON_SYSTEM_TOKEN"),
			cfg.get("RUBLON_SECRET_KEY"),
			cfg.get("RUBLON_API_SERVER")
		);
	}

	/**
	 * Hook bootstrap
	 *
	 * @param request
	 * @param response
	 */
	public void hook_bootstrap(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("rublon") != null && request.getParameter("rublon").equals("callback")) {
			try {
				RublonCallback callback = new Callback(rublon, request, response);
				callback.call();
			} catch (CallbackException | ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Hook before login
	 *
	 * @param request
	 * @param response
	 * @param userName
	 * @param userEmail
	 * @param consumerParams
	 * @throws IOException
	 * @throws ServletException
	 */
	public void authenticate(HttpServletRequest request, HttpServletResponse response, String userName, String userEmail, JSONObject consumerParams)
			throws IOException, ServletException {
		// Make sure that the user is not logged-in
		request.getSession().invalidate();

		/* initialize the Rublon authentication */
		try {
			String url = null;
			try {
				String callback = generateCallbackUrl(request);
				url = rublon.auth(callback, userName, userEmail, consumerParams);
			} catch(UserBypassedException e) {
				String dispatcher;
				if (userName != null) {
					HttpSession session = request.getSession();
					session.setAttribute("flashMsgType", NOTICE);
					session.setAttribute("flashMsgText", "User bypassed");
					session.setAttribute("username", userName);
					session.setAttribute("email", userEmail);
					dispatcher = "/success.jsp";
				} else {
					dispatcher = "/index.jsp";
				}

				RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(dispatcher);
				requestDispatcher.forward(request, response);
				return;
			} catch(APIException e) {
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
				RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/rublonError.jsp");
				dispatcher.forward(request, response);
				return;
			}

			if (url != null) {
				// Redirect the user's browser to the Rublon's server
				// to authenticate by Rublon
				try {
					response.sendRedirect(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// User is not protected by Rublon, so bypass the second factor

		} catch (RublonException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send APP init request
	 * @param request
	 */
	public String appInit(HttpServletRequest request) {
		String dispatcher = "/init.jsp";

		try {
			rublon.init(cfg.get("APP_VERSION"));
			request.setAttribute("appInit", "OK");
		} catch(RublonException e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			dispatcher = "/rublonError.jsp";
		}

		return dispatcher;
	}


	/**
	 * Method for generating callback url
	 *
	 * @param request
	 * @return String
	 */
	private String generateCallbackUrl(HttpServletRequest request) {
		return this.returnSiteUrl(request) + "/login?rublon=callback";
	}

	/**
	 * Method for returning site url
	 *
	 * @param request
	 * @return String
	 */
	public String returnSiteUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	/**
	 * Method to return config from cfg file
	 * 
	 * @return Map<String, String> config
	 * @throws IOException
	 */
	public Map<String, String> getConfig() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(this.configFileName).getFile());
		String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		String[] lines = content.split("\n");
		Map<String, String> config = new HashMap<>();

		for (String line : lines) {
			String[] elements = line.trim().split("=");
			
			if(elements.length == 2) {
				elements[0] = elements[0].trim();
				elements[1] = elements[1].trim();

				if (elements[0] != null && elements[1] != null) {
					config.put(elements[0], elements[1]);
				}
			}
		}

		return config;
	}

	/**
	 * Method to validate config
	 * 
	 * @param cfg
	 * @return boolean
	 */
	public boolean isValidConfig(Map<String, String> cfg) {
		return cfg.get("RUBLON_SYSTEM_TOKEN") != null && cfg.get("RUBLON_SECRET_KEY") != null
				&& cfg.get("RUBLON_API_SERVER") != null && cfg.get("USER_PASSWORD") != null;
	}

	/**
	 * Method to get config field by name
	 * 
	 * @param name field name
	 * @return String
	 * @throws IOException
	 */
	public String getConfigField(String name) throws IOException {
		Functions func = new Functions();
		Map<String, String> cfg = func.getConfig();

		return cfg.get(name);
	}
}
