package controllers;

import java.lang.reflect.Field;
import model.Customer;
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
import testCases.TestCase.TestCustomer;


public class FrmCustomerControllerTest
{
    private void insertMockedModel(FrmCustomerController controller, model.Model m)
    {
        try
        {
            Field field = FrmCustomerController.class.getDeclaredField("m");
            field.setAccessible(true);
            field.set(controller, m);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testNewCustomer()
    {
        // Arrange
        FrmCustomerController controller = new FrmCustomerController();
        
        // Act
        String result = controller.newCustomer();
        
        // Assert
        assertNotNull(result);                
    }
    
    @Test
    public void testEditCustomer()
    {
        // Arrange
        FrmCustomerController controller = new FrmCustomerController();
        ExtendedModelMap vm = new ExtendedModelMap();
        model.Model m = mock(model.Model.class);
        when(m.getCustomer(TestCustomer.CID)).thenReturn(TestCase.getTestCustomer());
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.editCustomer(TestCustomer.CID, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestCustomer.CID, vm.get("cid"));
        assertEquals(TestCustomer.COMPANY, vm.get("company"));
        assertEquals(TestCustomer.ADDRESS, vm.get("address"));
        assertEquals(TestCustomer.ZIP, vm.get("zip"));
        assertEquals(TestCustomer.CITY, vm.get("city"));
        assertEquals(TestCustomer.COUNTRY, vm.get("country"));
        assertEquals(TestCustomer.CONTRACT_ID, vm.get("contractId"));
        assertEquals(m.calendarToString(TestCustomer.getContractDate()), vm.get("contractDate"));
    }
    
    @Test
    public void testSubmit_noErrors()
    {
        // Arrange
        FrmCustomerController controller = new FrmCustomerController();
        ExtendedModelMap vm = new ExtendedModelMap();
        Customer customer = TestCase.getTestCustomer();
        BindingResult bindingResult = mock(BindingResult.class);
        model.Model m = mock(model.Model.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.submit(customer, bindingResult, vm);
        
        // Assert
        assertNotNull(result);
        verify(m, atLeastOnce()).persistCustomer(customer);
    }
    
    @Test
    public void testSubmit_errors()
    {
        // Arrange
        FrmCustomerController controller = new FrmCustomerController();
        ExtendedModelMap vm = new ExtendedModelMap();
        Customer customer = TestCase.getTestCustomer();
        BindingResult bindingResult = mock(BindingResult.class);
        model.Model m = mock(model.Model.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.submit(customer, bindingResult, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestCustomer.CID, vm.get("cid"));
        assertEquals(TestCustomer.COMPANY, vm.get("company"));
        assertEquals(TestCustomer.ADDRESS, vm.get("address"));
        assertEquals(TestCustomer.ZIP, vm.get("zip"));
        assertEquals(TestCustomer.CITY, vm.get("city"));
        assertEquals(TestCustomer.COUNTRY, vm.get("country"));
        assertEquals(TestCustomer.CONTRACT_ID, vm.get("contractId"));
        assertEquals(m.calendarToString(TestCustomer.getContractDate()), vm.get("contractDate"));
        verify(m, never()).persistCustomer(customer);
    }    
}
