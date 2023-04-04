package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    private int orderPrice;

    private int count;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


    public OrderItem(Item item, Order order, int orderPrice, int count, LocalDateTime regTime, LocalDateTime updateTime) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    public static OrderItem of(Item item, Order order, int orderPrice, int count, LocalDateTime regTime, LocalDateTime updateTime){
        return new OrderItem(item, order, orderPrice, count, regTime, updateTime);
    }
}
