package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import control.*;
import bean.*;

/**
 * Servlet implementation class ReserveServlet
 */
@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveServlet() {
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
            response.senRedirect(request.getContextPath() + "/login");
            return;
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/search.result.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        //doGet(request, response);

        int id = 0;
        //Need a way to make a unique ID for each order

        String houseId = (String)request.getParameter("house");
        int house = Int.parseInt(houseId);

        String hostId = (String)request.getParameter("host");
        int host = Int.parseInt(hostId);

        HttpSession session = request.getSession();
        Authentication authentication = (Authentication)session.getAttribute("authentication");
        int tenant = authentication.getUser().getId();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = dateFormat.parse((String)request.getParameter("start"));
        Date end = dateFormat.parse((String)request.getParameter("end"));
        
        Order order = new Order();
        order.setId(id);
        order.setHouse(house);
        order.setHost(host);
        order.setTenant(tenant);
        order.setStart(start);
        order.setEnd(end);

        Order.set(order);

        response.sendRedirect(request.getContextPath() + "/profile.jsp");
    }
}
