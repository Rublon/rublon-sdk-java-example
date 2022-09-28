package com.rublon.sdk.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class InitController
 */
public class InitController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Functions func = new Functions();
		String dispatcher = func.appInit(request);

		RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(dispatcher);
		requestDispatcher.forward(request, response);
	}

}
