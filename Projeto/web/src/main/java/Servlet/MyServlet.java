package Servlet;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.mail.Address;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.IManageStudents;
import data.Student;
import data.Tickets;
import data.User;

@WebServlet("/webaccess")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
   private IManageStudents manageStudents;
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

       manageStudents.addManager("emp","beatriz.a.a.madeira@gmail.com","1234");
/*
       manageStudents.comprarBilhete(187,120);

       //String idviagem = request.getParameter("idviagem");
       String mensagem = "A viagem " + 120 + " foi cancelada.";
       String dest = "/error.html";
       ArrayList<Address> emails = new ArrayList<>();

       emails = manageStudents.deleteTrip(120);
       response.getWriter().println(emails);
       dest = "/mail";
       request.setAttribute("mensagem",mensagem);
       request.setAttribute("emails", emails);

       request.getRequestDispatcher(dest).forward(request, response);

        //manageStudents.addManager("minhacomp","beatriz.a.a.madeira@gmail.com","33333");
        ArrayList<String> listaNomes = null;*/
       /*SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH");
       try {
           Date data = format.parse("11-12-2012 20");
           Date data1 = format.parse("11-12-2013 23");
           Date data2 = format.parse("11-12-2011 14");
           Date data3 = format.parse("11-12-2022 15");

           manageStudents.addTrip("lol", "lol2", data , 20, 1000, 6);
           manageStudents.addTrip("lol3", "lol4", data1 , 20, 1000, 6);
           manageStudents.addTrip("lol5", "lol6", data2 , 20, 1000, 6);
           manageStudents.addTrip("lol7", "lol8", data3 , 20, 1000, 6);
       } catch (ParseException e) {
           e.printStackTrace();
       } finally {
           response.getWriter().println("bruh");
       }*/

       /*try {
           listaNomes = manageStudents.ListarTopPassageiros();
       } catch(NullPointerException e) {
           response.getWriter().println("lista nullllllllll\n ");
       }
       response.getWriter().println("Top 5 utilizadores:\n");
       if (listaNomes != null) {
           for (String s : listaNomes)
               response.getWriter().println(s);
       } else {
           response.getWriter().println("did not work");
       }*/
   }
}