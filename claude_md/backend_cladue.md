
"JWT 검증하는 /main 엔드포인트 구현, 검증 성공시 http://localhost:3000/main.html 로 redirect"

검증성공시 이동할 URL은 http:///localhost:8080/main.html이며 
이정보는 application.yml이 관리할것

❯ 현재 인증 흐름

[Browser]                                                                                                                                  ◐ medium · /effort
│                                                                                                                                                          
│  ① GET http://localhost:8080/main (토큰 없음)                                                                                                            
▼                                                                                                                                                          
[saffron-api :8080 - SecurityConfig]                                                                                                                           
│  oauth2ResourceServer → Authorization: Bearer 헤더 없음                                                                                                  
│  → 401 Unauthorized
│                                                                                                                                                          
│  ② 사용자가 직접 로그인 페이지 접근
▼
[sffronAuth :8090 - SecurityConfig @Order(2)]
│  formLogin → GET http://localhost:8090/login
│
│  ③ POST /login (username=admin, password=1234!)
│  → UserDetailsService → InMemoryUserDetailsManager (secret.yml 기준)
│  → BCryptPasswordEncoder로 비밀번호 검증
▼
[sffronAuth - LoginSuccessHandler.onAuthenticationSuccess()]
│
│  ④ JwtClaimsSet 생성
│     - issuer:    "http://localhost:8090"
│     - subject:   "admin"
│     - roles:     "ROLE_ADMIN ROLE_USER"
│     - expiresAt: now + 3600초
│
│  ⑤ JwtEncoder(RSA키)로 서명 → JWT 발급
│
│  ⑥ sendRedirect("http://localhost:8080/main?token=<JWT>")
▼
[saffron-api :8080 - MainController]
│  GET /main?token=<JWT>

  ---
현재 코드의 문제점

LoginSuccessHandler는 토큰을 쿼리 파라미터(?token=)로 전달하지만, saffron-api의 SecurityConfig는 Authorization: Bearer 헤더만 읽습니다.

// LoginSuccessHandler.java:42
response.sendRedirect("http://localhost:8080/main?token=" + token);
//                                                  ↑ 쿼리 파라미터

// saffron-api SecurityConfig.java:24
.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
// ↑ Authorization: Bearer 헤더만 처리 → ?token= 무시 → 401

  ---
해결 방향 (선택)

┌───────────────────────┬───────────────────────────────────────────────────────────────────────────────────────────┐
│         방식          │                                           설명                                            │
├───────────────────────┼───────────────────────────────────────────────────────────────────────────────────────────┤
│ A. 헤더 방식          │ LoginSuccessHandler에서 redirect 대신 JSON으로 토큰 응답, 클라이언트가 헤더에 담아 재요청 │
├───────────────────────┼───────────────────────────────────────────────────────────────────────────────────────────┤
│ B. 쿼리 파라미터 방식 │ saffron-api에 커스텀 BearerTokenResolver 추가해서 ?token= 읽도록 변경                     │
└───────────────────────┴───────────────────────────────────────────────────────────────────────────────────────────┘

