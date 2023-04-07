package ssipgeukbbok.shoppingjpapractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ssipgeukbbok.shoppingjpapractice.dto.CartDetailDto;
import ssipgeukbbok.shoppingjpapractice.dto.CartItemDto;
import ssipgeukbbok.shoppingjpapractice.dto.CartOrderDto;
import ssipgeukbbok.shoppingjpapractice.service.CartService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public ResponseEntity<?> addCartItem(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return badRequestResponse(bindingResult);
        }

        Long cartItemId;

        try {
            cartItemId = cartService.addCartItems(cartItemDto, principal.getName());
        } catch (Exception e) {
            return badRequestResponse(e.getMessage());
        }
        return okResponse(cartItemId);
    }

    @GetMapping("/cart")
    public String getCartItems(Principal principal, Model model) {
        List<CartDetailDto> cartItems = cartService.getCartItems(principal.getName());

        model.addAttribute("cartItems", cartItems);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> updateCartItem(@PathVariable("cartItemId") Long cartItemId, Long count, Principal principal) {
        if (count <= 0) {
            return new ResponseEntity<>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return forbiddenResponse("수정 권한이 없습니다.");
        }

        cartService.updateCartItemCount(cartItemId, count);
        return okResponse(cartItemId);
    }


    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return forbiddenResponse("삭제 권한이 없습니다.");
        }
        cartService.deleteCartItem(cartItemId);
        return okResponse(cartItemId);
    }

    @PostMapping("/cart/orders")
    public ResponseEntity<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
        List<CartOrderDto> cartOrderDtos = cartOrderDto.getCartOrderDtos();
        if (cartOrderDtos == null || cartOrderDtos.isEmpty()) {
            return forbiddenResponse("주문할 상품을 선택해주세요");
        }

        boolean hasInvalidCartItems = cartOrderDtos.stream()
                .anyMatch(cartOrder -> !cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName()));

        return hasInvalidCartItems ? forbiddenResponse("주문 권한이 없습니다.") : okResponse(cartService.orderCartItem(cartOrderDtos, principal.getName()));
    }

    private ResponseEntity<?> badRequestResponse(BindingResult bindingResult) {
        List<String> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(fieldErrors);
    }

    private ResponseEntity<?> badRequestResponse(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    private ResponseEntity<?> okResponse(Object body) {
        return ResponseEntity.ok().body(body);
    }

    private ResponseEntity<?> forbiddenResponse(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }
}
