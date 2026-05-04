-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS saffron
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 사용자 생성
CREATE USER IF NOT EXISTS 'now009'@'localhost' IDENTIFIED BY '2799';

-- 권한 부여m
GRANT ALL PRIVILEGES ON saffron.* TO 'now009'@'localhost';

-- 권한 적용
FLUSH PRIVILEGES;

-- 확인
SHOW DATABASES;
SELECT User, Host FROM mysql.user WHERE User = 'now009';

----------------------------------------------------------------------------------------------
-- cmd
>mysql -u root -p


-- 데이터베이스 생성
DROP DATABASE saffron_source;
CREATE DATABASE IF NOT EXISTS saffron_source;
CREATE DATABASE IF NOT EXISTS saffron_target;

-- 사용자 생성
CREATE USER IF NOT EXISTS 'now009'@'localhost' IDENTIFIED BY '2799';
CREATE USER IF NOT EXISTS 'now008'@'localhost' IDENTIFIED BY '2799';
CREATE USER IF NOT EXISTS 'now007'@'localhost' IDENTIFIED BY '2799';

-- 권한 부여m
GRANT ALL PRIVILEGES ON saffron.* TO 'now007'@'localhost';
GRANT ALL PRIVILEGES ON saffron_source.* TO 'now007'@'localhost';
GRANT ALL PRIVILEGES ON saffron_target.* TO 'now007'@'localhost';

GRANT ALL PRIVILEGES ON saffron.* TO 'now008'@'localhost';
GRANT ALL PRIVILEGES ON saffron_source.* TO 'now008'@'localhost';
GRANT ALL PRIVILEGES ON saffron_target.* TO 'now008'@'localhost';

GRANT ALL PRIVILEGES ON saffron.* TO 'now009'@'localhost';
GRANT ALL PRIVILEGES ON saffron_source.* TO 'now009'@'localhost';
GRANT ALL PRIVILEGES ON saffron_target.* TO 'now009'@'localhost';

-- 권한 적용
FLUSH PRIVILEGES;

-- 확인
SHOW DATABASES;
SELECT User, Host FROM mysql.user WHERE User = 'now009';


CREATE TABLE `adapter_source_board_master` (
    `boardId` varchar(20) NOT NULL COMMENT '게시판 ID (BRD001 형식)',
    `boardName` varchar(100) NOT NULL COMMENT '게시판 명',
    `boardType` varchar(20) NOT NULL DEFAULT 'GENERAL' COMMENT '게시판 유형 (GENERAL/QNA/FAQ/NOTICE)',
    `description` varchar(500) DEFAULT NULL COMMENT '게시판 설명',
    `allowComment` char(1) NOT NULL DEFAULT 'Y' COMMENT '댓글 허용',
    `allowAttach` char(1) NOT NULL DEFAULT 'Y' COMMENT '첨부파일 허용',
    `allowAnonymous` char(1) NOT NULL DEFAULT 'N' COMMENT '익명 허용',
    `allowSearch` char(1) NOT NULL DEFAULT 'Y' COMMENT '검색 허용',
    `readRole` varchar(20) NOT NULL DEFAULT 'ALL' COMMENT '읽기 권한 (ALL/USER/ADMIN)',
    `writeRole` varchar(20) NOT NULL DEFAULT 'USER' COMMENT '쓰기 권한 (ALL/USER/ADMIN)',
    `listCount` int(11) NOT NULL DEFAULT 10 COMMENT '목록 표시 건수',
    `newDays` int(11) NOT NULL DEFAULT 3 COMMENT 'NEW 표시 기간 (일)',
    `sortOrder` int(11) NOT NULL DEFAULT 0 COMMENT '정렬순서',
    `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
    `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
    `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`boardId`)
) COMMENT='ADAPTER TEST 게시판 관리';