issuer-uri(http://localhost:8090/.well-known/oauth-authorization-server) test환경이 아닌 실행환경에서 이 주소정보가 필요한가?
필요하다면 yml에서 관리할 수 있도록 해줘


3개의 프로젝트를 통해 인증을 진행하는데

Front : Client (8080/8081) — 로그인 폼 보유, Login form (login.html), credentials POST
Auth : Auth 서버 (8090) — 인증 후 JWT 발급 후 backend /main으로 redirect
Login form (login.html) — 불필요 (client가 직접 보유)
API : Backend로서 auth 인증후 전달받아 front main.html로 이동

------------------------------------------------
이 프로젝트에 slfj log 설정을 추가해줘
log level을 default로 info로 하고 security 관련된 부분은 trace로 설정해주고
로그 파일을 C:\temp 아래에 날짜별로 생성하도록하고 폴더는 yml에서 설정할수 있도록 해줘


Secured GET /main?access_token=...   ← JWT 검증 성공                                                                                                                                             
Status Code: 302 Found               ← http://localhost:8080/main.html 로 리다이렉트

전체 인증 흐름이 완성됐습니다.

[Browser]
│ POST http://localhost:8090/auth/login (userId, password)
▼
[sffronAuth :8090]
│ AuthenticationManager → JWT 발급
│ 302 redirect → http://localhost:8080/main?access_token=<JWT>
▼
[saffron-api :8080]
│ BearerTokenResolver → ?access_token= 추출
│ JwtDecoder → sffronAuth JWK로 서명 검증 (issuer: localhost:8090)
│ 인증 성공 → MainController
│ 302 redirect → http://localhost:8080/main.html



- redirect URL을 http://localhost:8000/main.html 로 변경
- front에서 fetch를 이용하므로 CORS를 해결할수 있도록 헤더를 수정해줘
- 그리고 인증받은 access_token으로 backend api에서 계속 활용할수 있도록 하고
- front에서 access_token을 활용할수 있도록 하려면 어떻게 해야 되는가?

프론트엔드에서 access_token 활용 방법

현재 흐름에서 main.html은 ?access_token=<JWT>를 URL로 받습니다.

// main.html 로드 시 URL에서 토큰 추출 후 저장                                                                                                                                                   
const params = new URLSearchParams(window.location.search);
const token = params.get('access_token');

if (token) {
sessionStorage.setItem('access_token', token);
// URL에서 토큰 제거 (보안)
window.history.replaceState({}, '', window.location.pathname);
}

// 이후 API 호출 시 헤더에 포함
async function callApi(url) {
const token = sessionStorage.getItem('access_token');
const response = await fetch(url, {
headers: {
'Authorization': `Bearer ${token}`
}
});
return response.json();
}

최종 흐름 요약:

[Frontend :8000] POST /auth/login → sffronAuth :8090
sffronAuth → 302 → saffron-api :8080/main?access_token=<JWT>
saffron-api (JWT 검증) → 302 → frontend :8000/main.html?access_token=<JWT>
[Frontend] URL에서 토큰 추출 → sessionStorage 저장
[Frontend] fetch('/api/...', Authorization: Bearer <JWT>) → saffron-api :8080

http://localhost:8080/main?access_token=eyJraWQiOiI0MDRiNDdmMS04MDIwLTRiNjQtOTk1OC1lNGM2Njg0ODI0OWQiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwOTAiLCJzdWIiOiJhZG1pbiIsImV4cCI6MTc3NjY4ODc4MywiaWF0IjoxNzc2Njg1MTgzLCJyb2xlcyI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIn0.k-af3_dMbRu-JcEptAiPDX3V0TXSybXpgvr1SmSRym0bRjOWcAVCCDjFPsf8RNdQOnYLBGiONNra3RnuSpvl3gTZhbSO1AwaLBS3JNhqmq0W657SFHT64jvLOQgYRbkMmLsYJdTu2KBHBXtCAIpCpZ6QnWMelzMl238kWGV6ge0KDy54L-ryWl531-D1QEUCAdxW_DekcJoXmntghj1t5kc7FzMd4XFpp7x16Ch__8K6BIer29Cs68JdIKDBvBBIPsofRzKSwrInFnB7f4AiMfMBbB7_FejhfAWNL3kAM3-HE6xr5IZ98bqOV9K2MshXV5FuIpxEspnbAINY3IGtmA


front에서 JWT를 헤더에 담아 saffron-api로 요청하면 api가 다시 auth서버에 토큰을 확인하는 구조인가

● 아닙니다. JWT는 매 요청마다 auth 서버를 호출하지 않습니다.

앱 시작 시 (1회만):                                                                                                                                                                              
saffron-api → GET http://localhost:8090/.well-known/oauth-authorization-server
← RSA 공개키(JWK Set) 수신 → 로컬에 캐싱

요청마다:                                                                                                                                                                                        
Frontend → saffron-api (Authorization: Bearer <JWT>)                                                                                                                                             
↓                                                                                                                                                                                     
캐싱된 공개키로 서명 검증 (로컬, 네트워크 없음)
↓
인증 성공/실패

JWT의 핵심 장점이 바로 이것입니다.

┌──────────────┬───────────────────────────────────────┐
│     방식     │            auth 서버 호출             │
├──────────────┼───────────────────────────────────────┤
│ JWT (현재)   │ 시작 시 1회 (공개키 수신)             │
├──────────────┼───────────────────────────────────────┤
│ Opaque Token │ 매 요청마다 (/oauth2/introspect 호출) │
└──────────────┴───────────────────────────────────────┘

단, auth 서버가 재시작되면 RSA 키가 새로 생성되므로(generateRsaKey() 매 실행마다 새 키), 기존 토큰이 무효화됩니다. 운영 환경에서는 RSA 키를 고정값으로 관리해야 합니다.

front ui에서 아래와 같은 메뉴가 구성되는데 
    - 사용자 -> user 폴더
        - 사용자관리 -> user.jsx
        - 부서관리   -> dept.jsx
    - 메뉴관리 -> menu 폴더
        - 프로그램관리 -> program.jsx
          = 메뉴관리 - menu.jsx
    - 권한관리 -> role 폴더
        - 권한정보 -> role.jsx
        - 권한설정 -> roleSetting.jsx
    - 시스템관리 -> system 폴더
        - 코드관리 -> code.jsx
        - 스케줄관리 -> schedule.jsx
        - 환경설정관리 -> envSetting.jsx
 
각 메뉴별로 Controller를 만들어서 page로 연결할수 있도록 구현해줘

/api/users -> /portal/users 처럼 api를 portal로 변경해줘

각 컨트롤러의 엔드포인트 패턴:

┌─────────────────┬────────────────────┬──────────────────────────────────────────────────────────────────────────────┐
│   JSX 페이지    │      API 경로      │                               주요 엔드포인트                                │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ user.jsx        │ /api/users         │ GET(목록), GET/:id, POST, PUT/:id, DELETE/:id                                │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ dept.jsx        │ /portal/depts         │ GET(목록), GET/:id, POST, PUT/:id, DELETE/:id                                │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ program.jsx     │ /portal/programs      │ GET(목록), GET/:id, POST, PUT/:id, DELETE/:id                                │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ menu.jsx        │ /portal/menus         │ GET(목록), GET/tree(트리구조), GET/:id, POST, PUT/:id, DELETE/:id            │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ role.jsx        │ /portal/roles         │ GET(목록), GET/:id, POST, PUT/:id, DELETE/:id                                │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ roleSetting.jsx │ /portal/role-settings │ GET/:roleId/menus, POST/:roleId/menus, GET/:roleId/users, POST/:roleId/users │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ code.jsx        │ /portal/codes         │ GET(목록), GET/groups, GET/:id, POST, PUT/:id, DELETE/:id                    │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ schedule.jsx    │ /portal/schedules     │ GET(목록), GET/:id, POST, PUT/:id, DELETE/:id, POST/:id/run                  │
├─────────────────┼────────────────────┼──────────────────────────────────────────────────────────────────────────────┤
│ envSetting.jsx  │ /portal/env-settings  │ GET(목록), GET/:key, PUT/:key, PUT/bulk                                      │
└─────────────────┴────────────────────┴──────────────────────────────────────────────────────────────────────────────┘

1. MenuControll에서 menu정보 전체를 DB에서 조회해서 front로 전달하려는 로직을 미리 만들건데 
   아직 DB가 없으니 우선 resources/tempStorage의 menu.json에서 전달하는 로직을 만들어줘 
   menu조회하는 로직은 service에 만들어주고 이후 내부 로직만 변경하면 될수 있게 해줘

   아래내용은 front에서 config로 사용한것인데 이걸 이용해 json을 구현해줘

API 응답 (POST /portal/menus/list):
[
    {
        "label": "사용자",
        "pathKey": "user",
        "submenus": [
            { "label": "사용자관리", "path": "/user/user" },
            { "label": "부서관리", "path": "/user/dept" }
        ]
    },
    ...
]

1. DB정보 
   - DB : MariaDB
   - IP,Port : localhost,3306
   - userid/pw : now009/2799
2. mybatis를 이용해 munu를 조회해서 front로 전달할수 있도록 구현
-------------------------------------------------------------------------------

http://localhost:8080/portal/menus/delete/를 호출했을때 
1. menu_info table을 조회했을때 삭제하려는 menuId의 하위 menu가 존재하면 
 - {"messageCode":"fail","message":"하위 메뉴가 존재합니다"} 
2. 삭제되었으면 
 - {"messageCode":"fail","message":"하위 메뉴가 존재합니다"} 
3. 메뉴아디가 없으면 
 - {"messageCode":"fail","message":"메뉴가 존재하지 않습니다"}
이렇게 리턴하는 코드를 만들어줘

MenuController에 새로운 MenuId값을 조회해 리턴하는 코드를 추가해줘
- 새로운 menuId는 기존 menuId를 조회해 뒷자리를 sequence하게 생성할수 있도록 구현
- ex) MENU011 
그리고 생성된 URI도 알려줘
- 
  상위메뉴 ID는 depth 0 ~ 1 까지 조회한는 기능을 구현해줘 그리고 생성된 URI도 알려줘

