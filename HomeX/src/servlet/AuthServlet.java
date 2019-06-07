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
import cookie.Manage;

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
		// Get method is for Log out (supposedly)
		authentication = null;
		administrator = null;
		
		HttpSession session = request.getSession();
		session.setAttribute("authentication", authentication);
		session.setAttribute("administrator", administrator);
		
		Manage.setCookie(request, response, "Username", "null");
		Manage.setCookie(request, response, "Role", "visitor");
		Manage.setCookie(request, response, "Id", "null");
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Post method is for Log in
		String username = request.getParameter("useremail");
		String password = request.getParameter("password");

		boolean loginSuccess = true;
		try {
			authentication = new Authentication(username, password);
			if(authentication.getUser() == null)
				loginSuccess = false;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (loginSuccess) { // if login is successful
			Manage.setCookie(request, response, "Username", username);
			Manage.setCookie(request, response, "Id", Integer.toString(authentication.getUser().getId()));
			if (authentication.getUser().isAdministrator()) { // if user is administrator

				try {
					administrator = new Administrator(Role.administrator);
					Manage.setCookie(request, response, "Role", "administrator");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else { // if user is not administrator
				try {
					administrator = new Administrator(Role.user);
					Manage.setCookie(request, response, "Role", "user");
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			administrator.setId(authentication.getUser().getId());
			HttpSession session = request.getSession();
			session.setAttribute("authentication", authentication);
			session.setAttribute("administrator", administrator);
			response.sendRedirect(request.getContextPath() + "/profile.jsp");
			// dispatcher.include(request, response);
		} else {
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
			// Must change to appropriate jsp page upon unsuccessful login
			// Or send message such as:
			// login fail: username and password incorrect
			dispatcher.include(request, response);
		}
	}

}
