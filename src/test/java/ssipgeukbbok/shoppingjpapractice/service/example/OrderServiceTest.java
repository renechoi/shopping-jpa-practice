package ssipgeukbbok.shoppingjpapractice.service.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.OrderDto;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.OrderRepository;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;
import ssipgeukbbok.shoppingjpapractice.service.OrderService;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        orderService = new OrderService(itemRepository, userAccountRepository, orderRepository);
    }

    @Test
    @DisplayName("정상적인 주문이 이루어질 때, 주문 ID를 반환하는지 확인한다.")
    void order_ShouldReturnOrderId_WhenOrderIsSuccessful() {
        // given
        Long itemId = 1L;
        String email = "admin@admin.com";
        Long orderCount = 2L;
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(itemId);
        orderDto.setStockAmount(orderCount);
        Item item = new Item();
        item.setId(itemId);
        item.setStockAmount(10L);
        UserAccount userAccount = UserAccount.of("name", "admin@admin.com", "1234", "1234", RoleType.ADMIN);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item));
        Mockito.when(userAccountRepository.findByEmail(email)).thenReturn(java.util.Optional.of(userAccount));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(new Order());

        // when
        Long orderId = orderService.placeOrder(orderDto, email);
        System.out.println("orderId = " + orderId);

        // then
        assertThat(orderId).isNotNull();
    }


    @Test
    @DisplayName("주어진 email과 매칭되는 UserAccount가 없을 경우, EntityNotFoundException이 발생한다.")
    void order_ShouldThrowEntityNotFoundException_WhenUserAccountNotFound() {
        // given
        Long itemId = 1L;
        String email = "test@example.com";
        Long orderCount = 2L;
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(itemId);
        orderDto.setStockAmount(orderCount);
        Item item = new Item();
        item.setId(itemId);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item));
        Mockito.when(userAccountRepository.findByEmail(email)).thenReturn(java.util.Optional.empty());

        // when, then
        assertThatThrownBy(() -> orderService.placeOrder(orderDto, email)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("주어진 itemId와 매칭되는 Item이 없을 경우, EntityNotFoundException이 발생한다.")
    void order_ShouldThrowEntityNotFoundException_WhenItemNotFound() {
        // given
        Long itemId = 1L;
        String email = "admin@admin.com";
        Long orderCount = 2L;
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(itemId);
        orderDto.setStockAmount(orderCount);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.empty());
        Mockito.when(userAccountRepository.findByEmail(email)).thenReturn(java.util.Optional.of(new UserAccount()));

        // when, then
        assertThatThrownBy(() -> orderService.placeOrder(orderDto, email)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("주문 수량이 0 이하일 경우, IllegalArgumentException이 발생한다.")
    void order_ShouldThrowIllegalArgumentException_WhenOrderCountIsLessThanOrEqualToZero() {
        // given
        Long itemId = 1L;
        String email = "admin@admin.com";
        Long orderCount = 0L;
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(itemId);
        orderDto.setStockAmount(orderCount);

        // when, then
        assertThatThrownBy(() -> orderService.placeOrder(orderDto, email)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Item과 OrderItem을 생성하는데 문제가 있을 경우, NullPointerException이 발생한다.")
    void order_ShouldThrowNullPointerException_WhenItemOrOrderItemCreationFails() {
        // given
        Long itemId = 1L;
        String email = "admin@admin.com";
        Long orderCount = 2L;
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(itemId);
        orderDto.setStockAmount(orderCount);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        Mockito.when(userAccountRepository.findByEmail(email)).thenReturn(java.util.Optional.of(new UserAccount()));

        // when, then
        assertThatThrownBy(() -> orderService.placeOrder(orderDto, email)).isInstanceOf(NullPointerException.class);
    }
}



