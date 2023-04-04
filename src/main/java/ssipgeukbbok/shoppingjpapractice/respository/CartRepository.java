package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssipgeukbbok.shoppingjpapractice.domain.item.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
