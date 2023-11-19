package kr.inhatc.shop.repository;

import kr.inhatc.shop.dto.ItemSearchDto;
import kr.inhatc.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
