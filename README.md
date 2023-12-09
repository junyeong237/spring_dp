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
 
```
![image](https://github.com/junyeong237/spring_dp/assets/70509488/a3281888-3a07-4933-9331-52fef23fec3c)
```
 
## 구현 사항 

### **필수 구현 기능**

- [x]  **사용자 인증 기능**
    - 회원가입 기능
    - 로그인 및 로그아웃 기능
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
│  │              │  │  │      NotFoundUserException.java
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
│  │              │      │  UserRole.java
│  │              │      │
│  │              │      ├─controller
│  │              │      │      UserController.java
│  │              │      │
│  │              │      ├─dto
│  │              │      │  │  UserCheckCodeRequestDto.java
│  │              │      │  │  UserSendMailRequestDto.java
│  │              │      │  │
│  │              │      │  ├─request
│  │              │      │  │      UserLoginRequestDto.java
│  │              │      │  │      UserSignupRequestDto.java
│  │              │      │  │
│  │              │      │  └─response
│  │              │      │          UserResponseDto.java
│  │              │      │
│  │              │      ├─entity
│  │              │      │      User.java
│  │              │      │
│  │              │      ├─exception
│  │              │      │      ExistsUserEmailException.java
│  │              │      │      ExistsUsernameException.java
│  │              │      │      NotFoundUserException.java
│  │              │      │      UserErrorCode.java
│  │              │      │
│  │              │      ├─repository
│  │              │      │      UserRepository.java
│  │              │      │
│  │              │      └─service
│  │              │          │  UserService.java
│  │              │          │
│  │              │          └─impl
│  │              │                  UserLogoutImpl.java
│  │              │                  UserServiceImpl.java
│  │              │
│  │              └─global
│  │                  ├─config
│  │                  │      AwsS3Config.java
│  │                  │      JasyptConfig.java
│  │                  │      JpaAuditingConfig.java
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
│  │                  │      │  MailConfig.java
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
│  │                  │      RedisConfig.java
│  │                  │      RedisUtil.java
│  │                  │
│  │                  ├─s3
│  │                  │  │  AwsS3Util.java
│  │                  │  │
│  │                  │  └─exception
│  │                  │          AwsS3ErrorCode.java
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
│      │
│      ├─static
│      └─templates
│              mail.html
│
└─test
    ├─java
    │  └─com
    │      └─example
    │          └─dp
    │              │  DpApplicationTests.java
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

```

</details>




