package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ssipgeukbbok.shoppingjpapractice.domain.item.CartItem;
import ssipgeukbbok.shoppingjpapractice.dto.CartDetailDto;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new ssipgeukbbok.shoppingjpapractice.dto.CartDetailDto(cartItem.id, item.itemName, item.price, cartItem.itemCount, itemImage.imageUrl) from CartItem cartItem, ItemImage itemImage join cartItem.item item where cartItem.cart.id =:cartId and itemImage.item.id = cartItem.item.id and itemImage.representativeImageYn = 'Y' order by cartItem.createdAt desc")
    List<CartDetailDto> findCartDetailDtos(Long cartId);


    List<CartItem> deleteAllByIdIn(List<Long> cartIds);

}
