-- board table 구성
--board_master (게시판 정의)
--│
--    └── board_post (게시글)
--├── board_file      (첨부파일)
--├── board_like      (게시글 좋아요)  ← targetType = 'POST'
--└── board_comment   (댓글/대댓글)
--└── board_like (댓글 좋아요) ← targetType = 'COMMENT'

--테이블ID 형식예시
--board_masterBRD + 3자리BRD001
--board_postPOST + 7자리POST0000001
--board_fileFILE + 7자리FILE0000001
--board_commentCMT + 7자리CMT0000001
--board_likeLIKE + 7자리LIKE0000001

-- 첨부파일 저장 경로는 yml -> {board.filePath}

-- =============================================
-- 게시판 관리 테이블 (어떤 게시판이 있는지 정의)
-- =============================================
CREATE TABLE board_master (
                              boardId         VARCHAR(20)     NOT NULL                    COMMENT '게시판 ID (BRD001 형식)',
                              boardName       VARCHAR(100)    NOT NULL                    COMMENT '게시판 명',
                              boardType       VARCHAR(20)     NOT NULL DEFAULT 'GENERAL'  COMMENT '게시판 유형 (GENERAL/QNA/FAQ/NOTICE)',
                              description     VARCHAR(500)    NULL                        COMMENT '게시판 설명',
-- 기능 설정
                              allowComment    CHAR(1)         NOT NULL DEFAULT 'Y'        COMMENT '댓글 허용',
                              allowAttach     CHAR(1)         NOT NULL DEFAULT 'Y'        COMMENT '첨부파일 허용',
                              allowAnonymous  CHAR(1)         NOT NULL DEFAULT 'N'        COMMENT '익명 허용',
                              allowSearch     CHAR(1)         NOT NULL DEFAULT 'Y'        COMMENT '검색 허용',
-- 접근 권한 설정
                              readRole        VARCHAR(20)     NOT NULL DEFAULT 'ALL'      COMMENT '읽기 권한 (ALL/USER/ADMIN)',
                              writeRole       VARCHAR(20)     NOT NULL DEFAULT 'USER'     COMMENT '쓰기 권한 (ALL/USER/ADMIN)',
-- 표시 설정
                              listCount       INT             NOT NULL DEFAULT 10         COMMENT '목록 표시 건수',
                              newDays         INT             NOT NULL DEFAULT 3          COMMENT 'NEW 표시 기간 (일)',
                              sortOrder       INT             NOT NULL DEFAULT 0          COMMENT '정렬순서',
                              useYn           CHAR(1)         NOT NULL DEFAULT 'Y'        COMMENT '사용여부',
                              createdUser     VARCHAR(20)     DEFAULT 'system'            COMMENT '생성자',
                              createdDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '생성일시',
                              updateUser      VARCHAR(20)     DEFAULT 'system'            COMMENT '수정자',
                              updatedDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '수정일시',
                              PRIMARY KEY (boardId)
) COMMENT='게시판 관리';


