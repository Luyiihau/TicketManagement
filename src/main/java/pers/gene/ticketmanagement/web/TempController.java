package pers.gene.ticketmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  //注意这里必须为Controller
public class TempController {

    @RequestMapping("/hello")
    public String testF2F() {
        return "indexaa.htasdml";

    }
}