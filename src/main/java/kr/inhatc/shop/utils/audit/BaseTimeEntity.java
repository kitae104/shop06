package kr.inhatc.shop.utils.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass                       // 테이블로 매핑하지 않고, 자식 Entity에게 매핑 정보를 상속하기 위한 어노테이션
@Getter
@Setter
public abstract class BaseTimeEntity{

    @CreatedDate                        // 최초 생성 시점
    @Column(updatable = false)          // 수정 불가
    private LocalDateTime regTime;      // 등록일

    @LastModifiedDate                   // 최종 수정 시점
    private LocalDateTime updateTime;   // 수정일
}