-- 메뉴추가시 front에서
{
"menuId": "MENU006",
"parentMenuId": "MENU001",
"menuName": "테스트메뉴",
"menuLevel": 1,
"programId": "PRG001",
"sortOrder": 3,
"useYn": "Y"
}
이런 포맷으로 전달되는데 /portal/menus/save에 구현해줘
저장실행후
- {"messageCode":"fail","message":""} <- 에러메세지 Exception 
- {"messageCode":"success","message":"저장되었습니다"}

실행후 list redirect
 
-- 메뉴수정시
{
"menuId": "MENU001",
"menuName": "시스템관리",
"programId": "PRG001",
"useYn": "Y"
}
이런 포맷으로 전달되는데 /portal/menus/update에 구현해줘
저장실행후
- {"messageCode":"fail","message":""} <- 에러메세지 Exception
- {"messageCode":"success","message":"저장되었습니다"}
실행후 list redirect

-------------------------------------------------------------------------
Program Controller의 URI를
/portal/programs/list
/portal/programs/save
/portal/programs/update
/portal/programs/delet로 수정

/portal/programs/list는 program_info table을 조회하고
검색조건으로 programId, programName, programUrl로도 조회할 수 있도록 수정

-- Front에서 메뉴생성 , 수정요청이 요구
    예제)
    1. 메뉴 생성 (add)
    URL: POST http://localhost:8080/portal/menus/save
    Body:
    
    {
    "menuId": "MENU006",
    "parentMenuId": "MENU001",
    "menuName": "테스트메뉴",
    "menuLevel": 1,
    "programId": "PRG001",
    "programUrl": "/test",
    "sortOrder": 3,
    "useYn": "Y"
    }
    
    2.메뉴 수정 (edit)
    
    URL: POST http://localhost:8080/portal/menus/update
    Body:
    
    {
    "menuId": "MENU001",
    "menuName": "시스템관리",
    "programId": "PRG001",
    "programUrl": "/system",
    "useYn": "Y"
    }

