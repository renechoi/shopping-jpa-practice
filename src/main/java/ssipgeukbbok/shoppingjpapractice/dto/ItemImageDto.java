package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;

@Data
@NoArgsConstructor
public class ItemImageDto {

    private Long id;

    private String imageName;

    private String originalImageName;

    private String imageUrl;

    private String representativeImageYn;

    public ItemImageDto(String imageName, String originalImageName, String imageUrl, String representativeImageYn) {
        this.imageName = imageName;
        this.originalImageName = originalImageName;
        this.imageUrl = imageUrl;
        this.representativeImageYn = representativeImageYn;
    }

    public static ItemImageDto of(ItemImage itemImage){
        return ItemImageDto.of(itemImage.getImageName(), itemImage.getOriginalImageName(), itemImage.getImageUrl(), itemImage.getRepresentativeImageYn());
    }

    public static ItemImageDto of(String imageName, String originalImageName, String imageUrl, String representativeImageYn){
        return new ItemImageDto(imageName,originalImageName,imageUrl,representativeImageYn);
    }


}
