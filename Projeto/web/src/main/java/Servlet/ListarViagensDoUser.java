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

@WebServlet("/Secured/ListarViagensDoUser")
public class ListarViagensDoUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<ArrayList<String>> listaViagens = new ArrayList<>();
        int iduser = (Integer) request.getSession(true).getAttribute("auth");
        String dest = "/error.html";
        List<TripsDto> td = manageUsers.listarViagensdoUser(iduser);

        //criar string com viagens
        for (TripsDto p : td) {
            ArrayList<String> viagem = new ArrayList<>();
            String id = Integer.toString(p.getTripId());
            viagem.add(id);
            viagem.add(p.getDestination());
            String data = p.getDepartureDateHour().toString();
            viagem.add(data);
            listaViagens.add(viagem);
        }

        request.setAttribute("lista", listaViagens);
        dest = "/Secured/ListarViagensDoUser.jsp";

        request.getRequestDispatcher(dest).forward(request, response);
    }
}