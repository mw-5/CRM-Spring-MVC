package controllers;

import org.junit.Test;
import static org.junit.Assert.*;


public class HomeControllerTest
{
    @Test
    public void testHome()
    {
        // Arrange
        HomeController controller = new HomeController();
        
        // Act
        String result = controller.home();
        
        // Assert
        assertNotNull(result);
    }
}