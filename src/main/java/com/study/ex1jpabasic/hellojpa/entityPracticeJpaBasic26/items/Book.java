package com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26.items;

import com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26.Item;

import javax.persistence.Entity;

@Entity
public class Book extends Item {

    private String author;
    private String isbn;
}
