package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    @ToString.Exclude
    private Order order;

    private Long orderPrice;

    private Long stockAmount;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


    public OrderItem(Item item, Order order, Long orderPrice, Long stockAmount, LocalDateTime regTime, LocalDateTime updateTime) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.stockAmount = stockAmount;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    public static OrderItem of(Item item, Order order, Long orderPrice, Long stockAmount, LocalDateTime regTime, LocalDateTime updateTime){
        return new OrderItem(item, order, orderPrice, stockAmount, regTime, updateTime);
    }



    public static OrderItem createOrderItem(Item item, Long stockAmount){
        OrderItem orderItem = OrderItem.of(item, null, item.getPrice(), stockAmount, LocalDateTime.now(), LocalDateTime.now());
        item.removeStock(stockAmount);
        return orderItem;
    }

    public long getTotalPrice(){
        return orderPrice * stockAmount;
    }
}
