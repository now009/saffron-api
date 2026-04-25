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