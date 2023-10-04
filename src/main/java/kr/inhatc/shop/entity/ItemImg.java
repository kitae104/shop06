package kr.inhatc.shop.entity;

import jakarta.persistence.*;
import kr.inhatc.shop.utils.audit.BaseEntity;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImg extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id", nullable = false)
    private Long id;

    private String imgName;         // 저장된 이미지 파일명

    private String oriImgName;      // 원본 이미지 파일명

    private String imgUrl;          // 이미지 저장 경로

    private String repImgYn;        // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;              // 상품 정보

    /**
     * 상품 이미지 수정
     * @param oriImgName
     * @param imgName
     * @param imgUrl
     */
    public void updateItemImg(String oriImgName, String imgName,  String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
