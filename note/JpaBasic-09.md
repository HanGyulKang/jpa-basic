## 플러시(Flush) 란?

* 영속성 컨텍스트의 변경내용을 데이터베이스에 반영하는 것
<br>

### 플러시 발생
* 변경 감지(더티 체킹)
* 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송<br>(등록, 수정, 삭제 쿼리)

### 영속성 컨텍스트를 플러시하는 방법
* __직접 호출__<br>- em.flush()
* __플러시 자동 호출__<br>- 트랜잭션 커밋<br>- JPQL 쿼리 실행 
---
* 혹시 플러시를 하게되면 __1차 캐시를 다 지우게 될까?__<br>
아님. 등록, 수정, 삭제 등 데이터가 변경된 것이 데이터베이스에 반영되는 것일 뿐.
* JPQL 쿼리 실행 시 왜 자동으로 플러시가 실행될까?<br>
```java
em.persist(memberA);
em.persist(memberB);
em.persist(memberC);
// ======= 위 세개 쿼리는 데이터베이스에 '당연히' 안 날아감
// ======= 아직 쓰기 지연 SQL 저장소에 저장만 되어있음

// 중간에 JPQL 실행
// memberA, B, C는 당연히 아직 조회되지 않아야 하지만 이렇게 되면 문제가 발생할 수가 있어서
// JPA는 기본적으로 JPQL 실행 시 무조건 쓰기 지연 SQL 저장소에 저장되어있는 쿼리를 데이터베이스로 날림
query = em.createQuery("select m from Member m", Member.class);
List<Member> members = query.getResultList();
```
---
### 플러시 모드 옵션
> ```java
> em.setFlushMode(FlushModeType.COMMIT);
> ```

* __FlushModeType.AUTO__<br>
커밋이나 쿼리를 실행할 때 플러시(__Default__)<br>
가급적 손 대지말고 AUTO를 기본으로 쓰는걸 권장함
* __FlushModeType.COMMIT__<br>
커밋할 때만 플러시
---
### 총정리. 플러시는?
* 영속성 컨텍스트를 비우지 않음
* 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화 함
* 트랜잭션이라는 작업 단위가 중요 -> __커밋 직전에만 동기화하면 됨__