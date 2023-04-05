package ssipgeukbbok.shoppingjpapractice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.domain.item.Item;
import ssipgeukbbok.shoppingjpapractice.domain.item.ItemImage;
import ssipgeukbbok.shoppingjpapractice.dto.response.ItemResponse;
import ssipgeukbbok.shoppingjpapractice.respository.ItemImageRepository;
import ssipgeukbbok.shoppingjpapractice.respository.ItemRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ItemServiceTest {

    private final ItemService itemService;

    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;

    @Autowired
    public ItemServiceTest(ItemService itemService, ItemRepository itemRepository, ItemImageRepository itemImageRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.itemImageRepository = itemImageRepository;
    }


    List<MultipartFile> createMultipartFiles() {
        return IntStream.range(0, 5)
                .mapToObj(i -> new MockMultipartFile(
                        "src/test/resources/testFiles",
                        "image" + i + ".jpg",
                        "image/jpg",
                        new byte[]{1, 2, 3, 4}))
                .collect(Collectors.toList());
    }


//    @Test
//    @DisplayName("상품 등록 테스트")
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    void saveItem() throws Exception {
//        // Given
//        ItemResponse itemResponse = ItemResponse.of("테스트상품", 1000L, "상품 디테일", 1000L, ItemSellStatus.SELL);
//        List<MultipartFile> multipartFileList = createMultipartFiles();
//
//
//
//
//
//        // When
//        Long itemId = itemService.saveAll(itemResponse, multipartFileList);
//
//        // Then
//        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
//        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
//
//        assertEquals(itemResponse.getItemName(), item.getItemName());
//        assertEquals(itemResponse.getItemSellStatus(), item.getItemSellStatus());
//        assertEquals(itemResponse.getItemDetail(), item.getItemDetail());
//        assertEquals(itemResponse.getPrice(), item.getPrice());
//        assertEquals(itemResponse.getStockAmount(), item.getStockAmount());
//        // assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImages.get(0).getOriginalImageName());
//    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {

        /**
         * Given:
         *
         * 관리자 계정으로 로그인 되어 있음
         * 등록할 상품 정보가 주어짐
         * 등록할 상품에 대한 이미지 파일이 존재함
         * When:
         *
         * 상품 등록 요청을 보냄
         * Then:
         *
         * 상품 정보와 이미지 파일이 정상적으로 등록되었는지 확인
         */


        // Given
        ItemResponse itemResponse = ItemResponse.of("테스트상품", 1000L, "상품 디테일", 1000L, ItemSellStatus.SELL);
        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveAll(itemResponse, multipartFileList);

        // When & then
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        assertAll(
                () -> assertEquals(itemResponse.getItemName(), item.getItemName()),
                () -> assertEquals(itemResponse.getItemSellStatus(), item.getItemSellStatus()),
                () -> assertEquals(itemResponse.getItemDetail(), item.getItemDetail()),
                () -> assertEquals(itemResponse.getPrice(), item.getPrice()),
                () -> assertEquals(itemResponse.getStockAmount(), item.getStockAmount()),
                () -> assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImages.get(0).getImageName())
        );
    }



}