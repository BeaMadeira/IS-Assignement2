package data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity (name="utilizador")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUser;
    //private int numero;
    private String nome;
    private String password;
    private String email;
    private Double wallet;

    @OneToMany(mappedBy = "passageiro")
    private List<Tickets> bilhetes;

    public User(String nome, String password, String email) {
        this.wallet=0.0;
        this.nome = nome;
        this.password = password;
        this.email = email;
        //this.userTrips=new ArrayList<>();
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public List<Tickets> getBilhetes() {
        return bilhetes;
    }

    public void setBilhetes(List<Tickets> bilhetes) {
        this.bilhetes = bilhetes;
    }

    public User() {

    }
}
