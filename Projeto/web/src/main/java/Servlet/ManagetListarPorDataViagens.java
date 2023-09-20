package Servlet;

import beans.IManageStudents;
import beansDto.TripsDto;
import data.Trips;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Secured/ManagerlistaPorDataViagens")
public class ManagetListarPorDataViagens extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<ArrayList<String>> listaViagens = new ArrayList<>();
        String startDate = request.getParameter("startDate");
        String dest = "/error.html";
        if (startDate != null) {

            try {
                List<TripsDto> td = manageUsers.listarViagenspordata(startDate);

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
            dest = "/Secured/ManagerListarViagem.jsp";
        }
        request.getRequestDispatcher(dest).forward(request, response);
    }
}
