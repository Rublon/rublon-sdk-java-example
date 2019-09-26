package com.rublon.sdk.example;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rublon.sdk.core.exception.RublonException.ConfigurationException;
import com.rublon.sdk.twofactor.Rublon;
import com.rublon.sdk.twofactor.RublonCallback;

class Callback extends RublonCallback {

	HttpServletRequest  request  = null;
	HttpServletResponse response = null;

	/**
	 * Callback`s constructor
	 * 
	 * @param Rublon rublon
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @throws ConfigurationException
	 */
	public Callback(Rublon rublon, HttpServletRequest request, HttpServletResponse response)
			throws ConfigurationException {
		super(rublon);
		this.request = request;
		this.response = response;
	}

	/**
	 * Method to get state
	 */
	public String getState() {
		return request.getParameter(PARAMETER_STATE);
	}

	/**
	 * Method to get access token
	 */
	public String getAccessToken() {
		return request.getParameter(PARAMETER_ACCESS_TOKEN);
	}

	/**
	 * Method to handle cancel action
	 */
	protected void handleCancel() {
		try {
			response.sendRedirect("/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to handle error action
	 */
	protected void handleError() {
		try {
			request.setAttribute("error", "Rublon`s callback error");
			response.sendRedirect("/rublonError.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method is running, when user has authenticated
	 */
	protected void userAuthenticated(String userId) {
		HttpSession session = request.getSession();
		session.setAttribute("email", userId);
		request.setAttribute("email", userId);

		RequestDispatcher dispatcher = request.getSession().getServletContext().getRequestDispatcher("/success.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
