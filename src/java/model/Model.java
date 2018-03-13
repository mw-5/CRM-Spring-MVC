package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
        
        
public class Model
{
    private EntityManager em = null;
    
    public EntityManager getEntityManager()
    {
        if(em == null)
        {
            em = Persistence.createEntityManagerFactory("CRM_webPU").createEntityManager();
        }
        return em;
    }
    
    private static Model model = null;
    public static Model getModel()
    {
        if(model == null)
        {
            model = new Model();
        }
        return model;
    }
    
    public Customer getCustomer(Integer cid)
    {
        try
        {
            return (Customer)getEntityManager().<Customer>createNamedQuery("Customer.findByCid").setParameter("cid", cid).getSingleResult();
        }
        catch(Exception e)
        {
            return null;
        }              
    }
    
    public List<Customer> getCustomers()
    {
        List<Customer> list = getEntityManager().<Customer>createNamedQuery("Customer.findAll").getResultList();
        return list;
    }
    
    public Note getNote(Integer id)
    {
        return (Note)getEntityManager().<Note>createNamedQuery("Note.findById").setParameter("id", id).getSingleResult();
    }
    
    public List<Note> getNotes(int cid)
    {
        try
        {
            List<Note> list = getEntityManager().<Note>createNamedQuery("Note.findByCid").setParameter("cid", cid).getResultList();
            
            // if new note was created set attachment file to null if no file has been attached
            Note newNote = list.get(list.size()-1);
            if (newNote.getAttachment() == null)
            {
                newNote.setAttachmentFile(null);
            }
            
            return list;
            
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public List<ContactPerson> getContactPersons(int cid)
    {
        try
        {
            return getEntityManager().<ContactPerson>createNamedQuery("ContactPerson.findByCid").setParameter("cid", cid).getResultList();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    public int getNewCid()
    {
        return getNewId("Customer.maxId");
    }
    
    public int getNewContactPersonId()
    {
        return getNewId("ContactPerson.maxId");
    }
    
    public int getNewNoteId()
    {
        return getNewId("Note.maxId");
    }
    
    private int getNewId(String qryName)
    {
        int id = (int)getEntityManager().createNamedQuery(qryName).getSingleResult();
        id++;
        return id;
    }
    
    public void persistCustomer(Customer customer)
    {
        getEntityManager().getTransaction().begin();
        if (customer.getCid() == null || customer.getCid() == 0)
        {
            customer.setCid(getNewCid());
            getEntityManager().persist(customer);
        }
        else
        {
            getEntityManager().merge(customer);
        }
        getEntityManager().getTransaction().commit();
    }
    
    public void persistContactPerson(ContactPerson cp)
    {
        getEntityManager().getTransaction().begin();
        if(cp.getId() == null || cp.getId() == 0)
        {
            cp.setId(getNewContactPersonId());
            getEntityManager().persist(cp);
        }
        else
        {
            getEntityManager().merge(cp);
        }
        getEntityManager().getTransaction().commit();
    }
    
    public void persistNote(Note note)
    {
        getEntityManager().getTransaction().begin();
        if (note.getId() == null || note.getId() == 0)
        {
            note.setId(getNewNoteId());
            getEntityManager().persist(note);
        }
        else
        {
            getEntityManager().merge(note);
        }
        getEntityManager().getTransaction().commit();
    }
    
    public String calendarToString(Calendar cal)
    {
        if (cal == null)
        {
            return "";
        }
        else
        {
            return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        }                
    }
    
    private Properties config = null;
    private ServletContext context = null;
    public Properties getConfig(ServletContext context)
    {
        this.context = context;
        if (config == null || config.isEmpty())
        {
            loadConfig();
        }
        return config;
    }
    public boolean loadConfig()
    {
        boolean isSuccess = true;
        try
        {
            config = new Properties();
            InputStream is = new FileInputStream(context.getRealPath("/WEB-INF/Config.properties"));
            config.load(is);
        }
        catch(IOException e)
        {
            isSuccess = false;
        }
        return isSuccess;
    }
}
