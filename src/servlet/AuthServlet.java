package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Authentication.java;
import Administrator.java;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/AuthServlet")
public class NewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    static Authentication authentication;
    static Administrator administrator;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
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

        if(loginSuccess){
            if(authentication.getUser().isAdministrator()){
                Set<Authority> authority = new Set<Authority>();
                authority.add(Authority.house);
                authority.add(Authority.order);
                authority.add(Authority.user);
                authority.add(Authority.message);

                administrator = new Administrator(authority);
            }
            else {
                Set<Authority> authority = new Set<Authority>();
                authority.add(Authority.none);

                administrator = new Administrator(authority);
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/welcome.jsp");
            //Must change to appropriate jsp page upon successful login
            dispatcher.include(request, response);
        }
        else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/loginFailed.jsp");
            //Must change to appropriate jsp page upon unsuccessful login
            dispatcher.include(request, response);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
