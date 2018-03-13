package controllers;

import javax.ws.rs.QueryParam;
import org.springframework.stereotype.Controller;
import model.ContactPerson;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FrmContactPersonController
{
    model.Model m = model.Model.getModel();
    
    @RequestMapping("FrmContactPerson_new")
    @QueryParam("cid")
    public String newContactPerson(int cid, Model vm)
    {
        vm.addAttribute("cid", cid);
        return "FrmContactPerson";                
    }
    
    @RequestMapping("FrmContactPerson_edit")
    @QueryParam("id")
    public String editContactPerson(int id, Model vm)
    {
        vm.addAttribute("id", id);
        
        ContactPerson cp = m.getEntityManager().find(ContactPerson.class, id);
        loadModel(cp, vm);
        
        return "FrmContactPerson";
    }
    
    private void loadModel(ContactPerson cp, Model vm)
    {
        vm.addAttribute("cid", cp.getCid());
        vm.addAttribute("forename", cp.getForename());
        vm.addAttribute("surname", cp.getSurname());
        vm.addAttribute("gender", cp.getGender());
        vm.addAttribute("email", cp.getEmail());
        vm.addAttribute("phone", cp.getPhone());
        vm.addAttribute("mainContact", cp.getMainContact());
    }
    
    @RequestMapping(value = "FrmContactPerson_submit", method = RequestMethod.POST)
    public String submit(@Validated ContactPerson cp, BindingResult result, Model vm)
    {
        if (result.hasErrors())
        {
            vm.addAttribute("id", cp.getCid());
            loadModel(cp, vm);
            return "FrmContactPerson";
        }
        
        m.persistContactPerson(cp);
        
        return "redirect:/Cockpit_search?cid=" + cp.getCid();
    }
}