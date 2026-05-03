-- ============================================================
-- EAI 메뉴 등록 SQL
-- 실행 순서: ① program_info → ② menu_info → ③ 권한 할당
-- ============================================================

select * from menu_info;
select * from program_info;

GET /eai/datasources?dbType=&isActive=
GET /eai/db-adapter-configs?interfaceId=&datasourceId=
GET /eai/rest-configs?interfaceId=
GET /eai/soap-configs?interfaceId=

delete from program_info where programUrl like '%/eai%';
-- ① program_info: EAI 페이지 프로그램 등록
INSERT INTO program_info (programId, programName, programUrl, useYn, sortOrder) VALUES
('PGM020', 'EAI 대시보드',        '/eai',            'Y', 1),
('PGM021', 'EAI 인터페이스 관리',  '/eai/interfaces', 'Y', 2),
('PGM022', 'EAI 메시지 이력',      '/eai/history',    'Y', 3),
('PGM023', 'EAI 모니터링',         '/eai/monitoring', 'Y', 4),
('PGM024', 'EAI 스케줄 관리',      '/eai/schedules',  'Y', 5),
('PGM025', 'DataSource',      '/eai/datasources',  'Y', 6),
('PGM026', 'DB 어댑터',      '/eai/db-adapter-configs',  'Y', 7),
('PGM027', 'REST 어댑터',      '/eai/rest-configs',  'Y', 8),
('PGM028', 'SOAP 어댑터',      '/eai/soap-configs',  'Y', 9);


delete from menu_info where site = 'eai';

-- ② menu_info: EAI 메뉴 등록
-- 최상위 디렉토리 (menu_dir_yn='Y', program_id 없음)
INSERT INTO menu_info (menuId, parentMenuId, menuName, menuLevel, menuDirYn, programId, sortOrder, useYn, site) values
    ('MENU033', NULL, 'EAI', 1, 'Y', NULL, 1, 'Y', 'eai');

-- 하위 메뉴 (menu_dir_yn='N', program_id 연결)
INSERT INTO menu_info (menuId, parentMenuId, menuName, menuLevel, menuDirYn, programId, sortOrder, useYn, site) values
('MENU034', 'MENU033', '대시보드',        2, 'N', 'PGM020', 1, 'Y', 'eai'),
('MENU035', 'MENU033', '인터페이스 관리',  2, 'N', 'PGM021', 2, 'Y', 'eai'),
('MENU039', 'MENU033', '어댑터 관리',      2, 'Y', NULL, 3, 'Y', 'eai'),
('MENU040', 'MENU039', 'DataSource 관리',      3, 'N', 'PGM025', 1, 'Y', 'eai'),
('MENU041', 'MENU039', 'DB 어댑터',      3, 'N', 'PGM026', 2, 'Y', 'eai'),
('MENU042', 'MENU039', 'REST 어댑터',      3, 'N', 'PGM027', 3, 'Y', 'eai'),
('MENU043', 'MENU039', 'SOAP 어댑터',      3, 'N', 'PGM028', 4, 'Y', 'eai'),
('MENU036', 'MENU033', '메시지 이력',      2, 'N', 'PGM022', 4, 'Y', 'eai'),
('MENU037', 'MENU033', '모니터링',         2, 'N', 'PGM023', 5, 'Y', 'eai'),
('MENU038', 'MENU033', '스케줄 관리',      2, 'N', 'PGM024', 6, 'Y', 'eai');

-- ③ 권한 할당: 특정 roleCode에 위 메뉴를 모두 허용
--   아래의 role_code를 실제 관리자 권한 코드로 변경 후 실행
--   (관리 UI의 권한설정 화면에서 처리해도 됩니다)
--
-- INSERT INTO role_menu_map (role_code, menu_id, use_yn) VALUES
-- ('ROLE_ADMIN', 'EAIM001', 'Y'),
-- ('ROLE_ADMIN', 'EAIM010', 'Y'),
-- ('ROLE_ADMIN', 'EAIM011', 'Y'),
-- ('ROLE_ADMIN', 'EAIM012', 'Y'),
-- ('ROLE_ADMIN', 'EAIM013', 'Y'),
-- ('ROLE_ADMIN', 'EAIM014', 'Y');
--
-- role_menu_map 테이블명이 다를 경우 아래 쿼리로 확인:
--   SHOW TABLES LIKE '%role%menu%';
--   SHOW TABLES LIKE '%menu%role%';
