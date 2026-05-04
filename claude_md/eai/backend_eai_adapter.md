
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