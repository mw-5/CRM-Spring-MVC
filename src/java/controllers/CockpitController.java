package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import model.ContactPerson;
import model.Customer;
import model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CockpitController
{
    model.Model m = model.Model.getModel();
    
    @RequestMapping(value = "Cockpit", method = RequestMethod.GET)
    public String cockpit(Locale locale)
    {
        return "Cockpit";
    }
    
    @RequestMapping(value = "Cockpit_search", method = RequestMethod.GET)
    @QueryParam("cid")
    public String search(int cid, Locale locale, Model vm)
    {
        Customer customer = m.getCustomer(cid);
        
        if (customer != null)
        {
            vm.addAttribute("cid", cid);
            vm.addAttribute("company", customer.getCompany());
            vm.addAttribute("address", customer.getAddress());
            vm.addAttribute("zip", customer.getZip());
            vm.addAttribute("city", customer.getCity());
            vm.addAttribute("country", customer.getCountry());
            vm.addAttribute("contractId", customer.getContractId());
            vm.addAttribute("contractDate", m.calendarToString(customer.getContractDate()));
        }
        
        return "Cockpit";
    }
    
    @RequestMapping(value = "contact_persons", method = RequestMethod.GET)
    @QueryParam("cid")
    public @ResponseBody List<ContactPerson> getContactPersons(int cid)
    {
        return m.getContactPersons(cid);
    }
    
    @RequestMapping(value = "notes", method = RequestMethod.GET)
    @QueryParam("cid")
    public @ResponseBody List<Note> getNotes(int cid)
    {
        return m.getNotes(cid);
    }
    
    @Autowired
    ServletContext context;
    
    @RequestMapping(value = "Attachment", method = RequestMethod.GET)
    @QueryParam("id")
    public void getFile(int id, HttpServletResponse response)
    {
        try
        {
            Note note = m.getNote(id);
            String pathFile = m.getConfig(context).getProperty("customersFolder") + File.separator + note.getCid() + File.separator + note.getAttachment();
            InputStream is = new FileInputStream(new File(pathFile));
            response.addHeader("filename", note.getAttachment());
            response.addHeader("Content-Disposition", "attachment; filename=\"" + note.getAttachment() + "\"");
            FileCopyUtils.copy(is, response.getOutputStream());
        }
        catch(Exception e)
        {
            try
            {
                response.sendError(404);
            }
            catch(Exception ex)
            {
                System.out.println(e.toString());
            }
        }
    }
}
