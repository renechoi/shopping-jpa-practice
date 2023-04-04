package ssipgeukbbok.shoppingjpapractice.respository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {


    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("연관관계 매핑 테스트 - order를 저장하면 그 안에 매핑된 orderItem까지 저장이 된다")
    @Rollback(value = false)
    public void cascadeTest(){

        // given
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();

        for (int i =0; i<3; i++){

            Item item = Item.of("item name", i * 10000L, i * 10L, "상세설명", ItemSellStatus.SELL, now, now);
            itemRepository.save(item);

            OrderItem orderItem = OrderItem.of(item, order, 1000, 10, now, now);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        entityManager.clear();



        // when & then

        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);

        Assertions.assertEquals(3, savedOrder.getOrderItems().size());

    }


}