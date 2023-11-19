package kr.inhatc.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.inhatc.shop.constant.ItemSellStatus;
import kr.inhatc.shop.entity.Item;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    @Builder.Default        // Builder를 사용할 때 기본값을 설정한다.
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    @Builder.Default        // Builder를 사용할 때 기본값을 설정한다.
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        ItemFormDto itemFormDto = modelMapper.map(item, ItemFormDto.class);
        return itemFormDto;
    }
}
