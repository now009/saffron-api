
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

