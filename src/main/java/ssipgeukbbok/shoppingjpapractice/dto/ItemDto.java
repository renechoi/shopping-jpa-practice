package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class ItemDto {

    private Long id;
    private String itemName;
    private Integer price;
    private String itemDetail;
    private String sellStatus;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    private ItemDto(String itemName, Integer price, String itemDetail, String sellStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.sellStatus = sellStatus;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
    }

    public static ItemDto of(String itemName, Integer price, String itemDetail, String sellStatus, LocalDateTime registeredAt, LocalDateTime updatedAt) {
        return new ItemDto(itemName, price, itemDetail, sellStatus, registeredAt, updatedAt);
    }

}
