package ssipgeukbbok.shoppingjpapractice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.service.UserAccountService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 유저")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserAccountControllerTest {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Test
    @DisplayName("클라이언트 로그인 시도 -> 성공")
    void loginSuccessTest() throws Exception{

        // given
        String email = "admin@admin.com";
        String password = "1234";

        // when & then
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login/process")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("권한 있는 유저의 접근 -> 허용")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void authorizedAdminTest() throws Exception{

        // given

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print());
//                .andExpect(status().isFound());
    }


    @Test
    @DisplayName("권한 없는 유저의 접근 -> 허용 불가")
    @WithMockUser(username = "user", roles = "USER")
    void unauthorizedUserTest() throws Exception{

        // given

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }




}
