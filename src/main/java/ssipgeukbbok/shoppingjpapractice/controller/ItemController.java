package ssipgeukbbok.shoppingjpapractice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.dto.ItemSearchDto;
import ssipgeukbbok.shoppingjpapractice.dto.request.ItemRequest;
import ssipgeukbbok.shoppingjpapractice.dto.response.ItemResponse;
import ssipgeukbbok.shoppingjpapractice.service.ItemService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("ItemResponse", new ItemResponse());
        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(
            @Valid ItemResponse itemResponse,
            BindingResult bindingResult,
            Model model,
            @RequestParam("itemImgFile") List<MultipartFile> itemImageFiles) {

        if (bindingResult.hasErrors()) {     // 상품 등록시 필수 값이 없다면 다시 상품 등록 페이지로 전환
            return "item/itemForm";
        }

        // 상품 등록시 첫 번째 이미지가 없다면 에러 메시지와 함게 상품 등록 페이지로 전환
        if (itemImageFiles.get(0).isEmpty() && itemResponse.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemResponse, itemImageFiles);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다");
            return "item/itemForm";
        }

        log.info("상품 등록 완료 = {} ", itemResponse);
        return "redirect:/";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
        System.out.println("itemId = " + itemId);
        try {
            ItemRequest itemRequest = itemService.getItemDetail(itemId);
            System.out.println("itemRequest = " + itemRequest);
            model.addAttribute("ItemResponse", itemRequest);
        } catch (EntityNotFoundException e) {
            System.out.println("e = " + e);
            model.addAttribute("ItemResponse", "존재하지 않는 상품입니다");
            model.addAttribute("ItemResponse", new ItemResponse());
            return "item/itemForm";
        }
        return "item/itemForm";

    }


    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.orElse(0), 20);

        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/itemMng";
    }

}
