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

@WebServlet("/Secured/devolver")
public class DevolverViagem extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idviagem = request.getParameter("idviagem");
        String dest = "/error.html";
        if (idviagem != null) {

            int iduser = (Integer) request.getSession(true).getAttribute("auth");

            int res = manageUsers.devolverBilhete(iduser,Integer.parseInt(idviagem));
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
