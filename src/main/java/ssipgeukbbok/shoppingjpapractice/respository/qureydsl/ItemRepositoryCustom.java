package ssipgeukbbok.shoppingjpapractice.respository.qureydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.dto.ItemSearchDto;
import ssipgeukbbok.shoppingjpapractice.dto.MainItemDto;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
