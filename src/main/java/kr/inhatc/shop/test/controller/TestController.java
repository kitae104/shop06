package kr.inhatc.shop.test.controller;

import kr.inhatc.shop.test.dto.TestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "Hello, World! 123";
    }

    @GetMapping(value = "/test")
    public TestDto test(){
        TestDto dto = TestDto.builder()
                .name("홍길동")
                .age(20)
                .build();
        return dto;
    }
}

