package ssipgeukbbok.shoppingjpapractice.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/layout")
    public String test(){
        return "/layouts/layout1";
    }
}