-- =============================================
-- 게시글 테이블
-- =============================================
CREATE TABLE board_post (
                            postId          VARCHAR(50)     NOT NULL                    COMMENT '게시글 ID (POST0000001 형식)',
                            boardId         VARCHAR(20)     NOT NULL                    COMMENT '게시판 ID',
    -- 답글 구조 (QnA 등)
                            parentPostId    VARCHAR(50)     NULL                        COMMENT '부모 게시글 ID (답글인 경우)',
                            depth           INT             NOT NULL DEFAULT 0          COMMENT '답글 깊이 (0: 원글, 1: 답글)',
                            title           VARCHAR(200)    NOT NULL                    COMMENT '제목',
                            content         LONGTEXT        NOT NULL                    COMMENT '내용',
    -- 분류
                            category        VARCHAR(50)     NULL                        COMMENT '말머리/카테고리',
                            tags            VARCHAR(500)    NULL                        COMMENT '태그 (쉼표 구분)',
    -- 표시 설정
                            isPinned        CHAR(1)         NOT NULL DEFAULT 'N'        COMMENT '상단고정 여부',
                            isSecret        CHAR(1)         NOT NULL DEFAULT 'N'        COMMENT '비밀글 여부',
                            isAnonymous     CHAR(1)         NOT NULL DEFAULT 'N'        COMMENT '익명 여부',
    -- 통계
                            viewCount       INT             NOT NULL DEFAULT 0          COMMENT '조회수',
                            likeCount       INT             NOT NULL DEFAULT 0          COMMENT '좋아요 수',
                            commentCount    INT             NOT NULL DEFAULT 0          COMMENT '댓글 수',
                            attachCount     INT             NOT NULL DEFAULT 0          COMMENT '첨부파일 수',
    -- 대상
                            targetDeptId    VARCHAR(50)     NULL                        COMMENT '대상부서 (NULL이면 전체)',
                            useYn           CHAR(1)         NOT NULL DEFAULT 'Y'        COMMENT '사용여부 (N: 삭제처리)',
                            createdUser     VARCHAR(20)     DEFAULT 'system'            COMMENT '작성자 userId',
                            createdDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '작성일시',
                            updateUser      VARCHAR(20)     DEFAULT 'system'            COMMENT '수정자 userId',
                            updatedDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '수정일시',
                            PRIMARY KEY (postId),
                            INDEX idx_board_post (boardId, useYn, isPinned, createdDate DESC)
) COMMENT='게시글';


-- =============================================
-- 첨부파일 테이블
-- =============================================
CREATE TABLE board_file (
                            fileId          VARCHAR(50)     NOT NULL                    COMMENT '파일 ID (FILE0000001 형식)',
                            postId          VARCHAR(50)     NOT NULL                    COMMENT '게시글 ID',
                            boardId         VARCHAR(20)     NOT NULL                    COMMENT '게시판 ID',
                            originalName    VARCHAR(255)    NOT NULL                    COMMENT '원본 파일명',
                            savedName       VARCHAR(255)    NOT NULL                    COMMENT '저장 파일명 (UUID)',
                            filePath        VARCHAR(500)    NOT NULL                    COMMENT '저장 경로',
                            fileSize        BIGINT          NOT NULL                    COMMENT '파일 크기 (bytes)',
                            fileExt         VARCHAR(20)     NOT NULL                    COMMENT '파일 확장자',
                            sortOrder       INT             NOT NULL DEFAULT 0          COMMENT '정렬순서',
                            createdUser     VARCHAR(20)     DEFAULT 'system'            COMMENT '업로드한 userId',
                            createdDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '업로드일시',
                            PRIMARY KEY (fileId),
                            INDEX idx_board_file (postId)
) COMMENT='게시글 첨부파일';


-- =============================================
-- 댓글 테이블
-- =============================================
CREATE TABLE board_comment (
                               commentId       VARCHAR(50)     NOT NULL                    COMMENT '댓글 ID (CMT0000001 형식)',
                               postId          VARCHAR(50)     NOT NULL                    COMMENT '게시글 ID',
                               boardId         VARCHAR(20)     NOT NULL                    COMMENT '게시판 ID',
                               parentCommentId VARCHAR(50)     NULL                        COMMENT '부모 댓글 ID (대댓글인 경우)',
                               depth           INT             NOT NULL DEFAULT 0          COMMENT '댓글 깊이 (0: 댓글, 1: 대댓글)',
                               content         VARCHAR(2000)   NOT NULL                    COMMENT '댓글 내용',
                               isAnonymous     CHAR(1)         NOT NULL DEFAULT 'N'        COMMENT '익명 여부',
                               likeCount       INT             NOT NULL DEFAULT 0          COMMENT '좋아요 수',
                               useYn           CHAR(1)         NOT NULL DEFAULT 'Y'        COMMENT '사용여부 (N: 삭제처리)',
                               createdUser     VARCHAR(20)     DEFAULT 'system'            COMMENT '작성자 userId',
                               createdDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '작성일시',
                               updateUser      VARCHAR(20)     DEFAULT 'system'            COMMENT '수정자 userId',
                               updatedDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '수정일시',
                               PRIMARY KEY (commentId),
                               INDEX idx_board_comment (postId, useYn, createdDate)
) COMMENT='게시글 댓글';

