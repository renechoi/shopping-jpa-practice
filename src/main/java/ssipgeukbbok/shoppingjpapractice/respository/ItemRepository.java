package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;
import ssipgeukbbok.shoppingjpapractice.respository.qureydsl.ItemRepositoryCustom;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom
{

    List<Item> findByItemName(String itemName);

    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);



}
