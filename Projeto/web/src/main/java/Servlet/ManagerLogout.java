package Servlet;
import beans.IManageStudents;
import data.User;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Secured/ManagerLogout")
public class ManagerLogout extends HttpServlet {
    @EJB
    private IManageStudents manageStudents;
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String destination  = "/Managerlogin.html";
        request.getSession(true).invalidate();
        request.getRequestDispatcher(destination).forward(request, response);
    }
}
