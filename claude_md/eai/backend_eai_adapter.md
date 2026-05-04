
front eai 각 화면에서 백엔드로 보내는 페이로드
1. Datasource
    - /eai/datasources/test 는 추가할것
      POST /eai/datasources (생성), PUT /eai/datasources/{id} (수정), POST /eai/datasources/test (Connection Test) — 동일 구조
      {
      "datasourceId":    "DS_ERP",
      "datasourceName":  "ERP 운영 DB",
      "dbType":          "MARIADB",
      "jdbcUrl":         "jdbc:mariadb://host:3306/erp",
      "dbUsername":      "eai_user",
      "dbPassword":      "secret",
      "driverClass":     "org.mariadb.jdbc.Driver",
      "defaultSchema":   "ERP",
      "poolMinSize":     5,
      "poolMaxSize":     20,
      "poolTimeoutMs":   30000,
      "queryTimeoutSec": 30,
      "connectionProps": "{\"useUnicode\":\"true\"}",
      "description":    "",
      "isActive":       true
      }
      dbType enum: POSTGRESQL | ORACLE | MSSQL | MYSQL | MARIADB | H2
      poolMinSize, poolMaxSize, poolTimeoutMs, queryTimeoutSec — number
      connectionProps — JSON 문자열 (서버가 파싱 책임)
      수정/Connection Test 시에는 id 등 서버 필드도 함께 spread되어 전송됨
      Connection Test 응답 기대 형식: { "success": bool, "message": string } 또는 { "ok": bool, "message": string }
2. DbAdapterConfig
   POST /eai/db-adapter-configs / PUT /eai/db-adapter-configs/{id}


{
"interfaceId":     "IF-0001",
"datasourceId":    "DS_ERP",
"statementId":     "com.mapper.EaiMapper.selectData",
"operationType":   "QUERY",
"resultType":      "LIST",
"paramMapping":    "{\"orderId\":\"$.order.id\"}",
"rollbackOnError": true,
"isActive":        true
}
operationType enum: QUERY | INSERT | UPDATE | DELETE | PROCEDURE
resultType enum: LIST | SINGLE | COUNT | NONE
paramMapping — JSON 문자열
3. RestConfig
   POST /eai/rest-configs / PUT /eai/rest-configs/{id}


{
"interfaceId":      "IF-0001",
"configName":       "ERP주문조회",
"url":              "https://api.example.com/orders",
"httpMethod":       "POST",
"contentType":      "application/json",
"timeoutMs":        "5000",
"successHttpCodes": "200,201,202",
"responsePath":     "$.data",

"authType":     "OAUTH2",
"authValue":    "",
"apiKeyHeader": "",
"tokenUrl":     "https://idp.example.com/oauth2/token",
"clientId":     "abc123",
"clientSecret": "xyz",
"tokenScope":   "read",

"requestHeaders":  "[{\"key\":\"X-Tenant\",\"value\":\"T001\"}]",
"requestTemplate": "{\"orderId\":\"${orderId}\"}",

"sslVerify":     true,
"proxyHost":     "",
"proxyPort":     "",
"proxyUser":     "",
"proxyPassword": "",

"isActive": true
}
httpMethod enum: GET | POST | PUT | DELETE | PATCH | HEAD
authType enum: NONE | BEARER | API_KEY | BASIC | OAUTH2
⚠️ timeoutMs, proxyPort 는 text input 이라 문자열로 전송됩니다.
백엔드가 숫자로 파싱하거나 - DB컬럼 type이 숫자형이면 숫자로 파싱
requestHeaders — JSON 배열 문자열

4. SoapConfig
   POST /eai/soap-configs / PUT /eai/soap-configs/{id}


{
"interfaceId":     "IF-0001",
"configName":      "Legacy 주문생성",
"wsdlUrl":         "http://legacy/service?wsdl",
"serviceUrl":      "http://legacy/service",
"namespace":       "http://example.com/schema",
"operationName":   "createOrder",
"portName":        "",

"soapVersion": "1.1",
"soapAction":  "urn:createOrder",
"mtomEnabled": false,
"timeoutMs":   "10000",

"wsSecurityType": "USERNAME_TOKEN",
"wsUsername":     "soap_user",
"wsPassword":     "secret",
"wsPasswordType": "PasswordText",
"keystorePath":     "",
"keystorePassword": "",

"requestTemplate": "<soap:Envelope>...</soap:Envelope>",
"responsePath":    "//order/id",

"isActive": true
}
-----------------------------------------------------------------------------
EAI Endpoint 정리

Datasource — eai_datasource

