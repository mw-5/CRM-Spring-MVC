package controllers;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import model.Note;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import testCases.TestCase;
import testCases.TestCase.TestCustomer;
import testCases.TestCase.TestNote;


public class FrmNoteControllerTest
{
    private void insertMockedModel(FrmNoteController controller, model.Model m)
    {
        try
        {
            Field field = FrmNoteController.class.getDeclaredField("m");
            field.setAccessible(true);
            field.set(controller, m);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
    
    @Test
    public void testNewNote()
    {
        // Arrange
        FrmNoteController controller = new FrmNoteController();
        ExtendedModelMap vm = new ExtendedModelMap();
        
        // Act
        ModelAndView result = controller.newNote(TestCustomer.CID, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(null, vm.get("id"));
        assertEquals(TestCustomer.CID, vm.get("cid"));
    }
    
    @Test
    public void testEditNote()
    {
        // Arrange
        FrmNoteController controller = new FrmNoteController();
        ExtendedModelMap vm = new ExtendedModelMap();
        model.Model m = mock(model.Model.class);
        Note n = TestCase.getTestNote();
        when(m.getNote(TestNote.ID)).thenReturn(n);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.editNote(TestNote.ID, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestNote.ID, vm.get("id"));
        assertEquals(TestNote.CID, vm.get("cid"));
        assertEquals(TestNote.MEMO, vm.get("memo"));
        assertEquals(TestNote.CATEGORY, vm.get("category"));
        assertEquals(TestNote.ATTACHMENT, vm.get("attachment"));
        assertEquals(TestNote.CREATED_BY, vm.get("createdBy"));
        assertEquals(m.calendarToString(TestNote.getEntryDate()), vm.get("entryDate"));        
    }
    
    @Test
    public void testSubmit_noErrorsWithoutAttachment()
    {
        // Arrange
        FrmNoteController controller = new FrmNoteController();
        ExtendedModelMap vm = new ExtendedModelMap();
        BindingResult bindingResult = mock(BindingResult.class);
        Note n = TestCase.getTestNote();
        model.Model m = mock(model.Model.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.submit(n, bindingResult, vm);
        
        // Assert
        assertNotNull(result);
        verify(m, atLeastOnce()).persistNote(n);
    }
    
    @Test
    public void testSubmit_noErrorsWithAttachment() throws Exception
    {
        // Arrange
        // set up source file
        final String customersFolder = "customersFolder";
        final String testUploadFolder = "testUploadCRMweb";
        final String baseUri = System.getProperty("user.dir");
        Path path = Paths.get(baseUri + File.separator + testUploadFolder);
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
        FrmNoteController controller = new FrmNoteController();
        ExtendedModelMap vm = new ExtendedModelMap();
        BindingResult bindingResult = mock(BindingResult.class);
        Note n = TestCase.getTestNote();
        MultipartFile mpf = mock(MultipartFile.class);
        n.setAttachmentFile(mpf);
        model.Model m = mock(model.Model.class);
        Properties props = mock(Properties.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(m.getConfig(anyObject())).thenReturn(props);
        when(props.getProperty(customersFolder)).thenReturn(baseUri + File.separator + testUploadFolder + File.separator + customersFolder);
        when(mpf.getOriginalFilename()).thenReturn(TestNote.ATTACHMENT);
        byte[] arr = Files.readAllBytes(srcFile.toPath());
        when(mpf.getBytes()).thenReturn(arr);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.submit(n, bindingResult, vm);
        File dstFile = new File(baseUri + File.separator + testUploadFolder + File.separator + customersFolder + File.separator + TestCustomer.CID + File.separator + TestNote.ATTACHMENT);
        boolean dstFileExists = dstFile.exists();
        
        // clean up
        if (srcFile.exists())
        {
            srcFile.delete();
        }
        if (dstFile.exists())
        {
            dstFile.delete();
        }
        path = path.resolve(customersFolder).resolve(Integer.toString(TestCustomer.CID));
        String[] folders = {Integer.toString(TestCustomer.CID), customersFolder, testUploadFolder};
        for (String folder : folders)
        {
            if (path.toFile().exists() && path.endsWith(folder))
            {
                path.toFile().delete();
                path = path.getParent();
            }
        }
        
        // Assert
        assertNotNull(result);
        verify(mpf).getBytes();
        verify(m, atLeastOnce()).persistNote(n);
        assertTrue(dstFileExists);
    }
    
    @Test
    public void testSubmit_errors()
    {
        // Arrange
        FrmNoteController controller = new FrmNoteController();
        ExtendedModelMap vm = new ExtendedModelMap();
        BindingResult bindingResult = mock(BindingResult.class);
        Note n = TestCase.getTestNote();
        model.Model m = mock(model.Model.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        insertMockedModel(controller, m);
        
        // Act
        String result = controller.submit(n, bindingResult, vm);
        
        // Assert
        assertNotNull(result);
        assertEquals(TestNote.ID, vm.get("id"));
        assertEquals(TestNote.CID, vm.get("cid"));
        assertEquals(TestNote.MEMO, vm.get("memo"));
        assertEquals(TestNote.CATEGORY, vm.get("category"));
        assertEquals(TestNote.ATTACHMENT, vm.get("attachment"));
        assertEquals(TestNote.CREATED_BY, vm.get("createdBy"));
        assertEquals(m.calendarToString(TestNote.getEntryDate()), vm.get("entryDate"));
        verify(m, never()).persistNote(n);
    }
}
