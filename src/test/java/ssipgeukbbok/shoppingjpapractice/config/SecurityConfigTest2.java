//package ssipgeukbbok.shoppingjpapractice.config;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import
//        org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.test.web.servlet.MockMvc;
//import ssipgeukbbok.shoppingjpapractice.controller.UserAccountController;
//import ssipgeukbbok.shoppingjpapractice.domain.UserAccount;
//import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
//import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;
//import ssipgeukbbok.shoppingjpapractice.service.UserAccountService;
//
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.instanceOf;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//@SpringBootTest
//@AutoConfigureMockMvc
//@Import(SecurityConfig.class)
//public class SecurityConfigTest2 {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired @MockBean
//    private UserAccountService userAccountService;
//
//    @Autowired  @MockBean
//    UserAccountController userAccountController;
//
//    @Test
//    @DisplayName("정적 리소스 허용 테스트")
//    @WithAnonymousUser
//    void testStaticResources() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/min.css"))
//
//                .andExpect(status().isOk());
//    }
////
////    @Test
////    @DisplayName("로그인 페이지 접근 가능 테스트")
////    @WithAnonymousUser
////    void testLoginForm() throws Exception {
////        mockMvc.perform(SecurityMockMvcRequestBuilders
////                        .get("/members/login"))
////                .andExpect(status().isOk())
////                .andExpect(MockMvcResultMatchers.view().name("login"));
////    }
////
////    @Test
////    @DisplayName("로그인 성공 테스트")
////    @WithMockUser(username = "admin@admin.com", password = "1234", roles = "ADMIN")
////    void testLoginSuccess() throws Exception {
////        mockMvc.perform(SecurityMockMvcRequestBuilders
////                        .post("/members/login/process")
////                        .param("email", "admin@admin.com")
////                        .param("password", "1234"))
////                .andExpect(status().is3xxRedirection())
////                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
////    }
////
////    @Test
////    @DisplayName("로그인 실패 테스트")
////    @WithAnonymousUser
////    void testLoginFailure() throws Exception {
////        mockMvc.perform(SecurityMockMvcRequestBuilders
////                        .post("/members/login/process")
////                        .param("email", "wrong_email@admin.com")
////                        .param("password", "wrong_password"))
////                .andExpect(status().is3xxRedirection())
////                .andExpect(MockMvcResultMatchers.redirectedUrl("/members/login/error"));
////    }
////
////    @Test
////    @DisplayName("로그아웃 테스트")
////    @WithMockUser(username = "admin@admin.com", password = "1234", roles = "ADMIN")
////    void testLogout() throws Exception {
////        mockMvc.perform(SecurityMockMvcRequestBuilders
////                        .post("/logout"))
////                .andExpect(status().is3xxRedirection())
////                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
////    }
////
////    @Test
////    @DisplayName("관리자 페이지 접근 가능 테스트")
////    @WithMockUser(username = "admin@admin.com", password = "1234", roles = "ADMIN")
////    void testAdminPageAccess() throws Exception {
////        mockMvc.perform(SecurityMockMvcRequestBuilders
////                        .get("/admin/users"))
////                .andExpect(status().isOk())
////                .andExpect(MockMvcResultMatchers.view().name("admin/users"))
////                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
////    }
//}
//
