# <div align=center>SocialApp</div>
[KMP(Kotlin MultiPlatform)](https://firebase.google.com/) 기반 안드로이드 및 IOS 타겟 소셜 네트워크 서비스 어플리케이션입니다.

# 특징

* 안드로이드 및 IOS 멀티플랫폼 지원
* 자체 유저 정보 관리 서버 처리
* 팔로우 추천 유저 목록 제공
* 팔로우 유저 피드 목록 제공
* 게시글의 좋아요와 댓글 기능 제공

# 클라이언트 기술스택 및 라이브러리
* Android 기준 최소 SDK 26 / 타겟 SDK 35
* IOS 기준 최소 16.0
* kotlin 언어 기반, 비동기 처리를 위한 coroutine + Flow
* 종속성 주입을 위한 [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp)
* JetPack
  * CMP(Compose MultiPlatform) - 여러 플랫폼에서 UI를 공유할 수 있는 선언형 프레임워크로, KMP와 Compose를 기반으로 합니다.
  * LifeCycle - 어플리케이션의 수명 주기를 관찰하고 수명 주기 변경에 따라 UI 상태를 처리합니다.
  * ViewModel - UI와 DATA 관련된 처리 로직을 분리합니다.
  * Paging3 - 게시글과 댓글 목록을 무한 스크롤을 처리하고 관리합니다. (API 페이징 처리)
  * Navigation - [Compose Navigation](https://developer.android.com/develop/ui/compose/navigation?hl=ko)을 활용해 화면 전환시 Type-Safe한 인자를 전달하고 Notification과 연계하여 채팅방의 Deep Link를 구현합니다.
  * DataStore - SharedPreferences의 한계점을 개선한 라이브러리로 로그인된 유저 계정 정보를 관리합니다
* Architecture
  * MVVM 패턴 적용 - Model + View + ViewModel
  * MVI 패턴 적용 - Model + View + Intent
  * Repository 패턴 적용 - Data + Domain + Presentation Layer 클린 아키텍처
* [moko-Resource](https://github.com/icerockdev/moko-resources) - 다중 플랫폼 개발 환경의 폰트, 이미지와 같은 공통된 리소스 참조를 지원합니다. 
* [ktor client](https://ktor.io/docs/client-create-multiplatform-application.html) - 다중 플랫폼 클라이언트의 HTTP 통신 기능을 지원합니다.
* 이미지 관리
  * [Coil](https://coil-kt.github.io/coil/README-ko/) - Coroutine기반으로 효율적인 비동기 이미지를 로드하고 적용합니다.
  * [peekaboo](https://github.com/onseok/peekaboo) - CMP 타겟, 갤러리로부터 복수의 이미지 선택 기능을 연계하여 이미지를 업로드합니다.
  * [Material Icon Extended](https://fonts.google.com/icons) - 기본으로부터 더욱 확장한 ImageVector 아이콘을 사용합니다.

# 서버 기술스택 및 라이브러리

* [Ktor](https://ktor.io/) - Kotlin 기반의 비동기 웹 프레임워크
  * JWT 인증 - 사용자 인증 및 보안을 위한 토큰 기반 인증 구현
  * Routing - RESTful API 엔드포인트 구성
  * Content Negotiation - JSON 직렬화/역직렬화 처리
  * [서버 프로젝트 저장소](https://github.com/beh0907/Socialserver)
* Database
  * PostgreSQL - 관계형 데이터베이스
  * [Exposed](https://github.com/JetBrains/Exposed) - Kotlin SQL 프레임워크
    * DSL - 타입 안전한 SQL 쿼리 작성
    * Transaction 관리
    * 데이터베이스 마이그레이션
* 종속성 주입을 위한 [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp)
* kotlin 언어 기반, 비동기 처리를 위한 coroutine
* Utility
  * [Snowflake](https://github.com/twitter-archive/snowflake/tree/snowflake-2010) - 분산 환경에서 유니크한 ID 생성
    * 시간 기반으로 정렬 가능한 64비트 ID 생성
    * 높은 성능과 확장성 제공

# 스크린샷

| 로그인 화면                                                                                     | 회원가입 화면                                                                                     | 메인 화면                                                                                     |
|--------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| ![로그인 화면](https://github.com/user-attachments/assets/a908a748-74a7-456d-8065-84958b0830cc) | ![회원가입 화면](https://github.com/user-attachments/assets/4810ab86-9cdd-406a-8fa2-399a40538947) | ![메인 화면](https://github.com/user-attachments/assets/9002b62d-0e40-484a-9ee9-e28636a73911) |


| 게시글 작성 화면                                                                                   | 게시글 수정 화면                                                                                     | 게시글 상세 화면                                                                                     |
|---------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| ![게시글 작성 화면](https://github.com/user-attachments/assets/2e447d6e-ef4d-47cd-9479-cf2d26328330) | ![게시글 수정 화면](https://github.com/user-attachments/assets/a20caf7e-817f-41bc-8bab-9a68d6aed8dd) | ![게시글 상세 화면](https://github.com/user-attachments/assets/c693f2fd-d72a-4453-968d-9c1d9d8daa33) |


| 프로필 화면                                                                                     | 프로필 수정 화면                                                                                     | 팔로워/팔로윙 목록 화면                                                                                 |
|--------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|
| ![프로필 화면](https://github.com/user-attachments/assets/d463b011-bd13-440f-805a-902131aa5efe) | ![프로필 수정 화면](https://github.com/user-attachments/assets/89084f93-71a8-4c62-aa40-7fe0c7f10dad) | ![팔로워/팔로윙 목록 화면](https://github.com/user-attachments/assets/72467f79-934d-4a9c-883e-4db53f26c8b4) |


| ERD                                                                                          |
|----------------------------------------------------------------------------------------------|
| ![ERD](https://github.com/user-attachments/assets/85949367-6088-44a8-b967-d62cc1b937ec) |
