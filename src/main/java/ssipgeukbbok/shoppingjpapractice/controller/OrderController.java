package ssipgeukbbok.shoppingjpapractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssipgeukbbok.shoppingjpapractice.dto.OrderDto;
import ssipgeukbbok.shoppingjpapractice.service.OrderService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Object> placeOrder(@RequestBody @Valid OrderDto orderDto,
                                             BindingResult bindingResult,
                                             Principal principal) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMsg);
        }

        try {
            Long orderId = orderService.placeOrder(orderDto, principal.getName());
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
