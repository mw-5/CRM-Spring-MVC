package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ModelTest
{
    model.Model m = model.Model.getModel();
    
    private void insertMockedEM(EntityManager em)
    {
        try
        {
            Field field = Model.class.getDeclaredField("em");
            field.setAccessible(true);
            field.set(m, em);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testGetEntityManager()
    {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        insertMockedEM(em);
        
        // Act
        EntityManager result = m.getEntityManager();
        
        // Assert
        assertNotNull(result);
    }
    
    @Test
    public void testGetCustomer()
    {
        // Arrange
        Customer c = new Customer();
        c.setCid(1);
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.<Customer>createNamedQuery("Customer.findByCid")).thenReturn(qry);
        when(qry.setParameter("cid", 1)).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(c);
        insertMockedEM(em);
        
        // Act
        Customer customer = m.getCustomer(1);
        
        // Assert
        assertEquals(1, customer.getCid().intValue());
    }
    
    @Test
    public void testGetCustomers()
    {
        // Arrange
        Customer c = new Customer();
        c.setCid(1);
        List<Customer> resultList = new ArrayList<>();
        resultList.add(c);
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.<Customer>createNamedQuery("Customer.findAll")).thenReturn(qry);
        when(qry.getResultList()).thenReturn(resultList);
        insertMockedEM(em);
        
        // Act
        List<Customer> list = m.getCustomers();
        Customer customer = list.get(0);
        
        // Assert
        assertEquals(1, customer.getCid().intValue());
    }
    
    @Test
    public void testGetNote()
    {
        // Arrange
        Note n = new Note();
        n.setId(1);
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.<Note>createNamedQuery("Note.findById")).thenReturn(qry);
        when(qry.setParameter("id", 1)).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(n);
        insertMockedEM(em);
        
        // Act
        Note note = m.getNote(1);
        
        // Assert
        assertEquals(1, note.getId().intValue());
    }
    
    @Test
    public void testGetNotes()
    {
        // Arrange
        Note n = new Note();
        n.setId(1);
        List<Note> resultList = new ArrayList<>();
        resultList.add(n);
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.<Note>createNamedQuery("Note.findByCid")).thenReturn(qry);
        when(qry.setParameter("cid", 1)).thenReturn(qry);
        when(qry.getResultList()).thenReturn(resultList);
        insertMockedEM(em);
        
        // Act
        List<Note> list = m.getNotes(1);
        Note note = list.get(0);
        
        // Assert
        assertEquals(1, note.getId().intValue());
    }
    
    @Test
    public void testGetContactPersons()
    {
        // Arrange
        ContactPerson cp = new ContactPerson();
        cp.setId(1);
        List<ContactPerson> resultList = new ArrayList<>();
        resultList.add(cp);
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.<ContactPerson>createNamedQuery("ContactPerson.findByCid")).thenReturn(qry);
        when(qry.setParameter("cid", 1)).thenReturn(qry);
        when(qry.getResultList()).thenReturn(resultList);
        insertMockedEM(em);
        
        // Act
        List<ContactPerson> list = m.getContactPersons(1);
        ContactPerson contactPerson = list.get(0);
        
        // Assert
        assertEquals(1, contactPerson.getId().intValue());
    }
    
    @Test
    public void testGetNewCid()
    {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.createNamedQuery("Customer.maxId")).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(1);
        insertMockedEM(em);
        
        // Act
        int newCid = m.getNewCid();
        
        // Assert
        assertEquals(2, newCid);        
    }
    
    @Test
    public void testGetNewContactPersonId()
    {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.createNamedQuery("ContactPerson.maxId")).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(1);
        insertMockedEM(em);
        
        // Act
        int newCpId = m.getNewContactPersonId();
        
        // Assert
        assertEquals(2, newCpId);
    }
    
    @Test
    public void testGetNewNoteId()
    {
        // Arrange
        EntityManager em = mock(EntityManager.class);
        Query qry = mock(Query.class);
        when(em.createNamedQuery("Note.maxId")).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(1);
        insertMockedEM(em);
        
        // Act
        int newNoteId = m.getNewNoteId();
        
        // Assert
        assertEquals(2, newNoteId);
    }
    
    @Test
    public void testPersistCustomer()
    {
        // Arrange
        Customer customer = new Customer();
        EntityManager em = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(transaction);
        Query qry = mock(Query.class);
        when(em.createNamedQuery("Customer.maxId")).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(1);
        insertMockedEM(em);
        
        // Act & assert
        m.persistCustomer(customer);
        verify(em, atLeastOnce()).persist(customer);
        m.persistCustomer(customer);
        verify(em, atLeastOnce()).merge(customer);
    }
    
    @Test
    public void testPersistContactPerson()
    {
        // Arrange
        ContactPerson cp = new ContactPerson();
        EntityManager em = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(transaction);
        Query qry = mock(Query.class);
        when(em.createNamedQuery("ContactPerson.maxId")).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(1);
        insertMockedEM(em);
        
        // Act & assert
        m.persistContactPerson(cp);
        verify(em, atLeastOnce()).persist(cp);
        m.persistContactPerson(cp);
        verify(em, atLeastOnce()).merge(cp);
    }
    
    @Test
    public void testPersistNote()
    {
        // Arrange
        Note note = new Note();
        EntityManager em = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(transaction);
        Query qry = mock(Query.class);
        when(em.createNamedQuery("Note.maxId")).thenReturn(qry);
        when(qry.getSingleResult()).thenReturn(1);
        insertMockedEM(em);
        
        // Act & assert
        m.persistNote(note);
        verify(em, atLeastOnce()).persist(note);
        m.persistNote(note);
        verify(em, atLeastOnce()).merge(note);
    }
    
    @Test
    public void testCalendarToString()
    {
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 1, 3);
        String expResult = "2017-2-3";
        
        // Act
        String actResult = m.calendarToString(cal);
        
        // Assert
        assertEquals(expResult, actResult);                
    }
}