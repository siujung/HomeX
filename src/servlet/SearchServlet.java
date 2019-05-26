package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.House;
import control.Administrator;
import control.Authority;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String redirect = request.getParameter("redirect");
		String role = request.getParameter("role");
		Administrator administrator = null;
		String NextPage = "/index.html";

		try {
			switch (role) {
			default:
			case "none":
				administrator = new Administrator(new Authority(Authority.Role.none));
				break;
			case "visitor":
				administrator = new Administrator(new Authority(Authority.Role.visitor));
			case "user":
				administrator = new Administrator(new Authority(Authority.Role.user));
				break;
			case "administrator":
				administrator = new Administrator(new Authority(Authority.Role.administrator));
				break;
			}
		} catch (ParseException exception) {
			System.out.println(exception);
		}

		if (redirect.equals("houseList")) {
			House housePattern = (House) request.getAttribute("housePattern");
			Set<House> houseResult = administrator.getHouse(housePattern);

			request.setAttribute("houseResult", houseResult);
			NextPage = "/search.result.html";
		} else if (redirect.equals("houseView")) {
			int houseId = (int) request.getAttribute("houseId");
			House houseRedirect = administrator.getHouse(houseId);

			request.setAttribute("houseRedirect", houseRedirect);
			NextPage = "/view.property.html";
		}

		RequestDispatcher Dispatcher = getServletContext().getRequestDispatcher(NextPage);
		Dispatcher.include(request, response);
	}

}
