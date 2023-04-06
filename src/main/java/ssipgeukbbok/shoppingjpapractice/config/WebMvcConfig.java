package ssipgeukbbok.shoppingjpapractice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMVC Config
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/item/**") // 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 uploadPath 에 설정한  폴더를 기준으로 파일을 읽어오도록 한다
                .addResourceLocations(uploadPath);
    }
//
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/home").setViewName("index");
//        registry.addViewController("/").setViewName("index");
////        registry.addViewController("/login").setViewName("login");
//    }
}