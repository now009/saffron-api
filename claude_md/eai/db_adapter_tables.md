# EAI 어댑터별 필요 정보 및 테이블 설계
> DB / REST / SOAP / FILE 어댑터 생성에 필요한 입력 정보와 테이블 DDL

---

## 1. 어댑터별 필요 정보 요약

### 1-1. DB Adapter

| 구분 | 항목 | 필수 | 설명 |
|------|------|------|------|
| **연결** | DataSource ID | ✅ | 연결 식별자 (DS_ERP, DS_WMS…) |
| **연결** | JDBC URL | ✅ | jdbc:postgresql://host:5432/db |
| **연결** | DB 계정 | ✅ | AES-256 암호화 저장 |
| **연결** | DB 비밀번호 | ✅ | AES-256 암호화 저장 |
| **연결** | JDBC 드라이버 클래스 | ✅ | org.postgresql.Driver 등 |
| **실행** | Statement ID | ✅ | MyBatis Mapper ID (eai.erp.insertOrder) |
| **실행** | 작업 유형 | ✅ | QUERY / INSERT / UPDATE / PROCEDURE |
| **선택** | 커넥션 풀 min/max | ⬜ | 기본 5 / 20 |
| **선택** | 쿼리 타임아웃 | ⬜ | 초 단위 (기본 30s) |
| **선택** | 기본 스키마 | ⬜ | 스키마 지정 시 |

### 1-2. REST Adapter

| 구분 | 항목 | 필수 | 설명 |
|------|------|------|------|
| **엔드포인트** | URL | ✅ | https://api.example.com/v1/orders |
| **엔드포인트** | HTTP Method | ✅ | GET / POST / PUT / DELETE / PATCH |
| **엔드포인트** | 타임아웃 (ms) | ✅ | 기본 5,000ms 권장 |
| **인증** | 인증 방식 | ✅ | NONE / BEARER / API_KEY / BASIC / OAUTH2 |
| **인증** | 토큰 / 키 값 | ✅ | 암호화 저장, 만료 시 자동 갱신 |
| **선택** | Content-Type | ⬜ | application/json (기본) |
| **선택** | 추가 헤더 | ⬜ | Key-Value 배열 (X-Tenant-ID 등) |
| **선택** | SSL 인증서 무시 | ⬜ | 개발환경 전용, 운영 사용 금지 |
| **선택** | Proxy 설정 | ⬜ | Host:Port (사내 Proxy 경유 시) |
| **OAUTH2** | Token URL | ⬜ | 토큰 갱신 엔드포인트 |
| **OAUTH2** | Client ID / Secret | ⬜ | 암호화 저장 |

### 1-3. SOAP Adapter

| 구분 | 항목 | 필수 | 설명 |
|------|------|------|------|
| **WSDL** | WSDL URL | ✅ | http://legacy/service?wsdl |
| **WSDL** | Service URL | ✅ | 실제 SOAP 엔드포인트 주소 |
| **WSDL** | Namespace | ✅ | http://example.com/schema |
| **WSDL** | Operation 명 | ✅ | WSDL에 정의된 Operation 이름 |
| **보안** | WS-Security 유형 | ✅ | NONE / UsernameToken / X.509 |
| **보안** | 계정 / 비밀번호 | ✅ | UsernameToken 방식일 때 필수 |
| **선택** | SOAP 버전 | ⬜ | 1.1 (기본) / 1.2 |
| **선택** | SOAP Action | ⬜ | SOAPAction 헤더 값 |
| **선택** | MTOM (첨부파일) | ⬜ | 바이너리 첨부 전송 여부 |
| **선택** | 타임아웃 (ms) | ⬜ | 기본 10,000ms |

### 1-4. FILE Adapter

