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

    // 1:1 연관관계
    @OneToOne
    @JoinColumn(name = "locker_id")
    Locker locker;

    // 가급적이면 단방향 매핑이 좋다. 양방향의 경우 개발 중 필요할 경우 넣는게 좋다.
    // 데이터베이스에 변화를 주는 것이 아닌 객체가 변하는 것이기 때문에 미리 만들어둘 필요가 없다(복잡도만 올라감).
    // 보통 일반적인 쿼리를 짠다고 해도 Order에서 Member를 찾지 Member에서 Order를 찾지는 않는다(Order로부터 시작해서 Member를 탐색).
    // 그렇지만 예제고, 혹시 양방향 관계가 필요하다면..?
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @ToString.Exclude // ToString 무한 루프 방지...
    @Builder.Default
    // 객체를 초기화하는 이유는? NullPointerException 방지 및 JPA를 사용할 때는 보통 관례로 사용 함
    private List<Order> orders = new ArrayList<>();

    // JpaBasic-21 일대다 관계에서 1이 연관관계의 주인이 된 상태의 예제
//    @OneToMany
//    @JoinColumn(name = "order_id")
//    private List<Order> orders = new ArrayList<>();

    // 다대다 매핑
    @ManyToMany
    @JoinTable(name = "member_product")
    private List<Product> products = new ArrayList<>();
}
