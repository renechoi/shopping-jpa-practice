package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.OrderStatus;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    @ToString.Exclude
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;    // 주문일

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    /**
     * 양방향 참조 관계
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Order(UserAccount userAccount, OrderStatus orderStatus, List<OrderItem> orderItems, LocalDateTime orderDate) {
        this.userAccount = userAccount;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
    }



    public static Order createOrder(UserAccount userAccount, List<OrderItem> orderItems){
        Order order = new Order();
        order.setUserAccount(userAccount);
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        orderItems.forEach(order::addOrderItem);
        return order;

    }

    public Long getTotalPrice(){
        return orderItems.stream().mapToLong(OrderItem::getTotalPrice).sum();

    }


    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;
        orderItems.forEach(OrderItem::cancel);
    }
}

