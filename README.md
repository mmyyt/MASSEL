# MASSEL

Spring을 기반으로 하는 수요조사와 판매 관리를 위한 다양한 기능을 제공하는 사이트입니다

 🔗 [MASSEL](http://15.164.222.220:8080/MASSEL)




## 프로젝트 목적

MASSEL은 누구나 자신이 창작한 상품을 판매하는 것을 돕기 위해 만들어진 프로젝트 입니다.

사용자들은 MASSEL을 통해 상품 수요 조사를 직접 생성하고, 이를 바탕으로 실제 판매까지 진행할 수 있습니다.

이를 통해 사용자는 자신의 아이디어를 제품화하고, 직접 판매하며 다른 사용자들과 직접 소통하고 피드백을 받을 수 있습니다. 


또한, 실시간 채팅과 알림 기능을 통해 구매자와 판매자가 원활하게 소통할 수 있으며,

판매자들은 구매자의 피드백과 반응을 실시간으로 확인하여 상품과 서비스를 개선할 수 있습니다.

MASSEL은 새로운 아이디어와 상품을 손쉽게 공유하고, 이를 통해 창의적이고 혁신적인 마켓을 구축하려는 모든 사람을 위한 플랫폼입니다.





## 사용기술

- Java, Spring Framework, Spring Scheduler, MyBatis
- MySql
- JSP, HTML, CSS, JavaScript
- STOMP, WebSocket
- AWS EC2, Tomcat

## 주요 기능

1. **회원관리**
- 로그인/회원가입
- 회원 정보 수정

2. **수요조사 및 판매폼 관리**
-  수요조사 폼 및 판매폼 작성, 조회, 수정, 삭제
-  상품을 추가, 삭제, 수정하여 관리 가능

3. **찜 기능**
- 사용자가 원하는 상품을 찜목록에 추가/삭제

4. **1:1대화**
- STOMP를 활용한 실시간 채팅 기능

5. **알람기능**
- 새로운 채팅 메시지, 입금 알림 등을 실시간으로 알림

6. **결제 관리**
- 구매자의 결제 상태 조회 및 변경 기능

7. **배송 관리**
- 배송 상태 조회 및 변경 기능

8. **상태 자동 업데이트**
- Cron식을 이용해 글 상태, 결제 상태 자동 업데이트





## 데이터베이스 구조

![massel-erd](https://github.com/user-attachments/assets/516a7531-b19b-42b7-8c1d-d8624113902d)





## 프로젝트 설치 및 실행

$ git clone https://github.com/mmyyt/MASSEL.git

$ cd MASSEL

### database 설정 


### 1. MySQL접속
mysql -u root -p


### 2. 데이터베이스 생성 및 선택

CREATE DATABASE masseldb;

USE masseldb;


### 3. SQL파일 실행

mysql -u -root -p masseldb < schema.sql


### 4. RootConfig.java에 사용자 정보 입력
경로 : src/main/java/com/massel/www/config/RootConfig.java

hikariconfig.setUsername("username");

hikariconfig.setPassword("password");

