-- =====================================================================================
-- Saffron Database Init Script (v3)
--   Source : 실제 DB 조회 (saffron @ 2026-04-30)
--   Tables : code_info, notice_info, dept_info, user_info, role_info, role_mapping,
--            program_info, menu_info, user_role, dept_role, role_menu,
--            board_master, board_post, board_comment, board_file, board_like
-- =====================================================================================

-- =====================================================================================
-- DROP
-- =====================================================================================
DROP TABLE IF EXISTS `board_like`;
DROP TABLE IF EXISTS `board_file`;
DROP TABLE IF EXISTS `board_comment`;
DROP TABLE IF EXISTS `board_post`;
DROP TABLE IF EXISTS `board_master`;
DROP TABLE IF EXISTS `role_menu`;
DROP TABLE IF EXISTS `dept_role`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `menu_info`;
DROP TABLE IF EXISTS `program_info`;
DROP TABLE IF EXISTS `role_mapping`;
DROP TABLE IF EXISTS `role_info`;
DROP TABLE IF EXISTS `user_info`;
DROP TABLE IF EXISTS `dept_info`;
DROP TABLE IF EXISTS `notice_info`;
DROP TABLE IF EXISTS `code_info`;

-- =====================================================================================
-- CREATE & INSERT
-- =====================================================================================

