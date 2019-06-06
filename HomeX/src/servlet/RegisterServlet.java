package servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import cookie.Manage;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String NextPage = "/index.jsp";
		User user = null;
		String username = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			if (null == User.get(username)) {
				Manage.setCookie(request, response, "Username", username);
				Manage.setCookie(request, response, "Role", "user");

				user = new User(true);
				user.setUsername(username);
				user.setEmail(username);
				user.setPassword(password);
				user.set();
			}
		} catch (ParseException exception) {
			exception.printStackTrace();
		}

		RequestDispatcher Dispatcher = getServletContext().getRequestDispatcher(NextPage);
		Dispatcher.include(request, response);
	}

}
