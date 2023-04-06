package ssipgeukbbok.shoppingjpapractice.domain.user;

import lombok.*;
import ssipgeukbbok.shoppingjpapractice.config.PasswordEncoderConfig;
import ssipgeukbbok.shoppingjpapractice.domain.AuditingFields;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor()
@Table(name = "user_account",
        indexes = {
                @Index(columnList = "email"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")})
@Entity
public class UserAccount extends AuditingFields {

    @Id // => pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // => db에 들어갈때 자동으로 생성된다. sequence 자동으로 올라가는
    private Long id; // Todo : 카카오톡 채팅방 -> chat gpt 설명 있음

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)  // Todo : 카카오톡 채팅방 -> chat gpt 설명 있음
    private RoleType roleType;

    private UserAccount(String name, String email, String password, String address, RoleType roleType, String createdBy, String modifiedBy) {
        this.name = name;
        this.email = email;
        this.password = encodePassword(password);   // 1234 -> 암호화 -> !@#%@#$asfasd@#asdf
        this.address = address;
        this.roleType = roleType;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    // 기존 코드 => 바깥이랑 연결이 많이 되어 있음 = 따라서 수정을 하면 안됨
    public static UserAccount of(String name, String email, String password, String address, RoleType roleType) {
        return UserAccount.of(name, email, password, address, roleType, name, name);
    }

    public static UserAccount of(String name, String email, String password, String address, RoleType roleType, String createdBy, String modifiedBy) {
        return new UserAccount(name, email, password, address, roleType, createdBy, modifiedBy);
    }

    private String encodePassword(String password) {
        return PasswordEncoderConfig.passwordEncoder().encode(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        UserAccount that = (UserAccount) o;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
