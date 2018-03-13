package controllers;

import java.util.List;
import model.Customer;
import model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ListOfCustomersController
{
    private Model model = Model.getModel();
    
    @RequestMapping("ListOfCustomers")
    public String listOfCustomers()
    {
        return "ListOfCustomers";
    }
    
    @RequestMapping("customers")
    public @ResponseBody List<Customer> getCustomers()
    {
        return model.getCustomers();
    }
}
