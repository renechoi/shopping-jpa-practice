package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.OrderDto;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.OrderRepository;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final UserAccountRepository userAccountRepository;
    private final OrderRepository orderRepository;


    public Long placeOrder(OrderDto orderDto, String email) {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        System.out.println("userAccount = " + userAccount);
        System.out.println("item = " + item);
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getStockAmount());
        List<OrderItem> orderItems = Collections.singletonList(orderItem);

        System.out.println("orderItems = " + orderItems);
        System.out.println("orderItem = " + orderItem);

        Order order = Order.createOrder(userAccount, orderItems);

        System.out.println("order = " + order);
        Order savedOrder = orderRepository.save(order);
        System.out.println("savedOrder = " + savedOrder);

        return savedOrder.getId();
    }




}