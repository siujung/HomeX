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
			House housePattern = new House();
			String[] constraintPattern = null;
			String[] servicePattern = null;
			Set<House> houseResult = administrator.getHouse(housePattern);
			Map<Constraint, String> constraintMap = new HashMap<>();
			Map<Service, String> serviceMap = new HashMap<>();

			housePattern.setAddress(request.getParameter("Address"));
			housePattern.setAvailable(request.getParameter("Available") != null
					? request.getParameter("Available").toLowerCase().equals("true")
					: false);
			constraintPattern = request.getParameterValues("Constraint");
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
			servicePattern = request.getParameterValues("Service");
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
					.setHost(request.getParameter("Host") != null ? Integer.valueOf(request.getParameter("Host")) : 0);
			housePattern.setTenant(
					request.getParameter("Tenant") != null ? Integer.valueOf(request.getParameter("Tenant")) : 0);
			housePattern.setId(request.getParameter("Id") != null ? Integer.valueOf(request.getParameter("Id")) : 0);
			housePattern.setTitle(request.getParameter("Title"));
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
