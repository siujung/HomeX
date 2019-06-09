package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.House;
import bean.House.Constraint;
import bean.House.Service;
import control.Administrator;
import control.Authentication;
import control.Authority.Role;

/**
 * Servlet implementation class AddHomeServlet
 */
@WebServlet("/AddHomeServlet")
public class AddHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddHomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
        Authentication authentication = (Authentication)session.getAttribute("authentication");

        if(!authentication.isLoggedIn()){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/profile.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		HttpSession session = request.getSession();
        String errorMessage;
        boolean hasError = true;
        
        Authentication authentication = (Authentication)session.getAttribute("authentication");
        Administrator administrator = (Administrator)session.getAttribute("administrator");
        
        if(authentication == null || !authentication.isLoggedIn()){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
   
        User user = authentication.getUser();
        
        boolean isNew = true;        
        House house = new House(isNew);
        
        String address = request.getParameter("address");
        if(address.equals("")) {
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/new.property.jsp");
		   	dispatcher.include(request, response);
        }
        //Services
        String pets = request.getParameter("pets");
        String plants = request.getParameter("plants");
        String clean = request.getParameter("clean");
        
        //Constraints
        String noSmoking = request.getParameter("noSmoking");
        String noNoise = request.getParameter("noNoise");
        String maxChildren = request.getParameter("maxChildren");
        String noPets = request.getParameter("noPets");
              
        house.setHost(user.getId());
        house.setAvailable(true);
        house.setAddress(address);
        
        Map<Service, String> service = new HashMap<>();
        Map<Constraint, String> constraint = new HashMap<>();             
        
        if(!pets.equals("")) {
        	service.put(Service.pet, pets);
        }
        if(!plants.equals("")) {
        	service.put(Service.plant, plants);
        }
        if(!clean.equals("")) {
        	service.put(Service.clean, clean);
        }
        
        if(!noSmoking.equals("")) {
        	constraint.put(Constraint.smoke, noSmoking);
        }
        if(!noNoise.equals("")) {
        	constraint.put(Constraint.noise, noNoise);
        }
        if(!maxChildren.equals("")) {
        	constraint.put(Constraint.kid, maxChildren);
        }
        if(!noPets.equals("")) {
        	constraint.put(Constraint.pet, noPets);
        }
        
        house.setService(service);
        house.setConstraint(constraint);
        
        //administrator.setId(user.getId());
        administrator.setHouse(house);
        authentication.setUser(administrator.getUser(user.getId()));
        //house.set();
        
        //authentication.setUser(user);
        
        AuthServlet.authentication = authentication;
        AuthServlet.administrator = administrator;
        
        session.setAttribute("authentication", authentication);
        session.setAttribute("Administrator", administrator);

        response.sendRedirect(request.getContextPath() + "/profile.jsp");
	}

}
