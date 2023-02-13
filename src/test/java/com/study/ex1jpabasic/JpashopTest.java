package com.study.ex1jpabasic;

import com.study.ex1jpabasic.jpashop.entity.Member;
import com.study.ex1jpabasic.jpashop.entity.Order;
import com.study.ex1jpabasic.jpashop.entity.subEntity.Movie;
import com.study.ex1jpabasic.jpashop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JpashopTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void setting() {
        Member memberA = Member
                .builder()
                .name("memberA")
                .street("streetA")
                .zipcode("08651")
                .city("seoul")
                .build();

        Member memberB = Member
                .builder()
                .name("memberB")
                .street("streetB")
                .zipcode("08651")
                .city("busan")
                .build();

        em.persist(memberA);
        em.persist(memberB);
    }


    @Test
    @Transactional
    public void insertTest() {
        Member member = em.find(Member.class, 1L);
        System.out.println(member.toString());


        Order orderA = Order
                .builder()
                .status(OrderStatus.ORDER)
                .member(member)
                .build();

        Order orderB = Order
                .builder()
                .status(OrderStatus.ORDER)
                .member(member)
                .build();

        em.persist(orderA);
        em.persist(orderB);

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

        System.out.println("order : " + orderA.toString());
        System.out.println("order1 : " + order1.toString());
        assertThat(orderA).isEqualTo(order1);

        em.flush();
        em.clear();

        Member member1 = em.find(Member.class, 1L);
        for(Order o : member1.getOrders()) {
            System.out.println(o.toString());
        }
    }

    @Test
    @Transactional
    void jpa_test() {
        System.out.println("\n1 ========= sql 실행 안 함 : 1차 캐시 조회");
        Member member = em.find(Member.class, 1L);
        System.out.println("========= sql 실행 안 함");

        Order order = Order.builder()
                .status(OrderStatus.SENT)
                .member(member)
                .build();

        em.persist(order);

        System.out.println("\n2 ========= sql 실행 안 함 : 1차 캐시 조회");
        System.out.println(order.toString());
        System.out.println("========= sql 실행 안 함");

        em.flush();
        // 1차 캐시 비움
        em.clear();

        System.out.println("\n3 ========= sql 실행 : DB 조회");
        Order order1 = em.find(Order.class, order.getId());
        System.out.println(order1.toString());
        System.out.println("========= sql 실행");
    }

    @Test
    @Transactional
//    @Commit
    void extendsEntity() {
        String director = "director1";
        String actor = "actor1";

        // super type, sub type 둘 다 @Builder를 @SuperBuilder로 해줘야 sub type Entity에서
        // 한 번에 super, sub 양 쪽에 데이터를 넣을 수 있다.
        Movie movie = Movie.builder()
                .director(director)
                .actor(actor)
                .price(10000)
                .name("바람과함께 사라지다.")
                .build();

        em.persist(movie);

        // JPA가 자동으로 조인 해서 데이터를 가져오는 것을 확인하기 위한 1차 캐시 삭제
        // 1차 캐시 미삭제 시 1차 캐시에서 데이터를 바로 가져오기 때문에
        // 아래에서 조회 쿼리가 나가지 않는다.
        em.flush();
        em.clear();

        Movie findMovie = em.find(Movie.class, movie.getId());
        /**
         * 결과 ::
         *    select
         *         movie0_.item_id as item_id1_5_0_,
         *         movie0_1_.name as name2_5_0_,
         *         movie0_1_.price as price3_5_0_,
         *         movie0_1_.stock_quantity as stock_qu4_5_0_,
         *         movie0_.actor as actor1_9_0_,
         *         movie0_.director as director2_9_0_
         *     from
         *         movie movie0_
         *     inner join
         *         item movie0_1_
         *             on movie0_.item_id=movie0_1_.item_id
         *     where
         *         movie0_.item_id=?
         */

        assertThat(findMovie.getActor()).isEqualTo(actor);
        assertThat(findMovie.getDirector()).isEqualTo(director);
    }
}
