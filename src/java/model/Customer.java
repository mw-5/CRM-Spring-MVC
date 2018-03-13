package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "customers")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findByCid", query = "SELECT c FROM Customer c WHERE c.cid = :cid")
    , @NamedQuery(name = "Customer.findByCompany", query = "SELECT c FROM Customer c WHERE c.company = :company")
    , @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address")
    , @NamedQuery(name = "Customer.findByZip", query = "SELECT c FROM Customer c WHERE c.zip = :zip")
    , @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city")
    , @NamedQuery(name = "Customer.findByCountry", query = "SELECT c FROM Customer c WHERE c.country = :country")
    , @NamedQuery(name = "Customer.findByContractId", query = "SELECT c FROM Customer c WHERE c.contractId = :contractId")
    , @NamedQuery(name = "Customer.findByContractDate", query = "SELECT c FROM Customer c WHERE c.contractDate = :contractDate")

    , @NamedQuery(name = "Customer.maxId", query = "SELECT max(c.cid) FROM Customer c")
})
public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "cid")
    private Integer cid;
    public Integer getCid()
    {
        return cid;
    }
    public void setCid(Integer cid)
    {
        this.cid = cid;
    }
    
    @Size(max = 255)
    @Column(name = "company")
    private String company;
    public String getCompany()
    {
        return company;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }
    
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    @Size(max = 255)
    @Column(name = "zip")
    private String zip;
    public String getZip()
    {
        return zip;
    }
    public void setZip(String zip)
    {
        this.zip = zip;
    }
    
    @Size(max = 255)
    @Column(name = "city")
    private String city;
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    
    @Size(max = 255)
    @Column(name = "country")
    private String country;
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    @Size(max = 255)
    @Column(name = "contract_id")
    private String contractId;
    public String getContractId()
    {
        return contractId;
    }
    public void setContractId(String contractId) 
    {
        this.contractId = contractId;
    }
    
    @Column(name = "contract_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="CET")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar contractDate;
    public Calendar getContractDate()
    {
        return contractDate;
    }
    public void setContractDate(Calendar contractDate)
    {
        this.contractDate = contractDate;
    }


    public Customer()
    {}

    public Customer(Integer cid)
    {
        this.cid = cid;
    }

    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.Customer[ cid=" + cid + " ]";
    }
    
}
