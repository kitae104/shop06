package kr.inhatc.shop.utils.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass                   // 테이블로 매핑하지 않고, 자식 Entity에게 매핑 정보를 상속하기 위한 어노테이션
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy                      // 생성자
    @Column(updatable = false)      // 수정 불가
    private String createdBy;       // 등록자

    @LastModifiedBy                 // 최종 수정자
    private String modifiedBy;      // 수정자
}
