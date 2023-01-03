# 기본 키 매핑

---

### 직접 할당
* @Id만 사용

### 자동생성(@GeneratedValue)
* IDENTITY : 데이터베이스에 위임, MySQL (예 : auto increment)
* SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, oracle<br><br>@SequenceGenerator 필요
* TABLE : 키 생성용 테이블 사용, 모든 DB(MySQL, Oracle, Maria, etc..)에서 사용 가능함
* AUTO : 방언(Dialect)에 따라 자동 지정

---