-- -------------------------------------------------------------------------------------
-- code_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `code_info` (
  `code` varchar(50) NOT NULL COMMENT '코드',
  `parentCode` varchar(50) DEFAULT NULL COMMENT '상위코드',
  `codeName` varchar(100) NOT NULL COMMENT '코드명',
  `sortOrder` int(11) DEFAULT 0 COMMENT '정렬순서',
  `useYn` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='코드정보';
INSERT INTO `code_info` (`code`, `parentCode`, `codeName`, `sortOrder`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('BOARD_TYPE',NULL,'게시판유형',1,'Y','system','2026-04-27 01:48:14','system','2026-04-27 01:48:14'),
('BOARD_TYPE_FAQ','BOARD_TYPE','FAQ',3,'Y','system','2026-04-27 01:48:14','system','2026-04-27 01:48:14'),
('BOARD_TYPE_FREE','BOARD_TYPE','자유게시판',5,'Y','system','2026-04-27 01:48:14','system','2026-04-27 01:48:14'),
('BOARD_TYPE_NOTICE','BOARD_TYPE','공지사항',2,'Y','system','2026-04-27 01:48:14','system','2026-04-27 01:48:14'),
('BOARD_TYPE_QNA','BOARD_TYPE','Q&A',4,'Y','system','2026-04-27 01:48:14','system','2026-04-27 01:48:14'),
('DEPT_LEVEL',NULL,'부서레벨',1,'Y','system','2026-04-27 01:48:07','system','2026-04-27 01:48:07'),
('DEPT_LEVEL_1','DEPT_LEVEL','본부',2,'Y','system','2026-04-27 01:48:07','system','2026-04-27 01:48:07'),
('DEPT_LEVEL_2','DEPT_LEVEL','부',3,'Y','system','2026-04-27 01:48:07','system','2026-04-27 01:48:07'),
('DEPT_LEVEL_3','DEPT_LEVEL','팀',4,'Y','system','2026-04-27 01:48:07','system','2026-04-27 01:48:07'),
('FILE_TYPE',NULL,'파일유형',1,'Y','system','2026-04-27 01:48:17','system','2026-04-27 01:48:17'),
('FILE_TYPE_DOC','FILE_TYPE','문서',3,'Y','system','2026-04-27 01:48:17','system','2026-04-27 01:48:17'),
('FILE_TYPE_IMAGE','FILE_TYPE','이미지',2,'Y','system','2026-04-27 01:48:17','system','2026-04-27 01:48:17'),
('FILE_TYPE_VIDEO','FILE_TYPE','동영상',4,'Y','system','2026-04-27 01:48:17','system','2026-04-27 01:48:17'),
('FILE_TYPE_ZIP','FILE_TYPE','압축파일',5,'Y','system','2026-04-27 01:48:17','system','2026-04-27 01:48:17'),
('GENDER',NULL,'성별',1,'Y','system','2026-04-27 01:48:01','system','2026-04-27 01:48:01'),
('GENDER_F','GENDER','여성',3,'Y','system','2026-04-27 01:48:01','system','2026-04-27 01:48:01'),
('GENDER_M','GENDER','남성',2,'Y','system','2026-04-27 01:48:01','system','2026-04-27 01:48:01'),
('JOB_GRADE',NULL,'직급',1,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('JOB_GRADE_1','JOB_GRADE','1급',2,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('JOB_GRADE_2','JOB_GRADE','2급',3,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('JOB_GRADE_3','JOB_GRADE','3급',4,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('JOB_GRADE_4','JOB_GRADE','4급',5,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('JOB_GRADE_5','JOB_GRADE','5급',6,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('JOB_GRADE_EX','JOB_GRADE','임원',7,'Y','system','2026-04-27 01:48:03','system','2026-04-27 01:48:03'),
('LOGIN_STATUS',NULL,'로그인상태',1,'Y','system','2026-04-27 01:48:12','system','2026-04-27 01:48:12'),
('LOGIN_STATUS_EXPIRED','LOGIN_STATUS','비밀번호 만료',5,'Y','system','2026-04-27 01:48:12','system','2026-04-27 01:48:12'),
('LOGIN_STATUS_FAIL','LOGIN_STATUS','로그인 실패',3,'Y','system','2026-04-27 01:48:12','system','2026-04-27 01:48:12'),
('LOGIN_STATUS_LOCKED','LOGIN_STATUS','계정 잠금',4,'Y','system','2026-04-27 01:48:12','system','2026-04-27 01:48:12'),
('LOGIN_STATUS_LOGOUT','LOGIN_STATUS','로그아웃',6,'Y','system','2026-04-27 01:48:12','system','2026-04-27 01:48:12'),
('LOGIN_STATUS_SUCCESS','LOGIN_STATUS','로그인 성공',2,'Y','system','2026-04-27 01:48:12','system','2026-04-27 01:48:12'),
('MENU_LEVEL',NULL,'메뉴레벨',1,'Y','system','2026-04-27 01:48:09','system','2026-04-27 01:48:09'),
('MENU_LEVEL_1','MENU_LEVEL','대메뉴',2,'Y','system','2026-04-27 01:48:09','system','2026-04-27 01:48:09'),
('MENU_LEVEL_2','MENU_LEVEL','중메뉴',3,'Y','system','2026-04-27 01:48:09','system','2026-04-27 01:48:09'),
('MENU_LEVEL_3','MENU_LEVEL','소메뉴',4,'Y','system','2026-04-27 01:48:09','system','2026-04-27 01:48:09'),
('POSITION',NULL,'직위',1,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_01','POSITION','사원',2,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_02','POSITION','주임',3,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_03','POSITION','대리',4,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_04','POSITION','과장',5,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_05','POSITION','차장',6,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_06','POSITION','부장',7,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_07','POSITION','팀장',8,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_08','POSITION','본부장',9,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_09','POSITION','이사',10,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('POSITION_10','POSITION','대표이사',11,'Y','system','2026-04-27 01:48:05','system','2026-04-27 01:48:05'),
('USE_YN',NULL,'사용여부',1,'Y','system','2026-04-27 01:47:58','system','2026-04-27 01:47:58'),
('USE_YN_N','USE_YN','미사용',3,'Y','system','2026-04-27 01:47:58','system','2026-04-27 01:47:58'),
('USE_YN_Y','USE_YN','사용',2,'Y','system','2026-04-27 01:47:58','system','2026-04-27 01:47:58');

-- -------------------------------------------------------------------------------------
-- notice_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `notice_info` (
  `noticeId` varchar(50) NOT NULL COMMENT '공지사항 ID',
  `title` varchar(200) NOT NULL COMMENT '제목',
  `content` longtext NOT NULL COMMENT '내용',
  `noticeType` varchar(20) NOT NULL DEFAULT 'NORMAL' COMMENT '공지유형 (NORMAL/IMPORTANT/POPUP)',
  `isPinned` char(1) NOT NULL DEFAULT 'N' COMMENT '상단고정 여부',
  `isPopup` char(1) NOT NULL DEFAULT 'N' COMMENT '팝업 여부',
  `popupStartDt` datetime DEFAULT NULL COMMENT '팝업 시작일시',
  `popupEndDt` datetime DEFAULT NULL COMMENT '팝업 종료일시',
  `targetDeptId` varchar(50) DEFAULT NULL COMMENT '대상부서 (NULL이면 전체)',
  `viewCount` int(11) NOT NULL DEFAULT 0 COMMENT '조회수',
  `attachYn` char(1) NOT NULL DEFAULT 'N' COMMENT '첨부파일 여부',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='공지사항';
INSERT INTO `notice_info` (`noticeId`, `title`, `content`, `noticeType`, `isPinned`, `isPopup`, `popupStartDt`, `popupEndDt`, `targetDeptId`, `viewCount`, `attachYn`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('NT0000001','2026년 상반기 전사 시스템 점검 안내','2026년 5월 1일(금) 오후 11시부터 익일 오전 2시까지 전사 시스템 정기 점검이 진행됩니다.\n점검 시간 동안 모든 서비스 이용이 불가하오니 업무에 참고하시기 바랍니다.','IMPORTANT','Y','Y','2026-04-28 00:00:00','2026-05-01 23:00:00',NULL,249,'N','Y','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000002','개인정보 보호 의무교육 실시 안내','전 직원 대상 개인정보 보호 의무교육을 아래와 같이 실시합니다.\n- 일시: 2026년 5월 10일 14:00\n- 장소: 대강당\n- 대상: 전 직원 필수 참석','IMPORTANT','Y','N',NULL,NULL,NULL,184,'Y','Y','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000003','근태 시스템 개편 안내 (5월 적용)','5월부터 근태 시스템이 새롭게 개편됩니다.\n주요 변경사항:\n1. 모바일 출퇴근 기능 추가\n2. 재택근무 신청 프로세스 간소화\n3. 초과근무 자동 집계 기능 개선','POPUP','N','Y','2026-04-25 00:00:00','2026-05-05 23:59:59',NULL,313,'Y','Y','hr_admin','2026-04-28 07:30:20','hr_admin','2026-04-28 07:30:20'),
('NT0000004','사내 카페테리아 메뉴 개편 및 운영시간 변경','5월 1일부터 카페테리아 운영시간 및 메뉴가 변경됩니다.\n- 운영시간: 07:30 ~ 20:00 (기존 08:00 ~ 19:00)\n- 조식 메뉴 추가 운영\n- 주말 운영 중단','POPUP','N','Y','2026-04-28 00:00:00','2026-05-03 23:59:59',NULL,98,'N','Y','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000005','2026년 5월 사내 휴일 안내','2026년 5월 임시 공휴일 지정에 따라 아래 날짜를 휴무일로 운영합니다.\n- 5월 1일(금): 근로자의 날\n- 5월 5일(화): 어린이날\n- 5월 6일(수): 임시공휴일','NORMAL','N','N',NULL,NULL,NULL,423,'N','Y','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000006','사내 복지포인트 지급 안내 (2026년 상반기)','2026년 상반기 복지포인트가 아래와 같이 지급됩니다.\n- 지급일: 2026년 5월 2일\n- 지급액: 직급별 차등 지급\n- 유효기간: 2026년 12월 31일까지\n복지몰 접속 후 사용 가능합니다.','NORMAL','N','N',NULL,NULL,NULL,367,'N','Y','hr_admin','2026-04-28 07:30:20','hr_admin','2026-04-28 07:30:20'),
('NT0000007','사내 도서관 신규 도서 입고 안내','4월 신규 도서 50권이 입고되었습니다.\n주요 도서:\n- AI 시대의 업무 혁신\n- 클린 아키텍처\n- 데이터 중심 애플리케이션 설계\n사내 도서관 시스템에서 예약 후 이용 가능합니다.','NORMAL','N','N',NULL,NULL,NULL,89,'N','Y','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000008','주차장 공사로 인한 주차 공간 제한 안내','4월 30일(목) ~ 5월 7일(수) 기간 중 본관 지하주차장 B2 구역 공사로 인해 해당 구역 이용이 불가합니다.\n대체 주차장: 별관 지상주차장 이용 바랍니다.','NORMAL','N','N',NULL,NULL,NULL,156,'N','Y','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000009','2026년 상반기 성과평가 일정 안내','2026년 상반기 성과평가 일정을 안내드립니다.\n- 자기평가 기간: 5월 12일 ~ 5월 16일\n- 팀장 평가: 5월 19일 ~ 5월 23일\n- 결과 확인: 6월 2일\nHR 시스템에 접속하여 기간 내 평가를 완료해 주시기 바랍니다.','NORMAL','N','N',NULL,NULL,NULL,278,'N','Y','hr_admin','2026-04-28 07:30:20','hr_admin','2026-04-28 07:30:20'),
('NT0000010','IT 보안 패치 적용 안내 (4월 29일)','보안 강화를 위해 아래 일정으로 보안 패치를 적용합니다.\n- 일시: 4월 29일(수) 오후 6시 ~ 8시\n- 대상: 전 임직원 업무용 PC\n- 방법: 자동 업데이트 (재부팅 필요)\n업무 종료 후 PC를 켜두신 채로 퇴근해 주시기 바랍니다.','NORMAL','N','N',NULL,NULL,NULL,203,'N','Y','it_admin','2026-04-28 07:30:20','it_admin','2026-04-28 07:30:20'),
('NT0000011','개발팀 코드 리뷰 프로세스 변경 안내','5월부터 개발팀 코드 리뷰 프로세스가 아래와 같이 변경됩니다.\n- PR 최소 승인자: 1명 → 2명\n- 리뷰 완료 기한: PR 등록 후 2 영업일 이내\n- 자동화 테스트 통과 필수\n세부 가이드는 Confluence를 참조하세요.','NORMAL','N','N',NULL,NULL,'D001',47,'Y','Y','dev_lead','2026-04-28 07:30:20','dev_lead','2026-04-28 07:30:20'),
('NT0000012','인사팀 급여 명세서 발송 안내 (4월분)','4월 급여 명세서가 발송되었습니다.\n- 발송일: 2026년 4월 25일\n- 확인 방법: 사내 HR 포털 → 급여 명세서 메뉴\n문의사항은 인사팀(내선 1234)으로 연락 바랍니다.','NORMAL','N','N',NULL,NULL,'D002',67,'N','Y','hr_admin','2026-04-28 07:30:20','hr_admin','2026-04-28 07:30:20'),
('NT0000013','2026년 1분기 전사 워크샵 안내','2026년 1분기 전사 워크샵을 아래와 같이 진행합니다.\n- 일시: 2026년 3월 14일 ~ 15일\n- 장소: 경기도 가평 리조트\n- 대상: 전 직원\n버스 탑승 명단은 첨부파일을 확인하세요.','NORMAL','N','N',NULL,NULL,NULL,534,'Y','N','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000014','사내 해커톤 참가자 모집 (마감)','2026 사내 해커톤 참가자를 모집합니다.\n- 주제: AI를 활용한 업무 효율화 아이디어\n- 일시: 2026년 4월 18일 ~ 19일\n- 팀구성: 3~5인\n- 신청마감: 4월 10일\n많은 참여 부탁드립니다.','NORMAL','N','N',NULL,NULL,NULL,612,'Y','N','admin','2026-04-28 07:30:20','admin','2026-04-28 07:30:20'),
('NT0000015','2025년 연말 결산 보고서 제출 안내 (완료)','2025년 연말 결산 보고서 제출 기한을 안내드립니다.\n- 제출기한: 2026년 1월 10일\n- 제출방법: 재경팀 이메일 또는 ERP 시스템\n- 문의: 재경팀 내선 5678','NORMAL','N','N',NULL,NULL,NULL,289,'N','N','finance_admin','2026-04-28 07:30:20','finance_admin','2026-04-28 07:30:20');

-- -------------------------------------------------------------------------------------
-- dept_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `dept_info` (
  `deptId` varchar(20) NOT NULL COMMENT '부서ID',
  `parentDeptId` varchar(20) DEFAULT NULL COMMENT '상위부서ID',
  `deptCode` varchar(20) NOT NULL COMMENT '부서코드',
  `deptName` varchar(100) NOT NULL COMMENT '부서명',
  `deptLevel` tinyint(4) DEFAULT 1 COMMENT '부서레벨(1:본부,2:부,3:팀)',
  `sortOrder` int(11) DEFAULT 0 COMMENT '정렬순서',
  `useYn` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`deptId`),
  UNIQUE KEY `ukDeptCode` (`deptCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='부서정보';
INSERT INTO `dept_info` (`deptId`, `parentDeptId`, `deptCode`, `deptName`, `deptLevel`, `sortOrder`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('DEPT001',NULL,'HQ','본사',1,1,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT002','DEPT001','MGMT','경영지원본부',2,1,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT003','DEPT001','DEV','개발본부',2,2,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT004','DEPT001','SALES','영업본부',2,3,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT005','DEPT002','HR','인사팀',3,1,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT006','DEPT002','FINANCE','재무팀',3,2,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT007','DEPT003','BACKEND','백엔드개발팀',3,1,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT008','DEPT003','FRONTEND','프론트엔드개발팀',3,2,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT009','DEPT003','INFRA','인프라팀',3,3,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36'),
('DEPT010','DEPT004','SALES1','영업1팀',3,1,'Y','system','2026-04-27 01:47:36','system','2026-04-27 01:47:36');

-- -------------------------------------------------------------------------------------
-- user_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `user_info` (
  `userId` varchar(50) NOT NULL COMMENT '사용자ID',
  `deptId` varchar(20) DEFAULT NULL COMMENT '부서ID',
  `userName` varchar(50) NOT NULL COMMENT '사용자명',
  `password` varchar(255) NOT NULL COMMENT '비밀번호',
  `email` varchar(100) DEFAULT NULL COMMENT '이메일',
  `phone` varchar(50) DEFAULT NULL COMMENT '연락처',
  `position` varchar(50) DEFAULT NULL COMMENT '직위',
  `jobGrade` varchar(20) DEFAULT NULL COMMENT '직급',
  `useYn` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `managerYn` char(1) DEFAULT NULL COMMENT '시스템관리자여부',
  `lastLoginAt` timestamp NULL DEFAULT NULL COMMENT '최종로그인일시',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자정보';
INSERT INTO `user_info` (`userId`, `deptId`, `userName`, `password`, `email`, `phone`, `position`, `jobGrade`, `useYn`, `managerYn`, `lastLoginAt`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('now009','DEPT001','홍길동','1234!','hong@saffron.com','010-1234-5678','대표이사','임원','Y','Y',NULL,'system','2026-04-27 01:47:38','system','2026-04-28 01:54:34'),
('user001','DEPT002','김경영','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','kim@saffron.com','010-2345-6789','본부장','1급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user002','DEPT003','이개발','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','lee@saffron.com','010-3456-7890','본부장','1급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user003','DEPT004','박영업','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','park@saffron.com','010-4567-8901','본부장','1급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user004','DEPT005','최인사','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','choi@saffron.com','010-5678-9012','팀장','2급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user005','DEPT006','정재무','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','jung@saffron.com','010-6789-0123','팀장','2급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user006','DEPT007','강백엔','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','kang@saffron.com','010-7890-1234','과장','3급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user007','DEPT008','윤프론','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','yoon@saffron.com','010-8901-2345','대리','4급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user008','DEPT009','장인프','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','jang@saffron.com','010-9012-3456','대리','4급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38'),
('user009','DEPT010','임영업','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','lim@saffron.com','010-0123-4567','사원','5급','Y','N',NULL,'system','2026-04-27 01:47:38','system','2026-04-27 01:47:38');

-- -------------------------------------------------------------------------------------
-- role_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `role_info` (
  `roleCode` varchar(50) NOT NULL COMMENT '권한코드',
  `roleName` varchar(100) NOT NULL COMMENT '권한명',
  `description` varchar(255) DEFAULT NULL COMMENT '권한설명',
  `useYn` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`roleCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='권한정보';
INSERT INTO `role_info` (`roleCode`, `roleName`, `description`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('ROLE_ADMIN','시스템관리자','모든 메뉴 및 기능 접근 가능','Y','system','2026-04-27 01:47:45','system','2026-04-27 01:47:45'),
('ROLE_GUEST','게스트','제한된 조회 기능만 접근 가능','Y','system','2026-04-27 01:47:45','system','2026-04-27 01:47:45'),
('ROLE_MANAGER','팀장','부서 관련 메뉴 및 승인 기능 접근','Y','system','2026-04-27 01:47:45','system','2026-04-27 01:47:45'),
('ROLE_USER','일반사용자','기본 메뉴 조회 및 본인 데이터 수정','Y','system','2026-04-27 01:47:45','system','2026-04-27 01:47:45');

-- -------------------------------------------------------------------------------------
-- role_mapping
-- -------------------------------------------------------------------------------------
CREATE TABLE `role_mapping` (
  `mappingId` varchar(50) NOT NULL COMMENT '매핑ID',
  `roleId` varchar(50) NOT NULL COMMENT '권한ID',
  `userId` varchar(50) DEFAULT NULL COMMENT '사용자ID',
  `programId` varchar(50) NOT NULL COMMENT '프로그램ID',
  `canRead` char(1) DEFAULT 'N' COMMENT '조회권한',
  `canWrite` char(1) DEFAULT 'N' COMMENT '등록권한',
  `canUpdate` char(1) DEFAULT 'N' COMMENT '수정권한',
  `canDelete` char(1) DEFAULT 'N' COMMENT '삭제권한',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`mappingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='권한매핑';
INSERT INTO `role_mapping` (`mappingId`, `roleId`, `userId`, `programId`, `canRead`, `canWrite`, `canUpdate`, `canDelete`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('MAP001','ROLE_ADMIN',NULL,'PGM001','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP002','ROLE_ADMIN',NULL,'PGM002','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP003','ROLE_ADMIN',NULL,'PGM003','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP004','ROLE_ADMIN',NULL,'PGM004','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP005','ROLE_ADMIN',NULL,'PGM005','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP006','ROLE_ADMIN',NULL,'PGM006','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP007','ROLE_ADMIN',NULL,'PGM007','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP008','ROLE_ADMIN',NULL,'PGM008','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP009','ROLE_ADMIN',NULL,'PGM009','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP010','ROLE_ADMIN',NULL,'PGM010','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP011','ROLE_MANAGER',NULL,'PGM007','Y','Y','Y','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP012','ROLE_MANAGER',NULL,'PGM008','Y','Y','Y','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP013','ROLE_MANAGER',NULL,'PGM009','Y','Y','Y','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP014','ROLE_MANAGER',NULL,'PGM010','Y','Y','Y','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP015','ROLE_USER',NULL,'PGM007','Y','N','N','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP016','ROLE_USER',NULL,'PGM008','Y','Y','N','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP017','ROLE_USER',NULL,'PGM010','Y','Y','N','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP018','ROLE_GUEST',NULL,'PGM007','Y','N','N','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP019','ROLE_GUEST',NULL,'PGM010','Y','N','N','N','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49'),
('MAP020','ROLE_ADMIN','now009','PGM001','Y','Y','Y','Y','system','2026-04-27 01:47:49','system','2026-04-27 01:47:49');

-- -------------------------------------------------------------------------------------
-- program_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `program_info` (
  `programId` varchar(50) NOT NULL COMMENT '프로그램ID',
  `programName` varchar(100) NOT NULL COMMENT '프로그램명',
  `programUrl` varchar(255) DEFAULT NULL COMMENT '프로그램URL',
  `useYn` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `sortOrder` int(11) DEFAULT 0 COMMENT '정렬순서',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`programId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='프로그램정보';
INSERT INTO `program_info` (`programId`, `programName`, `programUrl`, `useYn`, `sortOrder`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('PGM001','공통코드 목록','/portal/codes/list','Y',1,'system','2026-04-27 01:47:43','system','2026-04-27 01:47:43'),
('PGM003','메뉴 목록','/portal/menus/list','Y',2,'system','2026-04-27 01:47:43','system','2026-04-30 06:06:16'),
('PGM005','권한 목록','/portal/roles/list','Y',5,'system','2026-04-27 01:47:43','system','2026-04-30 06:10:18'),
('PGM007','사용자 목록','/portal/users/list','Y',3,'system','2026-04-27 01:47:43','system','2026-04-30 06:06:21'),
('PGM009','부서 목록','/portal/depts/list','Y',4,'system','2026-04-27 01:47:43','system','2026-04-30 06:10:04'),
('PGM010','업무현황 목록','/work/status/list','Y',11,'system','2026-04-27 01:47:43','system','2026-04-30 06:10:53'),
('PGM011','프로그램 관리','/portal/programs/list','Y',7,'system','2026-04-30 05:29:59','system','2026-04-30 06:10:42'),
('PGM012','권한 설정','/portal/rolesetting/list','Y',6,'system','2026-04-30 05:29:59','system','2026-04-30 06:10:24'),
('PGM013','스케줄 관리','/portal/schedules/list','Y',13,'system','2026-04-30 05:29:59','system','2026-04-30 05:29:59'),
('PGM014','환경설정 관리','/portal/env-settings/list','Y',10,'system','2026-04-30 05:29:59','system','2026-04-30 06:11:01'),
('PGM015','공지사항','/portal/notices/list','Y',8,'system','2026-04-30 06:05:33','system','2026-04-30 06:10:35'),
('PGM016','게시판','/portal/boards/list','Y',9,'system','2026-04-30 06:11:24','system','2026-04-30 06:11:36');

-- -------------------------------------------------------------------------------------
-- menu_info
-- -------------------------------------------------------------------------------------
CREATE TABLE `menu_info` (
  `site` varchar(50) NOT NULL,
  `menuId` varchar(50) NOT NULL COMMENT '메뉴ID',
  `parentMenuId` varchar(50) DEFAULT NULL COMMENT '상위메뉴ID',
  `menuName` varchar(100) NOT NULL COMMENT '메뉴명',
  `menuLevel` tinyint(4) DEFAULT 1 COMMENT '메뉴레벨(1:대,2:중,3:소)',
  `menuDirYn` char(1) DEFAULT 'N' COMMENT '메뉴폴더 여부',
  `menuIcon` varchar(100) DEFAULT NULL COMMENT '메뉴아이콘',
  `programId` varchar(50) DEFAULT NULL COMMENT '프로그램ID',
  `sortOrder` int(11) DEFAULT 0 COMMENT '정렬순서',
  `useYn` char(1) DEFAULT 'Y' COMMENT '사용여부',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='메뉴정보';
INSERT INTO `menu_info` (`site`, `menuId`, `parentMenuId`, `menuName`, `menuLevel`, `menuDirYn`, `menuIcon`, `programId`, `sortOrder`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('portal','MENU001',NULL,'시스템관리',1,'Y','icon-setting',NULL,1,'Y','system','2026-04-27 01:47:40','system','2026-04-27 01:47:40'),
('portal','MENU003',NULL,'업무관리',1,'Y','icon-work',NULL,3,'Y','system','2026-04-27 01:47:40','system','2026-04-27 01:47:40'),
('portal','MENU004',NULL,'보고서',1,'Y','icon-report',NULL,4,'Y','system','2026-04-27 01:47:40','system','2026-04-30 05:37:58'),
('portal','MENU010','MENU001','공통코드관리',2,'N','icon-code','PGM001',1,'Y','system','2026-04-27 01:47:40','system','2026-04-27 01:47:40'),
('portal','MENU011','MENU001','메뉴관리',2,'N','icon-menu','PGM003',1,'Y','system','2026-04-27 01:47:40','system','2026-04-29 01:02:03'),
('portal','MENU012','MENU001','권한관리',2,'N','icon-lock','PGM005',4,'Y','system','2026-04-27 01:47:40','system','2026-04-29 00:58:45'),
('portal','MENU013','MENU001','프로그램관리',2,'N','icon-setting','PGM011',3,'Y','system','2026-04-28 06:34:08','system','2026-04-30 05:58:28'),
('portal','MENU014','MENU001','권한설정',2,'N','icon-setting','PGM012',4,'Y','system','2026-04-28 06:34:08','system','2026-04-29 01:02:17'),
('portal','MENU015','MENU001','스케줄',2,'N','icon-setting','PGM013',6,'Y','system','2026-04-28 06:34:08','system','2026-04-30 06:02:41'),
('portal','MENU016','MENU001','환경설정',2,'Y','icon-setting',NULL,1,'N','system','2026-04-28 06:34:08','system','2026-04-28 06:42:32'),
('portal','MENU020','MENU001','사용자목록',2,'N','icon-list','PGM007',4,'Y','system','2026-04-27 01:47:40','system','2026-04-30 06:02:05'),
('portal','MENU021','MENU001','부서관리',2,'N','icon-dept','PGM009',5,'Y','system','2026-04-27 01:47:40','system','2026-04-30 06:02:47'),
('portal','MENU031',NULL,'게시판',1,'N',NULL,'PGM016',1,'Y','system','2026-04-30 06:16:38','system','2026-04-30 06:16:38'),
('portal','MENU032',NULL,'공지사항',1,'N',NULL,'PGM015',1,'Y','system','2026-04-30 06:16:57','system','2026-04-30 06:16:57');

-- -------------------------------------------------------------------------------------
-- user_role
-- -------------------------------------------------------------------------------------
CREATE TABLE `user_role` (
  `userId` varchar(50) NOT NULL COMMENT '사용자ID',
  `roleCode` varchar(50) NOT NULL COMMENT '권한코드',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  PRIMARY KEY (`userId`,`roleCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자권한매핑';
INSERT INTO `user_role` (`userId`, `roleCode`, `createdUser`, `createdDate`) VALUES
('now009','ROLE_ADMIN','system','2026-04-28 01:41:27'),
('user001','ROLE_GUEST','system','2026-04-28 01:41:58');

-- -------------------------------------------------------------------------------------
-- dept_role
-- -------------------------------------------------------------------------------------
CREATE TABLE `dept_role` (
  `deptId` varchar(20) NOT NULL COMMENT '부서ID',
  `roleCode` varchar(50) NOT NULL COMMENT '권한코드',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  PRIMARY KEY (`deptId`,`roleCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='부서권한매핑';

-- -------------------------------------------------------------------------------------
-- role_menu
-- -------------------------------------------------------------------------------------
CREATE TABLE `role_menu` (
  `roleCode` varchar(50) NOT NULL COMMENT '권한코드',
  `menuId` varchar(50) NOT NULL COMMENT '메뉴ID',
  `canRead` char(1) DEFAULT 'N' COMMENT '조회권한',
  `canWrite` char(1) DEFAULT 'N' COMMENT '등록권한',
  `canUpdate` char(1) DEFAULT 'N' COMMENT '수정권한',
  `canDelete` char(1) DEFAULT 'N' COMMENT '삭제권한',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '생성자',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '생성일시',
  PRIMARY KEY (`roleCode`,`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='역할메뉴매핑';
INSERT INTO `role_menu` (`roleCode`, `menuId`, `canRead`, `canWrite`, `canUpdate`, `canDelete`, `createdUser`, `createdDate`) VALUES
('ROLE_ADMIN','MENU001','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU003','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU004','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU010','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU011','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU012','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU013','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU014','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU015','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU020','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU021','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU031','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_ADMIN','MENU032','Y','Y','Y','Y','system','2026-04-30 06:25:11'),
('ROLE_GUEST','MENU004','Y','Y','Y','Y','system','2026-04-28 01:27:30');

-- -------------------------------------------------------------------------------------
-- board_master
-- -------------------------------------------------------------------------------------
CREATE TABLE `board_master` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='게시판 관리';
INSERT INTO `board_master` (`boardId`, `boardName`, `boardType`, `description`, `allowComment`, `allowAttach`, `allowAnonymous`, `allowSearch`, `readRole`, `writeRole`, `listCount`, `newDays`, `sortOrder`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('BRD001','공지사항','NOTICE','전사 공지사항 게시판','N','Y','N','Y','ALL','ADMIN',10,3,1,'Y','admin','2026-04-29 01:53:00','admin','2026-04-29 01:53:00'),
('BRD002','자유게시판','GENERAL','자유롭게 소통하는 공간','Y','Y','N','Y','USER','USER',10,3,2,'Y','admin','2026-04-29 01:53:00','admin','2026-04-29 01:53:00'),
('BRD003','Q&A','QNA','질문과 답변 게시판','Y','Y','N','Y','USER','USER',10,5,3,'Y','admin','2026-04-29 01:53:00','admin','2026-04-29 01:53:00'),
('BRD004','FAQ','FAQ','자주 묻는 질문 모음','N','N','N','Y','ALL','ADMIN',20,0,4,'Y','admin','2026-04-29 01:53:00','admin','2026-04-29 01:53:00'),
('BRD005','부서공지','NOTICE','부서별 공지사항','N','Y','N','Y','USER','USER',10,3,5,'Y','admin','2026-04-29 01:53:00','admin','2026-04-29 01:53:00'),
('BRD006','테스트','GENERAL','테스트','Y','Y','N','Y','ALL','ALL',10,3,0,'Y',NULL,'2026-04-29 05:05:42',NULL,'2026-04-29 05:05:42');

-- -------------------------------------------------------------------------------------
-- board_post
-- -------------------------------------------------------------------------------------
CREATE TABLE `board_post` (
  `postId` varchar(50) NOT NULL COMMENT '게시글 ID (POST0000001 형식)',
  `boardId` varchar(20) NOT NULL COMMENT '게시판 ID',
  `parentPostId` varchar(50) DEFAULT NULL COMMENT '부모 게시글 ID (답글인 경우)',
  `depth` int(11) NOT NULL DEFAULT 0 COMMENT '답글 깊이 (0: 원글, 1: 답글)',
  `title` varchar(200) NOT NULL COMMENT '제목',
  `content` longtext NOT NULL COMMENT '내용',
  `category` varchar(50) DEFAULT NULL COMMENT '말머리/카테고리',
  `tags` varchar(500) DEFAULT NULL COMMENT '태그 (쉼표 구분)',
  `isPinned` char(1) NOT NULL DEFAULT 'N' COMMENT '상단고정 여부',
  `isSecret` char(1) NOT NULL DEFAULT 'N' COMMENT '비밀글 여부',
  `isAnonymous` char(1) NOT NULL DEFAULT 'N' COMMENT '익명 여부',
  `viewCount` int(11) NOT NULL DEFAULT 0 COMMENT '조회수',
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '좋아요 수',
  `commentCount` int(11) NOT NULL DEFAULT 0 COMMENT '댓글 수',
  `attachCount` int(11) NOT NULL DEFAULT 0 COMMENT '첨부파일 수',
  `targetDeptId` varchar(50) DEFAULT NULL COMMENT '대상부서 (NULL이면 전체)',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부 (N: 삭제처리)',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '작성자 userId',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자 userId',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`postId`),
  KEY `idx_board_post` (`boardId`,`useYn`,`isPinned`,`createdDate` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='게시글';
INSERT INTO `board_post` (`postId`, `boardId`, `parentPostId`, `depth`, `title`, `content`, `category`, `tags`, `isPinned`, `isSecret`, `isAnonymous`, `viewCount`, `likeCount`, `commentCount`, `attachCount`, `targetDeptId`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('POST0000001','BRD001',NULL,0,'2026년 상반기 시스템 점검 안내','5월 1일(금) 23:00 ~ 익일 02:00 전사 시스템 점검이 진행됩니다.\n점검 중 모든 서비스 이용이 불가합니다.',NULL,'점검,시스템','Y','N','N',412,28,0,0,NULL,'Y','admin','2026-04-29 01:53:05','admin','2026-04-29 01:53:05'),
('POST0000002','BRD001',NULL,0,'개인정보 보호 의무교육 안내','5월 10일(일) 14:00 대강당에서 전 직원 의무교육이 진행됩니다.\n반드시 참석해 주시기 바랍니다.',NULL,'교육,필수','Y','N','N',389,15,0,1,NULL,'Y','admin','2026-04-29 01:53:05','admin','2026-04-29 01:53:05'),
('POST0000003','BRD001',NULL,0,'5월 사내 휴일 안내','5월 1일 근로자의 날, 5월 5일 어린이날, 5월 6일 임시공휴일로 지정되었습니다.',NULL,'휴일','N','N','N',521,42,0,0,NULL,'Y','admin','2026-04-29 01:53:05','admin','2026-04-29 01:53:05'),
('POST0000004','BRD002',NULL,0,'점심 추천 맛집 공유해요!','회사 근처 새로 생긴 파스타 집 너무 맛있어요~ 다들 한번씩 가보세요 :)','일상','맛집,점심,추천','N','N','N',234,31,3,0,NULL,'Y','user01','2026-04-29 01:53:05','user01','2026-04-29 01:53:05'),
('POST0000005','BRD002',NULL,0,'이번 주 금요일 팀빌딩 후기','마케팅팀 볼링 대회 정말 재미있었습니다! 다음에 또 해요~','일상','팀빌딩,볼링','N','N','N',178,24,2,2,NULL,'Y','user02','2026-04-29 01:53:05','user02','2026-04-29 01:53:05'),
('POST0000006','BRD002',NULL,0,'재택근무 꿀팁 공유','집에서 집중하기 힘드신 분들 위한 팁 공유합니다.\n1. 시작 전 루틴 만들기\n2. 포모도로 기법 활용\n3. 점심 후 산책 필수!','업무','재택,꿀팁','N','N','N',445,67,4,0,NULL,'Y','user03','2026-04-29 01:53:05','user03','2026-04-29 01:53:05'),
('POST0000007','BRD002',NULL,0,'사내 동호회 멤버 모집 (독서)','독서 동호회 멤버를 모집합니다.\n월 1회 모임, 부담 없이 참여 가능합니다.\n관심 있으신 분은 댓글 남겨주세요!','동호회','독서,동호회,모집','N','N','N',156,18,3,0,NULL,'Y','user04','2026-04-29 01:53:05','user04','2026-04-29 01:53:05'),
('POST0000008','BRD003',NULL,0,'연차 신청은 어디서 하나요?','HR 시스템에서 연차 신청하는 방법을 모르겠습니다. 알려주시면 감사하겠습니다.','인사','연차,HR','N','N','N',89,5,1,0,NULL,'Y','user05','2026-04-29 01:53:05','user05','2026-04-29 01:53:05'),
('POST0000009','BRD003','POST0000008',1,'Re: 연차 신청은 어디서 하나요?','HR 포털(hr.company.com) 접속 → 근태관리 → 휴가신청 메뉴에서 신청하시면 됩니다!\n팀장 승인 후 처리됩니다.','인사','연차,HR','N','N','N',76,12,0,0,NULL,'Y','hr_admin','2026-04-29 01:53:05','hr_admin','2026-04-29 01:53:05'),
('POST0000010','BRD003',NULL,0,'법인카드 사용 후 정산 기한이 언제인가요?','법인카드 사용 후 정산을 언제까지 해야 하는지 궁금합니다.','재무','법인카드,정산','N','N','N',134,8,1,0,NULL,'Y','user06','2026-04-29 01:53:05','user06','2026-04-29 01:53:05'),
('POST0000011','BRD003','POST0000010',1,'Re: 법인카드 사용 후 정산 기한이 언제인가요?','사용일로부터 7일 이내 ERP 시스템에서 정산하셔야 합니다.\n영수증 첨부 필수이며, 기한 초과 시 재무팀에 문의 바랍니다.','재무','법인카드,정산','N','N','N',98,9,0,0,NULL,'Y','finance_admin','2026-04-29 01:53:05','finance_admin','2026-04-29 01:53:05'),
('POST0000012','BRD004',NULL,0,'급여일은 언제인가요?','매월 25일이 급여일입니다. 25일이 주말 또는 공휴일인 경우 직전 영업일에 지급됩니다.','급여','급여,월급','N','N','N',678,45,0,0,NULL,'Y','hr_admin','2026-04-29 01:53:05','hr_admin','2026-04-29 01:53:05'),
('POST0000013','BRD004',NULL,0,'사내 주차 등록은 어떻게 하나요?','총무팀(내선 2000)으로 연락하시거나 총무팀 이메일(gs@company.com)로 차량번호를 보내주시면 등록해 드립니다.','복지','주차,차량','N','N','N',412,33,0,0,NULL,'Y','admin','2026-04-29 01:53:05','admin','2026-04-29 01:53:05'),
('POST0000014','BRD005',NULL,0,'[개발팀] 5월 스프린트 일정 안내','5월 스프린트 일정을 안내드립니다.\n- Sprint 1: 5/1 ~ 5/14\n- Sprint 2: 5/15 ~ 5/28\n각 스프린트 시작일 오전 10시 킥오프 미팅 예정입니다.',NULL,'스프린트,일정','N','N','N',67,8,1,1,'D001','Y','dev_lead','2026-04-29 01:53:05','dev_lead','2026-04-29 01:53:05'),
('POST0000015','BRD005',NULL,0,'[인사팀] 4월 급여명세서 발송 완료','4월 급여명세서가 발송되었습니다.\nHR 포털 → 급여 명세서 메뉴에서 확인하실 수 있습니다.\n문의: 내선 1234',NULL,'급여,명세서','N','N','N',234,12,0,0,'D002','Y','hr_admin','2026-04-29 01:53:05','hr_admin','2026-04-29 01:53:05'),
('POST0000016','BRD006',NULL,0,'test','backend에서 endpoint가 변경된 부분이 있는데 확인하고 수정할것이 있으면 수정해줘\n┌───────────────────────────────┬──────────────────────────────────────┐\n  │            변경 전            │               변경 후                │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/save            │ /portal/boards/posts/save            │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/list            │ /portal/boards/posts/list            │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/{postId}        │ /portal/boards/posts/{postId}        │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/update          │ /portal/boards/posts/update          │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/delete/{postId} │ /portal/boards/posts/delete/{postId} │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/next-id         │ /portal/boards/posts/next-id         │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/posts/top-likes       │ /portal/boards/posts/top-likes       │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/files/...             │ /portal/boards/files/...             │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/comments/...          │ /portal/boards/comments/...          │\n  ├───────────────────────────────┼──────────────────────────────────────┤\n  │ /portal/likes/...             │ /portal/boards/likes/...             │','테스트','#TEST','N','N','N',8,0,0,1,NULL,'Y','now009','2026-04-29 05:12:22','now009','2026-04-29 05:12:37');

-- -------------------------------------------------------------------------------------
-- board_comment
-- -------------------------------------------------------------------------------------
CREATE TABLE `board_comment` (
  `commentId` varchar(50) NOT NULL COMMENT '댓글 ID (CMT0000001 형식)',
  `postId` varchar(50) NOT NULL COMMENT '게시글 ID',
  `boardId` varchar(20) NOT NULL COMMENT '게시판 ID',
  `parentCommentId` varchar(50) DEFAULT NULL COMMENT '부모 댓글 ID (대댓글인 경우)',
  `depth` int(11) NOT NULL DEFAULT 0 COMMENT '댓글 깊이 (0: 댓글, 1: 대댓글)',
  `content` varchar(2000) NOT NULL COMMENT '댓글 내용',
  `isAnonymous` char(1) NOT NULL DEFAULT 'N' COMMENT '익명 여부',
  `likeCount` int(11) NOT NULL DEFAULT 0 COMMENT '좋아요 수',
  `useYn` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부 (N: 삭제처리)',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '작성자 userId',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `updateUser` varchar(20) DEFAULT 'system' COMMENT '수정자 userId',
  `updatedDate` timestamp NULL DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`commentId`),
  KEY `idx_board_comment` (`postId`,`useYn`,`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='게시글 댓글';
INSERT INTO `board_comment` (`commentId`, `postId`, `boardId`, `parentCommentId`, `depth`, `content`, `isAnonymous`, `likeCount`, `useYn`, `createdUser`, `createdDate`, `updateUser`, `updatedDate`) VALUES
('CMT0000001','POST0000004','BRD002',NULL,0,'저도 거기 가봤어요! 까르보나라 진짜 맛있더라고요 :)','N',5,'Y','user02','2026-04-29 01:53:12','user02','2026-04-29 01:53:12'),
('CMT0000002','POST0000004','BRD002',NULL,0,'점심에 줄이 좀 길지 않나요? 웨이팅 있었나요?','N',2,'Y','user03','2026-04-29 01:53:12','user03','2026-04-29 01:53:12'),
('CMT0000003','POST0000004','BRD002','CMT0000002',1,'11시 30분 전에 가면 줄 없어요! 12시 넘으면 웨이팅 있더라구요~','N',7,'Y','user01','2026-04-29 01:53:12','user01','2026-04-29 01:53:12'),
('CMT0000004','POST0000005','BRD002',NULL,0,'저도 다음번엔 꼭 참가하고 싶어요! 너무 재미있어 보여요.','N',3,'Y','user06','2026-04-29 01:53:12','user06','2026-04-29 01:53:12'),
('CMT0000005','POST0000005','BRD002',NULL,0,'사진 공유해 주세요~~','N',4,'Y','user07','2026-04-29 01:53:12','user07','2026-04-29 01:53:12'),
('CMT0000006','POST0000006','BRD002',NULL,0,'포모도로 기법 진짜 효과 있어요! 저도 쓰고 있는데 집중력이 확 올라가더라구요.','N',9,'Y','user08','2026-04-29 01:53:12','user08','2026-04-29 01:53:12'),
('CMT0000007','POST0000006','BRD002',NULL,0,'점심 후 산책이 제일 중요한 것 같아요. 안 하면 오후에 너무 졸려요 ㅠㅠ','N',11,'Y','user09','2026-04-29 01:53:12','user09','2026-04-29 01:53:12'),
('CMT0000008','POST0000006','BRD002','CMT0000007',1,'맞아요! 저는 15분씩 꼭 나가요. 완전 추천드려요','N',6,'Y','user03','2026-04-29 01:53:12','user03','2026-04-29 01:53:12'),
('CMT0000009','POST0000006','BRD002',NULL,0,'좋은 팁 감사합니다! 저장해뒀어요 ㅎㅎ','N',4,'Y','user10','2026-04-29 01:53:12','user10','2026-04-29 01:53:12'),
('CMT0000010','POST0000007','BRD002',NULL,0,'관심 있어요! 신청하려면 어떻게 해야 하나요?','N',2,'Y','user05','2026-04-29 01:53:12','user05','2026-04-29 01:53:12'),
('CMT0000011','POST0000007','BRD002','CMT0000010',1,'댓글 남겨주시거나 내선 3456으로 연락 주시면 됩니다 :)','N',3,'Y','user04','2026-04-29 01:53:12','user04','2026-04-29 01:53:12'),
('CMT0000012','POST0000007','BRD002',NULL,0,'저도 참여할게요! 첫 모임은 언제인가요?','N',1,'Y','user11','2026-04-29 01:53:12','user11','2026-04-29 01:53:12'),
('CMT0000013','POST0000008','BRD003',NULL,0,'답변 감사합니다! 바로 해결됐어요 :)','N',4,'Y','user05','2026-04-29 01:53:12','user05','2026-04-29 01:53:12'),
('CMT0000014','POST0000014','BRD005',NULL,0,'킥오프 미팅 회의실 예약은 누가 하나요?','N',1,'Y','user12','2026-04-29 01:53:12','user12','2026-04-29 01:53:12');

-- -------------------------------------------------------------------------------------
-- board_file
-- -------------------------------------------------------------------------------------
CREATE TABLE `board_file` (
  `fileId` varchar(50) NOT NULL COMMENT '파일 ID (FILE0000001 형식)',
  `postId` varchar(50) NOT NULL COMMENT '게시글 ID',
  `boardId` varchar(20) NOT NULL COMMENT '게시판 ID',
  `originalName` varchar(255) NOT NULL COMMENT '원본 파일명',
  `savedName` varchar(255) NOT NULL COMMENT '저장 파일명 (UUID)',
  `filePath` varchar(500) NOT NULL COMMENT '저장 경로',
  `fileSize` bigint(20) NOT NULL COMMENT '파일 크기 (bytes)',
  `fileExt` varchar(20) NOT NULL COMMENT '파일 확장자',
  `sortOrder` int(11) NOT NULL DEFAULT 0 COMMENT '정렬순서',
  `createdUser` varchar(20) DEFAULT 'system' COMMENT '업로드한 userId',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '업로드일시',
  PRIMARY KEY (`fileId`),
  KEY `idx_board_file` (`postId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='게시글 첨부파일';
INSERT INTO `board_file` (`fileId`, `postId`, `boardId`, `originalName`, `savedName`, `filePath`, `fileSize`, `fileExt`, `sortOrder`, `createdUser`, `createdDate`) VALUES
('FILE0000001','POST0000002','BRD001','개인정보보호_교육자료.pdf','a1b2c3d4-1111-2222-3333-aabbccdd0001.pdf','/upload/BRD001/2026/04/',2048576,'pdf',1,'admin','2026-04-29 01:53:09'),
('FILE0000002','POST0000005','BRD002','팀빌딩_사진1.jpg','a1b2c3d4-1111-2222-3333-aabbccdd0002.jpg','/upload/BRD002/2026/04/',512000,'jpg',1,'user02','2026-04-29 01:53:09'),
('FILE0000003','POST0000005','BRD002','팀빌딩_사진2.jpg','a1b2c3d4-1111-2222-3333-aabbccdd0003.jpg','/upload/BRD002/2026/04/',487000,'jpg',2,'user02','2026-04-29 01:53:09'),
('FILE0000004','POST0000014','BRD005','5월_스프린트_계획서.xlsx','a1b2c3d4-1111-2222-3333-aabbccdd0004.xlsx','/upload/BRD005/2026/04/',102400,'xlsx',1,'dev_lead','2026-04-29 01:53:09'),
('FILE0000005','POST0000016','BRD006','Saffron-auth.2026-04-27.log','a09b9f80-54a7-4210-a4e2-9376c0b3fe0d.log','BRD006/2026/04/',120150,'log',1,'now009','2026-04-29 05:12:37');

-- -------------------------------------------------------------------------------------
-- board_like
-- -------------------------------------------------------------------------------------
CREATE TABLE `board_like` (
  `likeId` varchar(50) NOT NULL COMMENT '좋아요 ID (LIKE0000001 형식)',
  `boardId` varchar(20) NOT NULL COMMENT '게시판 ID',
  `targetType` varchar(10) NOT NULL COMMENT '대상 유형 (POST/COMMENT)',
  `targetId` varchar(50) NOT NULL COMMENT '대상 ID (postId 또는 commentId)',
  `createdUser` varchar(20) NOT NULL COMMENT '좋아요 누른 userId',
  `createdDate` timestamp NULL DEFAULT current_timestamp() COMMENT '좋아요 일시',
  PRIMARY KEY (`likeId`),
  UNIQUE KEY `uk_board_like` (`targetType`,`targetId`,`createdUser`),
  KEY `idx_board_like_target` (`targetType`,`targetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='게시글/댓글 좋아요';
INSERT INTO `board_like` (`likeId`, `boardId`, `targetType`, `targetId`, `createdUser`, `createdDate`) VALUES
('LIKE0000001','BRD001','POST','POST0000001','user01','2026-04-29 01:53:45'),
('LIKE0000002','BRD001','POST','POST0000001','user02','2026-04-29 01:53:45'),
('LIKE0000003','BRD001','POST','POST0000003','user03','2026-04-29 01:53:45'),
('LIKE0000004','BRD002','POST','POST0000004','user02','2026-04-29 01:53:45'),
('LIKE0000005','BRD002','POST','POST0000004','user03','2026-04-29 01:53:45'),
('LIKE0000006','BRD002','POST','POST0000004','user04','2026-04-29 01:53:45'),
('LIKE0000007','BRD002','POST','POST0000005','user01','2026-04-29 01:53:45'),
('LIKE0000008','BRD002','POST','POST0000005','user06','2026-04-29 01:53:45'),
('LIKE0000009','BRD002','POST','POST0000006','user01','2026-04-29 01:53:45'),
('LIKE0000010','BRD002','POST','POST0000006','user02','2026-04-29 01:53:45'),
('LIKE0000011','BRD002','POST','POST0000006','user05','2026-04-29 01:53:45'),
('LIKE0000012','BRD002','POST','POST0000007','user03','2026-04-29 01:53:45'),
('LIKE0000013','BRD003','POST','POST0000009','user05','2026-04-29 01:53:45'),
('LIKE0000014','BRD003','POST','POST0000009','user06','2026-04-29 01:53:45'),
('LIKE0000015','BRD005','POST','POST0000014','user12','2026-04-29 01:53:45'),
('LIKE0000016','BRD002','COMMENT','CMT0000001','user03','2026-04-29 01:53:45'),
('LIKE0000017','BRD002','COMMENT','CMT0000001','user04','2026-04-29 01:53:45'),
('LIKE0000018','BRD002','COMMENT','CMT0000003','user01','2026-04-29 01:53:45'),
('LIKE0000019','BRD002','COMMENT','CMT0000006','user01','2026-04-29 01:53:45'),
('LIKE0000020','BRD002','COMMENT','CMT0000007','user02','2026-04-29 01:53:45'),
('LIKE0000021','BRD002','COMMENT','CMT0000007','user04','2026-04-29 01:53:45'),
('LIKE0000022','BRD002','COMMENT','CMT0000008','user05','2026-04-29 01:53:45'),
('LIKE0000023','BRD003','COMMENT','CMT0000013','user06','2026-04-29 01:53:45'),
('LIKE0000024','BRD004','COMMENT','CMT0000014','user07','2026-04-29 01:53:45');
