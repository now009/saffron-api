-- =====================================================================================
-- DROP
-- =====================================================================================
DROP TABLE IF EXISTS role_mapping;
DROP TABLE IF EXISTS role_info;
DROP TABLE IF EXISTS program_info;
DROP TABLE IF EXISTS menu_info;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS dept_info;
DROP TABLE IF EXISTS code_info;
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
                           deptId          VARCHAR(20)    MULL                      COMMENT '부서ID',
                           userName        VARCHAR(50)    NOT NULL                  COMMENT '사용자명',
                           password        VARCHAR(255)   NOT NULL                  COMMENT '비밀번호',
                           email           VARCHAR(100)                             COMMENT '이메일',
                           phone           VARCHAR(50)                              COMMENT '연락처',
                           position        VARCHAR(50)                              COMMENT '직위',
                           jobGrade        VARCHAR(20)                              COMMENT '직급',
                           useYn           CHAR(1)        DEFAULT 'Y'               COMMENT '사용여부',
                           managerYn       CHAR(1)                                  COMMENT '시스템관리자여부',
                           lastLoginAt     TIMESTAMP                                COMMENT '최종로그인일시',
                           createdUser     VARCHAR(20)    DEFAULT 'system'          COMMENT '생성자',
                           createdDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           updateUser      VARCHAR(20)    DEFAULT 'system'          COMMENT '수정자',
                           updatedDate     TIMESTAMP      DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (userId)
) COMMENT '사용자정보';

