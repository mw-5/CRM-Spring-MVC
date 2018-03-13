package controllers;

import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;


public class StartControllerTest
{
    @Test
    public void testStart()
    {
        // Arrange
        StartController controller = new StartController();
        
        // Act
        String result = controller.start(Locale.US);
        
        // Assert
        assertNotNull(result);
    }
}
