package ssipgeukbbok.shoppingjpapractice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ssipgeukbbok.shoppingjpapractice.config.PasswordEncoderConfig;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;

import javax.persistence.*;

@Entity
@Table(name ="member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAccount {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private UserAccount(String name, String email, String password, String address, RoleType roleType) {
        this.name = name;
        this.email = email;
        this.password = encodePassword(password);
        this.address = address;
        this.roleType = roleType;
    }

    public static UserAccount of(String name, String email, String password, String address, RoleType roleType){
        return new UserAccount(name, email, password, address, roleType);
    }

    private String encodePassword(String password){
        return PasswordEncoderConfig.passwordEncoder().encode(password);

    }
}


