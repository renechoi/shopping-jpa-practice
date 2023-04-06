package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ssipgeukbbok.shoppingjpapractice.domain.AuditingFields;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "cart_item")
@Entity
@ToString
public class CartItem extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Long itemCount;


    public CartItem(Cart cart, Item item, Long itemCount) {
        this.cart = cart;
        this.item = item;
        this.itemCount = itemCount;
    }

    public CartItem() {
    }

    public static CartItem of(Cart cart, Item item, Long itemCount) {
        return new CartItem(cart, item, itemCount);
    }

    public static CartItem createCartItem(Cart cart, Item item, Long itemCount) {
        return CartItem.of(cart,item,itemCount);
    }

    public void addItemCount(Long itemCount){
        this.itemCount += itemCount;
    }

    public void updateCount(Long itemCount){
        this.itemCount = itemCount;
    }
}