| 구분 | 항목 | 필수 | 설명 |
|------|------|------|------|
| **연결** | 프로토콜 | ✅ | SFTP / FTP / FTPS / LOCAL |
| **연결** | Host / Port | ✅ | ftp.example.com / 22 (SFTP 기본) |
| **연결** | 계정 / 비밀번호 | ✅ | 암호화 저장 |
| **파일** | 원격 업로드 경로 | ✅ | /upload/eai/ (송신 시) |
| **파일** | 원격 다운로드 경로 | ✅ | /download/eai/ (수신 시) |
| **파일** | 파일명 패턴 | ✅ | sales_{yyyyMMdd}.csv |
| **선택** | SSH 키 파일 경로 | ⬜ | SFTP 키 인증 시 |
| **선택** | 처리 완료 이동 경로 | ⬜ | /done/ (중복 처리 방지) |
| **선택** | 파일 형식 | ⬜ | CSV / Excel / Fixed / JSON |
| **선택** | 파일 인코딩 | ⬜ | UTF-8 (기본) / EUC-KR |
| **선택** | CSV 구분자 | ⬜ | 기본 ',' |
| **선택** | 헤더행 여부 | ⬜ | 첫 번째 행 헤더 여부 |
| **선택** | 폴링 간격 | ⬜ | 수신 폴링 주기 (초) |

---

## 2. 테이블 DDL

### 2-1. eai_datasource (DB Adapter 연결 정보)

```sql
CREATE TABLE eai_datasource (
    id                  BIGSERIAL       PRIMARY KEY,
    datasource_id       VARCHAR(50)     NOT NULL UNIQUE,   -- DS_ERP, DS_WMS
    datasource_name     VARCHAR(200)    NOT NULL,
    db_type             VARCHAR(20)     NOT NULL           -- POSTGRESQL, ORACLE, MSSQL, MYSQL, MARIADB
                        CHECK (db_type IN ('POSTGRESQL','ORACLE','MSSQL','MYSQL','MARIADB','H2')),
    jdbc_url            VARCHAR(500)    NOT NULL,          -- jdbc:postgresql://host:5432/dbname
    db_username         VARCHAR(100)    NOT NULL,
    db_password         VARCHAR(500)    NOT NULL,          -- AES-256 암호화 저장
    driver_class        VARCHAR(200)    NOT NULL,          -- org.postgresql.Driver
    pool_min_size       INT             DEFAULT 5,
    pool_max_size       INT             DEFAULT 20,
    pool_timeout_ms     INT             DEFAULT 30000,     -- 커넥션 대기 타임아웃
    query_timeout_sec   INT             DEFAULT 30,        -- 쿼리 실행 타임아웃
    default_schema      VARCHAR(100),
    connection_props    TEXT,                              -- 추가 JDBC 속성 JSON
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    description         TEXT,
    created_by          VARCHAR(100),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  eai_datasource              IS 'EAI DB 어댑터 DataSource 연결 정보';
COMMENT ON COLUMN eai_datasource.db_password  IS 'AES-256 암호화 저장';
COMMENT ON COLUMN eai_datasource.jdbc_url     IS 'JDBC 연결 URL 전체';

CREATE INDEX idx_eai_ds_active ON eai_datasource (is_active);
```

---

### 2-2. eai_db_adapter_config (DB Adapter 인터페이스별 실행 설정)

```sql
-- eai_datasource와 인터페이스 실행 설정을 분리
-- (1개 DataSource에 여러 인터페이스가 매핑될 수 있음)
CREATE TABLE eai_db_adapter_config (
    id                  BIGSERIAL       PRIMARY KEY,
    interface_id        VARCHAR(20)     NOT NULL REFERENCES eai_interface_def(interface_id),
    datasource_id       VARCHAR(50)     NOT NULL REFERENCES eai_datasource(datasource_id),
    statement_id        VARCHAR(300)    NOT NULL,           -- MyBatis Mapper ID
    operation_type      VARCHAR(20)     NOT NULL           -- QUERY, INSERT, UPDATE, PROCEDURE
                        CHECK (operation_type IN ('QUERY','INSERT','UPDATE','DELETE','PROCEDURE')),
    result_type         VARCHAR(20)     DEFAULT 'LIST'     -- LIST, SINGLE, COUNT
                        CHECK (result_type IN ('LIST','SINGLE','COUNT','NONE')),
    param_mapping       TEXT,                              -- 입력 파라미터 매핑 JSON
    rollback_on_error   BOOLEAN         DEFAULT TRUE,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  eai_db_adapter_config             IS 'EAI DB 어댑터 인터페이스별 실행 설정';
COMMENT ON COLUMN eai_db_adapter_config.statement_id IS 'MyBatis Mapper Namespace.ID';

CREATE INDEX idx_eai_db_conf_if ON eai_db_adapter_config (interface_id);
CREATE INDEX idx_eai_db_conf_ds ON eai_db_adapter_config (datasource_id);
```

