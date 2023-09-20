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
@WebServlet("/main")
public class MainServlet extends HttpServlet {
    @EJB
    private IManageStudents manageStudents;
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String key = request.getParameter("key");
        String destination = "/error.html";
        if (name != null && key != null) {
            //boolean auth = name.equals("john") && key.equals("11");
            int res = manageStudents.Login(name,key);
            //response.getWriter().println(res);
            System.out.println("-------------LOGIN----------------------"+request.getSession(true));
            if (res!=-1) {
                request.getSession(true).setAttribute("auth", res);
                //response.getWriter().println(request.getSession(true).getAttribute("auth"));
                destination = "/Secured/display.jsp";
            } else {
                request.getSession(true).invalidate();
            }
        }
        request.getRequestDispatcher(destination).forward(request, response);
    }
}
