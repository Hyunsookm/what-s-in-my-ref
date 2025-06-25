# 🧊 What's in my refrigerator

냉장고 속 재료를 기반으로 요리를 추천해주는 웹 서비스입니다.  
배달 음식 의존도를 줄이고, 직접 요리를 하도록 유도하는 목적에서 시작되었습니다.

---

## 💡 주요 기능 (Features)

- 냉장고 속 재료 선택 기능  
- 선택한 재료 기반 요리 추천  
- 추천된 요리에 대한 상세 레시피 확인  
- 외부 레시피 사이트 연결  
- 관리자 기능 (예: 레시피 등록, 삭제, 승인 등)  
- 사용자 인증 (로그인/회원가입)  
- 인기 요리 추천  
- 사용자 참여형 게시판  
  - 사용자가 직접 레시피 등록  
  - 추천 수가 높은 레시피는 관리자가 정식 레시피로 등록하여 추천에 포함
---

## 🧱 기술 스택 (Tech Stack)

- **Frontend**  
  - HTML, CSS, JavaScript  
  - Thymeleaf (타임리프)

- **Backend**  
  - Java Spring Boot

- **Database**  
  - MySQL

- **Deployment**  
  - Docker, Docker-Compose (Spring Boot + MySQL 멀티 컨테이너)  
  - AWS EC2

---

## ⚙️ 설치 및 실행 방법 (Installation & Run)

### 1. 프로젝트 클론

```bash
https://github.com/Hyunsookm/what-s-in-my-ref.git
cd your-project
```

```bash
./gradlew bootRun
```


🙋 기여자 및 역할 (Contributors & Roles)

	•	김현수: 프로젝트 기획, 레시피 데이터 수집 및 정제, Docker 기반 배포
	•	이상현: 프론트엔드 개발, UI/UX 설계
	•	장준서: 프론트엔드 개발, 회원가입 및 로그인 기능 구현
	•	정동재: 백엔드 개발, 사용자 참여 게시판 설계, Thymeleaf 적용
