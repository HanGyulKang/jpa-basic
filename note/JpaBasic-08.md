# 영속성 컨텍스트

* 영속성 컨텍스트는 __1차 캐시__ 를 가지고있다.

### 1차 캐시
```java
Member member = new Member();
member.setId("id1");
member.setUSername("name1");

// 1차 캐시에 저장 됨
em.persist(member);

// 1차 캐시에서 조회
// 조회한 내용이 캐시에 있다면 DB까지 가지 않고 캐시에서 조회 함
Member findMember = em.find(Member.class, "id1");
```

### 데이터베이스
```java
Member findMember = em.find(Member.class, "id2");

// 조회 시 1차 캐시에 데이터가 없다면 데이터베이스를 다녀 옴
// 조회한 데이터를 1차 캐시에 저장 함
```
---
캐시를 사용한다고 해서 __엄청난 이점이 있는 것은 아님__
* 애플리캐이션 전체에서 공유하는 것은 1차가 아닌 __2차 캐시__
* EntityManager는 무조건 한 트랜잭션 안에서 돌아 감
* 데이터베이스 트랜잭션이 끝날 때 영속성 컨텍스트를 지움(=1차 캐시도 다 날아 감)
---
### 영속 엔티티의 동일성 보장
```java
Member a = em.find(Member.class, "member1");
Member b = em.find(Member.class, "member1");

System.out.print(a == b);

// 1차 캐시로 반복 가능한 읽기(Repeatable Read) 등급의 트랜잭션 격리 수준을
// 데이터베이스가 아닌 애플리케이션 차원에서 제공 함
```
---
### 엔티티 등록
#### 트랜잭션을 지원하는 쓰기 지연
```java
{
    EntityTransaction transaction = em.getTransaction();

    // 여기까지는 영속성 컨텍스트가 정보를 가지고있음
    // Insert SQL을 데이터베이스에 보내지 않는다(=Insert문을 실행하지 않음).
    em.persist(memberA);
    em.persist(memberB);
        
    // commit을 하는 순간 Insert SQL이 실행 됨
    transaction.commit();    
}
```
__memberA__ : persist 시 쓰기 지연 SQL 저장소에 쿼리를 저장 함 + 1차 캐시에 저장<br>
__memberB__ : persist 시 쓰기 지연 SQL 저장소에 쿼리를 저장 함 + 1차 캐시에 저장

__transaction.commit(); 시 쓰기 지연 SQL 저장소에 있는 쿼리가 데이터베이스로 날아 감__

