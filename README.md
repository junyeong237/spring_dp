# spring_dp

S.A 계획서 링크
https://www.notion.so/S-A-e4116270e905478cacd415ba7dea9811


## 개요 
> 4족배달 : 사용자는 메뉴를 탐색하고 주문할 수 있으며, 관리자는 주문 처리와 메뉴 관리를 수행할 수 있는 시스템

## 팀 구성원 및 역할 분담 
> 겨울이 지나면 Spring조 : 팀장 이준영, 팀원 최준영, 이종렬, 정해인     

* 최준영
  * 유저, 관리자 유저, 인증 인가(+redis), 이메일 인증, global 예외처리 
* 이종렬
  * 리뷰, 관리자 리뷰, AwsS3, 유저 이미지 업로드,검색
* 정해인
  * 메뉴, 관리자 메뉴, 카테고리, 메뉴 이미지 업로드
* 이준영
  * 장바구니,주문,관리자 주문,좋아요, 정렬

(각 도메인별 예외처리는 각자의 도메인에서 각자 처리)

## 설계전략

<details>
<summary>시스템 상황분석</summary>


- `4족 배달`은 `회원가입`을 할 수 있다.
    - id, **email, nickname, password**, intro, image
    - `id`, `email`, `password`, `nickname - 램덤 닉네임 기본으로 부여`,`ex) 바나나123
    - `이메일` 인증 기능이 존재한다.
    - `사용자`는 `손님`, `관리자`로 구분된다.
- `로그인을 안한 사용자`는
    - 리뷰 목록을 조회할 수 있다.
        - 메뉴 목록을 조회할 수 있다.
        - 작성된 모든 리뷰를 조회할 수 있다.
    - 카테고리별로 최신순 / 좋아요순으로 메뉴목록을 조회할 수 있다.
    - 다른 사람의 마이페이지를 볼 수 있다.
        - 닉네임, 사진, 한 줄 소개를 볼 수 있다.
        - 해당 사용자의 리뷰 목록, 좋아요 목록을 볼 수 있다.
- `로그인 사용자(손님)`는
    - `장바구니`에 `메뉴`를 담을 수 있다.
    - `손님`은 `장바구니`를 여러개 사용 가능하다 (1 : N)
    - 하나의 `장바구니`에는 여러 개의 `메뉴` 를 담을 수 있으며, 수량은 제한이없다.
    - 사용자는 본인의 `장바구니`에 담긴 `메뉴`를 `주문`할 수 있다.
    - 주문한 메뉴를 관리자가 진행을 하기 전까진 취소할 수 있다.
    - `리뷰`를 작성할 수 있다.
        - ~~별점을 추가할 수 있다. (보류)~~
    - `리뷰`를 수정할 수 있다.
    
    - `리뷰`를 삭제할 수 있다.
    - `메뉴` `좋아요`를 누를 수 있다. (N:M)
    - 자신의 `프로필`을 `수정`할 수 있다.
        - `주문 내역`을 볼 수 있다.
        - 자신이 작성한 `리뷰 목록`을 볼 수 있다.
        - 자신이 누른 `좋아요 목록`을 볼 수 있다.
        - 닉네임이나 한 줄 소개, 사진은 바로 `수정` 가능하다.
        - `비밀번호` `수정`은 한번 더 비밀번호를 `검증`한다.
        - 최근 3번 안에 사용한 비밀번호는 사용할 수 없다.
        - 로그인 또는 비밀번호 수정 시도를 3번 이상할 경우, 계정을 잠군다.
- `로그인 사용자 (관리자)` 는
    - `상품`을 `추가`할 수 있다.
        - 상품의 카테고리를 하나 이상 지정해야한다.
    - `상품`을 `수정`할 수 있다.
    - `상품`을 `삭제`할 수 있다.
    - `카테고리`를 `추가` 할 수 있다.
    - `카테고리`를 `수정`할 수 있다.
    - `카테고리`를 `삭제`할 수 있다
    - 당일 `판매 상품 목록`을 `조회`할 수 있다.
    - `총 판매 상품 목록`을 `조회`할 수 있다
    - `판매내역`을 `삭제`할 수 있다.
    - `주문 보류중`인 주문을 `취소` 혹은 `진행중`으로 변경할 수 있다.
    - `주문 진행중`인 주문을 `완료`상태로 변경 할 수 있다.
    - 사용의 `리뷰`를 관리 할 수 있다.
    - 사용자들을 조회할 수 있다.
    - 주문 처리를 할 수 있다. (보류 → 진행 중→ 완료 / 보류 → 취소) → Enum
    - 관리자가 사용자의 등급을 조정할 수 있다. (일반 사용자 → 관리자)


</details>


<details>
 
<summary>ERD 설계전 시뮬레이션 및 ERD 설계</summary>

![image](https://github.com/junyeong237/spring_dp/assets/70509488/124e8602-eb20-4fe4-b11f-3cc4d0eb24ac)


![erd](https://github.com/junyeong237/spring_dp/assets/70509488/331fdcf9-69c0-47df-85b8-b10bee63d81e)

https://www.erdcloud.com/d/KSXe7seXT4ftQCYD9

</details>
 

## 협업 전략

### 규칙

- 커밋 메세지는 한글로 작성하기
- 이슈 제목은 자유롭게(최대한 알아보기 쉽게)

### 커밋 컨벤션

<aside>
💡 https://bumkeyy.gitbook.io/bumkeyy-code/project-management/pull-request

</aside>

ex) `feat: 로그인 기능 구현`

→ Git Issue 를 사용했다면: `[#issue-number] feat: 로그인 기능 구현`

→ ex) `[#15] feat: 로그인 기능 구현`

