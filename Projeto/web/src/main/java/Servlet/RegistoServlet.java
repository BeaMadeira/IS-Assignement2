package Servlet;
import java.util.logging.Logger;
import beans.IManageStudents;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
    @WebServlet("/registo")
    public class RegistoServlet extends HttpServlet {
        private final Logger LOGGER = Logger.getLogger(getClass().getName());
        @EJB
        private IManageStudents manageStudents;
        private static final long serialVersionUID = 1L;
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String destination = "/error.html";
            if (name != null && email != null && password != null) {
                manageStudents.addUser(name,email,password);
                    //request.getSession(true).setAttribute("auth", name);
                    destination = "/login.html";
               /* } else {
                    request.getSession(true).removeAttribute("auth");*/

            }
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }