package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
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
import ssipgeukbbok.shoppingjpapractice.dto.CartDetailDto;
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
    public List<CartDetailDto> getCartItems(String email){
        List<CartDetailDto> cartDetailDtos = new ArrayList<>();

        UserAccount userAccount = getUserAccountByEmail(email);
        Cart cart = getCartByUserAccountId(userAccount.getId());

        if (cart == null){
            return cartDetailDtos;
        }

        return cartItemRepository.findCartDetailDtos(cart.getId());

    }




    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){
        UserAccount curMember = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        UserAccount savedMember = cartItem.getCart().getUserAccount();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void updateCartItemCount(Long cartItemId, Long count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
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