---

### 2-3. eai_rest_config (REST Adapter 설정)

```sql
CREATE TABLE eai_rest_config (
    id                  BIGSERIAL       PRIMARY KEY,
    interface_id        VARCHAR(20)     NOT NULL REFERENCES eai_interface_def(interface_id),
    config_name         VARCHAR(200)    NOT NULL,
    url                 VARCHAR(500)    NOT NULL,
    http_method         VARCHAR(10)     NOT NULL
                        CHECK (http_method IN ('GET','POST','PUT','DELETE','PATCH','HEAD')),
    timeout_ms          INT             NOT NULL DEFAULT 5000,

    -- 인증
    auth_type           VARCHAR(20)     NOT NULL DEFAULT 'NONE'
                        CHECK (auth_type IN ('NONE','BEARER','API_KEY','BASIC','OAUTH2')),
    auth_value          VARCHAR(1000),                     -- Bearer 토큰 or API Key (암호화)
    api_key_header      VARCHAR(100),                     -- API_KEY 방식 헤더명 (X-API-KEY 등)

    -- OAUTH2 전용
    token_url           VARCHAR(500),
    client_id           VARCHAR(200),
    client_secret       VARCHAR(500),                     -- 암호화 저장
    token_scope         VARCHAR(500),

    -- 요청 설정
    content_type        VARCHAR(100)    DEFAULT 'application/json',
    request_headers     TEXT,                              -- [{"key":"X-Tenant","value":"T001"}]
    request_template    TEXT,                              -- 요청 바디 템플릿 (선택)

    -- 응답 설정
    success_http_codes  VARCHAR(100)    DEFAULT '200,201,202', -- 성공으로 볼 HTTP 코드
    response_path       VARCHAR(200),                     -- JSON 응답에서 추출할 경로 ($.data)

    -- SSL / Proxy
    ssl_verify          BOOLEAN         DEFAULT TRUE,
    proxy_host          VARCHAR(200),
    proxy_port          INT,
    proxy_user          VARCHAR(100),
    proxy_password      VARCHAR(500),                     -- 암호화

    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  eai_rest_config              IS 'EAI REST 어댑터 설정';
COMMENT ON COLUMN eai_rest_config.auth_value   IS 'Bearer 토큰 또는 API Key 값, AES-256 암호화';
COMMENT ON COLUMN eai_rest_config.client_secret IS 'OAUTH2 Client Secret, AES-256 암호화';

CREATE INDEX idx_eai_rest_conf_if ON eai_rest_config (interface_id);
```

---

### 2-4. eai_soap_config (SOAP Adapter 설정)

```sql
CREATE TABLE eai_soap_config (
    id                  BIGSERIAL       PRIMARY KEY,
    interface_id        VARCHAR(20)     NOT NULL REFERENCES eai_interface_def(interface_id),
    config_name         VARCHAR(200)    NOT NULL,

    -- WSDL 연결 정보
    wsdl_url            VARCHAR(500)    NOT NULL,          -- http://legacy/service?wsdl
    service_url         VARCHAR(500)    NOT NULL,          -- 실제 SOAP 엔드포인트
    namespace           VARCHAR(300)    NOT NULL,          -- http://example.com/schema
    operation_name      VARCHAR(200)    NOT NULL,          -- WSDL Operation 이름
    port_name           VARCHAR(200),                     -- WSDL Port 이름 (선택)

    -- SOAP 프로토콜
    soap_version        VARCHAR(5)      NOT NULL DEFAULT '1.1'
                        CHECK (soap_version IN ('1.1','1.2')),
    soap_action         VARCHAR(300),                     -- SOAPAction 헤더 값
    mtom_enabled        BOOLEAN         DEFAULT FALSE,    -- 바이너리 첨부 MTOM 사용 여부

    -- WS-Security 인증
    ws_security_type    VARCHAR(30)     NOT NULL DEFAULT 'NONE'
                        CHECK (ws_security_type IN ('NONE','USERNAME_TOKEN','X509','SAML')),
    ws_username         VARCHAR(100),
    ws_password         VARCHAR(500),                     -- 암호화 저장
    ws_password_type    VARCHAR(50)     DEFAULT 'PasswordText', -- PasswordText / PasswordDigest
    keystore_path       VARCHAR(500),                     -- X.509 키스토어 경로
    keystore_password   VARCHAR(500),                     -- 암호화 저장

    -- 요청/응답 설정
    timeout_ms          INT             DEFAULT 10000,
    request_template    TEXT,                             -- SOAP 요청 템플릿 XML
    response_path       VARCHAR(200),                    -- 응답 XPath 추출 경로

    -- WSDL 캐시
    wsdl_cached_at      TIMESTAMP,
    wsdl_cache          TEXT,                            -- 파싱된 WSDL 캐시

    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  eai_soap_config             IS 'EAI SOAP 어댑터 설정';
COMMENT ON COLUMN eai_soap_config.wsdl_url   IS 'WSDL 정의 파일 URL (?wsdl 포함)';
COMMENT ON COLUMN eai_soap_config.ws_password IS 'WS-Security 비밀번호, AES-256 암호화';

CREATE INDEX idx_eai_soap_conf_if ON eai_soap_config (interface_id);
```

