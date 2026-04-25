-- =====================================================================================
-- DROP
-- =====================================================================================
DROP TABLE IF EXISTS role_mapping;
DROP TABLE IF EXISTS role_info;
DROP TABLE IF EXISTS program_info;
DROP TABLE IF EXISTS menu_info;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS dept_info;

-- =====================================================================================
-- CREATE
-- =====================================================================================

CREATE TABLE dept_info (
                           deptId          VARCHAR(20)    NOT NULL                  COMMENT '부서ID',
                           parentDeptId    VARCHAR(20)    NULL                      COMMENT '상위부서ID',
                           deptCode        VARCHAR(20)    NOT NULL                  COMMENT '부서코드',
                           deptName        VARCHAR(100)   NOT NULL                  COMMENT '부서명',
                           deptLevel       TINYINT        DEFAULT 1                 COMMENT '부서레벨(1:본부,2:부,3:팀)',
                           sortOrder       INT            DEFAULT 0                 COMMENT '정렬순서',
                           useYn           CHAR(1)        DEFAULT 'Y'               COMMENT '사용여부',
                           createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                           createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                           updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (deptId),
                           UNIQUE KEY ukDeptCode (deptCode)
) COMMENT '부서정보';

CREATE TABLE user_info (
                           userId          VARCHAR(50)    NOT NULL                  COMMENT '사용자ID',
                           deptId          VARCHAR(20)    NULL                      COMMENT '부서ID',
                           userName        VARCHAR(50)    NOT NULL                  COMMENT '사용자명',
                           password        VARCHAR(255)   NOT NULL                  COMMENT '비밀번호',
                           email           VARCHAR(100)   NULL                      COMMENT '이메일',
                           phone           VARCHAR(50)    NULL                      COMMENT '연락처',
                           position        VARCHAR(50)    NULL                      COMMENT '직위',
                           jobGrade        VARCHAR(20)    NULL                      COMMENT '직급',
                           useYn           CHAR(1)        DEFAULT 'Y'               COMMENT '사용여부',
                           managerYn       CHAR(1)        NULL                      COMMENT '시스템관리자여부',
                           lastLoginAt     TIMESTAMP      NULL                      COMMENT '최종로그인일시',
                           createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                           createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                           updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (userId)
) COMMENT '사용자정보';

CREATE TABLE role_info (
                           roleCode        VARCHAR(50)    NOT NULL                  COMMENT '권한코드',
                           roleName        VARCHAR(100)   NOT NULL                  COMMENT '권한명',
                           description     VARCHAR(255)   NULL                      COMMENT '권한설명',
                           useYn           CHAR(1)        DEFAULT 'Y'               COMMENT '사용여부',
                           createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                           createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                           updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (roleCode)
) COMMENT '권한정보';

CREATE TABLE menu_info (
                           menuId          VARCHAR(50)    NOT NULL                  COMMENT '메뉴ID',
                           parentMenuId    VARCHAR(50)    NULL                      COMMENT '상위메뉴ID',
                           menuName        VARCHAR(100)   NOT NULL                  COMMENT '메뉴명',
                           menuLevel       TINYINT        DEFAULT 1                 COMMENT '메뉴레벨(1:대,2:중,3:소)',
                           menuIcon        VARCHAR(100)   NULL                      COMMENT '메뉴아이콘',
                           sortOrder       INT            DEFAULT 0                 COMMENT '정렬순서',
                           useYn           CHAR(1)        DEFAULT 'Y'               COMMENT '사용여부',
                           createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                           createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                           updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (menuId)
) COMMENT '메뉴정보';

CREATE TABLE program_info (
                              programId       VARCHAR(50)    NOT NULL                  COMMENT '프로그램ID',
                              menuId          VARCHAR(50)    NOT NULL                  COMMENT '메뉴ID',
                              programCode     VARCHAR(50)    NOT NULL                  COMMENT '프로그램코드',
                              programName     VARCHAR(100)   NOT NULL                  COMMENT '프로그램명',
                              programUrl      VARCHAR(255)   NULL                      COMMENT '프로그램URL',
                              useYn           CHAR(1)        DEFAULT 'Y'               COMMENT '사용여부',
                              sortOrder       INT            DEFAULT 0                 COMMENT '정렬순서',
                              createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                              createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                              updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                              updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                              PRIMARY KEY (programId)
) COMMENT '프로그램정보';

