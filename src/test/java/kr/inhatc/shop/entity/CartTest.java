package kr.inhatc.shop.entity;

import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import kr.inhatc.shop.repository.CartRepository;
import kr.inhatc.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartTest {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext // JPA의 EntityManager 주입 = @Autowired
    private EntityManager em;

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest(){
        Member member = memberRepository.findByEmail("test@test.com").orElseThrow(EntityNotFoundException::new);
        //System.out.println("member = " + member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        //em.flush();
        //em.clear();

        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        //System.out.println("savedCart = " + savedCart);
        assertEquals(savedCart.getMember().getId(), member.getId());
    }
}