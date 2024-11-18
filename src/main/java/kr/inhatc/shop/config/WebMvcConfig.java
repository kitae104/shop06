package kr.inhatc.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Value(value = "${uploadPath}") // 설정에 정의된 값을 클래스의 필드에 주입하는 역할
    private String uploadPath;

    // 정적 리소스(Static Resources)를 처리하기 위한 설정
    // 정적 리소스 요청을 처리할 때 요청 URL 패턴과 실제 파일 경로를 매핑하는 역할
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/sample.jpg 요청이 들어오면 Spring이 실제 파일 시스템에서
        // uploadPath에 해당하는 경로에서 파일을 찾음
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