CREATE TABLE role_mapping (
                              mappingId       VARCHAR(50)    NOT NULL                  COMMENT '매핑ID',
                              roleId          VARCHAR(50)    NOT NULL                  COMMENT '권한ID',
                              userId          VARCHAR(50)    NULL                      COMMENT '사용자ID',
                              programId       VARCHAR(50)    NOT NULL                  COMMENT '프로그램ID',
                              canRead         CHAR(1)        DEFAULT 'N'               COMMENT '조회권한',
                              canWrite        CHAR(1)        DEFAULT 'N'               COMMENT '등록권한',
                              canUpdate       CHAR(1)        DEFAULT 'N'               COMMENT '수정권한',
                              canDelete       CHAR(1)        DEFAULT 'N'               COMMENT '삭제권한',
                              createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                              createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                              updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                              updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                              PRIMARY KEY (mappingId)
) COMMENT '권한매핑';

-- =====================================================================================
-- INIT SAMPLE DATA
-- =====================================================================================

INSERT INTO dept_info (deptId, parentDeptId, deptCode, deptName, deptLevel, sortOrder) VALUES
                                                                                           ('DEPT001', NULL,      'HQ',       '본사',             1, 1),
                                                                                           ('DEPT002', 'DEPT001', 'MGMT',     '경영지원본부',      2, 1),
                                                                                           ('DEPT003', 'DEPT001', 'DEV',      '개발본부',          2, 2),
                                                                                           ('DEPT004', 'DEPT001', 'SALES',    '영업본부',          2, 3),
                                                                                           ('DEPT005', 'DEPT002', 'HR',       '인사팀',            3, 1),
                                                                                           ('DEPT006', 'DEPT002', 'FINANCE',  '재무팀',            3, 2),
                                                                                           ('DEPT007', 'DEPT003', 'BACKEND',  '백엔드개발팀',      3, 1),
                                                                                           ('DEPT008', 'DEPT003', 'FRONTEND', '프론트엔드개발팀',  3, 2),
                                                                                           ('DEPT009', 'DEPT003', 'INFRA',    '인프라팀',          3, 3),
                                                                                           ('DEPT010', 'DEPT004', 'SALES1',   '영업1팀',           3, 1);

INSERT INTO user_info (userId, deptId, userName, password, email, phone, position, jobGrade, managerYn) VALUES
                                                                                                            ('now009',  'DEPT001', '홍길동', SHA2('2799', 256), 'hong@saffron.com',  '010-1234-5678', '대표이사', '임원', 'Y'),
                                                                                                            ('user001', 'DEPT002', '김경영', SHA2('1234', 256), 'kim@saffron.com',   '010-2345-6789', '본부장',   '1급',  'N'),
                                                                                                            ('user002', 'DEPT003', '이개발', SHA2('1234', 256), 'lee@saffron.com',   '010-3456-7890', '본부장',   '1급',  'N'),
                                                                                                            ('user003', 'DEPT004', '박영업', SHA2('1234', 256), 'park@saffron.com',  '010-4567-8901', '본부장',   '1급',  'N'),
                                                                                                            ('user004', 'DEPT005', '최인사', SHA2('1234', 256), 'choi@saffron.com',  '010-5678-9012', '팀장',     '2급',  'N'),
                                                                                                            ('user005', 'DEPT006', '정재무', SHA2('1234', 256), 'jung@saffron.com',  '010-6789-0123', '팀장',     '2급',  'N'),
                                                                                                            ('user006', 'DEPT007', '강백엔', SHA2('1234', 256), 'kang@saffron.com',  '010-7890-1234', '과장',     '3급',  'N'),
                                                                                                            ('user007', 'DEPT008', '윤프론', SHA2('1234', 256), 'yoon@saffron.com',  '010-8901-2345', '대리',     '4급',  'N'),
                                                                                                            ('user008', 'DEPT009', '장인프', SHA2('1234', 256), 'jang@saffron.com',  '010-9012-3456', '대리',     '4급',  'N'),
                                                                                                            ('user009', 'DEPT010', '임영업', SHA2('1234', 256), 'lim@saffron.com',   '010-0123-4567', '사원',     '5급',  'N');

INSERT INTO menu_info (menuId, parentMenuId, menuName, menuLevel, menuIcon, sortOrder) VALUES
                                                                                           ('MENU001', NULL,      '시스템관리',   1, 'icon-setting', 1),
                                                                                           ('MENU002', NULL,      '사용자관리',   1, 'icon-user',    2),
                                                                                           ('MENU003', NULL,      '업무관리',     1, 'icon-work',    3),
                                                                                           ('MENU004', NULL,      '보고서',       1, 'icon-report',  4),
                                                                                           ('MENU010', 'MENU001', '공통코드관리', 2, 'icon-code',    1),
                                                                                           ('MENU011', 'MENU001', '메뉴관리',     2, 'icon-menu',    2),
                                                                                           ('MENU012', 'MENU001', '권한관리',     2, 'icon-lock',    3),
                                                                                           ('MENU020', 'MENU002', '사용자목록',   2, 'icon-list',    1),
                                                                                           ('MENU021', 'MENU002', '부서관리',     2, 'icon-dept',    2),
                                                                                           ('MENU030', 'MENU003', '업무현황',     2, 'icon-status',  1);

