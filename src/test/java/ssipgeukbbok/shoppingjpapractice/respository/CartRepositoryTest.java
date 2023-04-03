package ssipgeukbbok.shoppingjpapractice.respository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.Cart;
import ssipgeukbbok.shoppingjpapractice.domain.UserAccount;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CartRepositoryTest {

    private final CartRepository cartRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CartRepositoryTest(CartRepository cartRepository, UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.cartRepository = cartRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("연관관계 매핑 테스트 - 장바구니 조회시 회원을 함께 가져온다")
    void findByCartWithMemberTest() throws Exception{

        // given
        UserAccount userAccount = userAccountRepository.findByEmail("user@user.com")
                .orElse(UserAccount.of(
                        "lee",
                        "user@user.com",
                        "test",
                        "test",
                        RoleType.USER));

        Cart cart = Cart.of(userAccount);

        // when
        cartRepository.save(cart);
        entityManager.flush();
        entityManager.clear();

        // then
        assertThatCode( ()-> cartRepository.findById(
                        cart.getId())).doesNotThrowAnyException();
        assertEquals(
                cartRepository.findById(cart.getId()).get().getUserAccount().getId(),
                userAccount.getId());

    }

}