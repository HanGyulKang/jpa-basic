# JPA 의 가장 중요한 2 가지

1. 객체와 관계형 데이터베이스 __맵핑__ 하기
2. __영속성__ 컨텍스트
---

## 영속성 컨텍스트
* 엔티티를 영구 저장하는 환경 <Br> __Context__ : 환경, 문맥

### 엔티티 매니저?<br>영속성 컨텍스트?
* 눈에 보이지 않는다.
* 엔티티 매니저를 통해서 영속성 컨텍스트에 접근한다.

EntityManager 를 생성하면 1:1로 PersistenceContext(영속성 컨텍스트) 가 생성 된다.

### 엔티티의 생명주기
* __비영속 (new/transient)__<br>영속성 컨텍스트와 전혀 관계가 없는 __새로운__ 상태
* __영속 (managed)__<br>영속성 컨텍스트에 __관리__ 되는 상태
* __준영속 (detached)__<br>영속성 컨텍스트에 저장되었다가 __분리__ 된 상태
* __삭제 (removed)__<br>__삭제__ 된 상태
---
> #### 1. 비영속
> ```java
> // 객체'만' 생성한 상태(비영속)
> Member member = new Member();
> member.setId("member1");
> member.setUsername("회원1");
> ```

> #### 2. 영속
> ```java
> // 생성한 객체를 얻어온 EntityManager에 넣으면 
> // EntityManager 안의 PersistenceContext가 객체를 관리하게 되면서
> // '영속 상태' 가 된다.
> private final EntityManager em;
>         
>   {
>       // 아직 DB에 저장은 되지 않은 상태
>       em.persist(member);
>   }
> ```

> #### 3. 준영속
> ```java
>   {
>       // 회원 엔티티를 영속성 컨텍스트에서 분리 함
>       em.detach(member);
>   }
> ```

> #### 4. 삭제
> ```java
>   {
>       // 객체를 삭제한 상태(삭제)
>       // 실제 영구 저장한 데이터베이스에서 지울 준비
>       em.remove(member);
>   }
> ```
---
### 영속성 컨텍스트의 장점
* 1차 캐시
* 동일성(identity) 보장
* 트랜잭션을 지원하는 쓰기 지연<br>(transactional write-behind)
* 변경 감지(Dirty Checking)
* 지연 로딩(Lazy Loading)
