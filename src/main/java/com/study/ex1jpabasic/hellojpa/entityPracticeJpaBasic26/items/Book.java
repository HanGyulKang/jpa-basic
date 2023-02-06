package com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26.items;

import com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26.Item;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Item {

    private String author;
    private String isbn;
}
