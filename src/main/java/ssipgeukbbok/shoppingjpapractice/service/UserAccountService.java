package ssipgeukbbok.shoppingjpapractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;
import ssipgeukbbok.shoppingjpapractice.exception.UserAccountDuplicateException;
import ssipgeukbbok.shoppingjpapractice.respository.UserAccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountDto save(String name, String email, String password, String address, RoleType roleType){

        if (isDuplicate(email)){
            throw new UserAccountDuplicateException();
        }

        UserAccount userAccount = userAccountRepository.save(UserAccount.of(name, email, password, address, roleType));
        return UserAccountDto.from(userAccount);
    }

    private boolean isDuplicate(String email) {
        return userAccountRepository.findByEmail(email).isPresent();
    }

    public Optional<UserAccountDto> searchUser(String email) {
        return userAccountRepository.findByEmail(email).map(UserAccountDto::from);
    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<UserAccount> foundUser = userAccountRepository.findByEmail(email);
//
//        if (foundUser.isEmpty()) {
//            throw new UsernameNotFoundException("찾는 회원이 없습니다");
//        }
//        UserAccount userAccount = foundUser.get();
//
//        return User.builder()
//                .username(userAccount.getName())
//                .password(userAccount.getPassword())
//                .roles(userAccount.getRoleType()
//                        .getRoleName())
//                .build();
//    }

}
