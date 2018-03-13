package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "contact_persons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactPerson.findAll", query = "SELECT c FROM ContactPerson c")
    , @NamedQuery(name = "ContactPerson.findById", query = "SELECT c FROM ContactPerson c WHERE c.id = :id")
    , @NamedQuery(name = "ContactPerson.findByForename", query = "SELECT c FROM ContactPerson c WHERE c.forename = :forename")
    , @NamedQuery(name = "ContactPerson.findBySurname", query = "SELECT c FROM ContactPerson c WHERE c.surname = :surname")
    , @NamedQuery(name = "ContactPerson.findByGender", query = "SELECT c FROM ContactPerson c WHERE c.gender = :gender")
    , @NamedQuery(name = "ContactPerson.findByEmail", query = "SELECT c FROM ContactPerson c WHERE c.email = :email")
    , @NamedQuery(name = "ContactPerson.findByPhone", query = "SELECT c FROM ContactPerson c WHERE c.phone = :phone")
    , @NamedQuery(name = "ContactPerson.findByMainContact", query = "SELECT c FROM ContactPerson c WHERE c.mainContact = :mainContact")
        
    , @NamedQuery(name = "ContactPerson.maxId", query = "SELECT max(c.id) FROM ContactPerson c")
    , @NamedQuery(name = "ContactPerson.findByCid", query = "SELECT c FROM ContactPerson c WHERE c.cid = :cid ORDER BY c.id")
})
public class ContactPerson implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
        
    @Size(max = 255)
    @Column(name = "forename")
    private String forename;
    public String getForename()
    {
        return forename;
    }
    public void setForename(String forename)
    {
        this.forename = forename;
    }
    
    @Size(max = 255)
    @Column(name = "surname")
    private String surname;
    public String getSurname()
    {
        return surname;
    }
    public void setSurname(String surname)
    {
        this.surname = surname;
    }
    
    @Column(name = "gender")
    private Character gender;
    public Character getGender()
    {
        return gender;
    }
    public void setGender(Character gender)
    {
        this.gender = gender;
    }
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "phone")
    private String phone;
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    @Column(name = "main_contact")
    private Boolean mainContact;
    public Boolean getMainContact()
    {
        return mainContact;
    }
    public void setMainContact(Boolean mainContact)
    {
        this.mainContact = mainContact;
    }
    
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
    

    public ContactPerson() 
    {}

    public ContactPerson(Integer id)
    {
        this.id = id;
    }

  
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactPerson))
        {
            return false;
        }
        ContactPerson other = (ContactPerson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.ContactPerson[ id=" + id + " ]";
    }    
}
