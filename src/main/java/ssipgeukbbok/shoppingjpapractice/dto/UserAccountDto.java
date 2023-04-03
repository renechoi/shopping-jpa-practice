package ssipgeukbbok.shoppingjpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.domain.UserAccount;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {

    private String name;
    private String email;
    private String password;
    private String address;
    private RoleType roleType;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

    public static UserAccountDto from (UserAccount userAccount){
        return new UserAccountDto(
                userAccount.getName(),
                userAccount.getEmail(),
                userAccount.getPassword(),
                userAccount.getAddress(),
                userAccount.getRoleType(),
                userAccount.getCreatedAt(),
                userAccount.getCreatedBy(),
                userAccount.getModifiedAt(),
                userAccount.getModifiedBy()
        );
    }

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

