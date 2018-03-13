package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import javax.servlet.ServletContext;
import javax.ws.rs.QueryParam;
import model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FrmNoteController
{
    model.Model m = model.Model.getModel();
    
    @Autowired
    ServletContext context;
    
    @RequestMapping("FrmNote_new")
    @QueryParam("cid")
    public ModelAndView newNote(int cid, Model vm)
    {
        vm.addAttribute("cid", cid);
        
        return new ModelAndView("FrmNote", "vm", vm);                        
    }
    
    @RequestMapping("FrmNote_edit")
    @QueryParam("id")
    public String editNote(int id, Model vm)
    {
        Note note = m.getNote(id);
        vm.addAttribute("id", id);
        loadModel(note, vm);
        
        return "FrmNote";
    }
    
    private void loadModel(Note note, Model vm)
    {
        vm.addAttribute("cid", note.getCid());
        vm.addAttribute("memo", note.getMemo());
        vm.addAttribute("category", note.getCategory());
        vm.addAttribute("attachment", note.getAttachment());
        vm.addAttribute("createdBy", note.getCreatedBy());
        vm.addAttribute("entryDate", m.calendarToString(note.getEntryDate()));        
    }
    
    @RequestMapping(value = "FrmNote_submit", method = RequestMethod.POST)
    public String submit(@Validated Note note, BindingResult result, Model vm)
    {
        if (result.hasErrors())
        {
            vm.addAttribute("id", note.getId());
            loadModel(note, vm);
            for (ObjectError err : result.getAllErrors())
            {
                System.out.println(err.toString() + "\n");
            }
            return "FrmNote";                    
        }
        
        if (!copyAttachment(note))
        {
            if (note.getAttachmentFile() != null && !note.getAttachmentFile().getOriginalFilename().equals(""))
            {
                vm.addAttribute("id", note.getId());
                loadModel(note, vm);
                System.out.println("upload failed");
                return "FrmNote";
            }
        }
        
        if (note.getCreatedBy() == null || note.getCreatedBy().equals("")){ note.setCreatedBy("anonymous"); }
        if (note.getEntryDate() == null){ note.setEntryDate(Calendar.getInstance()); }
        
        m.persistNote(note);
        
        return "redirect:/Cockpit_search?cid=" + note.getCid();
    }
    
    private boolean copyAttachment(Note note)
    {
        // Is implemented in controller instead of model because
        // model has no access to WEB-INF folder where Config.properties is placed.
        
        MultipartFile multipartFile = note.getAttachmentFile();
        if (multipartFile == null)
        {
            return false;
        }
        else
        {
            try
            {
                Path path = Paths.get(m.getConfig(context).getProperty("customersFolder") + File.separator + note.getCid());
                if (!path.toFile().exists())
                {
                    path.toFile().mkdirs();
                }
                FileCopyUtils.copy(multipartFile.getBytes(), new File(path.toString() + File.separator + multipartFile.getOriginalFilename()));
                note.setAttachment(multipartFile.getOriginalFilename());
                return true;
            }
            catch(IOException e)
            {
                return false;
            }
        }
    }
}
