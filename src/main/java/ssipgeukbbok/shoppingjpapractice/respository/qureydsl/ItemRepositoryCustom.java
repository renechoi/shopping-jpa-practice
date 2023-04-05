package ssipgeukbbok.shoppingjpapractice.respository.qureydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.dto.ItemSearchDto;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
