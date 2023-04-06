package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Getter;
import lombok.Setter;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.OrderStatus;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistoryDto {

    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus;

    private List<OrderItemDto> orderItemDtos = new ArrayList<>();

    public OrderHistoryDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    public OrderHistoryDto(Order order, List<OrderItemDto> orderItemDtos) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
        this.orderItemDtos = orderItemDtos;
    }

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtos.add(orderItemDto);
    }
}
