package com.rublon.sdk.example;

import org.json.JSONObject;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Functions func = new Functions();
        if (validateConfig(request, response, func)) {
            return;
        }

		String username = request.getParameter("username");
        String email = func.getConfigField("USER_EMAIL");
		String password = request.getParameter("password");

		func.hook_bootstrap(request, response);

		if (password != null && password.equals(func.getConfigField("USER_PASSWORD"))) {
		    JSONObject consumerParams = new JSONObject();
		    consumerParams.put("logoutUrl", func.returnSiteUrl(request));
			consumerParams.put("appVer", func.getConfigField("APP_VERSION"));
			func.authenticate(request, response, username, email, consumerParams);
        } else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
			dispatcher.forward(request, response);
        }
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Functions func = new Functions();
        if (validateConfig(request, response, func)) {
            return;
        }

        HttpSession session = request.getSession(false);
		func.hook_bootstrap(request, response);

		if (session != null && session.getAttribute("username") != null) {
			// session exists
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/success.jsp");
			dispatcher.forward(request, response);
        } else if (request.getParameter("rublon") == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
        }
	}

    private boolean validateConfig(HttpServletRequest request, HttpServletResponse response, Functions func)
            throws ServletException, IOException {
        if (!func.isValidConfig(func.getConfig())) {
            request.setAttribute("configError", true);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            return true;
        } else {
            request.setAttribute("configError", false);
        }
        return false;
    }
}
