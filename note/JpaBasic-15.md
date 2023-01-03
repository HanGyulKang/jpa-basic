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

```java
@Entity
@SequenceGenerator(name = "member_seq_generator"
                        , initialValue = 1, allocationSize = 1)
public class MemberEntity {...}
```

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