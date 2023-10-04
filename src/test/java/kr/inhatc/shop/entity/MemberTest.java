package kr.inhatc.shop.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import kr.inhatc.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "김기태", roles = "USER")
    public void auditingTest(){
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 회원이 없습니다."));

        System.out.println("register time : " + findMember.getRegTime());
        System.out.println("update time : " + findMember.getUpdateTime());
        System.out.println("create member : " + findMember.getCreatedBy());
        System.out.println("modify member : " + findMember.getModifiedBy());
    }
}