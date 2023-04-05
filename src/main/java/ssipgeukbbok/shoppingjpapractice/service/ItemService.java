package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;
import ssipgeukbbok.shoppingjpapractice.dto.response.ItemResponse;
import ssipgeukbbok.shoppingjpapractice.respository.ItemImageRepository;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    @Value("${itemImageLocation}")
    private String itemImageLocation;

    private final FileService fileService;

    public Long saveAll(ItemResponse itemResponse, List<MultipartFile> itemImageFiles) {
        Item item = itemRepository.save(itemResponse.toEntity());

        List<ItemImage> itemImages = convertToItemImages(itemImageFiles, item);
        itemImageRepository.saveAll(itemImages);

        return item.getId();
    }

    private List<ItemImage> convertToItemImages(List<MultipartFile> itemImageFiles, Item item) {
        return IntStream.range(0, itemImageFiles.size())
                .mapToObj(i -> updateItemImage(ItemImage.of(item, i), itemImageFiles.get(i)))
                .collect(Collectors.toList());
    }

    private ItemImage updateItemImage(ItemImage itemImage, MultipartFile itemImageFile) {
        String originalImageName = itemImageFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalImageName)) {
            return itemImage;
        }

        try {
            String imageName = fileService.uploadFiles(itemImageLocation, originalImageName, itemImageFile.getBytes());
            String imageUrl = "/images/item/" + imageName;
            itemImage.updateItemImage(originalImageName, imageName, imageUrl);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return itemImage;
    }


}



