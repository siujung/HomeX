package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.*;
import bean.*;

/**
 * Servlet implementation class EditHouseServlet
 */
@WebServlet("/EditHouseServlet")
public class EditHouseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditHouseServlet() {
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
            //Must change redirect page to proper login page name
            return;
        }

        String houseId = (String)request.getParameter("houseId");
        int id = Integer.parseInt(houseId);

        Administrator administrator = (Administrator)session.getAttribute("administrator");
        House house = administrator.getHouse(id);
        
        if(house != null){
            response.sendRedirect(request.getServletPath() + "/view.property.jsp");
            return;
        }

        request.setAttribute("houseId", id);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/profile.jsp");
        //Must change profile view page name
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        //doGet(request, response);        

        HttpSession session = request.getSession();
        //Authentication authentication = (Authentication)session.getAttribute("authentication");
        //User user = authentication.getUser();

        Authentication authentication = (Authentication)session.getAttribute("authentication");
        Administrator administrator = (Administrator)session.getAttribute("administrator");

		if(authentication == null || !authentication.isLoggedIn()){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
		
        String houseId = (String)request.getParameter("houseId");
        int id = Integer.parseInt(houseId);

        String available = (String)request.getParameter("available");
        boolean isAvailable;
        if(available.equals("Y")){
            isAvailable = true;
        }
        else {
            isAvailable = false;
        }

        //We need a way to get Constraints and Services

        String address = (String)request.getParameter("address");
        String title = (String)request.getParameter("title");

        //Problem with data structure?
        //Maybe it will be easier if every user has a field through which he can access his houses

        House house = administrator.getHouse(id);

        house.setAvailable(isAvailable);
        house.setAddress(address);
        house.setTitle(title);

        House.set(house);

        response.sendRedirect(request.getContextPath() + "/profile.jsp");
	}
}
