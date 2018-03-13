package controllers;

import java.lang.reflect.Field;
import javax.persistence.EntityManager;
import model.ContactPerson;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import testCases.TestCase;
import testCases.TestCase.TestContactPerson;
import testCases.TestCase.TestCustomer;


public class FrmContactPersonControllerTest
{
    private void insertMockedModel(FrmContactPersonController controller, model.Model m)
    {
        try
        {
            Field field = FrmContactPersonController.class.getDeclaredField("m");
            field.setAccessible(true);
            field.set(controller, m);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testNewContactPerson()
    {
        // Arrange
        FrmContactPersonController controller = new FrmContactPersonController();
        ExtendedModelMap vm = new ExtendedModelMap();
        
        // Act
        String result = controller.newContactPerson(TestCustomer.CID, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(null, vm.get("id"));
        assertEquals(TestCustomer.CID, vm.get("cid"));
    }
    
    @Test
    public void testEditContactPerson()
    {
        // Arrange
        FrmContactPersonController controller = new FrmContactPersonController();
        ExtendedModelMap vm = new ExtendedModelMap();
        model.Model m = mock(model.Model.class);
        EntityManager em = mock(EntityManager.class);
        ContactPerson cp = TestCase.getTestContactPerson();
        when(m.getEntityManager()).thenReturn(em);
        when(em.find(ContactPerson.class, TestContactPerson.ID)).thenReturn(cp);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.editContactPerson(TestContactPerson.ID, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestContactPerson.ID, vm.get("id"));
        assertEquals(TestContactPerson.CID, vm.get("cid"));
        assertEquals(TestContactPerson.FORENAME, vm.get("forename"));
        assertEquals(TestContactPerson.SURNAME, vm.get("surname"));
        assertEquals(TestContactPerson.GENDER, vm.get("gender"));
        assertEquals(TestContactPerson.EMAIL, vm.get("email"));
        assertEquals(TestContactPerson.PHONE, vm.get("phone"));
        assertEquals(TestContactPerson.MAIN_CONTACT, vm.get("mainContact"));
    }
    
    @Test
    public void testSubmit_noErrors()
    {
        // Arrange
        FrmContactPersonController controller = new FrmContactPersonController();
        ExtendedModelMap vm = new ExtendedModelMap();
        ContactPerson cp = TestCase.getTestContactPerson();
        model.Model m = mock(model.Model.class);
        insertMockedModel(controller, m);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        
        // Act
        String result = controller.submit(cp, bindingResult, vm);
        
        // Assert
        assertNotNull(result);
        verify(m, atLeastOnce()).persistContactPerson(cp);
    }
    
    @Test
    public void testSubmit_errors()
    {
        // Arrange
        FrmContactPersonController controller = new FrmContactPersonController();
        ExtendedModelMap vm = new ExtendedModelMap();
        ContactPerson cp = TestCase.getTestContactPerson();
        model.Model m = mock(model.Model.class);
        insertMockedModel(controller, m);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        
        // Act
        String result = controller.submit(cp, bindingResult, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestContactPerson.ID, vm.get("id"));
        assertEquals(TestContactPerson.CID, vm.get("cid"));
        assertEquals(TestContactPerson.FORENAME, vm.get("forename"));
        assertEquals(TestContactPerson.SURNAME, vm.get("surname"));
        assertEquals(TestContactPerson.GENDER, vm.get("gender"));
        assertEquals(TestContactPerson.EMAIL, vm.get("email"));
        assertEquals(TestContactPerson.PHONE, vm.get("phone"));
        assertEquals(TestContactPerson.MAIN_CONTACT, vm.get("mainContact"));
        verify(m, never()).persistContactPerson(cp);
    }
}
