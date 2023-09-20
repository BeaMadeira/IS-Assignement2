package beans;

import beansDto.CompanyManagerDto;
import beansDto.TicketsDto;
import beansDto.TripsDto;
import beansDto.UserDto;
import data.*;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IManageStudents {

    public void addManager(String nomeEmpresa,String email, String pass);//CRIAR EM CRIPT A PARTE
    public int ManagerLogin(String name, String key);
    public List<TripsDto> ManagerlistarViagens(String startDate, String finalDate);
    public List<TripsDto> listarViagenspordata(String startDate) throws ParseException;
    public List<UserDto> ManagerlistarPassageiros(int idViagem);

    public void addUser(String name, String email, String password);

    public int deleteUser (int iduser, String password) throws NoSuchAlgorithmException;

    public String editNome(String nome, int iduser);

    public void editEmail(String nome, int iduser);

    public void editPass(String pass, int iduser);

    public List<TicketsDto> deleteTrip(int idViagem);

    public List<TripsDto> totalRevenue();

    public List<CompanyManagerDto> getManagerEmails();

    public void addTrip(String departurePoint, String destination, Date departureDate, int capacity, double price, int iduser);

    public List<TripsDto> listarViagens(String data_i, String data_f) throws ParseException;

    public void chargeWallet(int iduser, double dinheiro);

    public List<UserDto> ListarTopPassageiros();

    public String comprarBilhete(int iduser, int id_viagem);

    public int devolverBilhete(int iduser, int id_viagem);

    public List<TripsDto> listarViagensdoUser(int iduser);

    public void addStudent(String name);

    public int Login(String name, String key);

    List<Student> listStudents();
}
