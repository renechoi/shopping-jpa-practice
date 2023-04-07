package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;
import ssipgeukbbok.shoppingjpapractice.domain.item.OrderItem;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.OrderDto;
import ssipgeukbbok.shoppingjpapractice.dto.OrderHistoryDto;
import ssipgeukbbok.shoppingjpapractice.dto.OrderItemDto;
import ssipgeukbbok.shoppingjpapractice.respository.ItemImageRepository;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;
import ssipgeukbbok.shoppingjpapractice.respository.OrderRepository;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final UserAccountRepository userAccountRepository;
    private final OrderRepository orderRepository;
    private final ItemImageRepository itemImageRepository;


    public Long placeOrder(OrderDto orderDto, String email) {

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getOrderCount());

        List<OrderItem> orderItems = Collections.singletonList(orderItem);

        Order order = Order.createOrder(userAccount, orderItems);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    public Long placeOrders(List<OrderDto> orderDtoList, String email) {
        UserAccount userAccount = findUserAccountByEmail(email);
        List<OrderItem> orderItems = createOrderItems(orderDtoList);

        Order order = Order.createOrder(userAccount, orderItems);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    private List<OrderItem> createOrderItems(List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                .map(orderDto -> {
                    Item item = itemRepository.findById(orderDto.getItemId())
                            .orElseThrow(EntityNotFoundException::new);
                    return OrderItem.createOrderItem(item, orderDto.getOrderCount());
                })
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        UserAccount currentUser = findUserAccountByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        UserAccount savedUser = order.getUserAccount();

        return (currentUser == savedUser);
    }

    public void cancelOrder(Long orderId) {
        orderRepository.
                findById(orderId)
                .orElseThrow(EntityNotFoundException::new)
                .cancelOrder();
    }

    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
        List<OrderHistoryDto> orderHistoryDtos =
                orderRepository.findOrders(email, pageable).stream()
                        .map(this::createOrderHistoryDto)
                        .collect(Collectors.toList());

        Long totalOrderCounts = orderRepository.countOrder(email);
        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
    }


    private OrderHistoryDto createOrderHistoryDto(Order order) {
        OrderHistoryDto orderHistoryDto = new OrderHistoryDto(order);
        orderHistoryDto.setOrderItemDtos(createOrderItemDtos(order));
        return orderHistoryDto;
    }

    private List<OrderItemDto> createOrderItemDtos(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        return orderItems.stream()
                .map(this::createOrderItemDto)
                .collect(Collectors.toList());
    }

    private OrderItemDto createOrderItemDto(OrderItem orderItem) {
        ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
        String imageUrl = itemImage.getImageUrl();
        return new OrderItemDto(orderItem, imageUrl);
    }

    private UserAccount findUserAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }


}




















//
//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable){
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//
//        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();
//
//        for (Order order : orders) {
//            OrderHistoryDto orderHistoryDto = new OrderHistoryDto(order);
//            List<OrderItem> orderItems = order.getOrderItems();
//            for (OrderItem orderItem : orderItems) {
//                ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
//                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImage.getImageUrl());
//                orderHistoryDto.addOrderItemDto(orderItemDto);
//            }
//            orderHistoryDtos.add(orderHistoryDto);
//
//        }
//        return new PageImpl<OrderHistoryDto>(orderHistoryDtos, pageable, totalOrderCounts);
//    }
//

//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//
//        List<OrderHistoryDto> orderHistoryDtos = orders.stream()
//                .map(order -> {
//                    List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
//                            .map(orderItem -> {
//                                ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
//                                return new OrderItemDto(orderItem, itemImage.getImageUrl());
//                            })
//                            .collect(Collectors.toList());
//                    return new OrderHistoryDto(order, orderItemDtos);
//                })
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
//    }


