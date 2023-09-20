package data;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Tickets {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;

    private Double price;
    private Date date;
    @ManyToOne(cascade=CascadeType.REMOVE)
    private Trips viagem;

    @ManyToOne(cascade=CascadeType.REMOVE)
    private User passageiro;

    public Tickets(Double price, Trips viagem, Date date) {
        this.price = price;
        this.viagem = viagem;
        this.passageiro=null;
        this.date = date;
    }

    public Tickets() {

    }

    public User getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(User passageiro) {
        this.passageiro = passageiro;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Trips getViagem() {
        return viagem;
    }

    public void setViagem(Trips viagem) {
        this.viagem = viagem;
    }

    public Date getDate() { return date; }
}
