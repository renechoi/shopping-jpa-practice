package ssipgeukbbok.shoppingjpapractice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("서비스 로직 - 회원 테스트")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserAccountServiceTest {

    /**
     * @InjectMocks와 @Mock는 Mockito 프레임워크에서 사용되는 애노테이션입니다.
     * 이 두 애노테이션은 Mockito에서 Mock 객체를 생성하고 관리하기 위한 것입니다.
     * @InjectMocks는 테스트 대상 객체를 Mock 객체로 만든 다음,
     * 해당 객체에 Mock 객체를 주입하는 데 사용됩니다.
     * 이 애노테이션은 테스트 대상 객체가 생성자를 통해 의존하는 객체를 자동으로 주입하므로,
     * 의존성 주입(Dependency Injection)을 쉽게 할 수 있습니다.
     * @Mock는 테스트 대상 객체가 의존하는 객체를 대체하기 위한 Mock 객체를 생성하기 위해 사용됩니다.
     * 이 애노테이션은 Mock 객체를 생성하기만 하고, 어떤 객체에 주입되는 것은 아닙니다.
     * <p>
     * 즉,
     * @InjectMocks는 테스트 대상 객체를 Mock 객체로 만들어 의존하는 객체를 주입하고,
     * @Mock는 테스트 대상 객체가 의존하는 객체를 대체하기 위한 Mock 객체를 생성하는 것입니다.
     */


    @InjectMocks
//    @Autowired
    private UserAccountService userAccountService;

//    @Autowired
    @Mock
    private UserAccountRepository userAccountRepository;


    @Test
    @DisplayName("존재하는 회원 email 검색 -> Optional 결과 값을 반환한다")
    void findTest1() throws Exception {

        //given
        String email = "user@user.com";
        given(userAccountRepository.findByEmail(email)).willReturn(Optional.of(createUserAccount(email)));

        // When
        Optional<UserAccountDto> result = userAccountService.searchUser(email);
        System.out.println(result.get());
        // Then
        assertThat(result).isPresent();
        then(userAccountRepository).should().findByEmail(email);
    }


    @Test
    @DisplayName("존재하지 않는 회원 email 검색 -> Optional 결과 값을 반환한다")
    void findTest2() throws Exception {

        //given
        String email = "user-not-exist@user.com";
        given(userAccountRepository.findByEmail(email)).willReturn(Optional.empty());

        // When
        Optional<UserAccountDto> result = userAccountService.searchUser(email);

        // Then
        assertThat(result).isEmpty();
        then(userAccountRepository).should().findByEmail(email);
    }


//
//    @Test
//    @DisplayName("회원 정보 입력 (가입 시도) -> 저장 후 Dto 객체를 반환한다")
//    void save2() {
//        // Given
//        UserAccount userAccount = createUserAccount("user-signingup@user.com");
//
//
//
//        // When
//        UserAccountDto result = userAccountService.save(
//                userAccount.getName(),
//                userAccount.getEmail(),
//                userAccount.getPassword(),
//                userAccount.getAddress(),
//                userAccount.getRoleType()
//        );
//
//        // Then
//        assertThat(result)
//                .hasFieldOrPropertyWithValue("name", userAccount.getName())
//                .hasFieldOrPropertyWithValue("email", userAccount.getEmail())
////                .hasFieldOrPropertyWithValue("password", userAccount.getPassword())
//                .hasFieldOrPropertyWithValue("address", userAccount.getAddress())
//                .hasFieldOrPropertyWithValue("roleType", userAccount.getRoleType());
//        then(userAccountRepository).should().save(userAccount);
//    }
//


    @Test
    @DisplayName("회원 정보 입력 (가입 시도) -> 저장 후 Dto 객체를 반환한다")
    void save() {

        // Given
        UserAccount userAccount = createUserAccount("user-signingup@user.com");

        given(userAccountRepository.save(any(UserAccount.class))).willReturn(userAccount);

        // When
        UserAccountDto result = userAccountService.save(
                userAccount.getName(),
                userAccount.getEmail(),
                userAccount.getPassword(),
                userAccount.getAddress(),
                userAccount.getRoleType()
        );
//
//        // Then
//        assertThat(result)
//                .hasFieldOrPropertyWithValue("name", userAccount.getName())
//                .hasFieldOrPropertyWithValue("email", userAccount.getEmail())
//                .hasFieldOrPropertyWithValue("password", userAccount.getPassword())
//                .hasFieldOrPropertyWithValue("address", userAccount.getAddress())
//                .hasFieldOrPropertyWithValue("roleType", userAccount.getRoleType());
//        then(userAccountRepository).should().save(userAccount);
    }

    @Test
    void isDuplicate() {
    }


    /**
     * 인자값으로 anyString()을 전달하려고 했으나 given 메서드에서는
     * 이를 사용할 수 없다
     *
     * @param email
     * @return
     */
    private UserAccount createUserAccount(String email) {
        return createUserAccount(email, email);
    }

    private UserAccount createUserAccount(String email, String createdBy) {
        return UserAccount.of(
                "name",
                email,
                "password",
                "address",
                RoleType.USER

        );
    }
}