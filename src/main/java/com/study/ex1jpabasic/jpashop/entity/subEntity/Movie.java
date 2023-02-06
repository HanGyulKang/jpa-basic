package com.study.ex1jpabasic.jpashop.entity.subEntity;

import com.study.ex1jpabasic.jpashop.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
// super type Entity의 DTYPE에 들어갈 명을 정해줄 수 있다.
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;
}
