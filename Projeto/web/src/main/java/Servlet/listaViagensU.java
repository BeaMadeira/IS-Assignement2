package Servlet;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import beansDto.TripsDto;
import data.Student;
import data.Trips;

@WebServlet("/Secured/listaViagensU")
public class listaViagensU extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<ArrayList<String>> listaViagens = new ArrayList<>();

        String startDate = request.getParameter("startDate");
        String finalDate = request.getParameter("finalDate");
        String dest = "/error.html";
        if (startDate != null && finalDate != null) {

            try {
                List<TripsDto> td = manageUsers.listarViagens(startDate, finalDate);

                for (TripsDto p : td) {
                    ArrayList<String> viagem = new ArrayList<>();
                    String id = Integer.toString(p.getTripId());
                    viagem.add(id);
                    viagem.add(p.getDestination());
                    String data = p.getDepartureDateHour().toString();
                    viagem.add(data);
                    listaViagens.add(viagem);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            request.setAttribute("lista", listaViagens);
            dest = "/Secured/listarViagensU.jsp";
        }
        request.getRequestDispatcher(dest).forward(request, response);
    }
}