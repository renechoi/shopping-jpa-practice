package ssipgeukbbok.shoppingjpapractice.respository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserAccountRepositoryTest {

    private final UserAccountRepository userAccountRepository;

    public UserAccountRepositoryTest(@Autowired UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = userAccountRepository.count();

        // When
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("newUno", "pw", "null", "null", RoleType.USER));

        System.out.println("userAccount = " + userAccount);
        // Then
        assertThat(userAccountRepository.count()).isEqualTo(previousCount + 1);
    }


    @DisplayName("select 테스트")
    @Test
    void test() {
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        assertThat(userAccounts).isNotNull().hasSize(4);
    }
//
//    @DisplayName("update 테스트")
//    @Test
//    void givenUserAccountAndRoleType_whenUpdating_thenWorksFine() {
//        // Given
//        UserAccount adminAccount = userAccountRepository.getReferenceByEmail("uno");
//        adminAccount.addRoleType(RoleType.DEVELOPER);
//        adminAccount.addRoleTypes(List.of(RoleType.USER, RoleType.USER));
//        adminAccount.removeRoleType(RoleType.ADMIN);
//
//        // When
//        AdminAccount updatedAccount = adminAccountRepository.saveAndFlush(adminAccount);
//
//        // Then
//        assertThat(updatedAccount)
//                .hasFieldOrPropertyWithValue("userId", "uno")
//                .hasFieldOrPropertyWithValue("roleTypes", Set.of(RoleType.DEVELOPER, RoleType.USER));
//    }
////
//    @DisplayName("delete 테스트")
//    @Test
//    void givenUserAccount_whenDeleting_thenWorksFine() {
//        // Given
//        long previousCount = adminAccountRepository.count();
//        AdminAccount adminAccount = adminAccountRepository.getReferenceById("uno");
//
//        // When
//        adminAccountRepository.delete(adminAccount);
//
//        // Then
//        assertThat(adminAccountRepository.count()).isEqualTo(previousCount - 1);
//    }

}