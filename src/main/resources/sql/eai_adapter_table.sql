-- ============================================================
-- EAI 어댑터 설정 테이블 (MariaDB 10.5+)
-- 인코딩: utf8mb4 / 엔진: InnoDB
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 2-1. eai_datasource (DB Adapter 연결 정보)
-- ============================================================
CREATE TABLE eai_datasource (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    datasource_id       VARCHAR(50)     NOT NULL,
    datasource_name     VARCHAR(200)    NOT NULL,
    db_type             VARCHAR(20)     NOT NULL                 COMMENT 'POSTGRESQL, ORACLE, MSSQL, MYSQL, MARIADB, H2',
    jdbc_url            VARCHAR(500)    NOT NULL                 COMMENT 'jdbc:mariadb://host:3306/dbname',
    db_username         VARCHAR(100)    NOT NULL,
    db_password         VARCHAR(500)    NOT NULL                 COMMENT 'AES-256 암호화 저장',
    driver_class        VARCHAR(200)    NOT NULL                 COMMENT 'org.mariadb.jdbc.Driver',
    pool_min_size       INT             DEFAULT 5,
    pool_max_size       INT             DEFAULT 20,
    pool_timeout_ms     INT             DEFAULT 30000            COMMENT '커넥션 대기 타임아웃',
    query_timeout_sec   INT             DEFAULT 30               COMMENT '쿼리 실행 타임아웃',
    default_schema      VARCHAR(100),
    connection_props    TEXT                                     COMMENT '추가 JDBC 속성 JSON',
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    description         TEXT,
    created_by          VARCHAR(100),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_eai_datasource_id (datasource_id),
    CONSTRAINT chk_eai_datasource_db_type CHECK (db_type IN ('POSTGRESQL','ORACLE','MSSQL','MYSQL','MARIADB','H2'))
) COMMENT='EAI DB 어댑터 DataSource 연결 정보';

CREATE INDEX idx_eai_ds_active ON eai_datasource (is_active);

-- ============================================================
-- 2-2. eai_db_adapter_config (DB Adapter 인터페이스별 실행 설정)
-- ============================================================
CREATE TABLE eai_db_adapter_config (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    interface_id        VARCHAR(20)     NOT NULL,
    datasource_id       VARCHAR(50)     NOT NULL,
    statement_id        VARCHAR(300)    NOT NULL                 COMMENT 'MyBatis Mapper Namespace.ID',
    operation_type      VARCHAR(20)     NOT NULL                 COMMENT 'QUERY, INSERT, UPDATE, DELETE, PROCEDURE',
    result_type         VARCHAR(20)     DEFAULT 'LIST'           COMMENT 'LIST, SINGLE, COUNT, NONE',
    param_mapping       TEXT                                     COMMENT '입력 파라미터 매핑 JSON',
    rollback_on_error   BOOLEAN         DEFAULT TRUE,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT chk_eai_db_conf_op_type  CHECK (operation_type IN ('QUERY','INSERT','UPDATE','DELETE','PROCEDURE')),
    CONSTRAINT chk_eai_db_conf_res_type CHECK (result_type     IN ('LIST','SINGLE','COUNT','NONE')),
    CONSTRAINT fk_eai_db_conf_interface FOREIGN KEY (interface_id)  REFERENCES eai_interface_def(interface_id),
    CONSTRAINT fk_eai_db_conf_ds        FOREIGN KEY (datasource_id) REFERENCES eai_datasource(datasource_id)
) COMMENT='EAI DB 어댑터 인터페이스별 실행 설정';

CREATE INDEX idx_eai_db_conf_if ON eai_db_adapter_config (interface_id);
CREATE INDEX idx_eai_db_conf_ds ON eai_db_adapter_config (datasource_id);

