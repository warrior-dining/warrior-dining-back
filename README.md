# WarriorDining

음식점 예약 사이트 백앤드 서버

# 프로젝트 소개

바쁜 현대인들이 좋은 음식을 즐기기 위해 인기 있는 식당을 찾지만, 긴 대기 시간 때문에 불편함을 겪는 경우가 많습니다. 이를 해결하기 위해 '웨이팅' 이라는 시스템이 도입되었지만 여전히 줄을 서서 기다려야 하고
식당에서는 그로인한 추가적인 인력을 사용하는 등 기존의 불편함을 온전히 해결하지는 못하고 있습니다.

저희 플랫폼은 사용자들이 원하는 시간에 예약을 통해 식당을 방문할 수 있게 하여, 대기를 최소화 하여 편리하게 외식을 즐길 수 있도록 돕습니다. 이를 통해 고객은 시간을 절약하고, 식당은 효율적인 예약 관리를 통해
더 나은 서비스를 제공할 수 있습니다. 월간 광고비를 기반으로 수익을 창출하면서, 사용자와 식당 모두에게 유익한 솔루션이 되고자 합니다.

![프로젝트 간략 소개](https://github.com/user-attachments/assets/87474a67-8c15-4cb1-b768-f531109d49ec)

# Team Members (팀원 및 팀 소개)

|                                           오상민                                           |                                           차성민                                           |                                           원요엘                                           |                                           김은서                                           |
|:---------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/170058824?v=4" alt="오상민" width="150"> | <img src="https://avatars.githubusercontent.com/u/169964868?v=4" alt="차성민" width="150"> | <img src="https://avatars.githubusercontent.com/u/170058857?v=4" alt="원요엘" width="150"> | <img src="https://avatars.githubusercontent.com/u/170059046?v=4" alt="김은서" width="150"> |
|      [GitHub](https://github.com/o-tao)<br/>[Blog](https://tao-tech.tistory.com/)       |                         [GitHub](https://github.com/JJAJJAKIM)                          |                           [GitHub](https://github.com/myin98)                           |                          [GitHub](https://github.com/mirinaes)                          |

## 기능

1. 일반 회원 회원가입 및 로그인

2. 소셜 로그인 (네이버, 카카오)

3. 회원 마이페이지
    - 내정보, 정보 수정, 회원 탈퇴
    - 리뷰 관리, 문의 내역, 즐겨찾기

4. 음식점 예약 및 리뷰(별점) 작성

5. 음식점 카테고리별 예약건수 통계자료를 바탕으로 인기 맛집 정보 제공

6. 관리자 페이지
    - 회원관리, 음식점 관리, 예약 관리, 리뷰 관리, 문의사항 관리

7. 오너 페이지
    - 사용자 음식점 예약에 따른 본인 음식점 예약 내역 관리

## BackEnd 개발환경

### Languages & Frameworks

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white" alt="java"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="gradle"> <img src="https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="spring_boot"> <img src="https://img.shields.io/badge/spring_data_jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="spring_data_jpa">
<br>

### Database & Deployment

<img src="https://img.shields.io/badge/mariadb-1F305F?style=for-the-badge&logo=mariadb&logoColor=white" alt="mariadb"> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="docker">
<br>

### Development & Collaboration Tools

<img src="https://img.shields.io/badge/intellij_idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white" alt="intellij"> <img src="https://img.shields.io/badge/eclipse_ide-2C2255?style=for-the-badge&logo=eclipseide&logoColor=white" alt="eclipse"> <img src="https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white" alt="visual-studio-code">
<br>
<img src="https://img.shields.io/badge/datagrip-000000?style=for-the-badge&logo=datagrip&logoColor=white" alt="datagrip"> <img src="https://img.shields.io/badge/dbeaver-382923?style=for-the-badge&logo=dbeaver&logoColor=white" alt="dbeaver">
<br>
<img src="https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white" alt="git"> <img src="https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white" alt="github"> <img src="https://img.shields.io/badge/sourcetree-0052CC?style=for-the-badge&logo=sourcetree&logoColor=white" alt="sourcetree"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white" alt="postman"> <img src="https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white" alt="discord">

### Security & Authentication

<img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens" alt="JWT"> <img src="https://img.shields.io/badge/Spring_Security-%6DB33F.svg?style=for-the-badge&logo=SpringSecurity&logoColor=white" alt="SpringSecurity"><img src="https://img.shields.io/badge/oauth2.0-black?style=for-the-badge" alt="oauth2.0">

# 프로젝트 실행 및 세팅

## 환경 세팅

### git clone
```text
git clone https://github.com/warrior-dining/warrior-dining-back.git
```

###  .env 세팅
```dotenv
DB_URL=jdbc:mariadb://localhost:13306/dining
DB_USERNAME=root
DB_PASSWORD={PASSWORD}

DOCKER_DB_DATABASE=dining
DOCKER_DB_PASSWORD={PASSWORD}
DOCKER_TZ=Asia/Seoul

SECRET_KEY={TOKEN_SECRET_KEY}

KAKAO_REST_API_KEY={KAKAO_REST_API_KEY}
KAKAO_CLIENT_SECRET={KAKAO_CLIENT_SECRET}

NAVER_REST_API_KEY={NAVER_REST_API_KEY}
NAVER_CLIENT_SECRET={NAVER_CLIENT_SECRET}

```

###  DB 환경 세팅

- docker-compose.yml
```yaml
services:
  mysql:
    image: mariadb:latest
    container_name: mariadb
    restart: always
    ports:
      - "13306:3306"
    volumes:
      - ./db/data:/var/lib/mysql:rw
      - ./db/init:/docker-entrypoint-initdb.d:ro
    environment:
      MYSQL_ROOT_PASSWORD: ${DOCKER_DB_PASSWORD}
      MYSQL_DATABASE: ${DOCKER_DB_DATABASE}
      TZ: ${DOCKER_TZ}

```
- docker-compose.yml 실행 명령어
```text
docker-compose up -d
```

###  DB 사용자 생성

```sql
GRANT ALL PRIVILEGES ON dining.* TO 'warrior'@'%' IDENTIFIED BY 'warrior!@3';
SELECT * FROM mysql.user;
```

- resources/JOB.sql안의 ddl 생성 후 실행

# API 명세서

API 엔드포인트 상세 문서는 [Swagger](http://localhost:8080/swagger-ui/index.html)에서 확인할 수 있습니다.

| Description    | HTTP Method | Endpoint            | QueryParameter                                                 |
|----------------|-------------|---------------------|----------------------------------------------------------------|
| **예약TOP 조회**   | `GET`       | `/api/place/top`    |                                                                |
| **이달의 맛집 조회**  | `GET`       | `/api/place/month`  |                                                                |
| **음식점 전체 리스트** | `GET`       | `/api/place`        | ?page=0                                                        |
| **음식점 상단바 검색** | `GET`       | `/api/place/search` | ?keyword=검색어&page=3&categoryId=5&minPrice=20001&maxPrice=50000 |
| **음식점 상세**     | `GET`       | `/api/place/{id}`   |                                                                |

## USER

| Description | HTTP Method | Endpoint           |
|-------------|-------------|--------------------|
| **회원가입**    | `POST`      | `/api/user/signup` |
| **로그인**     | `POST`      | `/api/user/signin` |
| **비밀번호 찾기** | `POST`      | `/api/user`        |

### 마이페이지

| Description          | HTTP Method | Endpoint                                        | QueryParameter  |
|----------------------|-------------|-------------------------------------------------|-----------------|
| **마이페이지 내정보**        | `GET`       | `/api/user`                                     |                 |
| **내정보 수정**           | `PUT`       | `/api/user`                                     |                 |
| **회원 탈퇴**            | `DELETE`    | `/api/user`                                     |                 |
| **마이페이지 즐겨찾기 리스트**   | `GET`       | `/api/user/bookmarks`                           | ?page=0&size=4  |
| **즐겨찾기 추가**          | `POST`      | `/api/user/bookmarks`                           |                 |
| **즐겨찾기 삭제**          | `DELETE`    | `/api/user/bookmarks`                           | ?placeId=1      |
| **마이페이지 문의사항 리스트**   | `GET`       | `/api/user/inquiries`                           | ?page=0&size=10 |
| **문의사항 상세**          | `GET`       | `/api/user/inquiries/{id}`                      |                 |
| **문의사항 작성**          | `POST`      | `/api/user/inquiries`                           |                 |
| **문의사항 수정**          | `PUT`       | `/api/user/inquiries/{id}`                      |                 |
| **마이페이지 음식점 예약 리스트** | `GET`       | `/api/user/reservation`                         | ?page=0&size=3  |
| **음식점 예약 상세**        | `GET`       | `/api/user/reservation/{reservationId}`         |                 |
| **음시점 예약 추가**        | `POST`      | `/api/user/reservation`                         |                 |
| **음식점 예약 수정**        | `PUT`       | `/api/user/reservation/{reservationId}`         |                 |
| **음식점 예약 취소**        | `DELETE`    | `/api/user/reservation/{reservationId}`         |                 |
| **마이페이지 리뷰 리스트**     | `GET`       | `/api/user/reviews`                             | ?page=0&size=5  |
| **리뷰 수정 상세**         | `GET`       | `/api/user/reviews/{id}`                        |                 |
| **리뷰 등록 상세**         | `GET`       | `/api/user/reviews/reservation/{reservationId}` |                 |
| **리뷰 등록**            | `POST`      | `/api/user/reviews/reservation/{reservationId}` |                 |
| **리뷰 수정**            | `PUT`       | `/api/user/reviews/{id}`                        |                 |
| **리뷰 삭제**            | `DELETE`    | `/api/user/reviews/{id}`                        |                 |

## ADMIN

| Description    | HTTP Method | Endpoint     |
|----------------|-------------|--------------|
| **관리자 메인 페이지** | `GET`       | `/api/admin` |

### 회원 관리

| Description     | HTTP Method | Endpoint                    | QueryParameter                         |
  |-----------------|-------------|-----------------------------|----------------------------------------|
| **전체 회원 목록 조회** | `GET`       | `/api/admin/users`          | ?type=email&keyword=검색어&page=0&size=10 |
| **회원 상세**       | `GET`       | `/api/admin/users/{userId}` |                                        |
| **사용자 권한 부여**   | `POST`      | `/api/admin/users/{userId}` |                                        |

### 음식점 관리

| Description      | HTTP Method | Endpoint                      | QueryParameter                        |
  |------------------|-------------|-------------------------------|---------------------------------------|
| **전체 음식점 목록 조회** | `GET`       | `/api/admin/places`           | ?type=name&keyword=검색어&page=0&size=10 |
| **음식점 상세**       | `GET`       | `/api/admin/places/{placeId}` |                                       |
| **음식점 이미지 조회**   | `GET`       | `/api/admin/places/view`      |                                       |
| **음식점 등록**       | `POST`      | `/api/admin/places`           |                                       |
| **음식점 수정**       | `PUT`       | `/api/admin/places/{placeId}` |                                       |

### 예약 관리

| Description       | HTTP Method | Endpoint                       | QueryParameter                              |
  |-------------------|-------------|--------------------------------|---------------------------------------------|
| **전체 예약 목록 조회**   | `GET`       | `/api/admin/reservations`      | ?page=0&size=10&status=&type=id&keyword=검색어 |
| **예약 상태값(취소) 변경** | `PATCH`     | `/api/admin/reservations/{id}` |                                             |

### 리뷰 관리

| Description       | HTTP Method | Endpoint                  | QueryParameter                                              |
  |-------------------|-------------|---------------------------|-------------------------------------------------------------|
| **전체 리뷰 목록 조회**   | `GET`       | `/api/admin/reviews`      | ?searchtype=user&searchkeyword=&page=0&size=10&sorttype=검색어 |
| **예약 상태값(취소) 변경** | `PATCH`     | `/api/admin/reviews/{id}` |                                                             |

### 문의사항 관리

| Description       | HTTP Method | Endpoint                    | QueryParameter                      |
  |-------------------|-------------|-----------------------------|-------------------------------------|
| **전체 문의사항 목록 조회** | `GET`       | `/api/admin/inquiries`      | ?type=title&keyword=&page=0&size=10 |
| **문의사항 상세**       | `GET`       | `/api/admin/inquiries/{id}` |                                     |
| **문의사항 답변**       | `POST`      | `/api/admin/inquiries/{id}` |                                     |

## OWNER

### 예약 관리

| Description       | HTTP Method | Endpoint          | QueryParameter               |
  |-------------------|-------------|-------------------|------------------------------|
| **예약 현황 리스트**     | `GET`       | `/api/owner`      | ?status=예약상태값&page=0&size=10 |
| **예약 상태값(취소) 변경** | `PATCH`     | `/api/owner/{id}` |                              |