-- =============================================
-- 좋아요 테이블 (게시글 + 댓글 통합 관리)
-- =============================================
CREATE TABLE board_like (
                            likeId          VARCHAR(50)     NOT NULL                    COMMENT '좋아요 ID (LIKE0000001 형식)',
                            boardId         VARCHAR(20)     NOT NULL                    COMMENT '게시판 ID',
                            targetType      VARCHAR(10)     NOT NULL                    COMMENT '대상 유형 (POST/COMMENT)',
                            targetId        VARCHAR(50)     NOT NULL                    COMMENT '대상 ID (postId 또는 commentId)',
                            createdUser     VARCHAR(20)     NOT NULL                    COMMENT '좋아요 누른 userId',
                            createdDate     TIMESTAMP       DEFAULT CURRENT_TIMESTAMP   COMMENT '좋아요 일시',
                            PRIMARY KEY (likeId),
    -- 동일 유저가 같은 대상에 중복 좋아요 방지
                            UNIQUE KEY uk_board_like (targetType, targetId, createdUser),
                            INDEX idx_board_like_target (targetType, targetId)
) COMMENT='게시글/댓글 좋아요';

-- -- 좋아요 추가 시 → likeCount 자동 증가

-- 주요 쿼리 패턴

-- 이미 좋아요 눌렀는지 확인
SELECT COUNT(*) FROM board_like
WHERE targetType = 'POST'
  AND targetId   = 'POST0000001'
  AND createdUser = 'now009';

-- 좋아요 추가
INSERT INTO board_like (likeId, boardId, targetType, targetId, createdUser)
VALUES ('LIKE0000001', 'BRD002', 'POST', 'POST0000001', 'now009');

-- 좋아요 취소
DELETE FROM board_like
WHERE targetType  = 'POST'
  AND targetId    = 'POST0000001'
  AND createdUser = 'now009';

SELECT
    p.postId,
    p.title,
    p.likeCount,
    p.viewCount,
    p.commentCount,
    CASE WHEN l.likeId IS NOT NULL THEN 'Y' ELSE 'N' END AS isLiked
FROM board_post p
         LEFT JOIN board_like l
                   ON  l.targetType  = 'POST'
                       AND l.targetId    = p.postId
                       AND l.createdUser = 'now009'   -- 현재 로그인 userId
WHERE p.boardId = 'BRD002'
  AND p.useYn   = 'Y'
ORDER BY p.isPinned DESC, p.createdDate DESC;

SELECT
    p.postId,
    p.title,
    p.likeCount,
    p.viewCount
FROM board_post p
WHERE p.boardId = 'BRD002'
  AND p.useYn   = 'Y'
ORDER BY p.likeCount DESC
    LIMIT 5;

------------------------------------------------------------
-- Test Sample 데이타
------------------------------------------------------------

-- =============================================
-- 1. board_master 테스트 데이터
-- =============================================
INSERT INTO board_master
(boardId, boardName, boardType, description, allowComment, allowAttach, allowAnonymous, allowSearch, readRole, writeRole, listCount, newDays, sortOrder, useYn, createdUser, updateUser)
VALUES
    ('BRD001', '공지사항',   'NOTICE',  '전사 공지사항 게시판',       'N', 'Y', 'N', 'Y', 'ALL',  'ADMIN', 10, 3, 1, 'Y', 'admin', 'admin'),
    ('BRD002', '자유게시판', 'GENERAL', '자유롭게 소통하는 공간',     'Y', 'Y', 'N', 'Y', 'USER', 'USER',  10, 3, 2, 'Y', 'admin', 'admin'),
    ('BRD003', 'Q&A',       'QNA',     '질문과 답변 게시판',          'Y', 'Y', 'N', 'Y', 'USER', 'USER',  10, 5, 3, 'Y', 'admin', 'admin'),
    ('BRD004', 'FAQ',       'FAQ',     '자주 묻는 질문 모음',         'N', 'N', 'N', 'Y', 'ALL',  'ADMIN', 20, 0, 4, 'Y', 'admin', 'admin'),
    ('BRD005', '부서공지',  'NOTICE',  '부서별 공지사항',             'N', 'Y', 'N', 'Y', 'USER', 'USER',  10, 3, 5, 'Y', 'admin', 'admin');


