package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ssipgeukbbok.shoppingjpapractice.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName);

    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);




}
