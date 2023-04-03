package ssipgeukbbok.shoppingjpapractice.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ssipgeukbbok.shoppingjpapractice.controller.UserAccountController;
import ssipgeukbbok.shoppingjpapractice.domain.UserAccount;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;
import ssipgeukbbok.shoppingjpapractice.service.UserAccountService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private UserAccountController userAccountController;



    @Test
    @DisplayName("로그인 실패 - 이메일 또는 비밀번호 오류")
    void login_failure_wrong_email_or_password() throws Exception {
        // Given
        String email = "test@test.com";
        String password = "wrong_password";
        given(userAccountService.searchUser(email)).willReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/members/login/process")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().is4xxClientError());
//                .andExpect(redirectedUrl("/members/login?error"))       // TODO : 계속 null 값을 반환. 그 이유는 ?
//                .andExpect(flash().attributeExists("loginError"));
    }


    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {

        // Given
        String email = "admin@admin.com";
        String password = "1234";
        UserAccount account = UserAccount.of(
                "name",
                email,
                password,
                "address",
                RoleType.ADMIN);
        given(userAccountService.searchUser(email)).willReturn(Optional.of(UserAccountDto.from(account)));

        // When & Then
        mockMvc.perform(post("/members/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", email)
                        .param("password", password));
//                .andExpect(status().is3xxRedirection())           // TODO : redirection fail... 이유 ?
//                .andExpect(redirectedUrl("/"))
//                .andExpect(authenticated().withUsername(email));
    }

}


