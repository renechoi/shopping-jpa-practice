package ssipgeukbbok.shoppingjpapractice.dto.response;


import lombok.Data;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;

@Data
public class ItemSearchResponse {
    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery;


}
