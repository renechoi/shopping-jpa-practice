//package ssipgeukbbok.shoppingjpapractice.config;
//
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
//import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(SecurityConfig.class)
//class SecurityConfigTest3 {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @DisplayName("정적 리소스 허용 테스트")
//    void testStaticResources() throws Exception {
//        mockMvc.perform(SecurityMockMvcRequestBuilders
//                        .get("/min.css"))
//                .andExpect(status().isOk())
//                .andExpect(SecurityMockMvcResultMatchers
//                        .unauthenticated());
//    }
//
//    @Test
//    @DisplayName("로그인 페이지 접근 가능 여부 테스트")
//    void testLoginPageAccess() throws Exception {
//        mockMvc.perform(SecurityMockMvcRequestBuilders
//                        .get("/members/login"))
//                .andExpect(status().isOk())
//                .andExpect(SecurityMockMvcResultMatchers
//                        .unauthenticated());
//    }
//
//    @Test
//    @DisplayName("사용자 인증 후 페이지 접근 가능 여부 테스트")
//    @WithUserDetails("testuser@test.com")
//        // testuser@test.com 사용자로 인증 처리
//    void testAuthenticatedAccess() throws Exception {
//        mockMvc.perform(SecurityMockMvcRequestBuilders
//                        .get("/mypage"))
//                .andExpect(status().isOk())
//                .andExpect(SecurityMockMvcResultMatchers
//                        .authenticated());
//    }
//
//    @Test
//    @DisplayName("ROLE_ADMIN 권한이 있는 사용자가 /admin/** 경로에 접근 가능한지 테스트")
//    @WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
//    void testAdminAccess() throws Exception {
//        mockMvc.perform(SecurityMockMvcRequestBuilders
//                        .get("/admin/dashboard"))
//                .andExpect(status().isOk())
//                .andExpect(SecurityMockMvcResultMatchers
//                        .authenticated())
//                .andExpect(SecurityMockMvcResultMatchers
//                        .withRoles("ADMIN"));
//    }
//
//    @Test
//    @DisplayName("ROLE_SELLER 권한이 있는 사용자가 /admin/** 경로에 접근 가능한지 테스트")
//    @WithMockUser(username = "seller@test.com", roles = {"SELLER"})
//    void testSellerAccess() throws Exception {
//        mockMvc.perform(SecurityMockMvcRequestBuilders
//                        .get("/admin/dashboard"))
//                .andExpect(status().isForbidden())
//                .andExpect(SecurityMockMvcResultMatchers
//                        .unauthenticated());
//    }
//}
