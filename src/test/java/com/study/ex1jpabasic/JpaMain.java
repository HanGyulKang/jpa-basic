//package com.study.ex1jpabasic;
//
//import com.study.ex1jpabasic.hellojpa.Member;
//import com.study.ex1jpabasic.hellojpa.MemberRepository;
//import com.study.ex1jpabasic.hellojpa.RoleType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import javax.persistence.EntityManager;
//import javax.transaction.Transactional;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//@Transactional
//public class JpaMain {
//
//    // **** JPA 의 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.
//    // **** 엔티티 매니져를 스레드간에 공유하는 경우는 매우 위험하다(사용하고 반드시 버려야한다. close)
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    private Logger log = LoggerFactory.getLogger(this.getClass().getName());
//
//    @BeforeEach
//    public void before() {
//        Member member1 = Member.builder().id(11L).name("Hello 11").build();
//        Member member2 = Member.builder().id(12L).name("Hello 12").build();
//        Member member3 = Member.builder().id(13L).name("Hello 13").build();
//        Member member4 = Member.builder().id(14L).name("Hello 14").build();
//        Member member5 = Member.builder().id(201L).name("Hello 201").build();
//
//        em.persist(member1);
//        em.persist(member2);
//        em.persist(member3);
//        em.persist(member4);
//        em.persist(member5);
//
//        em.flush();
//    }
//
//    @Test
//    @Rollback
//    public void main() {
//        // 비영속
//        Member member = Member.builder()
//                .id(2L)
//                .name("HelloB")
//                .build();
//
//        // 영속 (아직 데이터베이스에 저장은 되지 않은 상태)
//        System.out.println("==== BEFORE ====");
//        em.persist(member);
//        System.out.println("==== AFTER ====");
//        em.close();
//    }
//
//    @Test
//    @Transactional
//    public void find() {
//        // 영속성 획득
//        Member member = em.find(Member.class, 12L);
//
//        log.info("\nmember get : {}\n", member.toString());
//
//        assertThat(member)
//                .extracting("name")
//                .isEqualTo("Hello 12");
//
//        // data update
//        member.setName("Hello 1122");
//
//        Member renamedMember = em.find(Member.class, 12L);
//
//        log.info("\nrename member : {}\n", renamedMember.toString());
//
//        assertThat(renamedMember)
//                .extracting("name")
//                .isEqualTo("Hello 1122");
//    }
//
//    @Test
//    public void test_jpql() {
//        // JPQL은 엔티티를 대상으로 쿼리
//        // SQL은 데이터베이스 테이블을 대상으로 쿼리
//        List<Member> resultList = em.createQuery("select m from Member as m ", Member.class)
//                .setFirstResult(5)
//                .setMaxResults(8)
//                .getResultList();
//
//        for(Member m : resultList) {
//            System.out.println(m.toString());
//        }
//
//        em.close();
//    }
//
//    @Test
//    @Transactional
//    public void test_cache_01() {
//        Member member = Member.builder()
//                .id(101L)
//                .name("HelloJPA")
//                .build();
//
//        System.out.println("===== BEFORE =====");
//        em.persist(member);
//        System.out.println("===== AFTER =====");
//
//        // 위에서 1차 캐시에 저장한 데이터를 조회하는 것이기 떄문에 1차 캐시에서 조회 함
//        Member findMember = em.find(Member.class, 101L);
//
//        System.out.println("findMember.id = " + findMember.getId());
//        System.out.println("findMember.name = " + findMember.getName());
//
//        em.close();
//    }
//
//    @Test
//    @Transactional
//    public void test_cache_02() {
//        Member member1 = em.find(Member.class, 1L);
//        // 1차 캐시에 저장된 데이터를 불러오기 때문에 조회 쿼리가 실행되지 않음
//        Member member2 = em.find(Member.class, 1L);
//
//        System.out.println("result : " + (member1 == member2)); // 동일성 비교 true
//
//        em.close();
//    }
//
//    @Test
//    @Transactional
//    public void test_transaction() {
//        try {
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("======== persist에서는 아직 Insert 쿼리가 실행되지 않았다 ========");
//
//            em.flush();
//        } catch (Exception e) {
//            em.close();
//            log.error(e.getMessage());
//        } finally {
//            em.close();
//        }
//    }
//
//    @Test
//    @Transactional
//    public void test_dirty_checking() {
//        // dirty checking : 변경 감지
//
//        String name = "ZZZZZ";
//
//        // 조회 함
//        Member member = em.find(Member.class, 14L);
//        member.setName(name);
//
//        // 이름이 변경되었다면 Update 아니면 그대로
//        // 이 소스가 무쓸모임
//        // 이렇게 하더라도 값이 바뀐이상 트랜잭션이 커밋되는 순간 변경사항을 디비에 반영 함
//        if(member.getName().equals(name)) {
//            em.persist(member);
//        }
//
//        System.out.println("\n이름을 바꿈");
//        System.out.println(member.toString());
//        System.out.println("\n");
//        // em.persist를 하지 않아도 조회가 됨
//        // JPA의 목표는 Java Collection 다루듯 객체를 다루는 것임
//        // Java List나 Map같은 데이터에서 값을 꺼내고 변경했다고 다시 집어넣지 않음 ㅋㅋ
//
//        // db로 쓰기 지연 SQL 저장소에 있는 update 쿼리를 보냄
//        em.flush();
//
//        Member member1 = em.find(Member.class, 14L);
//        // 이름 바뀜
//        assertThat(member1)
//                .extracting("name")
//                .isEqualTo(name);
//
//        // 객체 동일함
//        assertThat(member).isEqualTo(member1);
//    }
//
//    @Test
//    @Transactional
////    @Commit
//    public void test2() {
//        Member member = Member
//                .builder()
//                .id(202L)
//                .name("number 201")
//                .build();
//
//        em.persist(member);
//
//        Member member1 = memberRepository.findById(202L).get();
//        System.out.println(member1.toString());
//
//        assertThat(member).isEqualTo(member1);
//
//        System.out.println("======== query s");
//        // database로 쓰기 지연 sql 저장소에 있는 쿼리를 날림
//        // commit 어노테이션을 주석해제하면 해당 로직이 다 돌고나서 transaction이 commit하는 시점에
//        // 쓰기 지연 sql 저장소에 있는 쿼리를 날리기 때문에 위 아래 sout을 벗어나서 쿼리가 로그에 찍힘
//        em.flush();
//        System.out.println("======== query e");
//
//        Member member2 = memberRepository.findById(202L).get();
//        assertThat(member).isEqualTo(member1);
//        assertThat(member).isEqualTo(member2);
//    }
//
//    @Test
//    public void jpabasic_10() {
//        /**
//         * 영속 상태를 준영속 상태로 바꾸기 때문에 select 쿼리만 두 번 나감
//         */
//
//        // 영속
//        Member member = em.find(Member.class, 201L);
//        member.setName("AAAA");
//
//        // 준영속 : 영속에서 떼버림 JPA에서 관리 안 함
//        em.detach(member);
//
//        // 준영속 : 엔티티 매니저 안의 영속성 컨텍스트(=1차 캐시)를 통째로 다 지움
//        em.clear();
//
//        // 준영속 : 엔티티 매니저를 닫아버림
//        em.close();
//
//        // 다시 영속
//        Member member1 = em.find(Member.class, 201L);
//        assertThat(member1).extracting("name").isEqualTo("Hello 201");
//    }
//
//    @Test
//    @Transactional
//    public void jpabasic_10_1() {
//        Member member = Member.builder().id(301L).name("number301").build();
//        em.persist(member);
//
//        // 조회 쿼리는 돌지 않음
//        // 위에서 영속을 했기 때문에 1차 캐시에 있는 데이터를 바로 가져옴
//        em.find(Member.class, 301L);
//        // 영속성 컨텍스트에 없는 데이터이기 때문에 조회 쿼리가 날아감
//        em.find(Member.class, 201L);
//    }
//
//    @Test
//    @Transactional
//    public void jpabasic_enum() {
//        Member member = Member.builder()
//                .id(99L)
//                .name("99num")
//                .roleType(RoleType.USER)
//                .build();
//
//        em.persist(member);
//
//
//    }
//}