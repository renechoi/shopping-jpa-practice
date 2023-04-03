package ssipgeukbbok.shoppingjpapractice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cart {

    @Id
    @Column(name ="cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;


    private Cart(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public static Cart of(UserAccount userAccount){
        return new Cart(userAccount);
    }
}
