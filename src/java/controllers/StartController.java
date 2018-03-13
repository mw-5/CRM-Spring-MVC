package controllers;

import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StartController
{
    @RequestMapping("Start")
    public String start(Locale locale)
    {
        return "Start";
    }
}