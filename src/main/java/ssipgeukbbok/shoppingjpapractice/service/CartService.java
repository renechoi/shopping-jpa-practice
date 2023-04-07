package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.item.Cart;
import ssipgeukbbok.shoppingjpapractice.domain.item.CartItem;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.CartDetailDto;
import ssipgeukbbok.shoppingjpapractice.dto.CartItemDto;
import ssipgeukbbok.shoppingjpapractice.dto.CartOrderDto;
import ssipgeukbbok.shoppingjpapractice.dto.OrderDto;
import ssipgeukbbok.shoppingjpapractice.respository.CartItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.CartRepository;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final UserAccountRepository userAccountRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCartItems(CartItemDto cartItemDto, String email) {
        Item item = getItemById(cartItemDto.getItemId());
        UserAccount userAccount = getUserAccountByEmail(email);
        Cart cart = getCartByUserAccountId(userAccount.getId());

        if (cart == null) {
            cart = createCart(userAccount);
        }

        CartItem savedCartItem = getCartItemByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null) {
            savedCartItem.addItemCount(cartItemDto.getItemCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = createCartItem(cart, item, cartItemDto.getItemCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartItems(String email) {
        List<CartDetailDto> cartDetailDtos = new ArrayList<>();

        UserAccount userAccount = getUserAccountByEmail(email);
        Cart cart = getCartByUserAccountId(userAccount.getId());

        if (cart == null) {
            return cartDetailDtos;
        }

        return cartItemRepository.findCartDetailDtos(cart.getId());
    }


    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        UserAccount currentUserAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        UserAccount savedMember = cartItem.getCart().getUserAccount();

        return currentUserAccount.getEmail().equals(savedMember.getEmail());
    }


    public void updateCartItemCount(Long cartItemId, Long count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

//    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
//        List<OrderDto> orderDtos = new ArrayList<>();
//
//        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
//            CartItem cartItem = getCartItemById(cartOrderDto.getCartItemId());
//
//            OrderDto orderDto = new OrderDto();
//            orderDto.setItemId(cartItem.getItem().getId());
//            orderDto.setOrderCount(cartItem.getItemCount());
//            orderDtos.add(orderDto);
//        }
//
//        Long orderId = orderService.placeOrders(orderDtos, email);
//        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
//            CartItem cartItem = getCartItemById(cartOrderDto.getCartItemId());
//
//            cartItemRepository.delete(cartItem);
//        }
//
//        return orderId;
//    }

//    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
//        List<OrderDto> orderDtos = cartOrderDtoList.stream()
//                .map(cartOrderDto -> {
//                    CartItem cartItem = getCartItemById(cartOrderDto.getCartItemId());
//                    OrderDto orderDto = new OrderDto();
//                    orderDto.setItemId(cartItem.getItem().getId());
//                    orderDto.setOrderCount(cartItem.getItemCount());
//                    return orderDto;
//                })
//                .collect(Collectors.toList());
//
//        Long orderId = orderService.placeOrders(orderDtos, email);
//
//        List<Long> cartItemIds = cartOrderDtoList.stream()
//                .map(CartOrderDto::getCartItemId)
//                .collect(Collectors.toList());
//
//        cartItemRepository.deleteAllByIdIn(cartItemIds);
//
//        return orderId;
//    }


    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
        List<OrderDto> orderDtos = getOrdersFromCartItems(cartOrderDtoList);
        Long orderId = orderService.placeOrders(orderDtos, email);

        List<Long> cartItemIds = getCartItemIds(cartOrderDtoList);
        cartItemRepository.deleteAllByIdIn(cartItemIds);

        return orderId;
    }

    private List<OrderDto> getOrdersFromCartItems(List<CartOrderDto> cartOrderDtoList) {
        return cartOrderDtoList.stream()
                .map(cartOrderDto -> {
                    CartItem cartItem = getCartItemById(cartOrderDto.getCartItemId());
                    OrderDto orderDto = new OrderDto();
                    orderDto.setItemId(cartItem.getItem().getId());
                    orderDto.setOrderCount(cartItem.getItemCount());
                    return orderDto;
                })
                .collect(Collectors.toList());
    }

    private List<Long> getCartItemIds(List<CartOrderDto> cartOrderDtoList) {
        return cartOrderDtoList.stream()
                .map(CartOrderDto::getCartItemId)
                .collect(Collectors.toList());
    }


    private CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
    }


    private Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
    }

    private UserAccount getUserAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    private Cart getCartByUserAccountId(Long userAccountId) {
        return cartRepository.findByUserAccountId(userAccountId);
    }

    private Cart createCart(UserAccount userAccount) {
        Cart cart = Cart.createCart(userAccount);
        cartRepository.save(cart);
        return cart;
    }

    private CartItem getCartItemByCartIdAndItemId(Long cartId, Long itemId) {
        return cartItemRepository.findByCartIdAndItemId(cartId, itemId);
    }

    private CartItem createCartItem(Cart cart, Item item, Long itemCount) {
        return CartItem.createCartItem(cart, item, itemCount);
    }


}
