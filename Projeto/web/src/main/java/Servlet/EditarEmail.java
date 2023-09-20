package Servlet;
import java.io.IOException;
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

@WebServlet("/Secured/editaremail")
public class EditarEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter("email");
        String dest = "/error.html";
        if (email != null) {
            System.out.println("------------------EMAIL-----------------------------"+request.getSession(true));
            response.getWriter().println(request.getSession(true));
            int iduser = (Integer) request.getSession(true).getAttribute("auth");
            manageUsers.editEmail(email, iduser);
            response.getWriter().println("exe");
            dest = "/Secured/mensagemSucesso.jsp";
        }else{
            dest="/error.html";
        }
        request.getRequestDispatcher(dest).forward(request, response);

    }
}
