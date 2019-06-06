package servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.*;
import control.Authority.Role;

@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static Authentication authentication;
	public static Administrator administrator;

	public AuthServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get method is for Log out
		authentication = null;
		administrator = null;
		HttpSession session = request.getSession();
		session.setAttribute("authentication", authentication);
		session.setAttribute("administrator", administrator);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("login.html");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Post method is for Log in
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		boolean loginSuccess = false;
		try {
			authentication = new Authentication(username, password);
			loginSuccess = authentication.isAuthentic();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (loginSuccess) { // if login is successful
			if (authentication.getUser().isAdministrator()) { // if user is administrator

				try {
					administrator = new Administrator(Role.administrator);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else { // if user is not administrator
				try {
					administrator = new Administrator(Role.user);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			HttpSession session = request.getSession();
			session.setAttribute("authentication", authentication);
			session.setAttribute("administrator", administrator);
			response.sendRedirect(request.getContextPath() + "/profile.jsp");
			// dispatcher.include(request, response);
		} else {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.html");
			// Must change to appropriate jsp page upon unsuccessful login
			// Or send message such as:
			// login fail: username and password incorrect
			dispatcher.forward(request, response);
		}
	}

}
