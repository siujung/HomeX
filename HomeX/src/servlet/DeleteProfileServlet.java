package servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

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
import cookie.Manage;

/**
 * Servlet implementation class DeleteProfileServlet
 */
@WebServlet("/DeleteProfileServlet")
public class DeleteProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProfileServlet() {
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
        dispatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		String errorMessage = "";
		boolean hasError = true;
		
        Authentication authentication = (Authentication)session.getAttribute("authentication");
        Administrator administrator = (Administrator)session.getAttribute("administrator");
        
        User user = authentication.getUser();
        int id = user.getId();
        
        Set<Integer> house = user.getHouse();
        Iterator<Integer> itr = house.iterator();
        int houseId;
        while(itr.hasNext()) {
        	houseId = itr.next();
        	if(!administrator.deleteHouse(houseId)) {
            	errorMessage = "Error occured while deleting houses\n";    
            	request.setAttribute("errorMessage", errorMessage);
            	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("profile.jsp");
            	dispatcher.include(request, response);
            }
        	itr.remove();
        }
        
        if(administrator.deleteUser(id)) {
        	hasError = false;
        }
        
        if(hasError) {
        	errorMessage = errorMessage + "Error occured while deleting user\n";
        	request.setAttribute("errorMessage", errorMessage);
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("profile.jsp");
        	dispatcher.include(request, response);
        }
        else {
        	authentication = null;
        	administrator = null;            
            session.setAttribute("authentication", authentication);
            session.setAttribute("administrator", administrator);
            
            Manage.setCookie(request, response, "Username", null);
    		Manage.setCookie(request, response, "Role", "visitor");
    		Manage.setCookie(request, response, "Id", null);
            
    		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("login.html");
    		dispatcher.include(request, response);
        }
        
	}

}
