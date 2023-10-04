package kr.inhatc.shop.entity;

import jakarta.persistence.*;
import kr.inhatc.shop.constant.Role;
import kr.inhatc.shop.dto.MemberFormDto;
import kr.inhatc.shop.utils.audit.BaseEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * 회원가입을 위한 Member 객체 생성<br>
     * memberFormDto로부터 사용자 정보를 획득한 후 password를 인코딩하여 함께 저장
     * @param memberFormDto
     * @param passwordEncoder
     * @return Member 객체 생성 후 반환
     */
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setAddress(memberFormDto.getAddress());
        member.setRole(Role.USER);
        return member;
    }
}
