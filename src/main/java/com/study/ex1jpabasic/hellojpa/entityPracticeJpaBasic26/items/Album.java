package com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26.items;

import com.study.ex1jpabasic.hellojpa.entityPracticeJpaBasic26.Item;

import javax.persistence.Entity;

@Entity
public class Album extends Item {

    private String artist;
}
