# G사 주주총회 전자투표 시스템


## 개발 환경

- Java 11
- Spring Boot 2.7.7
- MySQL 8.0.31

## ERD

![image](https://user-images.githubusercontent.com/75138553/228469874-17902e30-a635-4cc9-a0dd-9a3806e1f4be.png)

## Use Case Diagram

![image](https://user-images.githubusercontent.com/75138553/228469908-caf4d610-ae1b-4127-84a4-aa50340a8b37.png)

## API 설명

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
[API 문서 링크](https://abgudv6403.gitlab.io)

## 주요 기능 흐름도

- **사용자 회원가입 기능**
    
![image](https://user-images.githubusercontent.com/75138553/228469958-05f41be3-9d3a-462f-8dac-5aefedec405f.png)
    

- **사용자 로그인 기능**
    
![image](https://user-images.githubusercontent.com/75138553/228469993-5b181773-f7aa-4ca0-847f-dc85f48b3d27.png)
    

- **인증, 인가 기능**
    
![image](https://user-images.githubusercontent.com/75138553/228470011-9df4acbb-47be-4c1b-876f-9712f25315e8.png)
    

- **투표 기능**
    
![image](https://user-images.githubusercontent.com/75138553/228470027-9ae2045a-8d32-4aeb-a68f-36b0d2143066.png)
    

## 추가 필요 개선 사항

- 중복 코드 제거
- 불필요한 Query 사용 제거
- 다양한 상황에 대한 단위, 통합 테스트 코드 작성
- 조회시 페이징 처리 구현
- ETC...
