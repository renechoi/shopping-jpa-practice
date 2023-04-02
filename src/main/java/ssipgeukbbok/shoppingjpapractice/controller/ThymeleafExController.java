package ssipgeukbbok.shoppingjpapractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssipgeukbbok.shoppingjpapractice.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.dto.ItemDto;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {

    public static final String ITEM_NAME = "테스트 상품1";

    @GetMapping("/ex02")
    public String thymeleafExample02(Model model) {

        ItemDto itemDto = ItemDto.of(
                ITEM_NAME,
                10000,
                "상세 설명",
                ItemSellStatus.SELL.name(),
                LocalDateTime.now(),
                LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";
    }
}

