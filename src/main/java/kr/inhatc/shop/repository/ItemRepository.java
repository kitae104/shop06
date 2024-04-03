package kr.inhatc.shop.repository;

import kr.inhatc.shop.dto.ItemSearchDto;
import kr.inhatc.shop.dto.MainItemDto;
import kr.inhatc.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 상품관련 레포지토리
 */
public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {       // ItemRepositoryCustom - 검색기능 추가

    List<Item> findByItemNm(String itemNm);     // 해당 이름에 대한 상품 리스트 가져오기

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); // :itemDetail 변수로 전달

    @Query(value="select * from item i where i.item_detail like %:itemDetail% order by i.price asc", nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("itemDetail") String itemDetail);

}
