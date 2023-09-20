package beansDto;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TicketsDto {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTicket;

    private Double price;
    private Date date;
    @ManyToOne(cascade=CascadeType.REMOVE)
    private TripsDto viagem;

    @ManyToOne(cascade=CascadeType.REMOVE)
    private UserDto passageiro;

    public TicketsDto(Double price, TripsDto viagem, Date date) {
        this.price = price;
        this.viagem = viagem;
        this.passageiro=null;
        this.date = date;
    }

    public TicketsDto(Double price, TripsDto viagem, Date date, int idTicket) {
        this.price = price;
        this.viagem = viagem;
        this.passageiro=null;
        this.date = date;
        this.idTicket = idTicket;
    }

    public TicketsDto(Double price, TripsDto viagem, Date date, int idTicket, UserDto passageiro) {
        this.price = price;
        this.viagem = viagem;
        this.passageiro=null;
        this.date = date;
        this.idTicket = idTicket;
        this.passageiro = passageiro;
    }

    public TicketsDto() {

    }

    public UserDto getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(UserDto passageiro) {
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

    public TripsDto getViagem() {
        return viagem;
    }

    public void setViagem(TripsDto viagem) {
        this.viagem = viagem;
    }
}
