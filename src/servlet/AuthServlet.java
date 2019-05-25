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
 * Servlet implementation class AuthServlet
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public static Authentication authentication;
    public static Administrator administrator;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        authentication = new Authentication();
        boolean loginSuccess = authentication.isAuthentic(username, password);

        if(loginSuccess){ //if login is successful
            if(authentication.getUser().isAdministrator()){ //if user is administrator
                Set<Authority> authority = new Set<Authority>();
                authority.add(Authority.house);
                authority.add(Authority.order);
                authority.add(Authority.user);
                authority.add(Authority.message);

                administrator = new Administrator(authority);
            }
            else { //if user is not administrator
                Set<Authority> authority = new Set<Authority>();
                authority.add(Authority.none);

                administrator = new Administrator(authority);
            }
            HttpSession session = request.getSession();
            session.setAttribute("authentication", authentication);
            session.setAttribute("administrator", administrator);
            response.sendRedirect(request.getContextPath() + "/profile.jsp");
            dispatcher.include(request, response);
        }
        else {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
            //Must change to appropriate jsp page upon unsuccessful login
            //Or send message such as:
            //  login fail: username and password incorrect
            dispatcher.include(request, response);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
