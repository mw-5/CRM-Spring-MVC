package controllers;

import javax.ws.rs.QueryParam;
import model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;


@Controller
public class FrmCustomerController
{
    model.Model m = model.Model.getModel();
    
    @RequestMapping(value = "FrmCustomer_new", method = RequestMethod.GET)
    public String newCustomer()
    {
        return "FrmCustomer";
    }
    
    @RequestMapping(value = "FrmCustomer_edit", method = RequestMethod.GET)
    @QueryParam("cid")
    public String editCustomer(int cid, Model vm)
    {
        Customer customer = m.getCustomer(cid);
        vm.addAttribute("cid", cid);
        loadModel(customer, vm);
        
        return "FrmCustomer";
    }
    
    private void loadModel(Customer c, Model vm)
    {
        vm.addAttribute("company", c.getCompany());
        vm.addAttribute("address", c.getAddress());
        vm.addAttribute("zip", c.getZip());
        vm.addAttribute("city", c.getCity());
        vm.addAttribute("country", c.getCountry());
        vm.addAttribute("contractId", c.getContractId());
        vm.addAttribute("contractDate", m.calendarToString(c.getContractDate()));
    }
    
    @RequestMapping(value = "FrmCustomer_submit", method = RequestMethod.POST)
    public String submit(@Validated Customer customer, BindingResult result, Model vm)
    {
        if (result.hasErrors())
        {
            if (customer.getCid() != null)
            {
                vm.addAttribute("cid", customer.getCid());
            }
            loadModel(customer, vm);
            for (ObjectError err : result.getAllErrors())
            {
                System.out.println(err.toString() + "\n");
            }
            return "FrmCustomer";
        }
    
        m.persistCustomer(customer);
    
        return "redirect:/ListOfCustomers";
    }        
}