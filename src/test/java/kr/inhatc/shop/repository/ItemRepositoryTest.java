package kr.inhatc.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.inhatc.shop.constant.ItemSellStatus;
import kr.inhatc.shop.entity.Item;
import kr.inhatc.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager em;

    public void createItemList(){
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    void findByItemNm() {
        createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("JPQL 쿼리")
    public void findByItemDetailTest(){
        createItemList();

        List<Item> itemList = itemRepository.findByItemDetail("테스트");

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("Native 쿼리")
    public void findByItemDetailNativeTest(){
        createItemList();

        List<Item> itemList = itemRepository.findByItemDetailNative("테스트");

        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest(){
        createItemList();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        List<Item> itemList = queryFactory.select(qItem)
                .from(qItem)
                .where(qItem.itemDetail.like("%테스트%"))
                .orderBy(qItem.price.desc())
                .fetch();

        itemList.forEach(item -> System.out.println(item));

//        for (Item item : itemList) {
//            System.out.println(item);
//        }

    }

    // 데이터 준비
    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("querydsl 테스트2")
    public void querydslTest2(){
        createItemList2();

        String itemDetail = "테스트";
        Integer price = 10003;
        String itemSellStatus = "SELL";

        QItem item = QItem.item;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)){
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = Pageable.ofSize(5).withPage(0);     // RageRequest.of(0, 5);

        itemRepository.findAll(builder, pageable).forEach(i -> System.out.println(i));        
        itemRepository.findAll(builder, pageable).forEach(System.out::println);

        Page<Item> all = itemRepository.findAll(builder, pageable);
        System.out.println("total elements : " + all.getTotalElements());
        System.out.println("total pages : " + all.getTotalPages());

        List<Item> content = all.getContent();
        content.forEach(System.out::println);
        for (Item item2 : content) {
            System.out.println(item2);
        }
    }
}