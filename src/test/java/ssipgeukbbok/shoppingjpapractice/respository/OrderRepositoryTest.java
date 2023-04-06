package ssipgeukbbok.shoppingjpapractice.respository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.OrderStatus;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
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
    @Autowired
    private UserAccountRepository userAccountRepository;


    @Test
    @DisplayName("연관관계 매핑 테스트 - order를 저장하면 그 안에 매핑된 orderItem까지 저장이 된다")
    @Rollback(value = false)
    public void cascadeTest() {

        // given
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 3; i++) {

            Item item = Item.of("item name", i * 10000L, i * 10L, "상세설명", ItemSellStatus.SELL, now, now);
            itemRepository.save(item);

            OrderItem orderItem = OrderItem.of(item, order, 1000L, 10L, now, now);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        entityManager.clear();

        // when & then
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        Assertions.assertEquals(3, savedOrder.getOrderItems().size());

    }

    @DisplayName("유효한 이메일과 페이지 정보를 전달할 때, 주문 목록을 반환한다")
    @Test
    @Transactional
    void shouldReturnListOfOrders_givenValidEmailAndPageable() {
        // given
        UserAccount userAccount = createUserAccount();
        List<Order> orders = createOrders(userAccount, 3);
        String email = userAccount.getEmail();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderDate").descending());
        // when
        List<Order> result = orderRepository.findOrders(email, pageable);

        // then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getUserAccount().getEmail()).isEqualTo(email);
    }

    private UserAccount createUserAccount() {
        UserAccount userAccount = UserAccount.of("user1@gmail.com", "user1@gmail.com", "user1@gmail.com", "user1@gmail.com", RoleType.USER);
        return userAccountRepository.saveAndFlush(userAccount);
    }

    private List<Order> createOrders(UserAccount userAccount, int numOrders) {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < numOrders; i++) {
            Item item1 = Item.of("item" + i, 1000L, 1000L, "detail", ItemSellStatus.SELL, LocalDateTime.now(), LocalDateTime.now());
            Item savedItem = itemRepository.saveAndFlush(item1);
            OrderItem orderItem1 = OrderItem.createOrderItem(savedItem, 120L);
            Order savedOrder = orderRepository.saveAndFlush(new Order(userAccount, OrderStatus.ORDER, List.of(orderItem1), LocalDateTime.now()));
            orders.add(savedOrder);
        }
        return orders;
    }


    @DisplayName("유효한 이메일이 주어졌을 때, 주문 수를 반환한다")
    @Test
    void countOrder_givenValidEmail_shouldReturnCountOfOrders() {
        // given
        UserAccount userAccount = userAccountRepository.saveAndFlush(UserAccount.of("user1@gmail.com", "user1@gmail.com", "user1@gmail.com", "user1@gmail.com", RoleType.USER));

        Item item1 = itemRepository.saveAndFlush(Item.of("item1", 1000L, 1000L, "detail", ItemSellStatus.SELL, LocalDateTime.now(), LocalDateTime.now()));
        Item item2 = itemRepository.saveAndFlush(Item.of("item2", 1000L, 1000L, "detail", ItemSellStatus.SELL, LocalDateTime.now(), LocalDateTime.now()));
        Item item3 = itemRepository.saveAndFlush(Item.of("item3", 1000L, 1000L, "detail", ItemSellStatus.SELL, LocalDateTime.now(), LocalDateTime.now()));
        OrderItem orderItem1 = OrderItem.createOrderItem(item1, 120L);
        OrderItem orderItem2 = OrderItem.createOrderItem(item2, 120L);
        OrderItem orderItem3 = OrderItem.createOrderItem(item3, 120L);
        Order order = orderRepository.saveAndFlush(new Order(
                userAccount,
                OrderStatus.ORDER,
                List.of(orderItem1, orderItem2, orderItem3),
                LocalDateTime.now()
        ));

        // when
        Long count = orderRepository.countOrder("user1@gmail.com");

        // then
        assertThat(count).isEqualTo(1);
    }



}
