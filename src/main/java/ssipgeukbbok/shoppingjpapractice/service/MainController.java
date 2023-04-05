package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssipgeukbbok.shoppingjpapractice.dto.ItemSearchDto;
import ssipgeukbbok.shoppingjpapractice.dto.MainItemDto;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/layout")
    public String test(){
        return "/layouts/layout1";
    }

    @GetMapping("/")
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        PageRequest pageRequest = PageRequest.of(page.orElse(0), 10);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageRequest);

        System.out.println("pageRequest = " + pageRequest);
        System.out.println("items = " + items);
        items.stream().forEach(System.out::println);

        System.out.println("itemSearchDto = " + itemSearchDto);


        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage",5);
        return "main";

    }


}
