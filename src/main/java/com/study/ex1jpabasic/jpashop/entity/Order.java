package com.study.ex1jpabasic.jpashop.entity;

import com.study.ex1jpabasic.jpashop.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // ORDINAL 쓰면 순서가 꼬이면 슬퍼짐
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 편의 메서드(양방향 매핑 시 두 객체에 모두 값을 셋팅해주는 메서드)
    public void changeMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
}
