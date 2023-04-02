package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.Data;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.domain.UserAccount;

@Data
public class UserAccountDto {

    private String name;

    private String email;

    private String password;

    private String address;

    private RoleType roleType;

    public UserAccount toEntity( ) {
        return UserAccount.of(
                name,
                email,
                password,
                address,
                roleType

        );
    }
}

