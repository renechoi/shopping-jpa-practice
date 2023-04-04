package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssipgeukbbok.shoppingjpapractice.domain.item.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
