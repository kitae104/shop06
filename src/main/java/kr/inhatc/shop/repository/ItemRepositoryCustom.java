package kr.inhatc.shop.repository;

import kr.inhatc.shop.dto.ItemSearchDto;
import kr.inhatc.shop.dto.MainItemDto;
import kr.inhatc.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    // 메인 페이지에 보여줄 상품 리스트 조회
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
