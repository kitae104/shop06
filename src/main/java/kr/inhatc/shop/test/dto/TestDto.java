package kr.inhatc.shop.test.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestDto {

    private String name;
    private int age;
}
