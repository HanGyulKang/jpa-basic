package com.study.ex1jpabasic.jpashop.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "locker_id")
    private Long id;

    private String lockerName;

    // 1:1 연관관계의 양방향 연관관계 : 읽기 전용이 됨
    @OneToOne(mappedBy = "locker")
    Member member;
}
