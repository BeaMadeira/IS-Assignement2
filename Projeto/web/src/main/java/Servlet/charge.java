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

@WebServlet("/Secured/charge")
public class charge extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String dinheiro = request.getParameter("dinheiro");
        String dest = "/error.html";
        if (dinheiro != null) {
                int iduser = (Integer) request.getSession(true).getAttribute("auth");
                manageUsers.chargeWallet(iduser,Integer.parseInt(dinheiro));
                //request.getSession(true).setAttribute("auth", name);
                dest = "/Secured/mensagemSucesso.jsp";
                /*} else {
                    request.getSession(true).removeAttribute("auth");*/
            }
        request.getRequestDispatcher(dest).forward(request, response);
    }
}
