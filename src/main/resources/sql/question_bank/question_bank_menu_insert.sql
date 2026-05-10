시험지 관리	/admin/qbank/papers	시험지 목록 + 등록/수정/삭제
시험 유형/대상 관리	/admin/qbank/types	시험종류(exam_type) + 시험대상(exam_subject) 2패널
시험 채점 관리	/admin/qbank/sessions	응시 결과 조회 + 주관식 수동채점
참고사항:

/admin/qbank/papers/:paperId/questions (문항 편집) — 시험지 관리에서 "문항 편집" 버튼으로 이동하는 서브페이지이므로 별도 메뉴 등록 불필요
/exam, /exam/room/:sessionId — 응시자용 독립 화면으로 포털 메뉴 외부에서 직접 URL로 접근하므로 메뉴 등록 불필요
메뉴 트리 구성 제안 (menu 등록 시):


QBANK (최상위, path: /admin/qbank)
├── 시험지 관리     → /admin/qbank/papers
├── 시험유형/대상   → /admin/qbank/types
└── 채점 관리       → /admin/qbank/sessions
    시험 응시	-> /exam	일반 직원
프로그램 관리(/portal/programs/list)에서 위 3개를 등록한 뒤, 메뉴 관리(/portal/menus/list)에서 QBANK 하위 메뉴로 연결하면 됩니다.


INSERT INTO program_info (programId, programName, programUrl, useYn, sortOrder) VALUES
('PGM030', 'QBANK 대시보드	',  '/admin/qbank',    'Y', 1),
('PGM031', '시험지 관리',      '/admin/qbank/papers',            'Y', 2),
('PGM032', '시험유형/대상',    '/admin/qbank/types', 'Y', 3),
('PGM033', '채점 관리',       '/admin/qbank/sessions',    'Y', 4),
('PGM034', '시험 응시',       '/exam',    'Y', 5);  -- 일반 직원

select * from menu_info;

delete from menu_info where site = 'qbank';

INSERT INTO menu_info (menuId, parentMenuId, menuName, menuLevel, menuDirYn, programId, sortOrder, useYn, site) values
    ('MENU050', NULL, 'QUESTION_BANK', 1, 'Y', NULL, 1, 'Y', 'qbank');

-- 하위 메뉴 (menu_dir_yn='N', program_id 연결)
INSERT INTO menu_info (menuId, parentMenuId, menuName, menuLevel, menuDirYn, programId, sortOrder, useYn, site) values
('MENU051', 'MENU050', '시험지 관리',        2, 'N', 'PGM031', 1, 'Y', 'qbank'),
('MENU052', 'MENU050', '시험유형/대상',  2, 'N', 'PGM032', 2, 'Y', 'qbank'),
('MENU053', 'MENU050', '채점 관리',      2, 'N', 'PGM033', 3, 'Y', 'qbank'),
('MENU054', 'MENU050', '시험 응시',      2, 'N', 'PGM034', 4, 'Y', 'qbank');

