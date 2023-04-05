package ssipgeukbbok.shoppingjpapractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ssipgeukbbok.shoppingjpapractice.dto.security.UserAccountPrincipal;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {


    /**
     * 아래 코드는 Spring Data JPA의 Audit 기능을 활성화하고,
     * 데이터베이스의 각 row에 대한 생성일(createdDate), 수정일(modifiedDate) 및 생성자(createdBy), 수정자(modifiedBy)
     * 정보를 자동으로 기록하기 위한 설정입니다.
     *
     * AuditorAware 인터페이스를 구현하고 있는 Bean에서 SecurityContextHolder를 사용하여
     * 현재 사용자의 인증 정보를 가져와 createdBy, modifiedBy 필드에 사용할 수 있도록 합니다.
     * 이렇게 설정해 두면 어떤 사용자가 언제 어떤 데이터를 생성하거나 수정했는지 추적할 수 있습니다.
     * 따라서 시스템의 보안성을 높일 수 있고, 데이터 변경에 대한 추적 및 감사(audit)를 수행할 수 있습니다.
     *
     */


    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(UserAccountPrincipal.class::cast)
                .map(UserAccountPrincipal::getUsername)
                .orElse("anonymousUser"));
    }

//
//    @Bean
//    public AuditorAware<UserAccountPrincipal> auditorAware() {
//        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return Optional.empty();
//            }
//
//            return Optional.of(((UserAccountPrincipal) authentication.getPrincipal()));
//        };
//    }

}
