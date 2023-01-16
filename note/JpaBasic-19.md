# 양방향 매핑 시 가장 많이 하는 실수

---

### 연관관계 주인에 값을 입력하지 않음

```java
/** 1. 연관관계 주인에 값을 입력하지 않은 경우 */
// 멤버 저장
Member member = new Member();
member.setUsername("memberA");
em.persist(member);

// 팀 저장
Team team = new Team();
team.setName("TeamA");
// 역방향(주인이 아닌 방향)만 연관관계 설정
team.getMembers().add(member);
em.persist(team);

em.flush();
em.clear();

/** =============================== */

/** 2. 올바른 경우 */
// 팀 저장
Team team = new Team();
team.setName("TeamA");
// team.getMembers().add(member);
em.persist(team);

// 멤버 저장
Member member = new Member();
member.setUsername("memberA");
// 연관관계 주인에 값 설정
member.setTeam(team);
em.persist(member);

em.flush();
em.clear();
```

#### 실무 팁
* 양방향 매핑 시 연관관계 주인 뿐만 아니라 __양 쪽에 모두 값을 넣어주는게 좋다.__

---

#### 양쪽에 모두 값을 안 넣게되면 무슨 문제가 발생할까?
1. __순수 객체 상태를 고려..!__
   <br>위 2번 예시에서 __em.flush(); em.clear();를 통해 1차 캐시를 완전히 비우지 않고__ 아래에서
   <br>
   <br>```Team findTeam = em.find(Team.class, team.getId));```
   <br>```List<Member> members = findTeam.getMembers();```
   <br>```for(Member m : members) m.getUsername()```
   <br>
   <br>를 하게되면 JPA는 쿼리를 하는 것이 아닌, __1차 캐시에 있는 값을 가져오려고 한다.__
   <br>그렇지만 __Team 객체에 member를 넣어주지 않았기 때문에__ 불러올 수 있는 값이 없다.
   <br>__객체 지향적으로 생각했을 때__ 양 쪽에 값을 모두 셋팅해주는게 좋다(=맞다).
   <br>
   <br>[가장 권장하는 방법]
   <br>연관관계 편의 메서드를 만들어서 사용하자!
   <br>com.study.ex1jpabasic.jpashop.entity.Order 클래스 참고
   <br>
   <br>

2. __양방향 매핑 시 무한 루프를 조심하자__
   <br>예 : toString(), lombok, JSON 생성 라이브러리
   <br>
   <br>- toString()
   <br>Member의 toString 시 연결된 Team도 toString으로 뱉는다.
   <br>Team을 toString으로 출력 시 연결된 Member도 toString으로 뱉는다.
   <br>Member <-> Team 무한루프에 걸린다.
   <br>__JSON 생성 라이브러리도 동일__ 하다.
   <br>
   <br>Controller에서 Entity를 직접 반환하려고 하면 JSON 라이브러리가 데이터를 변환하면서 toString()과 같이 서로의 연결된 Entity를 계속 호출해서 박살난다...
   <br>
   <br>가장 중요한 것은
   <br>__Controller에서 절대로 Entity를 반환하지 말아라.__
   <br>__무조건 DTO로 변환해서 뱉어라.__
   <br>(무한루프가 걸리던지, Entity를 변경하는 순간 API의 스펙이 바뀌어버린다.)

---

### 정리

1. 최대한 단방향 매핑으로 끝내라.
2. 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐이다.
3. JPQL에서 역방향으로 탐색할 일이 많음
4. 단방향 매핑을 잘 해두면, __양방향은 필요할 때 추가해도 됨__
   <br>(테이블에 영향을 주지 않음)