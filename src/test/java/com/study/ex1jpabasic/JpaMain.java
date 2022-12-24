package com.study.ex1jpabasic;

import com.study.ex1jpabasic.hellojpa.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class JpaMain {

    // **** JPA 의 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.
    // **** 엔티티 매니져를 스레드간에 공유하는 경우는 매우 위험하다(사용하고 반드시 버려야한다. close)
    @Autowired
    EntityManager em;

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @BeforeEach
    public void before() {
        Member member1 = Member.builder().id(11L).name("Hello 11").build();
        Member member2 = Member.builder().id(12L).name("Hello 12").build();
        Member member3 = Member.builder().id(13L).name("Hello 13").build();
        Member member4 = Member.builder().id(14L).name("Hello 14").build();

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    @Rollback
    public void main() {
        // 비영속
        Member member = Member.builder()
                .id(2L)
                .name("HelloB")
                .build();

        // 영속 (아직 데이터베이스에 저장은 되지 않은 상태)
        System.out.println("==== BEFORE ====");
        em.persist(member);
        System.out.println("==== AFTER ====");
        em.close();
    }

    @Test
    public void find() {
        // 영속성 획득
        Member member = em.find(Member.class, 12L);

        log.info("\nmember get : {}\n", member.toString());

        assertThat(member)
                .extracting("name")
                .isEqualTo("Hello 12");

        // data update
        member.setName("Hello 1122");

        Member renamedMember = em.find(Member.class, 12L);

        log.info("\nrename member : {}\n", renamedMember.toString());

        assertThat(renamedMember)
                .extracting("name")
                .isEqualTo("Hello 1122");
    }

    @Test
    public void test_jpql() {
        // JPQL은 엔티티를 대상으로 쿼리
        // SQL은 데이터베이스 테이블을 대상으로 쿼리
        List<Member> resultList = em.createQuery("select m from Member as m ", Member.class)
                .setFirstResult(5)
                .setMaxResults(8)
                .getResultList();

        for(Member m : resultList) {
            System.out.println(m.toString());
        }

        em.close();
    }

    @Test
    @Transactional
    public void test_cache_01() {
        Member member = Member.builder()
                .id(101L)
                .name("HelloJPA")
                .build();

        System.out.println("===== BEFORE =====");
        em.persist(member);
        System.out.println("===== AFTER =====");

        // 위에서 1차 캐시에 저장한 데이터를 조회하는 것이기 떄문에 1차 캐시에서 조회 함
        Member findMember = em.find(Member.class, 101L);

        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getName());

        em.close();
    }

    @Test
    @Transactional
    public void test_cache_02() {
        Member member1 = em.find(Member.class, 1L);
        // 1차 캐시에 저장된 데이터를 불러오기 때문에 조회 쿼리가 실행되지 않음
        Member member2 = em.find(Member.class, 1L);

        System.out.println("result : " + (member1 == member2)); // 동일성 비교 true

        em.close();
    }

    @Test
    public void test_transaction() {
        
    }
}
