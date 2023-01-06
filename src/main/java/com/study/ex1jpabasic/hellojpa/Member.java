//package com.study.ex1jpabasic.hellojpa;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Getter
//@ToString
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "Member")
//public class Member {
//
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // updatable : 업데이트 가능여부
//    // nullable : null 허용 여부
//    @Column(name = "name", updatable = false)
//    private String name;
//    private Integer age;
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createDate;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//    @Lob
//    private String description;
//
//    public Member(long id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//}
