package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Data;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;

@Data
public class ItemSearchDto {
    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery;


}
