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

    // 가급적이면 단방향 매핑이 좋다. 양방향의 경우 개발 중 필요할 경우 넣는게 좋다.
    // 데이터베이스에 변화를 주는 것이 아닌 객체가 변하는 것이기 때문에 미리 만들어둘 필요가 없다(복잡도만 올라감).
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    @ToString.Exclude
//    @Builder.Default
//    private List<Order> orders = new ArrayList<>();
}