INSERT INTO program_info (programId, menuId, programCode, programName, programUrl, sortOrder) VALUES
                                                                                                  ('PGM001', 'MENU010', 'CMN_CODE_LIST',   '공통코드 목록', '/system/code/list',    1),
                                                                                                  ('PGM002', 'MENU010', 'CMN_CODE_DETAIL', '공통코드 상세', '/system/code/detail',  2),
                                                                                                  ('PGM003', 'MENU011', 'SYS_MENU_LIST',   '메뉴 목록',     '/system/menu/list',    1),
                                                                                                  ('PGM004', 'MENU011', 'SYS_MENU_DETAIL', '메뉴 상세',     '/system/menu/detail',  2),
                                                                                                  ('PGM005', 'MENU012', 'SYS_ROLE_LIST',   '권한 목록',     '/system/role/list',    1),
                                                                                                  ('PGM006', 'MENU012', 'SYS_ROLE_DETAIL', '권한 상세',     '/system/role/detail',  2),
                                                                                                  ('PGM007', 'MENU020', 'USR_USER_LIST',   '사용자 목록',   '/user/list',           1),
                                                                                                  ('PGM008', 'MENU020', 'USR_USER_DETAIL', '사용자 상세',   '/user/detail',         2),
                                                                                                  ('PGM009', 'MENU021', 'USR_DEPT_LIST',   '부서 목록',     '/user/dept/list',      1),
                                                                                                  ('PGM010', 'MENU030', 'WRK_STATUS_LIST', '업무현황 목록', '/work/status/list',    1);

INSERT INTO role_info (roleCode, roleName, description) VALUES
                                                            ('ROLE_ADMIN',   '시스템관리자', '모든 메뉴 및 기능 접근 가능'),
                                                            ('ROLE_MANAGER', '팀장',        '부서 관련 메뉴 및 승인 기능 접근'),
                                                            ('ROLE_USER',    '일반사용자',   '기본 메뉴 조회 및 본인 데이터 수정'),
                                                            ('ROLE_GUEST',   '게스트',      '제한된 조회 기능만 접근 가능');

INSERT INTO role_mapping (mappingId, roleId, userId, programId, canRead, canWrite, canUpdate, canDelete) VALUES
                                                                                                             ('MAP001', 'ROLE_ADMIN',   NULL,     'PGM001', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP002', 'ROLE_ADMIN',   NULL,     'PGM002', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP003', 'ROLE_ADMIN',   NULL,     'PGM003', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP004', 'ROLE_ADMIN',   NULL,     'PGM004', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP005', 'ROLE_ADMIN',   NULL,     'PGM005', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP006', 'ROLE_ADMIN',   NULL,     'PGM006', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP007', 'ROLE_ADMIN',   NULL,     'PGM007', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP008', 'ROLE_ADMIN',   NULL,     'PGM008', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP009', 'ROLE_ADMIN',   NULL,     'PGM009', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP010', 'ROLE_ADMIN',   NULL,     'PGM010', 'Y', 'Y', 'Y', 'Y'),
                                                                                                             ('MAP011', 'ROLE_MANAGER', NULL,     'PGM007', 'Y', 'Y', 'Y', 'N'),
                                                                                                             ('MAP012', 'ROLE_MANAGER', NULL,     'PGM008', 'Y', 'Y', 'Y', 'N'),
                                                                                                             ('MAP013', 'ROLE_MANAGER', NULL,     'PGM009', 'Y', 'Y', 'Y', 'N'),
                                                                                                             ('MAP014', 'ROLE_MANAGER', NULL,     'PGM010', 'Y', 'Y', 'Y', 'N'),
                                                                                                             ('MAP015', 'ROLE_USER',    NULL,     'PGM007', 'Y', 'N', 'N', 'N'),
                                                                                                             ('MAP016', 'ROLE_USER',    NULL,     'PGM008', 'Y', 'Y', 'N', 'N'),
                                                                                                             ('MAP017', 'ROLE_USER',    NULL,     'PGM010', 'Y', 'Y', 'N', 'N'),
                                                                                                             ('MAP018', 'ROLE_GUEST',   NULL,     'PGM007', 'Y', 'N', 'N', 'N'),
                                                                                                             ('MAP019', 'ROLE_GUEST',   NULL,     'PGM010', 'Y', 'N', 'N', 'N'),
                                                                                                             ('MAP020', 'ROLE_ADMIN',   'now009', 'PGM001', 'Y', 'Y', 'Y', 'Y');