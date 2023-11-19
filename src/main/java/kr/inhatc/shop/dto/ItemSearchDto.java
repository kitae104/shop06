package kr.inhatc.shop.dto;

import kr.inhatc.shop.constant.ItemSellStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemSearchDto {

    private String searchDateType;              // 검색 기준일

    private ItemSellStatus searchSellStatus;    // 판매 상태

    private String searchBy;                    // 검색 조건

    private String searchQuery;                 // 검색어
}
