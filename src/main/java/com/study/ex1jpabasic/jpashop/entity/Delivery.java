package com.study.ex1jpabasic.jpashop.entity;

import com.study.ex1jpabasic.jpashop.enums.DeliveryStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;
    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
