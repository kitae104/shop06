package kr.inhatc.shop.dto;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import kr.inhatc.shop.constant.ItemSellStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
