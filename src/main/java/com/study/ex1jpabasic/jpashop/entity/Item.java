package com.study.ex1jpabasic.jpashop.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// JPA 기본 방식이 Single table(=단일 테이블) 전략이다.
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Joined 전략을 취해주면 상속받은 테이블들이 각 테이블로 생성되고, 슈퍼타입의 id를 PK, FK로 갖게 된다.
@Inheritance(strategy = InheritanceType.JOINED)
// DTYPE이라는 컬럼이 나온다.
// 해당 컬럼에는 Sub type Entity의 이름이 들어가게 된다.
// 해당 값을 통해서 데이터의 정체를 확인할 수 있다.
@DiscriminatorColumn(name = "item_type")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;
    private Integer price;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
