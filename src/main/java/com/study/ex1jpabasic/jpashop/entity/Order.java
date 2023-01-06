package com.study.ex1jpabasic.jpashop.entity;

import com.study.ex1jpabasic.jpashop.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING) // ORDINAL 쓰면 순서가 꼬이면 슬퍼짐
    private OrderStatus status;
}
