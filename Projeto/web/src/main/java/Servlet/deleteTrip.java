package Servlet;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.IManageStudents;
import beansDto.TicketsDto;
import data.Student;
import data.Tickets;

@WebServlet("/Secured/createTrip")
public class deleteTrip extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String departurePoint = request.getParameter("departurePoint");
        String idviagem = request.getParameter("idviagem");
        String mensagem = "A viagem " + idviagem + " foi cancelada.";
        String dest = "/error.html";
        ArrayList<Address> emails = new ArrayList<>();

        if(idviagem != null) {
            int id = Integer.parseInt(idviagem);
            List<TicketsDto> tid = manageUsers.deleteTrip(id);

            for(TicketsDto t:tid){
                try {
                    emails.add(new InternetAddress(t.getPassageiro().getEmail()));
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }

            response.getWriter().println("exe");
            dest = "/mail";
            request.setAttribute("mensagem",mensagem);
            request.setAttribute("emails", emails);
        }
        request.getRequestDispatcher(dest).forward(request, response);

    }
}