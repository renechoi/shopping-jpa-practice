package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CartDetailDto {

    private Long cartItemId;

    private String itemName;

    private Long price;

    private Long itemCount;

    private String imageUrl;

    public CartDetailDto(Long cartItemId, String itemName, Long price, Long itemCount, String imageUrl) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.itemCount = itemCount;
        this.imageUrl = imageUrl;
    }
}
