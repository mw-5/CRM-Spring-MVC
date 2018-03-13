package controllers;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import model.ContactPerson;
import model.Customer;
import model.Note;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import testCases.TestCase;
import testCases.TestCase.TestContactPerson;
import testCases.TestCase.TestCustomer;
import testCases.TestCase.TestNote;


public class CockpitControllerTest 
{
    private void insertMockedModel(CockpitController controller, model.Model m)
    {
        try
        {
            Field field = CockpitController.class.getDeclaredField("m");
            field.setAccessible(true);
            field.set(controller, m);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testCockpit()
    {
        // Arrange
        CockpitController controller = new CockpitController();
        
        // Act
        String result = controller.cockpit(Locale.US);
        
        // Assert
        assertNotNull(result);
    }
    
    @Test
    public void testSearch()
    {
        // Arrange
        CockpitController controller = new CockpitController();
        model.Model m = mock(model.Model.class);
        Customer customer = TestCase.getTestCustomer();
        when(m.getCustomer(TestCustomer.CID)).thenReturn(customer);
        insertMockedModel(controller, m);
        ExtendedModelMap vm = new ExtendedModelMap();
        
        // Act
        String result = controller.search(TestCustomer.CID, Locale.US, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestCustomer.CID, vm.get("cid"));
        assertEquals(TestCustomer.ADDRESS, vm.get("address"));
        assertEquals(TestCustomer.ZIP, vm.get("zip"));
        assertEquals(TestCustomer.CITY, vm.get("city"));
        assertEquals(TestCustomer.COUNTRY, vm.get("country"));
        assertEquals(TestCustomer.CONTRACT_ID, vm.get("contractId"));
        assertEquals(m.calendarToString(TestCustomer.getContractDate()), vm.get("contractDate"));
        assertEquals(TestCustomer.COMPANY, vm.get("company"));
    }
    
    @Test
    public void testGetContactPersons()
    {
        // Arrange
        CockpitController controller = new CockpitController();
        model.Model m = mock(model.Model.class);
        List<ContactPerson> resultList = new ArrayList<>();
        resultList.add(TestCase.getTestContactPerson());
        when(m.getContactPersons(TestCustomer.CID)).thenReturn(resultList);
        insertMockedModel(controller, m);
        
        // Act
        List<ContactPerson> list = controller.getContactPersons(TestCustomer.CID);
        
        // Assert
        ContactPerson contactPerson = list.get(0);
        assertEquals(TestContactPerson.ID, contactPerson.getId().intValue());        
    }
    
    @Test
    public void testGetNotes()
    {
        // Arrange
        CockpitController controller = new CockpitController();
        model.Model m = mock(model.Model.class);
        List<Note> resultList = new ArrayList<>();
        resultList.add(TestCase.getTestNote());
        when(m.getNotes(TestCustomer.CID)).thenReturn(resultList);
        insertMockedModel(controller, m);
        
        // Act
        List<Note> list = controller.getNotes(TestCustomer.CID);
        
        // Assert
        Note note = list.get(0);
        assertEquals(TestNote.ID, note.getId().intValue());
    }
    
    @Test
    public void testGetFile_notFound()
    {
        // Arrange
        CockpitController controller = new CockpitController();
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        // Act
        controller.getFile(0, response);
        
        // Assert
        try
        {
            verify(response, atLeastOnce()).sendError(404);                        
        }
        catch(Exception e)
        {
            fail("404 error hasn't been send");
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testGetFile_found() throws Exception
    {
        // Arrange
        // set up source file
        final String customersFolder = "customersFolder";
        final String testDownloadFolder = "testDownloadCRMweb";
        final String baseUri = System.getProperty("user.dir");
        Path path = Paths.get(baseUri + File.separator + testDownloadFolder + File.separator + customersFolder + File.separator + TestNote.CID);
        if (!path.toFile().exists())
        {
            path.toFile().mkdirs();
        }
        File srcFile = path.resolve(TestNote.ATTACHMENT).toFile();
        if (!srcFile.exists())
        {
            srcFile.createNewFile();
        }
        // set up controller and mocks
        CockpitController controller = new CockpitController();
        HttpServletResponse response = mock(HttpServletResponse.class);
        Note note = TestCase.getTestNote();
        model.Model model = mock(model.Model.class);
        Properties props = mock(Properties.class);
        ServletOutputStream os = mock(ServletOutputStream.class);
        when(model.getNote(TestNote.ID)).thenReturn(note);
        when(model.getConfig(anyObject())).thenReturn(props);
        when(props.getProperty(customersFolder)).thenReturn(baseUri + File.separator + testDownloadFolder + File.separator + customersFolder);
        when(response.getOutputStream()).thenReturn(os);
        insertMockedModel(controller, model);
        
        // Act
        controller.getFile(TestNote.ID, response);
        
        // clean up
        if (srcFile.exists())
        {
            srcFile.delete();
        }
        String[] folders = {Integer.toString(TestCustomer.CID), customersFolder, testDownloadFolder};
        for (String folder : folders)
        {
            if (path.toFile().exists() && path.endsWith(folder))
            {
                path.toFile().delete();
                path = path.getParent();
            }
        }
        
        // Assert
        verify(response, atLeastOnce()).addHeader("filename", note.getAttachment());
        verify(response).addHeader("Content-Disposition", "attachment; filename=\"" + note.getAttachment() + "\"");
        //verifyZeroInteractions(os);
        assertFalse(mockingDetails(os).getInvocations().isEmpty()); // verify that any interaction took place with output stream
    }
}