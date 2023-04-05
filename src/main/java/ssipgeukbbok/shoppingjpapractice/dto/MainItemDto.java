package ssipgeukbbok.shoppingjpapractice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MainItemDto {
    private Long id;
    private String itemName;
    private String itemDetail;
    private String imageUrl;
    private Long price;

    @QueryProjection
    public MainItemDto(Long id, String itemName, String itemDetail, String imageUrl, Long price) {
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
        this.price = price;
    }


}
