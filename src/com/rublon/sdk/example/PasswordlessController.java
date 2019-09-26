package com.rublon.sdk.example;

import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class PasswordlessController
 */
public class PasswordlessController extends HttpServlet {
	private Functions functions;

	public PasswordlessController() throws IOException {
		this.functions = new Functions();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setAttribute("rublonWidget", functions.getRublonWidget());

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/passwordless.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("userEmail") != null ? request.getParameter("userEmail") : "";
		functions.authenticate(request, response, email, new JSONObject(), true);
	}
}
