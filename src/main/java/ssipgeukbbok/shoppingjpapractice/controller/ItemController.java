package ssipgeukbbok.shoppingjpapractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssipgeukbbok.shoppingjpapractice.dto.request.ItemRequestDto;

@Controller
public class ItemController {

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemRequestDto());
        return "/item/itemForm";
    }


}
