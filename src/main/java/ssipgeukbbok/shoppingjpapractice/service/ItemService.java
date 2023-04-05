package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;
import ssipgeukbbok.shoppingjpapractice.dto.ItemImageDto;
import ssipgeukbbok.shoppingjpapractice.dto.ItemSearchDto;
import ssipgeukbbok.shoppingjpapractice.dto.request.ItemRequest;
import ssipgeukbbok.shoppingjpapractice.dto.response.ItemResponse;
import ssipgeukbbok.shoppingjpapractice.respository.ItemImageRepository;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;

import javax.persistence.EntityNotFoundException;
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

    public Long saveItem(ItemResponse itemResponse, List<MultipartFile> itemImageFiles) {
        Item item = itemRepository.save(itemResponse.toEntity());

        List<ItemImage> itemImages = createItemImages(itemImageFiles, item);
        itemImageRepository.saveAll(itemImages);

        return item.getId();
    }

    public Long updateItem(ItemResponse itemResponse, List<MultipartFile> itemImageFiles){
        Item item = itemRepository.findById(itemResponse.getId()).orElseThrow(EntityNotFoundException::new);

        item.updateItem(itemResponse);

        List<Long> itemImageIds = itemResponse.getItemImageIds();

        return 0L;

    }


    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }


    public ItemRequest getItemDetail(Long itemId){
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImageDto> itemImageDtos = itemImages.stream()
                .map(ItemImageDto::of)
                .collect(Collectors.toList());

        List<Long> itemImageIds = itemImages.stream()
                .mapToLong(ItemImage::getId).boxed().collect(Collectors.toList());

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        System.out.println("itemRequest = " + item);
        return ItemRequest.of(item, itemImageDtos, itemImageIds);
    }


    private List<ItemImage> createItemImages(List<MultipartFile> itemImageFiles, Item item) {
        return IntStream.range(0, itemImageFiles.size())
                .mapToObj(i -> convertToItemImages(ItemImage.of(item, i), itemImageFiles.get(i)))
                .collect(Collectors.toList());
    }

    private ItemImage convertToItemImages(ItemImage itemImage, MultipartFile itemImageFile) {
        String originalImageName = itemImageFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalImageName)) {
            return itemImage;
        }

        String imageName = saveAtLocal(itemImageFile, originalImageName);
        String imageUrl = "/images/item/" + imageName;
            itemImage.updateItemImage(originalImageName, imageName, imageUrl);

        return itemImage;
    }

    private String saveAtLocal(MultipartFile itemImageFile, String originalImageName) {
        try {
            return fileService.uploadFiles(itemImageLocation, originalImageName, itemImageFile.getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


}



