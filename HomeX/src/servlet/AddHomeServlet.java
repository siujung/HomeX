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
import bean.House;
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
            response.sendRedirect(request.getContextPath() + "/login");
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
   
        User user = authentication.getUser();
        
        boolean isNew = true;        
        House house = new House(isNew);
        
        String address = request.getParameter("address");
        house.setHost(user.getId());
        house.setAvailable(true);
        house.setAddress(address);
        //administrator.setId(user.getId());
        //administrator.setHouse(house);
        house.set();
        
        user.getHouse().add(house.getId());
        authentication.setUser(user);
        
        try {
			administrator = new Administrator(Role.user);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        AuthServlet.authentication = authentication;
        AuthServlet.administrator = administrator;
        
        session.setAttribute("authentication", authentication);
        session.setAttribute("Administrator", administrator);

        response.sendRedirect(request.getContextPath() + "/profile.jsp");
	}

}
