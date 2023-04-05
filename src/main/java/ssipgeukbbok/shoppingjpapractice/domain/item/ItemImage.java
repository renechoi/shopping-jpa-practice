package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ssipgeukbbok.shoppingjpapractice.domain.AuditingFields;

import javax.persistence.*;

@Entity
@Table(name = "item_image")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemImage extends AuditingFields {

    @Id
    @Column(name = "item_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    private String originalImageName;

    private String imageUrl;

    private String representativeImageYn;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private Item item;

    public void updateItemImage(String imageName, String originalImageName, String imageUrl) {
        this.imageName = imageName;
        this.originalImageName = originalImageName;
        this.imageUrl = imageUrl;
    }

    public ItemImage(Item item, String representativeImageYn) {
        this.item = item;
        this.representativeImageYn = representativeImageYn;
    }

    public static ItemImage of(Item item, int index) {
        return new ItemImage(item, index == 0 ? "Y" : "N");
    }





}
