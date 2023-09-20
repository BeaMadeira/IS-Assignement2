package beans;

import data.Student;
import data.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ManageUser {
    @PersistenceContext(unitName = "playAula")
    EntityManager em;

    /**
     *
     * private int idUser;
     *     private String nome;
     *     private String password;
     *     private String email;
     *     private ArrayList<Trips> userTrips;
     *     private Double wallet;
     * @param name
     */
    public void addUser(String name,String password, String email) {
        System.out.println("Adding user " + name + password + email +"...");
        User s = new User(name,password,email);
        em.persist(s);
    }
    public List<Student> listUser() {
        System.out.println("Retrieving students from the database...");
        TypedQuery<Student> q = em.createQuery("from Student s", Student.class);
        List<Student> list = q.getResultList();
        return list;
    }



}
