package ssipgeukbbok.shoppingjpapractice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);


}
