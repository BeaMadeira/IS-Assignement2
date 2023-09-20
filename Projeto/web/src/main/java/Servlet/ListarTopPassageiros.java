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

@WebServlet("/Secured/listartop")
public class ListarTopPassageiros extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    private IManageStudents manageUsers;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<ArrayList<String>> listaViagens = null;
        List<UserDto> ud = null;
        String dest = "/error.html";

        ArrayList<String> listaNomes = new ArrayList<>();

        ud = manageUsers.ListarTopPassageiros();
        for(UserDto u : ud) {
            listaNomes.add("ID do utilizador: " + u.getIdUser() + " Nome: " + u.getNome());
        }

        request.setAttribute("lista", listaNomes);
        dest = "/Secured/ListarTopPassageiros.jsp";

        request.getRequestDispatcher(dest).forward(request, response);
    }
}


