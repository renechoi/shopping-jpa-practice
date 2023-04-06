package ssipgeukbbok.shoppingjpapractice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.dto.ItemImageDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data()
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다")
    private Long price;

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다")
    private Long stockAmount;

    private ItemSellStatus itemSellStatus;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    private List<Long> itemImageIds = new ArrayList<>();

    public static Item toEntity(String itemName, Long price, String itemDetail, Long stockAmount, ItemSellStatus itemSellStatus) {
        return Item.of(itemName, price, stockAmount, itemDetail, itemSellStatus, LocalDateTime.now(), LocalDateTime.now()
        );
    }

    public Item toEntity() {
        return Item.of(
                this.itemName,
                this.price,
                this.stockAmount,
                this.itemDetail,
                this.itemSellStatus,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public ItemResponse(String itemName, Long price, String itemDetail, Long stockAmount, ItemSellStatus itemSellStatus) {
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockAmount = stockAmount;
        this.itemSellStatus = itemSellStatus;
    }

    public static ItemResponse of(String itemName, Long price, String itemDetail, Long stockAmount, ItemSellStatus itemSellStatus) {
        return new ItemResponse(
                itemName, price, itemDetail, stockAmount, itemSellStatus
        );
    }

    public static ItemResponse of(Item item){
        return ItemResponse.of(item.getItemName(), item.getPrice(), item.getItemDetail(), item.getStockAmount(), item.getItemSellStatus());
    }




    public static ItemResponse of(Item entity, List<ItemImageDto> itemImageDtos, List<Long> itemImageIds) {
        return new ItemResponse(
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
