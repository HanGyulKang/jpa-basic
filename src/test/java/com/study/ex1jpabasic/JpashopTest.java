package com.study.ex1jpabasic;

import com.study.ex1jpabasic.jpashop.entity.Member;
import com.study.ex1jpabasic.jpashop.entity.Order;
import com.study.ex1jpabasic.jpashop.enums.OrderStatus;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpashopTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setting() {
        Member member = Member
                .builder()
                .name("memberA")
                .street("streetA")
                .zipcode("08651")
                .city("seoul")
                .build();

        em.persist(member);
    }


    @Test
    @Transactional
    public void insertTest() {
        Member member = em.find(Member.class, 1L);
        System.out.println(member.toString());

        Order order = Order
                .builder()
                .status(OrderStatus.ORDER)
                .member(member)
                .build();

        em.persist(order);

        // 1차 캐시에 가지고있는 데이터여서 따로 select 쿼리는 실행되지 않음
        // 쿼리를 실행시키고싶으면
        // em.flush(); em.clear();
        // 대신 위 처럼 1차 캐시를 비우고 디비에서 다시 조회해오면
        // 두 Order 객체가 가지고있는 Member 객체의 참조 주소가 바뀌기 때문에 Member 객체를 비교하는 테스트는 실패한다.
        System.out.println("======== START : 1차 캐시에서 꺼내오기 때문에 조회 쿼리는 실행되지 않는다.");
        Order order1 = em.find(Order.class, 1L);
        System.out.println("======== END : 1차 캐시에서 꺼내오기 때문에 조회 쿼리는 실행되지 않는다.");

        // 연관관계 매핑이 되어있기 떄문에 order id를 가지고 따로 조회할 필요 없이 Member 객체를 찾을 수 있다.
        System.out.println("order1.getMember() : " + order1.getMember());
        assertThat(order1.getMember()).isEqualTo(member);

        System.out.println("order : " + order.toString());
        System.out.println("order1 : " + order1.toString());
        assertThat(order).isEqualTo(order1);
    }
}
