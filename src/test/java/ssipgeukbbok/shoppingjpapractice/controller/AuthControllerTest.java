package ssipgeukbbok.shoppingjpapractice.controller;

import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ssipgeukbbok.shoppingjpapractice.service.UserAccountService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 인증")
@WebMvcTest(SecurityConfig.class)
//@SpringBootTest
public class AuthControllerTest {

    private final MockMvc mvc;

    @MockBean UserAccountController userAccountController;
    @MockBean
    UserAccountService userAccountService;

    public AuthControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @ParameterizedTest
    @ValueSource(strings = {"/login" })   //  "/members/login", "/members/login/errors"
    public void givenUrl_whenTryingToLogIn_thenReturnsLogInView(String url) throws Exception {
        // Given

        // When & Then
        mvc.perform(get(url)
//                        .with(anonymous())
                        .with(user("admin@admin.com").password("1234").roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }





}
