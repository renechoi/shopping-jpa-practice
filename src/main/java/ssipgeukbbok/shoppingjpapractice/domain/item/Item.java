package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.*;
import ssipgeukbbok.shoppingjpapractice.domain.AuditingFields;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.dto.response.ItemResponse;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="item")
@Entity
@Getter
@Setter
@ToString
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Item extends AuditingFields {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(name="price", nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long stockAmount;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;


    private Item(String itemName, Long price, Long stockAmount, String itemDetail, ItemSellStatus itemSellStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.itemName = itemName;
        this.price = price;
        this.stockAmount = stockAmount;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
    }

    public static Item of(String itemName, Long price, Long stockAmount, String itemDetail, ItemSellStatus itemSellStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        return new Item(itemName,price,stockAmount,itemDetail,itemSellStatus,registeredAt,updatedAt);
    }

    public void updateItem(ItemResponse itemResponse){
        this.itemName = itemResponse.getItemName();
        this.price = itemResponse.getPrice();
        this.stockAmount = itemResponse.getStockAmount();
        this.itemDetail = itemResponse.getItemDetail();
        this.itemSellStatus = itemResponse.getItemSellStatus();
    }
}
