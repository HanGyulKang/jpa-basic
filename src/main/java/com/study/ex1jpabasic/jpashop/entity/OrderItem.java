package com.study.ex1jpabasic.jpashop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "order_price")
    private Integer orderPrice;
    private Integer count;
}
