package com.study.ex1jpabasic.jpashop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProduct {

    // 전통적으로는 id를 새로 만들기보다는 member_id와 product_id를 PK, FK로 잡고 사용한다.
    // 하지만 이보다는 해당 Entity와 같이 의미없는 값을 PK로 사용하는 것이 실무적으로는 더 좋다.
    // 왜?
    // 제약조건에서 벗어나기 때문에 데이터 관리가 유연해진다.
    // 다만 두 개의 PK를 만들려면 @IdClass 사용해서 Class로 PK를 관리해야 한다.
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long count;
    private Long price;
    private LocalDateTime orderDateTime;
}
