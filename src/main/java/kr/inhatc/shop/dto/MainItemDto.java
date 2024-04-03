package kr.inhatc.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

    private Long id;                    // 아이디
    private String itemNm;              // 상품명
    private String itemDetail;          // 상품 설명
    private String imgUrl;              // 이미지 경로
    private Integer price;              // 가격

    @QueryProjection  // 엔티티와 DTO를 매핑하기 위한 QueryDSL 어노테이션
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