┌────────┬────────────────────────────────────┬────────────────────────┐
│ Method │                Path                │          설명          │
├────────┼────────────────────────────────────┼────────────────────────┤
│ GET    │ /eai/datasources?dbType=&isActive= │ 목록                   │
├────────┼────────────────────────────────────┼────────────────────────┤
│ GET    │ /eai/datasources/{id}              │ 단건                   │
├────────┼────────────────────────────────────┼────────────────────────┤
│ POST   │ /eai/datasources                   │ 생성                   │
├────────┼────────────────────────────────────┼────────────────────────┤
│ PUT    │ /eai/datasources/{id}              │ 수정                   │
├────────┼────────────────────────────────────┼────────────────────────┤
│ POST   │ /eai/datasources/test              │ Connection Test (신규) │
├────────┼────────────────────────────────────┼────────────────────────┤
│ DELETE │ /eai/datasources/{id}              │ 삭제                   │
└────────┴────────────────────────────────────┴────────────────────────┘

DbAdapterConfig — eai_db_adapter_config

┌────────┬────────────────────────────────────────────────────┐
│ Method │                        Path                        │
├────────┼────────────────────────────────────────────────────┤
│ GET    │ /eai/db-adapter-configs?interfaceId=&datasourceId= │
├────────┼────────────────────────────────────────────────────┤
│ GET    │ /eai/db-adapter-configs/{id}                       │
├────────┼────────────────────────────────────────────────────┤
│ POST   │ /eai/db-adapter-configs                            │
├────────┼────────────────────────────────────────────────────┤
│ PUT    │ /eai/db-adapter-configs/{id}                       │
├────────┼────────────────────────────────────────────────────┤
│ DELETE │ /eai/db-adapter-configs/{id}                       │
└────────┴────────────────────────────────────────────────────┘

RestConfig — eai_rest_config

┌────────┬────────────────────────────────┐
│ Method │              Path              │
├────────┼────────────────────────────────┤
│ GET    │ /eai/rest-configs?interfaceId= │
├────────┼────────────────────────────────┤
│ GET    │ /eai/rest-configs/{id}         │
├────────┼────────────────────────────────┤
│ POST   │ /eai/rest-configs              │
├────────┼────────────────────────────────┤
│ PUT    │ /eai/rest-configs/{id}         │
├────────┼────────────────────────────────┤
│ DELETE │ /eai/rest-configs/{id}         │
└────────┴────────────────────────────────┘

SoapConfig — eai_soap_config

┌────────┬────────────────────────────────┐
│ Method │              Path              │
├────────┼────────────────────────────────┤
│ GET    │ /eai/soap-configs?interfaceId= │
├────────┼────────────────────────────────┤
│ GET    │ /eai/soap-configs/{id}         │
├────────┼────────────────────────────────┤
│ POST   │ /eai/soap-configs              │
├────────┼────────────────────────────────┤
│ PUT    │ /eai/soap-configs/{id}         │
├────────┼────────────────────────────────┤
│ DELETE │ /eai/soap-configs/{id}         │
└────────┴────────────────────────────────┘

Enum 검증 (DB CHECK 제약과 동일)

===============================================================

Endpoint

POST /eai/query/validate

Request                                                   
{
"datasourceId": "DS_ERP",
"query": "SELECT order_id, total_amt FROM t_order WHERE status = 'NEW'"
}
- datasourceId — eai_datasource.datasource_id. 이 값으로 dbType/jdbcUrl/driver/계정을 조회해서 해당 DB에 연결합니다 (= 자동으로 Oracle/MariaDB/Postgres 방언으로 검증)

Response — 성공
{
"timestamp": "2026-05-04T17:01:51.123",
"success": true,
"message": "Query 검증 성공 (MARIADB 방언)"
}

Response — 실패 (예: Oracle에서 존재하지 않는 테이블)
{
"timestamp": "...",
"success": false,
"message": "[42000/942] ORA-00942: table or view does not exist"
}
형식: [SQLState/errorCode] 드라이버 메시지

검증 방식

1. datasourceId로 eai_datasource 조회 → driver/url/계정 획득
2. Class.forName(driverClass) 로 드라이버 로드
3. DriverManager.getConnection(...) (loginTimeout 적용)
4. conn.setAutoCommit(false) → conn.prepareStatement(query) → rollback()
5. execute 하지 않음 — 데이터 변경 없음. 대부분의 JDBC 드라이버가 prepare 시점에 syntax + 테이블/컬럼 존재 여부까지 검증
6. 실패 시 SQLException 의 SQLState/errorCode/message 를 그대로 반환

안전장치

- 빈 쿼리 차단
- ;로 구분된 multi-statement 차단 (한 번에 한 SQL만)
- AutoCommit OFF + finally rollback — 혹시 드라이버가 prepare 단계에서 실행해버리는 케이스(드물지만)에도 변경 사항이 커밋되지 않음

한계

- prepareStatement만으로 syntax 체크가 부족한 드라이버(예: 일부 MySQL 버전은 client-side prepare로 syntax만 검증, 테이블 존재는 execute에서 확인)도 있습니다. 이 경우 "syntax는 OK,
  실제 실행 시 실패" 가능
- DDL(CREATE, DROP 등)은 드라이버에 따라 prepareStatement가 곧바로 실행되는 케이스가 있어 validate 의도와 다를 수 있습니다 — DDL 검증이 필요하면 알려주세요 (별도 처리 필요)