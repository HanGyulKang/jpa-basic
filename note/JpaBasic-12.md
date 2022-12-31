# 객체와 테이블 매핑

---
* __객체와 테이블 매핑__<br>@Entity, @Table


* __필드와 컬럼 매핑__<br>@Column



* __기본 키 매핑__<br>@Id


* __연관관계 매핑__<br>@ManyToOne, @JoinColumn
---

### @Entity
위의 어노테이션이 붙은 클래스는 JPA과 관리한다. 
* __주의__<br>
기본 생성자 필수<br>
final 클래스<br>
enum, interface, inner 클래스 사용 불가<br>
데이터베이스에 저장할 필드에 final 금지(당연...)<br>

