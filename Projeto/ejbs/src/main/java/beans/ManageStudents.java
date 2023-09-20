package beans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import beansDto.CompanyManagerDto;
import beansDto.TicketsDto;
import beansDto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

import beansDto.TripsDto;
import data.*;
@Stateless
public class ManageStudents implements IManageStudents {
    @PersistenceContext(unitName = "playAula")
    EntityManager em;


    Logger logger = LoggerFactory.getLogger(ManageStudents.class);
    public void addStudent(String name) {
        logger.info("Adicionar " + name + "...");

        Student s = new Student(name);
        em.persist(s);

    }

    public void addManager(String nomeEmpresa,String email, String password){//CRIAR EM CRIPT A PARTE


        String hashedpass;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] bytes = md.digest();


            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            hashedpass = sb.toString();

            //System.out.println("Adding User " + name + "...");
            CompanyManager c = new CompanyManager(nomeEmpresa,email,hashedpass);
            em.persist(c);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //adicionar verificacao de ccs iguais ou mails iguais
        //falso se nao passar verificacao
    }

    public void addTrip(String departurePoint, String destination, Date departureDate, int capacity, double price, int iduser){
        System.out.println("Creating trip to" + destination + "...");

        CompanyManager c = em.find(CompanyManager.class,iduser);
        Trips t = new Trips(departureDate, departurePoint, destination, capacity, price,c);
        Tickets[] bilhetes = {};
        // create a new ArrayList
        List<Tickets> arrlist = new ArrayList<Tickets>(Arrays.asList(bilhetes));
        for(int i=0;i<capacity;i++) {
            // Add the new element
            arrlist.add(new Tickets(price, t, departureDate));
        }
        // Convert the Arraylist to array
        bilhetes = arrlist.toArray(bilhetes);

        em.persist(t);
        for (Tickets p : bilhetes)
        {
            em.persist(p);
        }
    }

    public List<TicketsDto> deleteTrip(int idViagem){
        Trips a = em.find(Trips.class, idViagem);
        List<TicketsDto> tid = new ArrayList<>();
        if(a!=null){
            //reembolsar passageiros e avisar
            List<Tickets> bilhetes = a.getBilhetes();

            Trips trip;
            User user;
            UserDto ud;
            TripsDto tdto;

            for(Tickets t : bilhetes) {
                trip = t.getViagem();
                user = t.getPassageiro();
                tdto = new TripsDto(trip.getDepartureDateHour(), trip.getDeparturePoint(), trip.getDestination(), trip.getCapacity(), trip.getPrice());
                ud = new UserDto(user.getIdUser(), user.getNome(), user.getPassword(), user.getEmail());
                tid.add(new TicketsDto(t.getPrice(), tdto, t.getDate(), t.getIdTicket(), ud));
            }

            for(Tickets t:bilhetes){
                if(t.getPassageiro()!=null) {
                    t.getPassageiro().setWallet(t.getPassageiro().getWallet() + t.getPrice());
                    //tirar referencia dos users nos tickets!
                    //eliminar tickets
                    t.setPassageiro(null);
                }
                em.remove(t);
            }
            //eliminar trip
            em.remove(a);
        }
        return tid;
    }

    public List<CompanyManagerDto> getManagerEmails()  {
        TypedQuery<CompanyManager> q = em.createQuery(" from CompanyManager p  ", CompanyManager.class);
        List<CompanyManager> list = q.getResultList();
        List<CompanyManagerDto> cd = new ArrayList<>();

        for(CompanyManager a:list){
            cd.add(new CompanyManagerDto(a.getNomeEmpresa(),a.getEmail(),a.getPass()));
        }
        return cd;
    }
    // abre me o projeto novo...
    public int ManagerLogin(String name, String key) {
        boolean resultado;
        //nome = mail, key= password
        //ir a base de dados pesquisar user por email
        String hashedpass;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(key.getBytes());

            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            hashedpass = sb.toString();
            //nome = mail, key= password
            //ir a base de dados pesquisar user por email
            TypedQuery<CompanyManager> q = em.createQuery("from CompanyManager u where u.email=:name",CompanyManager.class);
            q.setParameter("name",name);
            List<CompanyManager> manager = q.getResultList();
            if(manager.isEmpty()){
                return -1;
            }
            //ver se key corresponde
            if(manager.get(0).getPass().equals(hashedpass)){
                return manager.get(0).getIdManager();
            }else{
                return -1;
            }
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<TripsDto> ManagerlistarViagens(String startDate, String finalDate){
        //criar objetos Date
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        /*Date dateInicio = ft.parse(data_i);
        Date dateFim = ft.parse(data_f);*/
        //from viagem where date a < viagem > date b and

        TypedQuery<Trips> q = em.createQuery("from Trips p where p.departureDateHour > date(?1) and p.departureDateHour < date(?2) order by p.departureDateHour", Trips.class);
        q.setParameter(1, startDate);
        q.setParameter(2, finalDate);

        List<Trips> list = q.getResultList();
        ArrayList<ArrayList<String>> listaViagens= new ArrayList<ArrayList<String>>();
        TripsDto viagemcopia=new TripsDto();
            ArrayList<TripsDto> td = new ArrayList<>();

            for (Trips t : list) {
                td.add(new TripsDto(t.getDepartureDateHour(), t.getDeparturePoint(), t.getDestination(), t.getCapacity(), t.getPrice()));
            }




        return td;
    }

    public List<TripsDto> listarViagenspordata(String startDate) throws ParseException {
        //criar objetos Date
        /*Date dateInicio = ft.parse(data_i);
        Date dateFim = ft.parse(data_f);*/
        //from viagem where date a < viagem > date b and

        /*SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        Date threshold = ft.parse(startDate);*/
        //System.out.println("-------threshold -----------"+threshold );
        TypedQuery<Trips> q = em.createQuery("from Trips p where p.departureDateHour = date(?1)", Trips.class);
        q.setParameter(1, startDate);

        List<Trips> list = q.getResultList();

        List<TripsDto> td = new ArrayList<>();

        for(Trips t : list)
            td.add(new TripsDto(t.getDepartureDateHour(),t.getDeparturePoint(),t.getDestination(),t.getCapacity(),t.getPrice()));

        for(Trips t : list) {
            logger.info("Trips\n");
            logger.info(t.getDepartureDateHour().toString());
        }
        for(TripsDto t : td) {
            logger.info("TripsDto\n");
            logger.info(t.getDepartureDateHour().toString());
        }
        return td;
    }

    public List<UserDto> ManagerlistarPassageiros(int idViagem){
        //criar objetos Date
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");

        //obter lista de bilhetes com esse id d viagem cujo passageiro nao e null
        TypedQuery<Tickets> q = em.createQuery("from Tickets t where t.viagem =: param1 and t.passageiro != null", Tickets.class);
        q.setParameter("param1", em.find(Trips.class,idViagem));
        List<Tickets> list = q.getResultList();

        List<UserDto> ud = new ArrayList<>();
        //tirar de cada bilhete o passageiro associado
        ArrayList<User> passageiros = new ArrayList<>();
        for(Tickets t : list){
            passageiros.add(t.getPassageiro());
        }

        for(User u : passageiros)
            ud.add(new UserDto(u.getIdUser(),u.getNome(),u.getPassword(),u.getEmail()));

        return ud;
    }

    public List<UserDto> ListarTopPassageiros(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH");
        LocalDateTime now = LocalDateTime.now();

        Query q2 = em.createNativeQuery("select passageiro_iduser from Tickets u where date < current_date group by passageiro_iduser having count(passageiro_iduser) <> 0 order by count(passageiro_iduser) desc;\n");

        List <Integer> a = (List<Integer>) q2.getResultList();

        ArrayList<UserDto> ud = new ArrayList<>();
        //List<Tickets> list = q.getResultList();

        for(int t : a){
            User u = em.find(User.class,t);
            ud.add(new UserDto(u.getIdUser(), u.getNome(), u.getPassword(), u.getEmail()));
        }

        return ud;
    }

    public List<TripsDto> totalRevenue(){
        //ir buscar as viagens
        TypedQuery<Trips> q = em.createQuery(" from Trips t where t.revenue <> 0", Trips.class);
        List<Trips> viagens = q.getResultList();
        ArrayList<TripsDto> td = new ArrayList<>();

        //ir buscar revenues e meter em lista d strings
        ArrayList<String> trips = new ArrayList<>();

        for(Trips t: viagens)
            td.add(new TripsDto(t.getDepartureDateHour(),t.getDeparturePoint(),t.getDestination(),t.getCapacity(),t.getPrice()));

        return td;
    }

    //----------------------------------------------------------------------------------------

    public void addUser(String name, String email, String password) {

        String hashedpass;
        User s = new User();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            hashedpass = sb.toString();

            //Pesquisar em todos os users se ja ha um mail igual
            /*TypedQuery<User> q = em.createQuery("from utilizador u where u.email =: param", User.class);
            q.setParameter("param",email);
            List<User> list = q.getResultList();
            if(list != null){
                //nao adiciona
            }else {*/
                s = new User(name, hashedpass, email);
                em.persist(s);
           // }
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.info("[NEW ACCOUNT] Account created with User ID: " + s.getIdUser() + " Name: " + s.getNome() + ".");
        //adicionar verificacao de ccs iguais ou mails iguais
        //falso se nao passar verificacao
    }

    //editar: um metodo para cada parametro
    public String editNome(String nome, int iduser){
        //pesquisar utilizador
        User u = em.find(User.class,iduser);
        logger.info("[NAME EDIT] User " + u.getNome() + " changed their name to " + nome);
        u.setNome(nome);
        /*TypedQuery<User> q = em.createQuery("Select u from utilizador u where u.idUser=:iduser", User.class)
                .setParameter("iduser", iduser);
        List<User> lista_users = q.getResultList();
        lista_users.get(0).getNome();*/
        return nome;
    }
    public void editEmail(String email, int iduser){
        //pesquisar utilizador
        User u = em.find(User.class,iduser);
        u.setEmail(email);
    }

    public void editPass(String pass, int iduser){
        //pesquisar utilizador
        User u = em.find(User.class,iduser);
        u.setPassword(pass);
    }

    public int deleteUser (int iduser, String password) throws NoSuchAlgorithmException { /* testar */
        int res;
        //verificar se pass corresponde a iduser
        List<Tickets> bilhetes = new ArrayList<>();
        User u=em.find(User.class,iduser);
        String nome = u.getNome();
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(password.getBytes());

        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // Get complete hashed password in hex format
        String hashedpass = sb.toString();

        if(u.getPassword().equals(hashedpass)){
            //ir aos tickets do user e remover o id d la
                bilhetes= u.getBilhetes();
                for(Tickets t : bilhetes){
                    t.setPassageiro(null);
                }
            em.remove(u);
            res=1;
        }else{
            res=0;
        }
        logger.info("[ACCOUNT DELETED] User " + nome + " deleted their account.");
        return res;
    }


    public void chargeWallet(int iduser,double dinheiro){
        User u = em.find(User.class,iduser);
        u.setWallet(u.getWallet()+dinheiro);
        logger.info("[DEPOSIT] User " + u.getNome() + " added " + dinheiro + "EUR to their account.");
    }

    /*Selo de qualidade*/
    public String comprarBilhete(int iduser, int id_viagem){
        //procurar um ticket available BUG NA TYPEQUERY
        TypedQuery<Tickets> q = em.createQuery("from Tickets s where s.viagem =: paramviagem", Tickets.class);
        q.setParameter("paramviagem",em.find(Trips.class,id_viagem));
        List<Tickets> list = q.getResultList();
        String resultado = "";
        Tickets bilh;
        int id = 0;
        User u = new User();
        int flag_lugares_disponiveis=0;
        for (Tickets p : list) {
            if(p.getPassageiro()==null){
                flag_lugares_disponiveis=1;
                //subtrair dinheiro a wallet
                //procurar user por id
                u = em.find(User.class,iduser);
                //aceder a p.price
                //fazer u.wallet - p.price
                //se for >0 p.setpassageiro
                //else return nao tem dinheiro
                if(u.getWallet() - p.getPrice() > 0){
                    u.setWallet(u.getWallet()-p.getPrice());
                    p.setPassageiro(u);
                    //ir a ticket, ir a viagem
                    //adicionar p.getprice as revenues
                    p.getViagem().setRevenue(p.getViagem().getRevenue()+p.getPrice());
                    resultado="bilhete comprado";
                    id = p.getIdTicket();
                }else{
                    resultado = "Utilizador não tem dinheiro suficiente na wallet";
                    break;
                }
                break;
            }
        }

        //verificar lugares disponiveis
        if(flag_lugares_disponiveis==0){
            resultado="viagem sem lugares disponiveis";
        }
        else {
            logger.info("[TICKET BOUGHT] User " + u.getNome() + " bought Ticket ID: " + id + " for Trip ID: " + id_viagem);
        }
        return resultado;
    }

    public int devolverBilhete(int iduser, int id_viagem){
        int res=1;
        //procurar ticket com id user e id viagem
        TypedQuery<Tickets> q = em.createQuery("from Tickets t where t.passageiro =: param1 and t.viagem =:param2",Tickets.class);
        q.setParameter("param1",em.find(User.class,iduser));
        q.setParameter("param2",em.find(Trips.class,id_viagem));
        List<Tickets> list = q.getResultList();

        //colocar ticket.passageiro=null
        list.get(0).setPassageiro(null);

        //user.wallet = wallet + ticket.price
        User u = em.find(User.class,iduser);
        if(u==null){
            res=0;
        }else {
            double precoBilhete = list.get(0).getPrice();
            u.setWallet(u.getWallet() + precoBilhete);
            //ir ao ticket ir a viagem subtrair revenue
            list.get(0).getViagem().setRevenue(list.get(0).getViagem().getRevenue()-precoBilhete);
            logger.info("[TICKET RETURNED] User " + u.getNome() + " returned Ticket ID: " + list.get(0).getIdTicket() + " for Trip ID: " + id_viagem);

        }
        return res;
    }

    public List<TripsDto> listarViagens(String data_i, String data_f) throws ParseException {
        //criar objetos Date
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        /*Date dateInicio = ft.parse(data_i);
        Date dateFim = ft.parse(data_f);*/
        //from viagem where date a < viagem > date b and


        TypedQuery<Trips> q = em.createQuery("from Trips p where p.departureDateHour > date(?1) and p.departureDateHour < date(?2)", Trips.class);
        q.setParameter(1, data_i);
        q.setParameter(2, data_f);

        List<Trips> list = q.getResultList();

        List<TripsDto> td = new ArrayList<>();

        for (Trips p : list)
            td.add(new TripsDto(p.getDepartureDateHour(), p.getDeparturePoint(), p.getDestination(), p.getCapacity(), p.getPrice()));

        for(TripsDto t : td)
            logger.info(t.getDepartureDateHour().toString());

        return td;
    }

    public List<TripsDto> listarViagensdoUser(int iduser){
        //encontrar tickets do user
        TypedQuery<Tickets> q = em.createQuery("from Tickets t where t.passageiro =: param1 ",Tickets.class);
        q.setParameter("param1",em.find(User.class,iduser));
        List<Tickets> bilhetes = q.getResultList();
        //guardar tickets.viagem
        List<Trips> viagensdouser= new ArrayList<>();

        List<TicketsDto> tid = new ArrayList<>();
        List<TripsDto> td = new ArrayList<>();
        Trips trip;
        TripsDto tdto;
        for(Tickets t : bilhetes) {
            trip = t.getViagem();
            tdto = new TripsDto(trip.getDepartureDateHour(), trip.getDeparturePoint(), trip.getDestination(), trip.getCapacity(), trip.getPrice());
            tid.add(new TicketsDto(t.getPrice(), tdto, t.getDate(), t.getIdTicket()));
        }

        for(Tickets t : bilhetes)
            viagensdouser.add(t.getViagem());
        for(Trips t : viagensdouser)
            td.add(new TripsDto(t.getDepartureDateHour(),t.getDeparturePoint(),t.getDestination(),t.getCapacity(),t.getPrice()));

        return td;
    }

    public int Login(String name, String key) {
        boolean resultado;
        //nome = mail, key= password
        //ir a base de dados pesquisar user por email
        String hashedpass;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(key.getBytes());

            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            hashedpass = sb.toString();


            TypedQuery<User> q = em.createQuery("from utilizador u where u.email=:name",User.class);
            q.setParameter("name",name);
            List<User> utilizador = q.getResultList();
            if(utilizador.isEmpty()){
                return -1;
            }
            //ver se key corresponde
            if(utilizador.get(0).getPassword().equals(hashedpass)){
                logger.info("User ID: " + utilizador.get(0).getIdUser() + " Name: " + utilizador.get(0).getNome() + " just logged in.");
                return utilizador.get(0).getIdUser();
            }else{
                return -1;
            }

        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Student> listStudents() {
        return null;
    }
}