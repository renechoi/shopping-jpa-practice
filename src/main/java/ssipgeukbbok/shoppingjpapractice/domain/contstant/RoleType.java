package ssipgeukbbok.shoppingjpapractice.domain.contstant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {

    USER("ROLE_USER", "사용자"),
    SELLER("ROLE_SELLER", "판매자"),
    ADMIN("ROLE_ADMIN", "관리자")
    ;

    @Getter private final String roleName;
    @Getter private final String description;

}