저장실행후
- {"messageCode":"fail","message":""} <- 에러메세지 Exception
- {"messageCode":"success","message":"저장되었습니다"}

관련된 코드를 수정해줘

role_mapping, role_info , program_info , menu_info, user_info, dept_info table에 insert/update시
parentDeptId    VARCHAR(20)    NULL 처럼 컬럼에 NULL 이 설정되어 있으면
front에서 ''값처럼 빈값이 오더라도 NULL 컬럼에 NULL이 입력될수 있도록 수정

MenuController를 참조해서 ProgramController를 구현해줘

MenuController를 참조해서 UserController를 구현해줘
MenuController를 참조해서 DeptController를 구현해줘

-- user
 UserController에 
 - front에서 사용자 ID로 중복체크할수 있는 endpoint를 만들어줘

-- dept_info table을 조회한 결과에 treeName을 deptNameTree를 변경해주고 
   dept별로 [ space + 컬럼값 ] 처럼 tree로 표현할수 있게 해줘 

 - {
   "deptId": "DEPT002",
   "parentDeptId": "DEPT001",
   "deptCode": "MGMT",
   "deptName": "경영지원본부",
   "deptLevel": 2,
   "sortOrder": 1,
   "useYn": "Y",
   "treePath": "DEPT001 > DEPT002",  
   "treeName": "본사 > 경영지원본부", -->  ex) "deptNameTree":"  경영지원본부"
   "depth": 1,
   "createdUser": null,
   "createdDate": null,
   "updateUser": null,
   "updatedDate": null
   },

