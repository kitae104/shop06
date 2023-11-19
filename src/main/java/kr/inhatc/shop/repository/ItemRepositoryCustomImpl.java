package kr.inhatc.shop.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.inhatc.shop.constant.ItemSellStatus;
import kr.inhatc.shop.dto.ItemSearchDto;
import kr.inhatc.shop.entity.Item;
import kr.inhatc.shop.entity.QItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static kr.inhatc.shop.entity.QItem.*;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom  {

    private JPAQueryFactory queryFactory;                       // JPAQueryFactory 사용을 위해 선언

    public ItemRepositoryCustomImpl(EntityManager em) {         // EntityManager 객체를 생성자로 받음
        this.queryFactory = new JPAQueryFactory(em);            // JPAQueryFactory 객체 생성
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory.selectFrom(item) // item 테이블에서 조회
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),                              // 검색 기준일
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),                    // 판매 상태
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))  // 검색 조건과 검색어
                .orderBy(item.id.desc())                    // id를 기준으로 내림차순 정렬
                .offset(pageable.getOffset())               // pageable에서 offset을 가져옴
                .limit(pageable.getPageSize())              // pageable에서 pageSize를 가져옴
                .fetch();// fetch()를 통해 조회 - 리스트 반환

        long total = queryFactory.select(Wildcard.count)    // 전체 개수 조회
                .from(item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                       searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                       searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne();                                // fetchOne()을 통해 조회 - 단건 반환

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 검색 기준일 검색 조건
     * @param searchDateType
     * @return
     */
    private BooleanExpression regDtsAfter(String searchDateType) {

        LocalDateTime dateTime = LocalDateTime.now();           // 현재 시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);                   // 현재 시간에서 1일을 뺌
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);                  // 현재 시간에서 1주일을 뺌
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);                 // 현재 시간에서 1달을 뺌
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);                 // 현재 시간에서 6달을 뺌
        }
        return item.regTime.after(dateTime);                    // regTime이 dateTime보다 이후인지 반환
    }

    /**
     * 판매 상태 검색 조건
     * @param searchSellStatus
     * @return
     */
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);  // 검색 조건이 없으면 null 반환
    }

    /**
     * 검색어 검색 조건
     * @param searchBy
     * @param searchQuery
     * @return
     */
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemName", searchBy)){
            return item.itemNm.like("%" + searchQuery + "%");    // 검색어가 itemName이면 itemNm에 검색어가 포함되어 있는지 반환
        } else if(StringUtils.equals("createBy", searchBy)){
            return item.createdBy.like("%" + searchQuery + "%");  // 검색어가 createdBy이면 createdBy에 검색어가 포함되어 있는지 반환
        }
        return null;
    }
}
