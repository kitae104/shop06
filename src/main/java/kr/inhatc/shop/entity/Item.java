package kr.inhatc.shop.entity;

import jakarta.persistence.*;
import kr.inhatc.shop.constant.ItemSellStatus;
import kr.inhatc.shop.utils.audit.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;             // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm;       // 상품명

    @Column(nullable = false)
    private int price;           // 상품 가격

    @Column(nullable = false, name="number")
    private int stockNumber;     // 상품 재고 수량

    @Lob // Large Object - CLOB, BLOB
    @Column(nullable = false)
    private String itemDetail;   // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

}