MenuController를 참조해 RoleController에 아래 endpoint를 구현해주고 
추가할것이 있으면 해줘
- GET /portal/role/next-id  roleCode 자동생성 
- list POST /portal/role/list
- 생성 POST /portal/role, 
- 수정 POST /portal/role/{roleCode}, 
- 삭제 POST /portal/role/{roleCode}

MenuController를 참조해 code폴더를 생성후 CodeController를 생성하고 endpoint를 구현
table : code_info
- list POST /portal/codes/list  - parentCode를 기준으로 Groupping해 조회
- 생성 POST /portal/codes/save,
- 수정 POST /portal/codes/update,
- 삭제 POST /portal/codes/delete/{code}
  ┌────────┬─────────────────────────────────┬─────────────────────────────────────────────────────────────────┐
  │ Method │               URI               │                           Description                           │
  ├────────┼─────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
  │ POST   │ /portal/codes/list              │ 조회 (parentCode/code/codeName 필터, parentCode 기준 그룹 정렬) │
  ├────────┼─────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
  │ POST   │ /portal/codes/save              │ 생성 → 성공 시 전체 목록 반환                                   │
  ├────────┼─────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
  │ POST   │ /portal/codes/update            │ 수정 → 성공 시 전체 목록 반환                                   │
  ├────────┼─────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
  │ POST   │ /portal/codes/delete/{code}     │ 삭제 (하위 코드 존재 시 fail)                                   │
  ├────────┼─────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
  │ GET    │ /portal/codes/check-code/{code} │ 코드 중복 체크                                                  │
  ├────────┼─────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
  │ GET    │ /portal/codes/next-id           │ 다음 코드값 (CODE001, CODE002...)                               │
  └────────┴─────────────────────────────────┴─────────────────────────────────────────────────────────────────┘

-----------------------------------------------------------------
-- Role Mapping
-------------------------------------------------------------------
로그인 시 권한 조회 흐름
user_info → user_role / dept_role → role_info → role_menu → menu_info → 접근 가능 메뉴 반환


---------------------
권한 설정 API 구현해줘

[테이블]
- role_info, role_menu, menu_info, user_role, dept_role

[API 목록]

1. Role 전체 조회
   GET /api/roles
    - role_info 전체 조회 (useYn = 'Y')
    - 응답: RoleDto { roleCode, roleName, description, useYn }

