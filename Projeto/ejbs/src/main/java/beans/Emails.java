package beans;

import data.CompanyManager;
import data.Trips;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Emails {
    @PersistenceContext(unitName = "playAula")
    EntityManager em;

    ArrayList<String> emails = new ArrayList<>();
    @Resource(mappedName="java:jboss/mail/Default")
    private Session mailSession;

    @Schedule(second = "*", minute = "*", hour = "*/24", persistent = false)
    public void atSchedule() throws InterruptedException, AddressException {
        TypedQuery<CompanyManager> q = em.createQuery(" from CompanyManager p  ", CompanyManager.class);
        List<CompanyManager> list = q.getResultList();
        ArrayList<Address> emails =new ArrayList<>();
        for(CompanyManager a:list){
            emails.add(new InternetAddress(a.getEmail())) ;
        }
        try    {
            MimeMessage m = new MimeMessage(mailSession);
            Address from = new InternetAddress("test@mastertheboss.com");
            Address[] to;

            to = emails.toArray(new Address[emails.size()]);

            m.setFrom(from);
            m.setRecipients(Message.RecipientType.TO, to);
            m.setSubject("WildFly Mail");
            m.setSentDate(new java.util.Date());
            /*criar mensagem */
            String mensagem = "viagem cancelada";

            //ir buscar as viagens
            TypedQuery<Trips> q2 = em.createQuery(" from Trips t where t.revenue <> 0 and t.departureDateHour =: current_date", Trips.class);
            List<Trips> viagens = q2.getResultList();

            //ir buscar revenues e meter em lista d strings
            ArrayList<String> trips = new ArrayList<>();
            int soma=0;
            for(Trips t: viagens){
                trips.add("Viagem: "+ t.getTripId()+" Revenue: "+ t.getRevenue());
                soma+=t.getRevenue();//dava p fazer sum mas teriamos q fazer 2 queries
            }
            trips.add(" revenue total: "+ soma);

            // transformar lista em string
            String format="";
            for(String lol: trips){
                format+=lol+"\n";
            }
            /*criar mensagem */
            m.setContent(format,"text/plain");
            Transport.send(m);
        }
        catch (javax.mail.MessagingException e)
        {
            e.printStackTrace();
        }
        System.out.println("DeclarativeScheduler:: In atSchedule()");
    }


}