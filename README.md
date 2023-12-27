# ⭐ SNS - OhLife
- 자신의 아름다운 일상을 공유하는 Social Network Service인 Oh,Life 입니다.
---
## 📚 기술스택
![Static Badge](https://img.shields.io/badge/Java-blue)
![Static Badge](https://img.shields.io/badge/Spring%20Boot-green)
![Static Badge](https://img.shields.io/badge/Gradle-blue)
![Static Badge](https://img.shields.io/badge/Redis-red)
![Static Badge](https://img.shields.io/badge/Amazon%20S3-orange)

![Static Badge](https://img.shields.io/badge/MySQL-blue)
![Static Badge](https://img.shields.io/badge/Spring%20Data%20JPA-green)
![Static Badge](https://img.shields.io/badge/QueryDSL-blue)
![Static Badge](https://img.shields.io/badge/Amazon%20Ec2-orange)

---
## 🏁 기술사용 목적
Redis
- 회원가입에 필요한 인증번호 저장소

Amazon S3
- 이미지 저장

QueryDSL
- 복잡한 쿼리와 동적 쿼리 문제를 해결하기 위해 사용
---

## ☑️ Usecase
- 사용자
  - 원활한 서비스의 이용을 위해 회원가입 기능을 제공한다.
  - 회원가입 시 이메일 형태의 아이디, 비밀번호, 사용자의 이름, 서비스에서 이용할 user name, 휴대폰 번호를 입력받는다.
  - 이메일과 휴대폰번호는 인증및 중복검사를 진행한다.
  - 서비스의 모든 기능을 이용하기 위해서는 로그인을 해야한다.
  - 이메일, 비밀번호를 입력하여 로그인한다.
  - 로그아웃 기능을 제공한다.
  - 회원정보 수정(변경) 기능을 제공한다.
  - 더 이상 서비스 이용을 원치 않는 사용자를 위해 회원 탈퇴 기능을 제공한다.
     
- 게시글
  - 사용자에게 게시글 작성기능을 제공한다.
  - 사용자에게 게시글 수정기능을 제공한다.
  - 사용자에게 게시글 조회기능을 제공한다.
  - 특정 사용자가 작성한 모든 게시글을 보기 위한 별도의 조회 기능을 제공한다.
  - 사용자에게 게시글 작성기능을 제공한다.

- 팔로우
  - 다른 서용자에 대해서 팔로우 기능을 제공한다.
  - 다른 서용자에 대해서 언팔로우 기능을 제공한다.

- 좋아요
  - 게시글에 대해서 좋아요 기능을 제공한다.
  - 좋아요를 누른 게시글에 대해서 취소 기능을 제공한다.

- 알람
  - 다른 사용자가 팔로우시 알람 기능을 제공한다.
  - 다른 사용자가 게시글에 졸아요를 누를시 알람 기능을 제공한다.
  - 팔로우한 사용자가 게시글을 작성시 알람 기능을 제공한다.
  - 게시글 작성시 사용자를 팔로우한 다른 사용자게에 알람 기능을 제공한다.
