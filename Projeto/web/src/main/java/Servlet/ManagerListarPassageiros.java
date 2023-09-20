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
import beansDto.UserDto;
import data.Student;
import data.User;

@WebServlet("/Secured/ManagerListarPassageirosViagem")
public class ManagerListarPassageiros extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<ArrayList<String>> listaPassageiros = new ArrayList<>();
        String idViagem = request.getParameter("idViagem");
        String dest = "/error.html";
        if (idViagem!= null) {

            List<UserDto> ud = manageUsers.ManagerlistarPassageiros(Integer.parseInt(idViagem));
            for (UserDto p : ud) {
                ArrayList<String> passageiro = new ArrayList<>();
                String id = Integer.toString(p.getIdUser());
                passageiro.add(id);
                passageiro.add(p.getNome());
                String email = p.getEmail();
                passageiro.add(email);
                listaPassageiros.add(passageiro);
            }

            request.setAttribute("lista", listaPassageiros);
            dest = "/Secured/ManagerListarViagem.jsp";
        }
        request.getRequestDispatcher(dest).forward(request, response);
    }
}