//
//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();
//
//        for (Order order : orders) {
//            OrderHistoryDto orderHistoryDto = createOrderHistoryDto(order);
//            List<OrderItem> orderItems = order.getOrderItems();
//            for (OrderItem orderItem : orderItems) {
//                ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
//                OrderItemDto orderItemDto = createOrderItemDto(orderItem, itemImage.getImageUrl());
//                orderHistoryDto.addOrderItemDto(orderItemDto);
//            }
//            orderHistoryDtos.add(orderHistoryDto);
//        }
//
//        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
//    }
//
//    private OrderHistoryDto createOrderHistoryDto(Order order) {
//        return new OrderHistoryDto(order);
//    }
//
//    private OrderItemDto createOrderItemDto(OrderItem orderItem, String imageUrl) {
//        return new OrderItemDto(orderItem, imageUrl);
//    }


//
//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();
//
//        for (Order order : orders) {
//            OrderHistoryDto orderHistoryDto = createOrderHistoryDto(order);
//            List<OrderItem> orderItems = order.getOrderItems();
//            for (OrderItem orderItem : orderItems) {
//                OrderItemDto orderItemDto = createOrderItemDto(orderItem);
//                orderHistoryDto.addOrderItemDto(orderItemDto);
//            }
//            orderHistoryDtos.add(orderHistoryDto);
//        }
//
//        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
//    }
//
//
//
//    private OrderHistoryDto createOrderHistoryDto(Order order) {
//        return new OrderHistoryDto(order);
//    }
//
//    private OrderItemDto createOrderItemDto(OrderItem orderItem) {
//        ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
//        String imageUrl = itemImage.getImageUrl();
//        return new OrderItemDto(orderItem, imageUrl);
//    }


//
//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();
//
//        for (Order order : orders) {
//            OrderHistoryDto orderHistoryDto = createOrderHistoryDto(order);
//            orderHistoryDto.setOrderItemDtos(createOrderItemDtos(order));
//            orderHistoryDtos.add(orderHistoryDto);
//        }
//
//        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
//    }
//
//    private List<OrderItemDto> createOrderItemDtos(Order order) {
//        List<OrderItemDto> orderItemDtos = new ArrayList<>();
//        List<OrderItem> orderItems = order.getOrderItems();
//        for (OrderItem orderItem : orderItems) {
//            ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
//            String imageUrl = itemImage.getImageUrl();
//            OrderItemDto orderItemDto = new OrderItemDto(orderItem, imageUrl);
//            orderItemDtos.add(orderItemDto);
//        }
//        return orderItemDtos;
//    }
//
//    private OrderHistoryDto createOrderHistoryDto(Order order) {
//        return new OrderHistoryDto(order);
//    }


//
//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();
//
//        for (Order order : orders) {
//            OrderHistoryDto orderHistoryDto = createOrderHistoryDto(order);
//            orderHistoryDto.setOrderItemDtos(createOrderItemDtos(order));
//            orderHistoryDtos.add(orderHistoryDto);
//        }
//
//        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
//    }


//
//
//    public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
//        List<Order> orders = orderRepository.findOrders(email, pageable);
//        Long totalOrderCounts = orderRepository.countOrder(email);
//
//        List<OrderHistoryDto> orderHistoryDtos = orders.stream()
//                .map(this::createOrderHistoryDto)
//                .peek(dto -> dto.setOrderItemDtos(createOrderItemDtos(dto.getOrder())))
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
//    }
//
//    private List<OrderItemDto> createOrderItemDtos(Order order) {
//        return order.getOrderItems().stream()
//                .map(this::createOrderItemDto)
//                .collect(Collectors.toList());
//    }
//
//    private OrderHistoryDto createOrderHistoryDto(Order order) {
//        return new OrderHistoryDto(order);
//    }
//
//    private OrderItemDto createOrderItemDto(OrderItem orderItem) {
//        ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
//        String imageUrl = itemImage.getImageUrl();
//        return new OrderItemDto(orderItem, imageUrl);
//    }
//
//
//

