package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Getter;
import lombok.Setter;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;

@Getter @Setter
public class OrderItemDto {

    private String itemName;

    private Long orderCount;

    private Long orderPrice;

    private String imageUrl;

    public OrderItemDto(OrderItem orderItem, String imageUrl){
        this.itemName = orderItem.getItem().getItemName();
        this.orderCount = orderItem.getOrderCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imageUrl = imageUrl;
    }


}
