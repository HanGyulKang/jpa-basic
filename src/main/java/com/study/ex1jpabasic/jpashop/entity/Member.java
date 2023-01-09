package com.study.ex1jpabasic.jpashop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
}
