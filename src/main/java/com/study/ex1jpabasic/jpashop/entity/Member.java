package com.study.ex1jpabasic.jpashop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long Id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
