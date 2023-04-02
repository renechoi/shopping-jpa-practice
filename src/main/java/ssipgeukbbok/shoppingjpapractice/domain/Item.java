package ssipgeukbbok.shoppingjpapractice.domain;

import lombok.*;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="item")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="item_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(name="price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int itemAmount;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;


    private Item(String itemName, int price, int itemAmount, String itemDetail, ItemSellStatus itemSellStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.itemName = itemName;
        this.price = price;
        this.itemAmount = itemAmount;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
    }

    public static Item of(String itemName, int price, int itemAmount, String itemDetail, ItemSellStatus itemSellStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        return new Item(itemName,price,itemAmount,itemDetail,itemSellStatus,registeredAt,updatedAt);
    }
}