-- =============================================
-- 2. board_post 테스트 데이터
-- =============================================
INSERT INTO board_post
(postId, boardId, parentPostId, depth, title, content, category, tags, isPinned, isSecret, isAnonymous, viewCount, likeCount, commentCount, attachCount, targetDeptId, useYn, createdUser, updateUser)
VALUES
-- BRD001 공지사항
('POST0000001', 'BRD001', NULL, 0, '2026년 상반기 시스템 점검 안내',
 '5월 1일(금) 23:00 ~ 익일 02:00 전사 시스템 점검이 진행됩니다.\n점검 중 모든 서비스 이용이 불가합니다.',
 NULL, '점검,시스템', 'Y', 'N', 'N', 412, 28, 0, 0, NULL, 'Y', 'admin', 'admin'),
('POST0000002', 'BRD001', NULL, 0, '개인정보 보호 의무교육 안내',
 '5월 10일(일) 14:00 대강당에서 전 직원 의무교육이 진행됩니다.\n반드시 참석해 주시기 바랍니다.',
 NULL, '교육,필수', 'Y', 'N', 'N', 389, 15, 0, 1, NULL, 'Y', 'admin', 'admin'),
('POST0000003', 'BRD001', NULL, 0, '5월 사내 휴일 안내',
 '5월 1일 근로자의 날, 5월 5일 어린이날, 5월 6일 임시공휴일로 지정되었습니다.',
 NULL, '휴일', 'N', 'N', 'N', 521, 42, 0, 0, NULL, 'Y', 'admin', 'admin'),
-- BRD002 자유게시판
('POST0000004', 'BRD002', NULL, 0, '점심 추천 맛집 공유해요!',
 '회사 근처 새로 생긴 파스타 집 너무 맛있어요~ 다들 한번씩 가보세요 :)',
 '일상', '맛집,점심,추천', 'N', 'N', 'N', 234, 31, 3, 0, NULL, 'Y', 'user01', 'user01'),
('POST0000005', 'BRD002', NULL, 0, '이번 주 금요일 팀빌딩 후기',
 '마케팅팀 볼링 대회 정말 재미있었습니다! 다음에 또 해요~',
 '일상', '팀빌딩,볼링', 'N', 'N', 'N', 178, 24, 2, 2, NULL, 'Y', 'user02', 'user02'),
('POST0000006', 'BRD002', NULL, 0, '재택근무 꿀팁 공유',
 '집에서 집중하기 힘드신 분들 위한 팁 공유합니다.\n1. 시작 전 루틴 만들기\n2. 포모도로 기법 활용\n3. 점심 후 산책 필수!',
 '업무', '재택,꿀팁', 'N', 'N', 'N', 445, 67, 4, 0, NULL, 'Y', 'user03', 'user03'),
('POST0000007', 'BRD002', NULL, 0, '사내 동호회 멤버 모집 (독서)',
 '독서 동호회 멤버를 모집합니다.\n월 1회 모임, 부담 없이 참여 가능합니다.\n관심 있으신 분은 댓글 남겨주세요!',
 '동호회', '독서,동호회,모집', 'N', 'N', 'N', 156, 18, 3, 0, NULL, 'Y', 'user04', 'user04'),
