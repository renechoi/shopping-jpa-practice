//package ssipgeukbbok.shoppingjpapractice.config;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
//import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//public class SecurityConfigTest4 {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    @DisplayName("ADMIN 권한이 필요한 페이지에 ADMIN 권한으로 접근")
//    @WithMockUser(username="admin@admin.com", roles={"ADMIN"})
//    void testAdminAccessWithAdminRole() throws Exception {
//        mockMvc.perform(get("/admin/item/new"))
//                                .andExpect(status().is3xxRedirection());
////                .andExpect(authenticated().withUsername("admin@admin.com"));
////                .andExpect(status().isOk());
////                .andExpect(MockMvcResultMatchers.content().string("상품등록"))
////                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("ADMIN 권한이 필요한 페이지에 ADMIN 권한 없이 접근")
//    @WithMockUser(username="testuser", roles={"USER"})
//    void testAdminAccessWithoutAdminRole() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().isForbidden())
//                .andDo(print());
//    }
//}
