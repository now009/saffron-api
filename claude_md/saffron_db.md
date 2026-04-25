================================================
-- Claude Saffron db 작업용
================================================

mariadb root 계정접속후 새로운 DB 와 사용자를 만들려고 해 script를 만들어줘
 - DB명 : saffron
 - User/Pw : now009/2799

-- 데이터베이스 생성
   CREATE DATABASE IF NOT EXISTS saffron
   CHARACTER SET utf8mb4
   COLLATE utf8mb4_unicode_ci;

-- 사용자 생성
CREATE USER IF NOT EXISTS 'now009'@'localhost' IDENTIFIED BY '2799';

-- 권한 부여
GRANT ALL PRIVILEGES ON saffron.* TO 'now009'@'localhost';

-- 권한 적용
FLUSH PRIVILEGES;

-- 확인
SHOW DATABASES;
SELECT User, Host FROM mysql.user WHERE User = 'now009';