-- BRD003 Q&A
('POST0000008', 'BRD003', NULL, 0, '연차 신청은 어디서 하나요?',
 'HR 시스템에서 연차 신청하는 방법을 모르겠습니다. 알려주시면 감사하겠습니다.',
 '인사', '연차,HR', 'N', 'N', 'N', 89, 5, 1, 0, NULL, 'Y', 'user05', 'user05'),
('POST0000009', 'BRD003', 'POST0000008', 1, 'Re: 연차 신청은 어디서 하나요?',
 'HR 포털(hr.company.com) 접속 → 근태관리 → 휴가신청 메뉴에서 신청하시면 됩니다!\n팀장 승인 후 처리됩니다.',
 '인사', '연차,HR', 'N', 'N', 'N', 76, 12, 0, 0, NULL, 'Y', 'hr_admin', 'hr_admin'),
('POST0000010', 'BRD003', NULL, 0, '법인카드 사용 후 정산 기한이 언제인가요?',
 '법인카드 사용 후 정산을 언제까지 해야 하는지 궁금합니다.',
 '재무', '법인카드,정산', 'N', 'N', 'N', 134, 8, 1, 0, NULL, 'Y', 'user06', 'user06'),
('POST0000011', 'BRD003', 'POST0000010', 1, 'Re: 법인카드 사용 후 정산 기한이 언제인가요?',
 '사용일로부터 7일 이내 ERP 시스템에서 정산하셔야 합니다.\n영수증 첨부 필수이며, 기한 초과 시 재무팀에 문의 바랍니다.',
 '재무', '법인카드,정산', 'N', 'N', 'N', 98, 9, 0, 0, NULL, 'Y', 'finance_admin', 'finance_admin'),
-- BRD004 FAQ
('POST0000012', 'BRD004', NULL, 0, '급여일은 언제인가요?',
 '매월 25일이 급여일입니다. 25일이 주말 또는 공휴일인 경우 직전 영업일에 지급됩니다.',
 '급여', '급여,월급', 'N', 'N', 'N', 678, 45, 0, 0, NULL, 'Y', 'hr_admin', 'hr_admin'),
('POST0000013', 'BRD004', NULL, 0, '사내 주차 등록은 어떻게 하나요?',
 '총무팀(내선 2000)으로 연락하시거나 총무팀 이메일(gs@company.com)로 차량번호를 보내주시면 등록해 드립니다.',
 '복지', '주차,차량', 'N', 'N', 'N', 412, 33, 0, 0, NULL, 'Y', 'admin', 'admin'),
-- BRD005 부서공지
('POST0000014', 'BRD005', NULL, 0, '[개발팀] 5월 스프린트 일정 안내',
 '5월 스프린트 일정을 안내드립니다.\n- Sprint 1: 5/1 ~ 5/14\n- Sprint 2: 5/15 ~ 5/28\n각 스프린트 시작일 오전 10시 킥오프 미팅 예정입니다.',
 NULL, '스프린트,일정', 'N', 'N', 'N', 67, 8, 1, 1, 'D001', 'Y', 'dev_lead', 'dev_lead'),
('POST0000015', 'BRD005', NULL, 0, '[인사팀] 4월 급여명세서 발송 완료',
 '4월 급여명세서가 발송되었습니다.\nHR 포털 → 급여 명세서 메뉴에서 확인하실 수 있습니다.\n문의: 내선 1234',
 NULL, '급여,명세서', 'N', 'N', 'N', 234, 12, 0, 0, 'D002', 'Y', 'hr_admin', 'hr_admin');

