package ssipgeukbbok.shoppingjpapractice.dto.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ssipgeukbbok.shoppingjpapractice.domain.AuditingFields;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAccountPrincipal extends AuditingFields implements UserDetails{
    private String name;
    private String password;
    private String email;
    private String address;
    private Collection<? extends GrantedAuthority> authorities;

    public UserAccountPrincipal(String name, String password, String email, String address,  Set<? extends GrantedAuthority> roleType) {
        this.name = email;
        this.password = password;
        this.email = email;
        this.address = address;
        this.authorities = roleType;
    }

    public static UserAccountPrincipal of(String name, String password, String email, String address, Set<RoleType> roleTypes){
        return new UserAccountPrincipal(
                email,
                password,
                email,
                address,
                roleTypes.stream()
                        .map(RoleType::getRoleName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static UserAccountPrincipal from(UserAccountDto dto){
        return UserAccountPrincipal.of(
                dto.getEmail(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getAddress(),
                Set.of(dto.getRoleType())
        );
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
