package ssipgeukbbok.shoppingjpapractice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.OrderStatus;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.OrderDto;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.OrderRepository;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserAccountRepository userAccountRepository;


    private Item createItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000L);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockAmount(100L);
        return itemRepository.save(item);
    }

    private UserAccount createUserAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("test@test.com");
        return userAccountRepository.save(userAccount);
    }


    /**
     * Given:
     *
     * 상품이 생성되어 있다.
     * 사용자 계정이 생성되어 있다.
     * When:
     *
     * 상품의 아이디와 주문 수량을 담은 OrderDto, 사용자의 이메일을 파라미터로 하여 placeOrder() 메서드를 실행한다.
     * Then:
     *
     * 생성된 주문을 orderId를 통해 조회한다.
     * 조회된 주문의 총 주문 금액과 예상 주문 금액을 비교한다.
     */
    @Test
    @DisplayName("주문 테스트")
    public void order() {
        // given
        Item item = createItem();
        UserAccount userAccount = createUserAccount();

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderCount(10L);
        orderDto.setItemId(item.getId());

        // when
        Long orderId = orderService.placeOrder(orderDto, userAccount.getEmail());
        Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertEquals(1, savedOrder.getOrderItems().size());
        assertEquals(OrderStatus.ORDER, savedOrder.getOrderStatus());
        assertEquals(userAccount.getId(), savedOrder.getUserAccount().getId());

        OrderItem savedOrderItem = savedOrder.getOrderItems().get(0);
        assertEquals(item.getId(), savedOrderItem.getItem().getId());
        assertEquals(orderDto.getOrderCount(), savedOrderItem.getOrderCount());
        assertEquals(item.getPrice() * orderDto.getOrderCount(), savedOrder.getTotalPrice());
    }

    @Test
    @DisplayName("주문 생성 - 성공")
    void createOrder_Success() {
        // given
        Item item = createItem();
        UserAccount userAccount = createUserAccount();
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderCount(10L);
        orderDto.setItemId(item.getId());

        // when
        Long orderId = orderService.placeOrder(orderDto, userAccount.getEmail());
        Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertEquals(OrderStatus.ORDER, savedOrder.getOrderStatus());
        assertEquals(userAccount.getId(), savedOrder.getUserAccount().getId());
        assertEquals(1, savedOrder.getOrderItems().size());

        OrderItem savedOrderItem = savedOrder.getOrderItems().get(0);
        assertEquals(item.getId(), savedOrderItem.getItem().getId());
        assertEquals(orderDto.getOrderCount(), savedOrderItem.getOrderCount());
        assertEquals(item.getPrice() * orderDto.getOrderCount(), savedOrder.getTotalPrice());
    }


    @Test
    @DisplayName("주문 생성 - 실패: 상품이 존재하지 않음")
    void createOrder_Fail_ItemNotExist() {
        UserAccount userAccount = createUserAccount();

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderCount(200L);
        orderDto.setItemId(101L);

        assertThrows(EntityNotFoundException.class, () -> orderService.placeOrder(orderDto, userAccount.getEmail()));
    }

    @Test
    @DisplayName("주문 생성 - 실패: 사용자가 존재하지 않음")
    void createOrder_Fail_UserNotExist() {
        Item item = createItem();

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderCount(10L);
        orderDto.setItemId(item.getId());

        assertThrows(EntityNotFoundException.class, () -> orderService.placeOrder(orderDto, "not_exist@test.com"));
    }
}
