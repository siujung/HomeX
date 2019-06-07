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

import bean.User;
import control.Administrator;
import control.Authentication;
import control.Authority.Role;
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

		try {
			AuthServlet.authentication = new Authentication(username, password);
			AuthServlet.administrator = new Administrator(Role.user);
			AuthServlet.administrator.setId(user.getId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		session.setAttribute("authentication", AuthServlet.authentication);
		session.setAttribute("administrator", AuthServlet.administrator);
		
		RequestDispatcher Dispatcher = getServletContext().getRequestDispatcher(NextPage);
		Dispatcher.include(request, response);
	}

}
