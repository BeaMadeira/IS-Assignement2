package beansDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CompanyManagerDto {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idManager;

    private String nomeEmpresa;

    @OneToMany(mappedBy="empresa")
    private List<TripsDto> companyManagerTrips;


    private String pass;
    private String email;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CompanyManagerDto() {
    }

    public CompanyManagerDto(String nomeEmpresa, String email, String pass) {
        this.nomeEmpresa = nomeEmpresa;
        this.email=email;
        this.pass = pass;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public List<TripsDto> getCompanyManagerTrips() {
        return companyManagerTrips;
    }

    public void setCompanyManagerTrips(List<TripsDto> companyManagerTrips) {
        this.companyManagerTrips = companyManagerTrips;
    }

}