-- ============================================================
-- 2-3. eai_rest_config (REST Adapter 설정)
-- ============================================================
CREATE TABLE eai_rest_config (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    interface_id        VARCHAR(20)     NOT NULL,
    config_name         VARCHAR(200)    NOT NULL,
    url                 VARCHAR(500)    NOT NULL,
    http_method         VARCHAR(10)     NOT NULL,
    timeout_ms          INT             NOT NULL DEFAULT 5000,

    -- 인증
    auth_type           VARCHAR(20)     NOT NULL DEFAULT 'NONE'  COMMENT 'NONE, BEARER, API_KEY, BASIC, OAUTH2',
    auth_value          VARCHAR(1000)                            COMMENT 'Bearer 토큰 or API Key (AES-256 암호화)',
    api_key_header      VARCHAR(100)                             COMMENT 'API_KEY 방식 헤더명 (X-API-KEY 등)',

    -- OAUTH2 전용
    token_url           VARCHAR(500),
    client_id           VARCHAR(200),
    client_secret       VARCHAR(500)                             COMMENT 'AES-256 암호화',
    token_scope         VARCHAR(500),

    -- 요청 설정
    content_type        VARCHAR(100)    DEFAULT 'application/json',
    request_headers     TEXT                                     COMMENT '[{"key":"X-Tenant","value":"T001"}]',
    request_template    TEXT                                     COMMENT '요청 바디 템플릿 (선택)',

    -- 응답 설정
    success_http_codes  VARCHAR(100)    DEFAULT '200,201,202'    COMMENT '성공으로 볼 HTTP 코드',
    response_path       VARCHAR(200)                             COMMENT 'JSON 응답 추출 경로 ($.data)',

    -- SSL / Proxy
    ssl_verify          BOOLEAN         DEFAULT TRUE,
    proxy_host          VARCHAR(200),
    proxy_port          INT,
    proxy_user          VARCHAR(100),
    proxy_password      VARCHAR(500)                             COMMENT 'AES-256 암호화',

    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT chk_eai_rest_http_method CHECK (http_method IN ('GET','POST','PUT','DELETE','PATCH','HEAD')),
    CONSTRAINT chk_eai_rest_auth_type   CHECK (auth_type   IN ('NONE','BEARER','API_KEY','BASIC','OAUTH2')),
    CONSTRAINT fk_eai_rest_interface    FOREIGN KEY (interface_id) REFERENCES eai_interface_def(interface_id)
) COMMENT='EAI REST 어댑터 설정';

CREATE INDEX idx_eai_rest_conf_if ON eai_rest_config (interface_id);

-- ============================================================
-- 2-4. eai_soap_config (SOAP Adapter 설정)
-- ============================================================
CREATE TABLE eai_soap_config (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    interface_id        VARCHAR(20)     NOT NULL,
    config_name         VARCHAR(200)    NOT NULL,

    -- WSDL 연결 정보
    wsdl_url            VARCHAR(500)    NOT NULL                 COMMENT 'http://legacy/service?wsdl',
    service_url         VARCHAR(500)    NOT NULL                 COMMENT '실제 SOAP 엔드포인트',
    namespace           VARCHAR(300)    NOT NULL                 COMMENT 'http://example.com/schema',
    operation_name      VARCHAR(200)    NOT NULL                 COMMENT 'WSDL Operation 이름',
    port_name           VARCHAR(200)                             COMMENT 'WSDL Port 이름 (선택)',

    -- SOAP 프로토콜
    soap_version        VARCHAR(5)      NOT NULL DEFAULT '1.1'   COMMENT '1.1, 1.2',
    soap_action         VARCHAR(300)                             COMMENT 'SOAPAction 헤더 값',
    mtom_enabled        BOOLEAN         DEFAULT FALSE            COMMENT '바이너리 첨부 MTOM 사용 여부',

    -- WS-Security 인증
    ws_security_type    VARCHAR(30)     NOT NULL DEFAULT 'NONE'  COMMENT 'NONE, USERNAME_TOKEN, X509, SAML',
    ws_username         VARCHAR(100),
    ws_password         VARCHAR(500)                             COMMENT 'AES-256 암호화',
    ws_password_type    VARCHAR(50)     DEFAULT 'PasswordText'   COMMENT 'PasswordText / PasswordDigest',
    keystore_path       VARCHAR(500)                             COMMENT 'X.509 키스토어 경로',
    keystore_password   VARCHAR(500)                             COMMENT 'AES-256 암호화',

    -- 요청/응답 설정
    timeout_ms          INT             DEFAULT 10000,
    request_template    TEXT                                     COMMENT 'SOAP 요청 템플릿 XML',
    response_path       VARCHAR(200)                             COMMENT '응답 XPath 추출 경로',

    -- WSDL 캐시
    wsdl_cached_at      TIMESTAMP       NULL,
    wsdl_cache          TEXT                                     COMMENT '파싱된 WSDL 캐시',

    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT chk_eai_soap_version     CHECK (soap_version     IN ('1.1','1.2')),
    CONSTRAINT chk_eai_soap_ws_sec_type CHECK (ws_security_type IN ('NONE','USERNAME_TOKEN','X509','SAML')),
    CONSTRAINT fk_eai_soap_interface    FOREIGN KEY (interface_id) REFERENCES eai_interface_def(interface_id)
) COMMENT='EAI SOAP 어댑터 설정';

