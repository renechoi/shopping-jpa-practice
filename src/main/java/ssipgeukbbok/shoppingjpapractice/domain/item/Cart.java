package ssipgeukbbok.shoppingjpapractice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ssipgeukbbok.shoppingjpapractice.domain.user.UserAccount;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;


    private Cart(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public static Cart of(UserAccount userAccount){
        return new Cart(userAccount);
    }
}
