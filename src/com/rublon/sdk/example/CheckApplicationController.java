package com.rublon.sdk.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CheckApplicationController
 */
public class CheckApplicationController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Functions func = new Functions();
		String dispatcher = func.checkApplication(request);

		RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(dispatcher);
		requestDispatcher.forward(request, response);
	}

}