---

### 2-5. eai_file_config (FILE Adapter 설정)

```sql
CREATE TABLE eai_file_config (
    id                  BIGSERIAL       PRIMARY KEY,
    interface_id        VARCHAR(20)     NOT NULL REFERENCES eai_interface_def(interface_id),
    config_name         VARCHAR(200)    NOT NULL,

    -- 서버 연결
    protocol            VARCHAR(10)     NOT NULL
                        CHECK (protocol IN ('SFTP','FTP','FTPS','LOCAL')),
    remote_host         VARCHAR(200),                     -- LOCAL 방식 제외 필수
    remote_port         INT,                              -- 기본 22(SFTP), 21(FTP)
    remote_user         VARCHAR(100),
    remote_password     VARCHAR(500),                     -- 암호화 저장 (비밀번호 인증)
    ssh_key_path        VARCHAR(500),                     -- SFTP 키 인증 시 private key 경로
    ssh_key_passphrase  VARCHAR(500),                     -- 암호화 저장

    -- 경로 설정
    upload_path         VARCHAR(500),                    -- 송신 업로드 원격 경로
    download_path       VARCHAR(500),                    -- 수신 다운로드 원격 경로
    local_temp_path     VARCHAR(500),                    -- 로컬 임시 저장 경로
    done_path           VARCHAR(500),                    -- 처리 완료 후 이동 경로 (중복 방지)
    error_path          VARCHAR(500),                    -- 오류 파일 이동 경로

    -- 파일 설정
    file_pattern        VARCHAR(200)    NOT NULL,        -- sales_{yyyyMMdd}.csv
    file_format         VARCHAR(20)     DEFAULT 'CSV'
                        CHECK (file_format IN ('CSV','EXCEL','FIXED','JSON','XML')),
    file_encoding       VARCHAR(20)     DEFAULT 'UTF-8',
    delimiter           VARCHAR(5)      DEFAULT ',',     -- CSV 구분자
    quote_char          VARCHAR(5)      DEFAULT '"',     -- CSV 인용부호
    escape_char         VARCHAR(5),
    has_header          BOOLEAN         DEFAULT TRUE,    -- 헤더행 여부
    skip_lines          INT             DEFAULT 0,       -- 상단 건너뛸 행 수

    -- 처리 설정
    polling_sec         INT             DEFAULT 60,      -- 수신 폴링 간격 (초)
    batch_size          INT             DEFAULT 1000,    -- 배치 처리 단위 (행)
    connect_timeout_ms  INT             DEFAULT 10000,
    read_timeout_ms     INT             DEFAULT 30000,
    delete_after_done   BOOLEAN         DEFAULT FALSE,   -- 처리 후 원격 파일 삭제 여부

    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    description         TEXT,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE  eai_file_config                  IS 'EAI FILE 어댑터 FTP/SFTP 설정';
COMMENT ON COLUMN eai_file_config.remote_password  IS '비밀번호 인증 시 AES-256 암호화';
COMMENT ON COLUMN eai_file_config.ssh_key_passphrase IS 'SSH 키 Passphrase, AES-256 암호화';
COMMENT ON COLUMN eai_file_config.done_path        IS '처리 완료 후 이동 경로 - 중복 처리 방지';

CREATE INDEX idx_eai_file_conf_if ON eai_file_config (interface_id);
```