CREATE INDEX idx_eai_soap_conf_if ON eai_soap_config (interface_id);

-- ============================================================
-- 2-5. eai_file_config (FILE Adapter 설정)
-- ============================================================
CREATE TABLE eai_file_config (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    interface_id        VARCHAR(20)     NOT NULL,
    config_name         VARCHAR(200)    NOT NULL,

    -- 서버 연결
    protocol            VARCHAR(10)     NOT NULL                 COMMENT 'SFTP, FTP, FTPS, LOCAL',
    remote_host         VARCHAR(200)                             COMMENT 'LOCAL 방식 제외 필수',
    remote_port         INT                                      COMMENT '기본 22(SFTP), 21(FTP)',
    remote_user         VARCHAR(100),
    remote_password     VARCHAR(500)                             COMMENT 'AES-256 암호화 (비밀번호 인증)',
    ssh_key_path        VARCHAR(500)                             COMMENT 'SFTP 키 인증 시 private key 경로',
    ssh_key_passphrase  VARCHAR(500)                             COMMENT 'SSH 키 Passphrase, AES-256 암호화',

    -- 경로 설정
    upload_path         VARCHAR(500)                             COMMENT '송신 업로드 원격 경로',
    download_path       VARCHAR(500)                             COMMENT '수신 다운로드 원격 경로',
    local_temp_path     VARCHAR(500)                             COMMENT '로컬 임시 저장 경로',
    done_path           VARCHAR(500)                             COMMENT '처리 완료 후 이동 경로 (중복 방지)',
    error_path          VARCHAR(500)                             COMMENT '오류 파일 이동 경로',

    -- 파일 설정
    file_pattern        VARCHAR(200)    NOT NULL                 COMMENT 'sales_{yyyyMMdd}.csv',
    file_format         VARCHAR(20)     DEFAULT 'CSV'            COMMENT 'CSV, EXCEL, FIXED, JSON, XML',
    file_encoding       VARCHAR(20)     DEFAULT 'UTF-8',
    delimiter           VARCHAR(5)      DEFAULT ','              COMMENT 'CSV 구분자',
    quote_char          VARCHAR(5)      DEFAULT '"'              COMMENT 'CSV 인용부호',
    escape_char         VARCHAR(5),
    has_header          BOOLEAN         DEFAULT TRUE             COMMENT '헤더행 여부',
    skip_lines          INT             DEFAULT 0               COMMENT '상단 건너뛸 행 수',

    -- 처리 설정
    polling_sec         INT             DEFAULT 60              COMMENT '수신 폴링 간격 (초)',
    batch_size          INT             DEFAULT 1000            COMMENT '배치 처리 단위 (행)',
    connect_timeout_ms  INT             DEFAULT 10000,
    read_timeout_ms     INT             DEFAULT 30000,
    delete_after_done   BOOLEAN         DEFAULT FALSE           COMMENT '처리 후 원격 파일 삭제 여부',

    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    description         TEXT,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT chk_eai_file_protocol CHECK (protocol    IN ('SFTP','FTP','FTPS','LOCAL')),
    CONSTRAINT chk_eai_file_format   CHECK (file_format IN ('CSV','EXCEL','FIXED','JSON','XML')),
    CONSTRAINT fk_eai_file_interface FOREIGN KEY (interface_id) REFERENCES eai_interface_def(interface_id)
) COMMENT='EAI FILE 어댑터 FTP/SFTP 설정';

CREATE INDEX idx_eai_file_conf_if ON eai_file_config (interface_id);

SET FOREIGN_KEY_CHECKS = 1;