2. Role별 메뉴 권한 조회
   GET /api/roles/{roleCode}/menus
    - menu_info 전체를 LEFT JOIN role_menu ON roleCode
    - role_menu 있으면 useYn = 'Y', 없으면 useYn = 'N'
    - 응답: RoleMenuDto { menuId, parentMenuId, menuName, menuLevel, menuIcon, sortOrder, useYn }
    - SQL:
      SELECT m.menuId, m.parentMenuId, m.menuName, m.menuLevel,
      m.menuIcon, m.sortOrder,
      CASE WHEN rm.menuId IS NOT NULL THEN 'Y' ELSE 'N' END AS useYn
      FROM menu_info m
      LEFT JOIN role_menu rm
      ON m.menuId = rm.menuId AND rm.roleCode = :roleCode
      WHERE m.useYn = 'Y'
      ORDER BY m.menuLevel, m.sortOrder

3. Role별 메뉴 권한 저장
   POST /api/roles/{roleCode}/menus
    - Body: [ { menuId, useYn } ]
    - 처리방식: DELETE 후 INSERT (일괄 저장)
    - useYn = 'Y' 인 항목만 INSERT
    - SQL:
      DELETE FROM role_menu WHERE roleCode = :roleCode
      INSERT INTO role_menu (roleCode, menuId, canRead, canWrite, canUpdate, canDelete)
      VALUES (:roleCode, :menuId, 'Y', 'Y', 'Y', 'Y')

4. Role별 소속 사용자 조회
   GET /api/roles/{roleCode}/users
    - user_role + dept_role 통합 조회
    - 응답: RoleUserDto { userId, userName, deptName, position, assignType }

5. Role별 소속 부서 조회
   GET /api/roles/{roleCode}/depts
    - dept_role 조회
    - 응답: RoleDeptDto { deptId, deptName, deptLevel }

[구현 클래스]
RoleController.java      ← @RestController, /api/roles
RoleService.java         ← 비즈니스 로직
RoleMapper.java          ← MyBatis or JPA Repository
RoleDto.java             ← Role 응답 DTO
RoleMenuDto.java         ← 메뉴 권한 응답 DTO
RoleMenuRequest.java     ← 저장 요청 DTO

[공통 조건]
- 응답 공통 포맷: { code: "200", message: "success", data: [...] }
- roleCode 유효성 검증 (존재하지 않으면 404)
- 저장 시 트랜잭션 처리 (@Transactional)

----------------------------------------------------------------
UPDATE user_info
SET    password    = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
updatedDate = NOW()
WHERE  userId      = 'now009';

Auth 서버로부터 redirect된 요청을 처리하는 /main 엔드포인트를 확인하고 수정해줘.

조건:
- GET /main?access_token={token} 요청 받음
- JWT 서명 검증 (Auth 서버와 동일한 secret key 사용)
- 만료 여부 확인
- 검증 실패 시 → http://localhost:8000/login 으로 redirect (에러 처리)
- 검증 성공 시 → http://localhost:8000/main?access_token={token} 으로 redirect
- JWT Claims에서 userId, deptId, userName, email 추출 가능하도록 유틸 함수도 만들어줘
  (이후 다른 API에서 사용자 정보 꺼낼 때 재사용)

현재 사용 중인 프레임워크: [Spring Boot / Node.js 등 기입]
Auth 서버와 동일한 JWT secret key를 application.yml(또는 .env)에서 관리할 것

--------------------------------------------------
인증단계를 확인해주고 JWT에 포함된 변수를 확인해서 SaffronTop.jsx 상단메뉴의 
User아이콘 대신 사용자명을 표시해줘 

[1단계 - 로그인 페이지]
- /login 페이지에서 userId, password 입력 받기
- 로그인 버튼 클릭 시 POST http://localhost:8090/auth/login 호출

[2단계 - 메인 페이지 진입]
- /main 페이지 진입 시 URL 파라미터에서 access_token 추출
- localStorage에 access_token 저장
- JWT payload decode해서 사용자 정보(userId, deptId, userName, email) 추출
- 화면에 userName 표시