CREATE TABLE role_info (
                           roleCode        VARCHAR(50)    NOT NULL                  COMMENT '권한코드',
                           roleName        VARCHAR(100)   NOT NULL                  COMMENT '권한명',
                           description     VARCHAR(255)                             COMMENT '권한설명',
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
                           menuDirYn      CHAR(1)        DEFAULT 'N'               COMMENT '메뉴폴더 여부',
                           menuIcon        VARCHAR(100)                             COMMENT '메뉴아이콘',
                           programId       VARCHAR(50)                              COMMENT '프로그램ID',
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


CREATE TABLE code_info (
                           code            VARCHAR(50)     NOT NULL                  COMMENT '코드',
                           parentCode      VARCHAR(50)     NULL                      COMMENT '상위코드',
                           codeName        VARCHAR(100)    NOT NULL                  COMMENT '코드명',
                           sortOrder       INT             DEFAULT 0                 COMMENT '정렬순서',
                           useYn           CHAR(1)         DEFAULT 'Y'               COMMENT '사용여부',
                           createdUser     VARCHAR(20)     DEFAULT 'system'          COMMENT '생성자',
                           createdDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                           updateUser      VARCHAR(20)     DEFAULT 'system'          COMMENT '수정자',
                           updatedDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (code)
) COMMENT '코드정보';
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

INSERT INTO user_info (userId, deptId, userName, password, email, phone, position, jobGrade, useYn, managerYn) VALUES
('now009',  'DEPT001', '홍길동', SHA2('2799', 256), 'hong@saffron.com',  '010-1234-5678', '대표이사', '임원', 'Y', 'Y'),
('user001', 'DEPT002', '김경영', SHA2('1234', 256), 'kim@saffron.com',   '010-2345-6789', '본부장',   '1급',  'Y', 'N'),
('user002', 'DEPT003', '이개발', SHA2('1234', 256), 'lee@saffron.com',   '010-3456-7890', '본부장',   '1급',  'Y', 'N'),
('user003', 'DEPT004', '박영업', SHA2('1234', 256), 'park@saffron.com',  '010-4567-8901', '본부장',   '1급',  'Y', 'N'),
('user004', 'DEPT005', '최인사', SHA2('1234', 256), 'choi@saffron.com',  '010-5678-9012', '팀장',     '2급',  'Y', 'N'),
('user005', 'DEPT006', '정재무', SHA2('1234', 256), 'jung@saffron.com',  '010-6789-0123', '팀장',     '2급',  'Y', 'N'),
('user006', 'DEPT007', '강백엔', SHA2('1234', 256), 'kang@saffron.com',  '010-7890-1234', '과장',     '3급',  'Y', 'N'),
('user007', 'DEPT008', '윤프론', SHA2('1234', 256), 'yoon@saffron.com',  '010-8901-2345', '대리',     '4급',  'Y', 'N'),
('user008', 'DEPT009', '장인프', SHA2('1234', 256), 'jang@saffron.com',  '010-9012-3456', '대리',     '4급',  'Y', 'N'),
('user009', 'DEPT010', '임영업', SHA2('1234', 256), 'lim@saffron.com',   '010-0123-4567', '사원',     '5급',  'Y', 'N');

INSERT INTO menu_info (menuId, parentMenuId, menuName, menuLevel, menuDirYn, menuIcon, programId, sortOrder) VALUES
('MENU001', NULL, '시스템관리',   1, 'Y','icon-setting', NULL,     1),
('MENU002', NULL, '사용자관리',   1, 'Y','icon-user',    NULL,     2),
('MENU003', NULL, '업무관리',     1, 'Y','icon-work',    NULL,     3),
('MENU004', NULL, '보고서',       1, 'Y','icon-report',  NULL,     4),
('MENU010', 'MENU001', '공통코드관리', 2, 'N','icon-code',    'PGM001', 1),
('MENU011', 'MENU001', '메뉴관리',     2, 'N','icon-menu',    'PGM003', 2),
('MENU012', 'MENU001', '권한관리',     2, 'N','icon-lock',    'PGM005', 3),
('MENU020', 'MENU002', '사용자목록',   2, 'N','icon-list',    'PGM007', 1),
('MENU021', 'MENU002', '부서관리',     2, 'N','icon-dept',    'PGM009', 2),
('MENU030', 'MENU003', '업무현황',     2, 'N','icon-status',  'PGM010', 1);

INSERT INTO program_info (programId, programName, programUrl, sortOrder) VALUES
 ('PGM001', '공통코드 목록', '/system/code/list',    1),
 ('PGM002', '프로그램 목록', '/portal/programs/list',  2),
 ('PGM003', '메뉴 목록',     '/portal/menus/list',   1),
 ('PGM004', '메뉴 상세',     '/system/menu/detail',  2),
 ('PGM005', '권한 목록',     '/system/role/list',    1),
 ('PGM006', '권한 상세',     '/system/role/detail',  2),
 ('PGM007', '사용자 목록',   '/user/list',           1),
 ('PGM008', '사용자 상세',   '/user/detail',         2),
 ('PGM009', '부서 목록',     '/user/dept/list',      1),
 ('PGM010', '업무현황 목록', '/work/status/list',    1);

INSERT INTO role_info (roleCode, roleName, description) VALUES
('ROLE_ADMIN',   '시스템관리자', '모든 메뉴 및 기능 접근 가능'),
('ROLE_MANAGER', '팀장',        '부서 관련 메뉴 및 승인 기능 접근'),
('ROLE_USER',    '일반사용자',   '기본 메뉴 조회 및 본인 데이터 수정'),
('ROLE_GUEST',   '게스트',      '제한된 조회 기능만 접근 가능');


-- 사용자 ↔ 역할
CREATE TABLE user_role (
userId      VARCHAR(50) NOT NULL  COMMENT '사용자ID',
roleCode    VARCHAR(50) NOT NULL  COMMENT '권한코드',
createdUser VARCHAR(20) DEFAULT 'system'          COMMENT '생성자',
createdDate TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
PRIMARY KEY (userId, roleCode)
) COMMENT '사용자권한매핑';

-- 부서 ↔ 역할
CREATE TABLE dept_role (
deptId      VARCHAR(20) NOT NULL  COMMENT '부서ID',
roleCode    VARCHAR(50) NOT NULL  COMMENT '권한코드',
createdUser VARCHAR(20) DEFAULT 'system'          COMMENT '생성자',
createdDate TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
PRIMARY KEY (deptId, roleCode)
) COMMENT '부서권한매핑';

-- 역할 ↔ 메뉴
CREATE TABLE role_menu (
roleCode    VARCHAR(50) NOT NULL  COMMENT '권한코드',
menuId      VARCHAR(50) NOT NULL  COMMENT '메뉴ID',
canRead     CHAR(1)     DEFAULT 'N' COMMENT '조회권한',
canWrite    CHAR(1)     DEFAULT 'N' COMMENT '등록권한',
canUpdate   CHAR(1)     DEFAULT 'N' COMMENT '수정권한',
canDelete   CHAR(1)     DEFAULT 'N' COMMENT '삭제권한',
createdUser VARCHAR(20) DEFAULT 'system'          COMMENT '생성자',
createdDate TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
PRIMARY KEY (roleCode, menuId)
) COMMENT '역할메뉴매핑';

-- =====================================================================================
-- INIT CODE DATA
-- =====================================================================================

-- 사용여부
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('USE_YN',   NULL,     '사용여부', 1),
('USE_YN_Y', 'USE_YN', '사용',     2),
('USE_YN_N', 'USE_YN', '미사용',   3);

-- 성별
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('GENDER',   NULL,     '성별', 1),
('GENDER_M', 'GENDER', '남성', 2),
('GENDER_F', 'GENDER', '여성', 3);

-- 직급
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('JOB_GRADE',    NULL,        '직급', 1),
('JOB_GRADE_1',  'JOB_GRADE', '1급',  2),
('JOB_GRADE_2',  'JOB_GRADE', '2급',  3),
('JOB_GRADE_3',  'JOB_GRADE', '3급',  4),
('JOB_GRADE_4',  'JOB_GRADE', '4급',  5),
('JOB_GRADE_5',  'JOB_GRADE', '5급',  6),
('JOB_GRADE_EX', 'JOB_GRADE', '임원', 7);

-- 직위
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('POSITION',    NULL,       '직위',    1),
('POSITION_01', 'POSITION', '사원',    2),
('POSITION_02', 'POSITION', '주임',    3),
('POSITION_03', 'POSITION', '대리',    4),
('POSITION_04', 'POSITION', '과장',    5),
('POSITION_05', 'POSITION', '차장',    6),
('POSITION_06', 'POSITION', '부장',    7),
('POSITION_07', 'POSITION', '팀장',    8),
('POSITION_08', 'POSITION', '본부장',  9),
('POSITION_09', 'POSITION', '이사',    10),
('POSITION_10', 'POSITION', '대표이사',11);

-- 부서레벨
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('DEPT_LEVEL',   NULL,         '부서레벨', 1),
('DEPT_LEVEL_1', 'DEPT_LEVEL', '본부',     2),
('DEPT_LEVEL_2', 'DEPT_LEVEL', '부',       3),
('DEPT_LEVEL_3', 'DEPT_LEVEL', '팀',       4);

-- 메뉴레벨
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('MENU_LEVEL',   NULL,         '메뉴레벨', 1),
('MENU_LEVEL_1', 'MENU_LEVEL', '대메뉴',   2),
('MENU_LEVEL_2', 'MENU_LEVEL', '중메뉴',   3),
('MENU_LEVEL_3', 'MENU_LEVEL', '소메뉴',   4);

-- 권한유형
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('ROLE_TYPE',         NULL,        '권한유형',     1),
('ROLE_TYPE_ADMIN',   'ROLE_TYPE', '시스템관리자', 2),
('ROLE_TYPE_MANAGER', 'ROLE_TYPE', '팀장',         3),
('ROLE_TYPE_USER',    'ROLE_TYPE', '일반사용자',   4),
('ROLE_TYPE_GUEST',   'ROLE_TYPE', '게스트',       5);

-- 로그인상태
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('LOGIN_STATUS',         NULL,           '로그인상태',    1),
('LOGIN_STATUS_SUCCESS', 'LOGIN_STATUS', '로그인 성공',   2),
('LOGIN_STATUS_FAIL',    'LOGIN_STATUS', '로그인 실패',   3),
('LOGIN_STATUS_LOCKED',  'LOGIN_STATUS', '계정 잠금',     4),
('LOGIN_STATUS_EXPIRED', 'LOGIN_STATUS', '비밀번호 만료', 5),
('LOGIN_STATUS_LOGOUT',  'LOGIN_STATUS', '로그아웃',      6);

-- 게시판유형
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('BOARD_TYPE',        NULL,         '게시판유형', 1),
('BOARD_TYPE_NOTICE', 'BOARD_TYPE', '공지사항',   2),
('BOARD_TYPE_FAQ',    'BOARD_TYPE', 'FAQ',         3),
('BOARD_TYPE_QNA',    'BOARD_TYPE', 'Q&A',         4),
('BOARD_TYPE_FREE',   'BOARD_TYPE', '자유게시판', 5);

-- 파일유형
INSERT INTO code_info (code, parentCode, codeName, sortOrder) VALUES
('FILE_TYPE',       NULL,        '파일유형', 1),
('FILE_TYPE_IMAGE', 'FILE_TYPE', '이미지',   2),
('FILE_TYPE_DOC',   'FILE_TYPE', '문서',     3),
('FILE_TYPE_VIDEO', 'FILE_TYPE', '동영상',   4),
('FILE_TYPE_ZIP',   'FILE_TYPE', '압축파일', 5);