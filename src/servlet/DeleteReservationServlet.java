package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Order;
import bean.User;
import control.Administrator;
import control.Authentication;

/**
 * Servlet implementation class DeleteReservation
 */
@WebServlet("/DeleteReservation")
public class DeleteReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
        
        Set<Integer> order = user.getOrder();
        Iterator<Integer> itr = order.iterator();
        
		String houseId = (String)request.getParameter("houseId");
        int id = Integer.parseInt(houseId);

        while(itr.hasNext()) {
        	try {
				if(Order.get(itr.next()).getHouse() == id) {
					itr.remove();
					hasError = false;
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        if(!administrator.deleteHouse(id)) { //if deletion unsuccessful
        	hasError = true;
        }
        
        if(hasError) { //if there's an error
        	errorMessage = "Error occured while deleting house!\n";
        	request.setAttribute("errorMessage", errorMessage);
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("profile.jsp");
        	dispatcher.forward(request, response);
        }
        else {
        	session.setAttribute("administrator", administrator);
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("profile.jsp");
        	dispatcher.forward(request, response);
        }
	}

}