-- =============================================
-- 3. board_file 테스트 데이터
-- =============================================
INSERT INTO board_file
(fileId, postId, boardId, originalName, savedName, filePath, fileSize, fileExt, sortOrder, createdUser)
VALUES
    ('FILE0000001', 'POST0000002', 'BRD001',
     '개인정보보호_교육자료.pdf',
     'a1b2c3d4-1111-2222-3333-aabbccdd0001.pdf',
     '/upload/BRD001/2026/04/', 2048576, 'pdf', 1, 'admin'),
    ('FILE0000002', 'POST0000005', 'BRD002',
     '팀빌딩_사진1.jpg',
     'a1b2c3d4-1111-2222-3333-aabbccdd0002.jpg',
     '/upload/BRD002/2026/04/', 512000, 'jpg', 1, 'user02'),
    ('FILE0000003', 'POST0000005', 'BRD002',
     '팀빌딩_사진2.jpg',
     'a1b2c3d4-1111-2222-3333-aabbccdd0003.jpg',
     '/upload/BRD002/2026/04/', 487000, 'jpg', 2, 'user02'),
    ('FILE0000004', 'POST0000014', 'BRD005',
     '5월_스프린트_계획서.xlsx',
     'a1b2c3d4-1111-2222-3333-aabbccdd0004.xlsx',
     '/upload/BRD005/2026/04/', 102400, 'xlsx', 1, 'dev_lead');

-- =============================================
-- 4. board_comment 테스트 데이터
-- =============================================
INSERT INTO board_comment
(commentId, postId, boardId, parentCommentId, depth, content, isAnonymous, likeCount, useYn, createdUser, updateUser)
VALUES
-- POST0000004 자유게시판 맛집 댓글
('CMT0000001', 'POST0000004', 'BRD002', NULL, 0,
 '저도 거기 가봤어요! 까르보나라 진짜 맛있더라고요 :)', 'N', 5, 'Y', 'user02', 'user02'),
('CMT0000002', 'POST0000004', 'BRD002', NULL, 0,
 '점심에 줄이 좀 길지 않나요? 웨이팅 있었나요?', 'N', 2, 'Y', 'user03', 'user03'),
('CMT0000003', 'POST0000004', 'BRD002', 'CMT0000002', 1,
 '11시 30분 전에 가면 줄 없어요! 12시 넘으면 웨이팅 있더라구요~', 'N', 7, 'Y', 'user01', 'user01'),
-- POST0000005 팀빌딩 댓글
('CMT0000004', 'POST0000005', 'BRD002', NULL, 0,
 '저도 다음번엔 꼭 참가하고 싶어요! 너무 재미있어 보여요.', 'N', 3, 'Y', 'user06', 'user06'),
('CMT0000005', 'POST0000005', 'BRD002', NULL, 0,
 '사진 공유해 주세요~~', 'N', 4, 'Y', 'user07', 'user07'),
-- POST0000006 재택근무 댓글
('CMT0000006', 'POST0000006', 'BRD002', NULL, 0,
 '포모도로 기법 진짜 효과 있어요! 저도 쓰고 있는데 집중력이 확 올라가더라구요.', 'N', 9, 'Y', 'user08', 'user08'),
('CMT0000007', 'POST0000006', 'BRD002', NULL, 0,
 '점심 후 산책이 제일 중요한 것 같아요. 안 하면 오후에 너무 졸려요 ㅠㅠ', 'N', 11, 'Y', 'user09', 'user09'),
('CMT0000008', 'POST0000006', 'BRD002', 'CMT0000007', 1,
 '맞아요! 저는 15분씩 꼭 나가요. 완전 추천드려요', 'N', 6, 'Y', 'user03', 'user03'),
('CMT0000009', 'POST0000006', 'BRD002', NULL, 0,
 '좋은 팁 감사합니다! 저장해뒀어요 ㅎㅎ', 'N', 4, 'Y', 'user10', 'user10'),
-- POST0000007 독서동호회 댓글
('CMT0000010', 'POST0000007', 'BRD002', NULL, 0,
 '관심 있어요! 신청하려면 어떻게 해야 하나요?', 'N', 2, 'Y', 'user05', 'user05'),
('CMT0000011', 'POST0000007', 'BRD002', 'CMT0000010', 1,
 '댓글 남겨주시거나 내선 3456으로 연락 주시면 됩니다 :)', 'N', 3, 'Y', 'user04', 'user04'),
