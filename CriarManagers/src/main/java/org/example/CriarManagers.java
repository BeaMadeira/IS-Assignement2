package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CriarManagers {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++)
            System.out.println(args[i]);


        String hashedpass;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(args[2].getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            hashedpass = sb.toString();


            CompanyManager manager = new CompanyManager(args[0], args[1], hashedpass);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("playAula");
            EntityManager em = emf.createEntityManager();
            EntityTransaction trx = em.getTransaction();
            trx.begin();

            em.persist(manager);

            trx.commit();
            em.close();
            emf.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}