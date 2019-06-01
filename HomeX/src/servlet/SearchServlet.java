package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.House;
import bean.House.Constraint;
import bean.House.Service;
import control.Administrator;
import control.Authority;
import cookie.Manage;

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
		String redirect = Manage.getCookie(request, "Redirect").getValue();
		String role = Manage.getCookie(request, "Role").getValue();
		Administrator administrator = null;
		String NextPage = "/index.jsp";

		try {
			switch (role) {
			default:
			case "none":
				administrator = new Administrator(Authority.Role.none);
				break;
			case "visitor":
				administrator = new Administrator(Authority.Role.visitor);
				break;
			case "user":
				administrator = new Administrator(Authority.Role.user);
				break;
			case "administrator":
				administrator = new Administrator(Authority.Role.administrator);
				break;
			}
		} catch (ParseException exception) {
			exception.printStackTrace();
		}

		if (redirect.equals("search.result")) {
			House housePattern = new House();
			String[] constraintPattern = null;
			String[] servicePattern = null;
			Set<House> houseResult;
			Map<Constraint, String> constraintMap = new HashMap<>();
			Map<Service, String> serviceMap = new HashMap<>();

			housePattern.setAddress(request.getParameter("address"));
			housePattern.setAvailable(request.getParameter("isAvailable") != null
					? request.getParameter("isAvailable").toLowerCase().equals("true")
					: false);
			constraintPattern = request.getParameterValues("constraint");
			if (constraintPattern != null) {
				for (String constraint : constraintPattern) {
					if (constraint.equals("kid"))
						constraintMap.put(Constraint.kid, "true");
					else if (constraint.equals("noise"))
						constraintMap.put(Constraint.noise, "true");
					else if (constraint.equals("pet"))
						constraintMap.put(Constraint.pet, "true");
					else if (constraint.equals("smoke"))
						constraintMap.put(Constraint.smoke, "true");
					else if (constraint.equals("other"))
						constraintMap.put(Constraint.other, "true");
				}
			}
			housePattern.setConstraint(constraintMap);
			servicePattern = request.getParameterValues("service");
			if (servicePattern != null) {
				for (String service : servicePattern) {
					if (service.equals("clean"))
						serviceMap.put(Service.clean, "true");
					else if (service.equals("pet"))
						serviceMap.put(Service.pet, "true");
					else if (service.equals("plant"))
						serviceMap.put(Service.plant, "true");
					else if (service.equals("other"))
						serviceMap.put(Service.other, "true");
				}
			}
			housePattern.setService(serviceMap);
			housePattern
					.setHost(request.getParameter("host") != null ? Integer.valueOf(request.getParameter("host")) : 0);
			housePattern.setTenant(
					request.getParameter("tenant") != null ? Integer.valueOf(request.getParameter("tenant")) : 0);
			housePattern.setId(request.getParameter("id") != null ? Integer.valueOf(request.getParameter("id")) : 0);
			housePattern.setTitle(request.getParameter("title"));
			houseResult = administrator.getHouse(housePattern);
			request.setAttribute("houseResult", houseResult);
			NextPage = "/search.result.jsp";
		}

		RequestDispatcher Dispatcher = getServletContext().getRequestDispatcher(NextPage);
		Dispatcher.include(request, response);
	}

}
