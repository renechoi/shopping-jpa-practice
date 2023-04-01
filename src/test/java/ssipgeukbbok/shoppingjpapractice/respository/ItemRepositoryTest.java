package ssipgeukbbok.shoppingjpapractice.respository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssipgeukbbok.shoppingjpapractice.contstant.ItemSellStatus;
import ssipgeukbbok.shoppingjpapractice.entity.Item;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("리포지토리 테스트")
@SpringBootTest
@Slf4j
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

    @DisplayName("상품 저장 테스트")
    @Test
    void createItemTest() {
       // 순수한 테스트를 유지하기 위해서 => 얘에서 for문을 써서 데이터 베이스 셋을 생성해주는 방식을 사용하지 말자

        Item item3 = Item.of(
                "상품",
                1000,
                10000,
                "상세",
                ItemSellStatus.SELL,
                LocalDateTime.now(),
                LocalDateTime.now());

        Item savedItem = itemRepository.save(item3);
        log.info("savedItem = {} ", savedItem);
    }

    @DisplayName("상품명 조회 테스트")
    @Test
    void findByItemNameTest() {
        assertThat(itemRepository.findByItemName("상품1")).isNotNull();

        List<Item> items = itemRepository.findByItemName("상품1");

        Item foundItem = items.stream()
                .filter(item -> item.getItemName().equals("상품1"))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("찾는 상품이 없습니다"));

        assertThat(foundItem).isNotNull();
    }


    @DisplayName("상품명 or 상품 상세 테스트")
    @Test
    void findByItemNameOrItemDetailTest() {
        List<Item> items = itemRepository.findByItemNameOrItemDetail("상품1", "상품 상세10");

        assertThat(items).hasSize(3);
    }
}