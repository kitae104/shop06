package kr.inhatc.shop.dto;

import kr.inhatc.shop.entity.ItemImg;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImgDto {
    private Long id;

    private String imgName;         // 저장된 이미지 파일명

    private String oriImgName;      // 원본 이미지 파일명

    private String imgUrl;          // 이미지 저장 경로

    private String repImgYn;    // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
    	return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
