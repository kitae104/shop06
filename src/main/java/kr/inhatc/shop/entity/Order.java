package kr.inhatc.shop.entity;

import jakarta.persistence.*;
import kr.inhatc.shop.constant.OrderStatus;
import kr.inhatc.shop.utils.audit.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")                             // order는 DB에서 예약어로 사용되므로 orders로 테이블명을 지정한다.
@Getter
@Setter
@ToString(exclude = {"member", "orderItems"})       // 연관관계 필드는 toString()을 호출할 때 무한 루프에 빠지므로 exclude로 제외시킨다.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;                        // 주문번호

    @ManyToOne(fetch = FetchType.LAZY)      // 다대일 관계이므로 연관관계의 주인은 다(N)쪽에 설정한다.
    @JoinColumn(name = "member_id")         // FK를 지정한다.
    private Member member;                  // 주문회원

    // mappedBy = "order"는 OrderItem의 order 필드에 의해 매핑된 것이라는 의미이다.(주인이 아님) : 양방향 관계 설정
    // cascade = CascadeType.ALL은 Order를 저장할 때 OrderItem도 함께 저장된다는 의미이다.
    // orphanRemoval = true는 OrderItem이 더 이상 Order와 연관관계가 없으면 OrderItem을 삭제한다는 의미이다.
    // fetch = FetchType.LAZY는 지연로딩을 의미한다.
    @Builder.Default                        // Builder를 사용할 때 기본값을 설정한다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();    // 주문상품 - 실제 컬럼이 생성되지 않는다(DB 확인)

    private LocalDateTime orderDate;        // 주문시간

    private OrderStatus orderStatus;        // 주문상태

}