('CMT0000012', 'POST0000007', 'BRD002', NULL, 0,
 '저도 참여할게요! 첫 모임은 언제인가요?', 'N', 1, 'Y', 'user11', 'user11'),
-- POST0000008 Q&A 댓글
('CMT0000013', 'POST0000008', 'BRD003', NULL, 0,
 '답변 감사합니다! 바로 해결됐어요 :)', 'N', 4, 'Y', 'user05', 'user05'),
-- POST0000014 개발팀 공지 댓글
('CMT0000014', 'POST0000014', 'BRD005', NULL, 0,
 '킥오프 미팅 회의실 예약은 누가 하나요?', 'N', 1, 'Y', 'user12', 'user12');


-- =============================================
-- 5. board_like 테스트 데이터
-- =============================================
DELETE FROM board_like;
INSERT INTO board_like
(likeId, boardId, targetType, targetId, createdUser)
VALUES
-- 게시글 좋아요 (POST)
('LIKE0000001', 'BRD001', 'POST', 'POST0000001', 'user01'),
('LIKE0000002', 'BRD001', 'POST', 'POST0000001', 'user02'),
('LIKE0000003', 'BRD001', 'POST', 'POST0000003', 'user03'),
('LIKE0000004', 'BRD002', 'POST', 'POST0000004', 'user02'),
('LIKE0000005', 'BRD002', 'POST', 'POST0000004', 'user03'),
('LIKE0000006', 'BRD002', 'POST', 'POST0000004', 'user04'),
('LIKE0000007', 'BRD002', 'POST', 'POST0000005', 'user01'),
('LIKE0000008', 'BRD002', 'POST', 'POST0000005', 'user06'),
('LIKE0000009', 'BRD002', 'POST', 'POST0000006', 'user01'),
('LIKE0000010', 'BRD002', 'POST', 'POST0000006', 'user02'),
('LIKE0000011', 'BRD002', 'POST', 'POST0000006', 'user05'),
('LIKE0000012', 'BRD002', 'POST', 'POST0000007', 'user03'),
('LIKE0000013', 'BRD003', 'POST', 'POST0000009', 'user05'),
('LIKE0000014', 'BRD003', 'POST', 'POST0000009', 'user06'),
('LIKE0000015', 'BRD005', 'POST', 'POST0000014', 'user12'),
-- 댓글 좋아요 (COMMENT)
('LIKE0000016', 'BRD002', 'COMMENT', 'CMT0000001', 'user03'),
('LIKE0000017', 'BRD002', 'COMMENT', 'CMT0000001', 'user04'),
('LIKE0000018', 'BRD002', 'COMMENT', 'CMT0000003', 'user01'),
('LIKE0000019', 'BRD002', 'COMMENT', 'CMT0000006', 'user01'),
('LIKE0000020', 'BRD002', 'COMMENT', 'CMT0000007', 'user02'),
('LIKE0000021', 'BRD002', 'COMMENT', 'CMT0000007', 'user04'),
('LIKE0000022', 'BRD002', 'COMMENT', 'CMT0000008', 'user05'),
('LIKE0000023', 'BRD003', 'COMMENT', 'CMT0000013', 'user06'),
('LIKE0000024', 'BRD004', 'COMMENT', 'CMT0000014', 'user07');

-- 확인 sql
-- 게시판별 게시글 현황
SELECT
    m.boardId,
    m.boardName,
    COUNT(p.postId)  AS postCount,
    SUM(p.likeCount) AS totalLikes
FROM board_master m
         LEFT JOIN board_post p ON m.boardId = p.boardId AND p.useYn = 'Y'
GROUP BY m.boardId, m.boardName
ORDER BY m.sortOrder;

-- 좋아요 많은 게시글 TOP 5
SELECT p.postId, p.title, p.likeCount, p.commentCount
FROM board_post p
WHERE p.useYn = 'Y'
ORDER BY p.likeCount DESC
    LIMIT 5;