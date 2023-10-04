package kr.inhatc.shop.config.auth;

import kr.inhatc.shop.entity.Member;
import kr.inhatc.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("==================> username : " + email);

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + email)
        );

        log.info("==================> loadUserByUsername : " + member);

        return new PrincipalDetails(member);
    }
}
