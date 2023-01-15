# G사 주주총회 전자투표 시스템

---

## 개발 환경

---

- Java 11
- Spring Boot 2.7.7
- MySQL 8.0.31

## ERD

---
![GabiaPrivateProject](/uploads/3087b664e6a48a5a01016610a8f3ddd4/GabiaPrivateProject.png)

## Use Case Diagram

---
![유스케이스.drawio](/uploads/b345dcb0cbfceaae1f7968fbd11c8999/유스케이스.drawio.png)

## API 설명

---

- **Client**

| index | Http UrI | Http Method | Description |
| --- | --- | --- | --- |
| 1 | /client | POST | 회원가입 |
| 2 | /client/login | POST | 사용자 로그인 |
- **Item**

| index | Http Url | Http Method | Description |
| --- | --- | --- | --- |
| 1 | /item | GET | 안건 목록 조회 |
| 2 | /item/{item-pk} | GET | 안건 상세 조회 |
| 3 | /item | POST | 안건 등록 |
| 4 | /item/{item-pk} | DELETE | 안건 삭제 |
| 5 | /item/{item-pk}/vote | POST | 안건에 대한 투표 등록 |
| 6 | /item/{item-pk}/vote | PUT | 안건에 대한 투표 변경 |
- **Vote-Result**

| index | Http Url | Http Method | Description |
| --- | --- | --- | --- |
| 1 | /vote/{item-pk}/client/{client-pk} | POST | 주주 의결권 사용 |
| 2 | /vote/{item-pk} | GET | 투표 결과 조회 |

## API 문서

---
[API 문서 링크](https://abgudv6403.gitlab.io)

## 주요 기능 흐름도

---

- **사용자 회원가입 기능**
    
![회원가입.drawio](/uploads/e51a6d2d2669bfc564c014afd0361347/회원가입.drawio.png)
    

- **사용자 로그인 기능**
    
![로그인.drawio](/uploads/37ad3ee4111fe5a0393d99f301334eb2/로그인.drawio.png)
    

- **인증, 인가 기능**
    
![인증_인가.drawio](/uploads/2710f44741566590747cf3be88c1538f/인증_인가.drawio.png)
    

- **투표 기능**
    
![투표기능.drawio](/uploads/6dff5a9b7056f0115e2b3f187d584bf1/투표기능.drawio.png)
    

## 추가 필요 개선 사항

---

- 중복 코드 제거
- 불필요한 Query 사용 제거
- 조회시 페이징 처리 구현
- ETC...
