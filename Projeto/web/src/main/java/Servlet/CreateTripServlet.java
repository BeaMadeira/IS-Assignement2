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

@WebServlet("/Secured/createTrip")
public class CreateTripServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String departurePoint = request.getParameter("departurePoint");
        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departureDate");
        String capacity = request.getParameter("capacity");
        String price = request.getParameter("price");
        String dest = "/error.html";
        if(departurePoint!=null && destination!=null && departureDate!=null &&  capacity!=null && price!=null) {
            int iduser = (Integer) request.getSession(true).getAttribute("auth");

            try {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH");
                Date data = format.parse(departureDate);

                int cap = Integer.parseInt(capacity);
                double preco = Double.parseDouble(price);

                manageUsers.addTrip(departurePoint, destination, data, cap, preco,iduser);
                response.getWriter().println("exe");
                dest = "/Secured/ManagermensagemSucesso.jsp";

            } catch (ParseException e) {
                response.getWriter().println("ex");
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher(dest).forward(request, response);

    }
    }