package beansDto;

import javax.persistence.*;
import java.util.*;


@Entity
public class TripsDto {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tripId;
    @Temporal(TemporalType.DATE)
    private Date departureDateHour;
    private String departurePoint;
    private String destination;
    private int capacity;
    private Double price;
    private Double revenue;

    @OneToMany(mappedBy="viagem")
    private List<TicketsDto> bilhetes;

    @ManyToOne
    private CompanyManagerDto empresa;

    public TripsDto() {
    }

    public TripsDto( Date departureDateHour, String departurePoint, String destination, int capacity, Double price,CompanyManagerDto empresa) {
        this.departureDateHour = departureDateHour;
        this.departurePoint = departurePoint;
        this.destination = destination;
        this.capacity = capacity;
        this.price = price;
        this.empresa = empresa;
        this.revenue=0.0;
    }

    public TripsDto( Date departureDateHour, String departurePoint, String destination, int capacity, Double price) {
        this.departureDateHour = departureDateHour;
        this.departurePoint = departurePoint;
        this.destination = destination;
        this.capacity = capacity;
        this.price = price;
        this.revenue=0.0;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Date getDepartureDateHour() {
        return departureDateHour;
    }

    public void setDepartureDateHour(Date departureDateHour) {
        this.departureDateHour = departureDateHour;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<TicketsDto> getBilhetes() {
        return bilhetes;
    }

    public void setBilhetes(List<TicketsDto> bilhetes) {
        this.bilhetes = bilhetes;
    }

    public CompanyManagerDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(CompanyManagerDto empresa) {
        this.empresa = empresa;
    }
}
