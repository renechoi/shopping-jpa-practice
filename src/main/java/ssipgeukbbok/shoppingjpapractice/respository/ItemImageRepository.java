package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    List<ItemImage> findByItemIdOrderByIdAsc(Long itemId);

}