| Tag | Description |
| --- | --- |
| Feat | 새로운 기능을 추가 |
| Fix | 버그 수정 |
| Style | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우 |
| Refactor | 프로덕션 코드 리팩토링 |
| Comment | 필요한 주석 추가 및 변경 |
| Docs | Readme 등 문서 수정 |
| Test | 테스트 코드, 리펙토링 테스트 코드 추가, Production Code 변경 없음 |
| Chore | 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트  |
| Rename | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| Remove | 파일을 삭제하는 작업만 수행한 경우 |
- ✅ **브랜치 전략(깃 플로우)**
    - main: 2명 리뷰
    - develop: 1명 리뷰
    - feature/(기능 명)

### 성과

![image](https://github.com/junyeong237/spring_dp/assets/70509488/0e5014e6-d322-4c6d-b1e3-65e7400093b9)

---------------------------------------------------------------------------------------------------------------

![image](https://github.com/junyeong237/spring_dp/assets/70509488/595815c8-4975-4207-a45a-d8d61db9c80b)


---------------------------------------------------------------------------------------------------------------

![image](https://github.com/junyeong237/spring_dp/assets/70509488/e3a5a945-6618-4c55-9485-99fc14522aaa)


---------------------------------------------------------------------------------------------------------------
![image](https://github.com/junyeong237/spring_dp/assets/70509488/4a8c7268-3173-4020-bc1d-aa35504d094b)
![image](https://github.com/junyeong237/spring_dp/assets/70509488/5efd541a-7a56-415c-a779-b524b38f416c)



 
## 구현 사항 

### **필수 구현 기능**

- [x]  **사용자 인증 기능**
    - 회원가입 기능
    - 로그인 및 로그아웃 기능
    - 이메일 인증
- [x]  **프로필 관리**
    - 프로필 수정 기능
- [x]  **주문 CRUD 기능**
    - 주문 작성, 조회, 수정, 삭제 기능
- [x]  **리뷰 CRUD 기능**
    - 리뷰 작성, 조회, 수정, 삭제 기능 


### **추가 요구사항**

- [ ]  ~~**소셜 로그인 기능**~~
- [x] **백오피스 만들어보기**
    - 관리자 페이지 구성
- [x]  **좋아요 기능**
    - 메뉴에 좋아요/좋아요 취소 기능


### [⭐](https://emojipedia.org/star/)명예의 전당[⭐](https://emojipedia.org/star/)

- [x] **프로필에 사진 업로드 구현**
    - AWS3 사용
- [x] **메뉴에 사진 업로드 구현**
    - AWS3 사용
- [ ] ~~**AWS 를 이용한 서비스의 배포 ⭐**~~
- [ ] ~~**HTTP를 HTTPS로 업그레이드 하기**~~
     

## 디렉토리 구조
> 크게 `domain`과 `global`로 나누었습니다.     
<details>
<summary>자세히보기</summary>

```
├─main
│  ├─java
│  │  └─com
│  │      └─example
│  │          └─dp
│  │              │  DpApplication.java
│  │              │
│  │              ├─domain
│  │              │  ├─admin
│  │              │  │  ├─controller
│  │              │  │  │      AdminCategoryController.java
│  │              │  │  │      AdminMenuController.java
│  │              │  │  │      AdminOrderController.java
│  │              │  │  │      AdminReviewController.java
│  │              │  │  │      AdminUserController.java
│  │              │  │  │      TempController.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │      │  AdminCategoryService.java
│  │              │  │      │  AdminMenuService.java
│  │              │  │      │  AdminOrderService.java
│  │              │  │      │  AdminReviewService.java
│  │              │  │      │  AdminUserService.java
│  │              │  │      │
│  │              │  │      └─impl
│  │              │  │              AdminCategoryServiceImpl.java
│  │              │  │              AdminMenuServiceImpl.java
│  │              │  │              AdminOrderServiceImpl.java
│  │              │  │              AdminReviewServiceImpl.java
│  │              │  │              AdminUserServiceImpl.java
│  │              │  │
│  │              │  ├─cart
│  │              │  │  ├─controller
│  │              │  │  │      CartController.java
│  │              │  │  │
│  │              │  │  ├─dto
│  │              │  │  │  ├─request
│  │              │  │  │  │      CartDeleteRequestMenuDto.java
│  │              │  │  │  │      CartRequestMenuDto.java
│  │              │  │  │  │
│  │              │  │  │  └─response
│  │              │  │  │          CartResponseDto.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      Cart.java
│  │              │  │  │
│  │              │  │  ├─exception
│  │              │  │  │      CartErrorCode.java
│  │              │  │  │      NotFoundCartMenuExcepiton.java
│  │              │  │  │      NotFoundMenuException.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      CartRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │      │  CartService.java
│  │              │  │      │
│  │              │  │      └─impl
│  │              │  │              CartServiceImpl.java
│  │              │  │
│  │              │  ├─category
│  │              │  │  ├─dto
│  │              │  │  │  ├─request
│  │              │  │  │  │      CategoryRequestDto.java
│  │              │  │  │  │
│  │              │  │  │  └─response
│  │              │  │  │          CategoryResponseDto.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      Category.java
│  │              │  │  │
│  │              │  │  ├─exception
│  │              │  │  │      CategoryErrorCode.java
│  │              │  │  │      ExistsCategoryTypeException.java
│  │              │  │  │      ForbiddenDeleteCategoryException.java
│  │              │  │  │      NotFoundCategoryException.java
│  │              │  │  │
│  │              │  │  └─repository
│  │              │  │          CategoryRepository.java
│  │              │  │
│  │              │  ├─menu
│  │              │  │  ├─controller
│  │              │  │  │      MenuController.java
│  │              │  │  │
│  │              │  │  ├─dto
│  │              │  │  │  ├─request
│  │              │  │  │  │      MenuRequestDto.java
│  │              │  │  │  │
│  │              │  │  │  └─response
│  │              │  │  │          MenuDetailResponseDto.java
│  │              │  │  │          MenuSimpleResponseDto.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      Menu.java
│  │              │  │  │
│  │              │  │  ├─exception
│  │              │  │  │      ExistsMenuNameException.java
│  │              │  │  │      ForbiddenUpdateMenuException.java
│  │              │  │  │      InvalidInputException.java
│  │              │  │  │      MenuErrorCode.java
│  │              │  │  │      NotFoundMenuException.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      MenuRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │      │  MenuService.java
│  │              │  │      │
│  │              │  │      └─impl
│  │              │  │              MenuServiceImpl.java
│  │              │  │
│  │              │  ├─menucategory
│  │              │  │  ├─entity
│  │              │  │  │      MenuCategory.java
│  │              │  │  │
│  │              │  │  └─repository
│  │              │  │          MenuCategoryRepository.java
│  │              │  │
│  │              │  ├─menulike
│  │              │  │  ├─controller
│  │              │  │  │      MenuLikeController.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      MenuLike.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      MenuLikeRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │      │  MenuLikeService.java
│  │              │  │      │
│  │              │  │      └─impl
│  │              │  │              MenuLikeServiceImpl.java
│  │              │  │
│  │              │  ├─model
│  │              │  │      TimeEntity.java
│  │              │  │
│  │              │  ├─order
│  │              │  │  ├─controller
│  │              │  │  │      OrderController.java
│  │              │  │  │
│  │              │  │  ├─dto
│  │              │  │  │  ├─request
│  │              │  │  │  │      OrderStateRequestDto.java
│  │              │  │  │  │
│  │              │  │  │  └─response
│  │              │  │  │          OrderResponseDto.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      Order.java
│  │              │  │  │      OrderState.java
│  │              │  │  │
│  │              │  │  ├─exception
│  │              │  │  │      ForbiddenDeleteOrderRoleExcepiton.java
│  │              │  │  │      ForbiddenDeleteOrderStateException.java
│  │              │  │  │      ForbiddenOrderQuantity.java
│  │              │  │  │      ForbiddenOrderState.java
│  │              │  │  │      ForbiddenOrderStateNotCreated.java
│  │              │  │  │      ForbiddenOrderStateNotPending.java
│  │              │  │  │      NotFoundCartListForOrderException.java
│  │              │  │  │      NotFoundOrderException.java
│  │              │  │  │      OrderErrorCode.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      OrderRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │      │  OrderService.java
│  │              │  │      │
│  │              │  │      └─impl
│  │              │  │              OrderServiceImpl.java
│  │              │  │
│  │              │  ├─ordermenu
│  │              │  │  ├─entity
│  │              │  │  │      OrderMenu.java
│  │              │  │  │
│  │              │  │  └─repository
│  │              │  │          OrderMenuRepository.java
│  │              │  │
│  │              │  ├─review
│  │              │  │  ├─controller
│  │              │  │  │      ReviewController.java
│  │              │  │  │
│  │              │  │  ├─dto
│  │              │  │  │  ├─request
│  │              │  │  │  │      ReviewRequestDto.java
│  │              │  │  │  │
│  │              │  │  │  └─response
│  │              │  │  │          ReviewResponseDto.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      Review.java
│  │              │  │  │
│  │              │  │  ├─exception
│  │              │  │  │      ForbiddenAccessReviewException.java
│  │              │  │  │      ForbiddenCreateReviewException.java
│  │              │  │  │      NotFoundOrderException.java
│  │              │  │  │      NotFoundReviewException.java
│  │              │  │  │      ReviewAlreadyExistsException.java
│  │              │  │  │      ReviewErrorCode.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      ReviewRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │      │  ReviewService.java
│  │              │  │      │
│  │              │  │      └─impl
│  │              │  │              ReviewServiceImpl.java
│  │              │  │
│  │              │  └─user
│  │              │      ├─constant
│  │              │      │      UserConstant.java
│  │              │      │
│  │              │      ├─controller
│  │              │      │      UserController.java
│  │              │      │
│  │              │      ├─dto
│  │              │      │  ├─request
│  │              │      │  │      UserCheckCodeRequestDto.java
│  │              │      │  │      UserDeleteRequestDto.java
│  │              │      │  │      UserIntroduceMessageUpdateRequestDto.java
│  │              │      │  │      UserLoginRequestDto.java
│  │              │      │  │      UsernameUpdateRequestDto.java
│  │              │      │  │      UserPasswordUpdateRequestDto.java
│  │              │      │  │      UserSendMailRequestDto.java
│  │              │      │  │      UserSignupRequestDto.java
│  │              │      │  │
│  │              │      │  └─response
│  │              │      │          PasswordHistoryResponseDto.java
│  │              │      │          UserCheckCodeResponseDto.java
│  │              │      │          UserIntroduceMessageUpdateResponseDto.java
│  │              │      │          UsernameUpdateResponseDto.java
│  │              │      │          UserPasswordUpdateResponseDto.java
│  │              │      │          UserResponseDto.java
│  │              │      │
│  │              │      ├─entity
│  │              │      │      AuthEmail.java
│  │              │      │      PasswordHistory.java
│  │              │      │      User.java
│  │              │      │      UserRole.java
│  │              │      │      UserStatus.java
│  │              │      │
│  │              │      ├─exception
│  │              │      │      BlockedUserException.java
│  │              │      │      ExistsUserEmailException.java
│  │              │      │      ExistsUsernameException.java
│  │              │      │      NotFoundUserException.java
│  │              │      │      PasswordRestrictionException.java
│  │              │      │      UnauthenticatedAuthEmailException.java
│  │              │      │      UnauthorizedEmailException.java
│  │              │      │      UserErrorCode.java
│  │              │      │      VerifyPasswordException.java
│  │              │      │
│  │              │      ├─repository
│  │              │      │      AuthEmailRepository.java
│  │              │      │      PasswordHistoryRepository.java
│  │              │      │      UserRepository.java
│  │              │      │
│  │              │      └─service
│  │              │          │  AuthEmailService.java
│  │              │          │  PasswordHistoryService.java
│  │              │          │  UserService.java
│  │              │          │
│  │              │          └─impl
│  │              │                  AuthEmailServiceImpl.java
│  │              │                  PasswordHistoryServiceImpl.java
│  │              │                  UserLogoutImpl.java
│  │              │                  UserServiceImpl.java
│  │              │
│  │              └─global
│  │                  ├─config
│  │                  │      AwsS3Config.java
│  │                  │      JasyptConfig.java
│  │                  │      JpaAuditingConfig.java
│  │                  │      MailConfig.java
│  │                  │      RedisConfig.java
│  │                  │      WebSecurityConfig.java
│  │                  │
│  │                  ├─exception
│  │                  │  │  RestApiException.java
│  │                  │  │
│  │                  │  ├─code
│  │                  │  │      CommonErrorCode.java
│  │                  │  │      ErrorCode.java
│  │                  │  │
│  │                  │  ├─handler
│  │                  │  │      GlobalExceptionHandler.java
│  │                  │  │
│  │                  │  └─response
│  │                  │          ErrorResponse.java
│  │                  │
│  │                  ├─infra
│  │                  │  └─mail
│  │                  │      ├─exception
│  │                  │      │      ExpiredCodeException.java
│  │                  │      │      MailErrorCode.java
│  │                  │      │
│  │                  │      └─service
│  │                  │          │  MailService.java
│  │                  │          │
│  │                  │          └─impl
│  │                  │                  MailServiceImpl.java
│  │                  │
│  │                  ├─jwt
│  │                  │      JwtUtil.java
│  │                  │
│  │                  ├─redis
│  │                  │      RedisUtil.java
│  │                  │
│  │                  ├─s3
│  │                  │  │  AwsS3Util.java
│  │                  │  │
│  │                  │  └─exception
│  │                  │          AwsS3ErrorCode.java
│  │                  │          AwsS3InternalException.java
│  │                  │          FileTypeNotAllowedException.java
│  │                  │          NotFoundS3FileException.java
│  │                  │
│  │                  └─security
│  │                          JwtAuthenticationFilter.java
│  │                          JwtAuthorizationFilter.java
│  │                          UserDetailsImpl.java
│  │                          UserDetailsServiceImpl.java
│  │
│  └─resources
│      │  application-dev.yml
│      │  application.yml
│      │  data.sql
│      │
│      ├─static
│      │  ├─css
│      │  │      style.css
│      │  │
│      │  └─js
│      │          basic.js
│      │          basic1.js
│      │
│      └─templates
│              index.html
│              login.html
│              mail.html
│              signup.html
│
└─test
    ├─java
    │  └─com
    │      └─example
    │          └─dp
    │              │  TestRedisConfiguration.java
    │              │
    │              ├─domain
    │              │  ├─admin
    │              │  │  └─service
    │              │  │      └─impl
    │              │  │              AdminReviewServiceImplTest.java
    │              │  │              AdminUserServiceImplTest.java
    │              │  │
    │              │  ├─cart
    │              │  │      CartIntegrationTest.java
    │              │  │
    │              │  ├─category
    │              │  │  └─service
    │              │  │      └─impl
    │              │  │              AdminCategoryServiceImplTest.java
    │              │  │
    │              │  ├─menu
    │              │  │  └─service
    │              │  │      └─impl
    │              │  │              MenuServiceImplTest.java
    │              │  │
    │              │  ├─order
    │              │  │      AdminOrderIntegrationTest.java
    │              │  │      OrderIntegrationTest.java
    │              │  │
    │              │  └─review
    │              │      └─service
    │              │          └─impl
    │              │                  ReviewServiceImplTest.java
    │              │
    │              └─global
    │                  ├─cart
    │                  └─config
    │                          JasyptConfigTest.java
    │
    └─resources
            application-test.properties
            application.yml
            data.sql


```

</details>




