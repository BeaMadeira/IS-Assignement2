package Servlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.IManageStudents;
import data.Student;

@WebServlet("/Secured/eliminarconta")
public class EliminarConta extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String password = request.getParameter("password");
        String dest = "/error.html";
        if (password != null) {

            int iduser = (Integer) request.getSession(true).getAttribute("auth");

            int res = 0;
            try {
                request.getSession().invalidate();
                res = manageUsers.deleteUser(iduser,password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            //response.getWriter().println("exe");
            if(res==1) {
                dest = "/Secured/mensagemSucesso.jsp";
            }
        }else{
            dest="/error.html";
        }
        request.getRequestDispatcher(dest).forward(request, response);

    }
}