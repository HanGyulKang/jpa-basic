package com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26;

import javax.persistence.*;

@Entity
// JPA 기본 방식이 Single table 전략이다.
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Joined 전략을 취해주면 상속받은 테이블들이 각 테이블로 생성되고, 슈퍼타입의 id를 PK, FK로 갖게 된다.
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    
    @Id
    @GeneratedValue
    Long id;
    private String name;
    private int price;
}
