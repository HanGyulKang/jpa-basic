# 기본 키 매핑

---

### 직접 할당
* @Id만 사용

### 자동생성(@GeneratedValue)
* IDENTITY : 데이터베이스에 위임, MySQL (예 : auto increment)
* SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, oracle<br>@SequenceGenerator 필요
* TABLE : 키 생성용 테이블 사용, 모든 DB(MySQL, Oracle, Maria, etc..)에서 사용 가능함
* AUTO : 방언(Dialect)에 따라 자동 지정
---

#### IDENTITY, SEQUENCE 전략
* 데이터베이스에 Id 생성을 위임한다.

| Database  | desc           |
|:----------|:---------------|
| MySQL     | Auto increment |
| Oracle    | sequence       |

##### 주의
* sequence나 auto increment의 경우 __Long을 사용__ 하는 것이 좋다.
* Integer의 경우 2,147,483,648까지만 쌓이고 이후 다시 1로 돌아가기 때문에<br>PK로 사용하는 것은 매우 위험하다(=한 바퀴 돎, _운영에서 생각보다 10억대 금방 돌파한다..._ ).

#### TABLE 전략
* 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
* 장점 : 모든 종류의 데이터베이스에서 사용 가능
* 단점 : 성능...
---

### 권장하는 식별자 전략
* 기본 키 제약 조건 : not null, 유일성 보장, __변동 금지__
* 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키 또는 대체키를 사용하는 것이 좋다.<br><br>
__- 자연키__ : 주민등록번호, 전화번호 같은 것들.. 인데 사실 얘네도 변동 가능성이 있기 때문에 적합하지 않다.
* __권장__ : __"Long형 + 대체키__(UUID같은 것) __+ 키"__ 생성전략 사용
---

## 생각해볼 점
### IDENTITY
@GeneratedValue.__IDENTITY__ 를 사용할 경우 데이터베이스에 값이 Insert 되기 전까지는 PK를 알 수가 없다.
<br>근데 JPA는 __PK(@Id)를 가지고있어야__ 영속성 컨텍스트에 등록이 된다...??!?!!?
<br><br>
그래서 이런 경우에만 persist를 호출하여 insert를 먼저 하고 id를 꺼내서 영속성 컨텍스트에 등록해야 한다...
```java
Member member = new Member();
member.setName("memberA");

// PK가 자동 생성되기 때문에 이름만 넣고 persist 시 DB에 쿼리를 날림
em.persist(member);

// JDBC 드라이버 내부에 입력 후 PK값을 반환하는 기능이 있어서 getId()로 바로 받아볼 수 있다.
// JPA가 해당 값을 입력 후에 들고오기 때문에 별도의 Select 없이 Id를 바로 조회할 수 있다.
member.getId();
```

### SEQUENCE
* __initialValue__ = n<br>n으로로 초기화
* __allocationSize__ = n<br>미리 n개의 시퀀스 값을 미리 땡겨와서 메모리에서 꺼내 씀 (기본 값 50)<br>여러 인스턴스가 올라와있어도 동시성 이슈 문제 없이 동작 함<br>50~100을 권장<br>당연히 서버 내려가면서 메모리에 저장된 데이터들도 날아가기 때문에 땡겨와서 안 쓴 애들은 사라짐
```java
@Entity
@SequenceGenerator(name = "member_seq_generator"
                        , initialValue = 1, allocationSize = 50)
public class MemberEntity {...}
```
SEQUENCE의 경우 persist 시 만들어둔 Sequence에서 값을 미리 얻어 옴
```java
Member member = new Member();
member.setName("memberA");

// GeneratedValue 전략이 Sequence라면 persist 시 미리 값을 얻어 옴
// call next value for MEMBER_SEQ
em.persist(member);
```
