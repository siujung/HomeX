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
 * Servlet implementation class ContactServlet
 */
@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public static Authentication authentication;
    public static Administrator administrator;
    public static Communication communication;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactServlet() {
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
            //Must change redirect page to proper login page name
            return;
        }

        String contactId = (String)request.getParameter("contactId");
        int id = Int.parseInt(contactId);

        Communication communication = (Communication)session.getAttribute("communication");
        Contact contact = administrator.getContact(id);
        
        if(contact != null){
            response.sendRedirect(request.ServletPath() + "/view.property.jsp")
            return;
        }

        request.setAttribute("contactId", id);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/chat.jsp");
        //Must change profile view page name
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // TODO Auto-generated method stub
		 String contanctId = (String)request.getParameter("contactId");
	     int id = Int.parseInt(contactId);

	     
	     //Way to get Constraints and Services
	     int from = (int)request.getParameter("from")
	     int to = (int)request.getParameter("to")
	     String content = (String)request.getParameter("content");
	     Timestamp time = (Timestamp)request.getParameter("time");

	     HttpSession session = request.getSession();
	     Authentication authentication = (Authentication)session.getAttribute("authentication");
	     User user = authentication.getUser();
	     
	     Communication communication = (Commmunication))session.getAttribute("communication");
	     //Problem with data structure?
	     //Maybe it will be easier if every user has a field through which he can access his houses

	     Contact contact = communication.getCommunication(id);

	     contact.setFrom(from);
	     contact.setTo(to);
	     contact.setTime(time);
	     contact.setContent(content);

	     Contact.set(contact);

	     response.sendRedirect(request.getContextPath() + "/chat.jsp");
		}

	}


