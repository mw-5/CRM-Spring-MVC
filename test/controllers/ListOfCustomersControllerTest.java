package controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import testCases.TestCase;
import testCases.TestCase.TestCustomer;


public class ListOfCustomersControllerTest
{
    private void insertMockedModel(ListOfCustomersController controller, model.Model m)
    {
        try
        {
            Field field = ListOfCustomersController.class.getDeclaredField("model");
            field.setAccessible(true);
            field.set(controller, m);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testListOfCustomers()
    {
        // Arrange
        ListOfCustomersController controller = new ListOfCustomersController();
        
        // Act
        String result = controller.listOfCustomers();
        
        // Assert
        assertNotNull(result);
    }
    
    @Test
    public void testGetCustomers()
    {
        // Arrange
        ListOfCustomersController controller = new ListOfCustomersController();
        model.Model m = mock(model.Model.class);
        List<Customer> resultList = new ArrayList<>();
        Customer c = TestCase.getTestCustomer();
        resultList.add(c);
        when(m.getCustomers()).thenReturn(resultList);
        insertMockedModel(controller, m);

        // Act
        List<Customer> list = controller.getCustomers();
        Customer customer = list.get(0);
        
        // Assert
        assertEquals(TestCustomer.CID, customer.getCid().intValue());
    }
}
