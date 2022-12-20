package com.study.ex1jpabasic.hellojpa;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member")
public class Member {

    @Id
    private Long id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
