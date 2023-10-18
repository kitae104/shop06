package kr.inhatc.shop.repository;

import kr.inhatc.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    //@Query("select i from ItemImg i where i.item.id = :itemId order by i.id asc")
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);  // 상품 이미지 리스트 가져오기
}
