package ssipgeukbbok.shoppingjpapractice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.dto.ItemImageDto;

import java.util.ArrayList;
import java.util.List;

@Data(staticConstructor = "of")
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    private Long id;

    private String itemName;

    private Long price;

    private String itemDetail;

    private Long stockAmount;

    private ItemSellStatus itemSellStatus;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    private List<Long> itemImageIds = new ArrayList<>();


    public static ItemRequest of(Item entity, List<ItemImageDto> itemImageDtos, List<Long> itemImageIds) {
        return new ItemRequest(
                entity.getId(),
                entity.getItemName(),
                entity.getPrice(),
                entity.getItemDetail(),
                entity.getStockAmount(),
                entity.getItemSellStatus(),
                itemImageDtos,
                itemImageIds
        );
    }

}
