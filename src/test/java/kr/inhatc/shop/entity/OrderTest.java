package kr.inhatc.shop.entity;

import jakarta.persistence.EntityManager;
import kr.inhatc.shop.repository.ItemRepository;
import kr.inhatc.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager em;


}