---

## 3. 테이블 관계 요약

```
eai_interface_def (1)
    ├── eai_db_adapter_config   (N)  → eai_datasource (N:1)
    ├── eai_rest_config         (N)
    ├── eai_soap_config         (N)
    └── eai_file_config         (N)

각 어댑터 설정 테이블은 interface_id로 eai_interface_def를 참조
하나의 인터페이스에 동일 타입 어댑터를 여러 개 등록 가능 (예: 다중 Target)
```

---

## 4. 테스트 INSERT SQL

```sql
-- DataSource 등록 (DB Adapter용)
INSERT INTO eai_datasource (datasource_id, datasource_name, db_type, jdbc_url, db_username, db_password, driver_class)
VALUES
  ('DS_ERP',  'SAP ERP DB',       'ORACLE',     'jdbc:oracle:thin:@erp-db:1521:ERPDB',        'eai_user', 'ENCRYPTED:xxx', 'oracle.jdbc.OracleDriver'),
  ('DS_WMS',  'WMS PostgreSQL',   'POSTGRESQL', 'jdbc:postgresql://wms-db:5432/wms',           'eai_user', 'ENCRYPTED:yyy', 'org.postgresql.Driver'),
  ('DS_LOCAL','로컬 테스트 DB',   'H2',         'jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1',        'sa',       '',              'org.h2.Driver');

-- DB Adapter 설정
INSERT INTO eai_db_adapter_config (interface_id, datasource_id, statement_id, operation_type)
VALUES
  ('IF-0038', 'DS_ERP', 'eai.erp.selectStockList', 'QUERY'),
  ('IF-0042', 'DS_WMS', 'eai.wms.insertInbound',   'INSERT');

-- REST Adapter 설정
INSERT INTO eai_rest_config (interface_id, config_name, url, http_method, timeout_ms, auth_type, auth_value)
VALUES
  ('IF-0041', 'ERP REST 주문 전송', 'https://erp-dev.example.com/api/v1/orders', 'POST', 5000, 'BEARER', 'ENCRYPTED:token123'),
  ('IF-0015', '그룹웨어 알림 전송', 'https://gw.example.com/api/notify',          'POST', 3000, 'API_KEY', 'ENCRYPTED:apikey456');

-- SOAP Adapter 설정
INSERT INTO eai_soap_config (interface_id, config_name, wsdl_url, service_url, namespace, operation_name, ws_security_type, ws_username, ws_password)
VALUES
  ('IF-0027', '레거시 CRM SOAP', 'http://legacy-crm/CustomerService?wsdl',
   'http://legacy-crm/CustomerService', 'http://legacy.example.com/crm',
   'updateCustomer', 'USERNAME_TOKEN', 'svc_eai', 'ENCRYPTED:pass789');

-- FILE Adapter 설정
INSERT INTO eai_file_config (interface_id, config_name, protocol, remote_host, remote_port, remote_user, remote_password, download_path, done_path, file_pattern, file_format)
VALUES
  ('IF-0019', 'POS 매출 SFTP 수신', 'SFTP', 'ftp.pos-system.example.com', 22, 'eai_recv', 'ENCRYPTED:sftp_pass',
   '/upload/sales', '/upload/sales/done', 'sales_{yyyyMMdd}.csv', 'CSV');
```

---

## 5. 암호화 저장 규칙

```
모든 인증 정보(비밀번호, 토큰, 키)는 DB에 저장 전 반드시 AES-256 암호화 필요

암호화 대상 컬럼:
  eai_datasource        → db_password
  eai_rest_config       → auth_value, client_secret, proxy_password
  eai_soap_config       → ws_password, keystore_password
  eai_file_config       → remote_password, ssh_key_passphrase

저장 형식: "ENCRYPTED:{base64_encoded_aes256_ciphertext}"
복호화:   런타임 시 EAI 서버의 AesEncryptor 빈에서 복호화 후 사용
키 관리:  환경변수 EAI_AES_KEY 주입 (32자 이상, Vault 또는 SecretManager 권장)
```
