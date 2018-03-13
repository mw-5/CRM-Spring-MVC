package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name = "notes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n")
    , @NamedQuery(name = "Note.findById", query = "SELECT n FROM Note n WHERE n.id = :id")
    , @NamedQuery(name = "Note.findByCreatedBy", query = "SELECT n FROM Note n WHERE n.createdBy = :createdBy")
    , @NamedQuery(name = "Note.findByEntryDate", query = "SELECT n FROM Note n WHERE n.entryDate = :entryDate")
    , @NamedQuery(name = "Note.findByMemo", query = "SELECT n FROM Note n WHERE n.memo = :memo")
    , @NamedQuery(name = "Note.findByCategory", query = "SELECT n FROM Note n WHERE n.category = :category")
    , @NamedQuery(name = "Note.findByAttachment", query = "SELECT n FROM Note n WHERE n.attachment = :attachment")

    , @NamedQuery(name = "Note.maxId", query = "SELECT max(n.id) FROM Note n")
    , @NamedQuery(name = "Note.findByCid", query = "SELECT n FROM Note n WHERE n.cid = :cid ORDER BY n.id")
})
public class Note implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
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
    
    @Column(name = "created_by")
    private String createdBy;
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    
    @Column(name = "entry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone="CET")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar entryDate;
    public Calendar getEntryDate()
    {
        return entryDate;
    }
    public void setEntryDate(Calendar entryDate)
    {
        this.entryDate = entryDate;
    }
    
    @Size(max = 2147483647)
    @Column(name = "memo")
    private String memo;
    public String getMemo()
    {
        return memo;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
    }
    
    @Size(max = 255)
    @Column(name = "category")
    private String category;
    public String getCategory()
    {
        return category;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    @Size(max = 2147483647)
    @Column(name = "attachment")
    private String attachment;
    public String getAttachment()
    {
        return attachment;
    }
    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }
    
    @Transient
    private MultipartFile attachmentFile;
    public MultipartFile getAttachmentFile()
    {
        return attachmentFile;
    }
    public void setAttachmentFile(MultipartFile attachmentFile)
    {
        this.attachmentFile = attachmentFile;
    }
    
    @Basic(optional = false)
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

    
    public Note()
    {}

    public Note(Integer id)
    {
        this.id = id;
    }

    public Note(Integer id, String createdBy, Calendar entryDate)
    {
        this.id = id;
        this.createdBy = createdBy;
        this.entryDate = entryDate;
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
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "model.Note[ id=" + id + " ]";
    }
}
