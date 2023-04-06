package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.util.StringUtils;
import ssipgeukbbok.shoppingjpapractice.domain.item.Cart;
import ssipgeukbbok.shoppingjpapractice.domain.item.CartItem;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.CartItemDto;
import ssipgeukbbok.shoppingjpapractice.respository.CartItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.CartRepository;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final UserAccountRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;


    public Long addCart(CartItemDto cartItemDto, String email) {
        Item item = getItemById(cartItemDto.getItemId());
        UserAccount member = getMemberByEmail(email);
        Cart cart = getCartByUserAccountId(member.getId());

        if (cart == null) {
            cart = createCart(member);
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

    private Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
    }

    private UserAccount getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    private Cart getCartByUserAccountId(Long userAccountId) {
        return cartRepository.findByUserAccountId(userAccountId);
    }

    private Cart createCart(UserAccount member) {
        Cart cart = Cart.createCart(member);
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