[3단계 - API 호출]
- 이후 모든 API 요청 시 Header에 자동으로 토큰 포함
  Authorization: Bearer {access_token}
- 토큰 없거나 만료 시 /login으로 redirect

--------------------------------------------------
## 문제은행(Question Bank) 모듈 - com.saffron.qbank

패키지: com.saffron.qbank
prefix: /api/qbank

### 패키지 구조
```
com.saffron.qbank/
├── domain/       ExamType, ExamSubject, ExamPaper, Question, QuestionChoice, ExamSession, AnswerSheet
├── dto/
│   ├── request/  ExamTypeRequest, ExamSubjectRequest, ExamPaperRequest, QuestionRequest,
│   │             QuestionChoiceRequest, SessionStartRequest, SubmitRequest, GradeRequest
│   └── response/ ExamTypeResponse, ExamSubjectResponse, ExamPaperResponse,
│                 QuestionAdminResponse, QuestionExamResponse,
│                 ChoiceAdminResponse, ChoiceExamResponse,
│                 SessionStartResponse, SessionResponse, AnswerSheetResponse
├── mapper/       ExamTypeMapper, ExamSubjectMapper, ExamPaperMapper, QuestionMapper,
│                 QuestionChoiceMapper, ExamSessionMapper, AnswerSheetMapper
├── service/      QbankAdminService(Impl), QbankExamService(Impl)
└── controller/   QbankAdminController, QbankExamController
```
mapper xml: src/main/resources/mapper/qbank/

### 관리자 API (/api/qbank/admin)
| Method | URL | 설명 |
|--------|-----|------|
| GET    | /exam-types                    | 시험종류 목록 |
| POST   | /exam-types                    | 시험종류 등록 |
| PUT    | /exam-types/{id}               | 시험종류 수정 |
| DELETE | /exam-types/{id}               | 시험종류 삭제 |
| GET    | /exam-subjects                 | 시험대상 목록 |
| POST   | /exam-subjects                 | 시험대상 등록 |
| PUT    | /exam-subjects/{id}            | 시험대상 수정 |
| DELETE | /exam-subjects/{id}            | 시험대상 삭제 |
| GET    | /papers                        | 시험지 목록 |
| GET    | /papers/{id}                   | 시험지 단건 |
| POST   | /papers                        | 시험지 생성 |
| PUT    | /papers/{id}                   | 시험지 수정 |
| DELETE | /papers/{id}                   | 시험지 삭제 |
| GET    | /papers/{paperId}/questions    | 문항 목록(보기포함) |
| POST   | /papers/{paperId}/questions    | 문항 등록 |
| PUT    | /questions/{id}                | 문항 수정 |
| DELETE | /questions/{id}                | 문항 삭제 |
| POST   | /questions/{id}/choices        | 보기 등록 |
| PUT    | /choices/{id}                  | 보기 수정 |
| DELETE | /choices/{id}                  | 보기 삭제 |
| GET    | /sessions?paperId=&examineeName= | 응시 세션 목록 |
| GET    | /sessions/{id}/answers         | 답안지 조회 |
| POST   | /sessions/{id}/grade           | 채점(자동+수동) |

### 응시자 API (/api/qbank/exam)
| Method | URL | 설명 |
|--------|-----|------|
| GET    | /types                   | 시험종류 목록 |
| GET    | /subjects                | 시험대상 목록 |
| GET    | /papers?typeId=&subjectId= | 활성 시험지 조회 |
| POST   | /sessions                | 응시 시작 (sessionId + 문항+보기 반환, is_correct 제외) |
| POST   | /sessions/{id}/submit    | 답안 제출 |

### 채점 로직
- autoGrade=true: single/multi 선택지를 question_choice.is_correct로 자동 채점
  - multi: 정답 set과 선택 set이 완전 일치해야 정답
- manualGrades: subjective 문항 수동 채점 { questionId, isCorrect }
- 채점 후 exam_session.total_score 합산 업데이트