## 2. DB 테이블 설계

drop table exam_type; --> qbank_
drop table exam_subject;
drop table exam_paper;
drop table question;
drop table question_choice;
drop table exam_session;
drop table answer_sheet;

---

## 3. DDL 및 예제 데이터 SQL

-- ============================================================
-- 문제은행 시스템 DDL + 예제 데이터
-- Database: saffron (MariaDB)
-- 컬럼명: camelCase 통일
-- ============================================================

USE saffron;

-- ------------------------------------------------------------
-- 1. 시험 종류
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_exam_type (
                                         id        INT AUTO_INCREMENT PRIMARY KEY,
                                         name      VARCHAR(100) NOT NULL COMMENT '시험종류명 (기말시험, 설문조사 등)',
    isSurvey  TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '설문이면 1 (정답 없음)',
    createdAt DATETIME              DEFAULT NOW()
    ) COMMENT='시험 종류';

-- ------------------------------------------------------------
-- 2. 시험 대상
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_exam_subject (
                                            id        INT AUTO_INCREMENT PRIMARY KEY,
                                            name      VARCHAR(100) NOT NULL COMMENT '과목/대상명 (국어, 수학 등)',
    grade     VARCHAR(50)           COMMENT '학년 (1학년, 2학년 등, 선택)',
    createdAt DATETIME              DEFAULT NOW()
    ) COMMENT='시험 대상';

-- ------------------------------------------------------------
-- 3. 출제 시험지 (종류 + 대상 조합)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_exam_paper (
                                          id             INT AUTO_INCREMENT PRIMARY KEY,
                                          examTypeId     INT          NOT NULL,
                                          examSubjectId  INT          NOT NULL,
                                          title          VARCHAR(200)          COMMENT '시험지 제목',
    timeLimitMin   INT                   COMMENT '제한시간(분), NULL=무제한',
    isActive       TINYINT(1)   NOT NULL DEFAULT 1 COMMENT '출제 활성화 여부',
    createdAt      DATETIME              DEFAULT NOW()
    ) COMMENT='출제 시험지';

-- ------------------------------------------------------------
-- 4. 문항
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_question (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    examPaperId  INT          NOT NULL,
    seq          INT          NOT NULL COMMENT '출제 순서',
    qType        ENUM('single','multi','subjective') NOT NULL
                COMMENT 'single=단일선택, multi=다중선택, subjective=주관식',
    questionText TEXT         NOT NULL COMMENT '질문 내용',
    imageFileName     VARCHAR(100)          COMMENT '문제 이미지 파일명',
    imageUrl     VARCHAR(500)          COMMENT '문제 이미지 경로',
    score        INT          NOT NULL DEFAULT 1 COMMENT '배점',
    createdAt    DATETIME              DEFAULT NOW()
    ) COMMENT='문항';

-- ------------------------------------------------------------
-- 5. 객관식 보기 (2~8개)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_question_choice (
                                               id         INT AUTO_INCREMENT PRIMARY KEY,
                                               questionId INT         NOT NULL,
                                               seq        INT         NOT NULL COMMENT '보기 번호 (1~8)',
                                               choiceText TEXT                 COMMENT '보기 텍스트',
                                               imageUrl   VARCHAR(500)         COMMENT '보기 이미지 경로',
    isCorrect  TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '정답 여부 (관리자 채점용)'
    ) COMMENT='객관식 보기';

-- ------------------------------------------------------------
-- 6. 응시 세션
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_exam_session (
                                            id           INT AUTO_INCREMENT PRIMARY KEY,
                                            examPaperId  INT          NOT NULL,
                                            examineeName VARCHAR(100) NOT NULL COMMENT '응시자 이름',
    examineeNo   VARCHAR(50)           COMMENT '사번 또는 학번',
    startedAt    DATETIME              DEFAULT NOW(),
    submittedAt  DATETIME              COMMENT '제출 시각',
    totalScore   INT                   COMMENT '채점 완료 후 합산 점수'
    ) COMMENT='응시 세션';

-- ------------------------------------------------------------
-- 7. 답안지
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS qbank_answer_sheet (
                                            id             INT AUTO_INCREMENT PRIMARY KEY,
                                            sessionId      INT        NOT NULL,
                                            questionId     INT        NOT NULL,
                                            answerText     TEXT                COMMENT '주관식 답안',
                                            selectedChoice INT                 COMMENT '객관식 선택 choice_id',
                                            isCorrect      TINYINT(1)          COMMENT '채점 결과 (1=정답, 0=오답, NULL=미채점)',
    gradedAt       DATETIME            COMMENT '채점 시각'
    ) COMMENT='제출 답안 및 채점 결과';


-- ============================================================
-- 예제 데이터
-- ============================================================

-- 1. 시험 종류
INSERT INTO qbank_exam_type (name, isSurvey) VALUES
('중간고사', 0),
('기말고사', 0),
('수시평가', 0),
('만족도 설문', 1);

-- 2. 시험 대상
INSERT INTO qbank_exam_subject (name, grade) VALUES
('국어', '1학년'),
('수학', '1학년'),
('영어', '2학년'),
('과학', '2학년'),
('교육과정 만족도', NULL);

-- 3. 출제 시험지
INSERT INTO qbank_exam_paper (examTypeId, examSubjectId, title, timeLimitMin, isActive) VALUES
(1, 1, '1학년 국어 중간고사', 50, 1),
(1, 2, '1학년 수학 중간고사', 60, 1),
(4, 5, '2024년 교육과정 만족도 설문', NULL, 1);

-- 4. 문항 — 1학년 국어 중간고사 (examPaperId=1)
INSERT INTO qbank_question (examPaperId, seq, qType, questionText, score) VALUES
(1, 1, 'single',     '다음 중 맞춤법이 올바른 것은?', 2),
(1, 2, 'single',     '다음 글의 중심 내용으로 가장 적절한 것은?', 3),
(1, 3, 'multi',      '다음 중 품사가 같은 것을 모두 고르시오.', 4),
(1, 4, 'subjective', '다음 시의 주제를 한 문장으로 서술하시오.', 5),
(1, 5, 'single',     '밑줄 친 단어의 반의어는?', 2);

-- 6. 문항 — 설문 (examPaperId=3)
INSERT INTO qbank_question (examPaperId, seq, qType, questionText, score) VALUES
(3, 1, 'single',     '전반적인 교육과정에 만족하십니까?', 0),
(3, 2, 'single',     '강의 내용의 난이도는 적절했습니까?', 0),
(3, 3, 'subjective', '개선이 필요한 사항을 자유롭게 작성해주세요.', 0);

-- 5. 객관식 보기 — question 1 (맞춤법)
INSERT INTO qbank_question_choice (questionId, seq, choiceText, isCorrect) VALUES
(1, 1, '안되요',   0),
(1, 2, '안돼요',   1),
(1, 3, '않돼요',   0),
(1, 4, '않되요',   0);

-- 객관식 보기 — question 2 (중심 내용)
INSERT INTO qbank_question_choice (questionId, seq, choiceText, isCorrect) VALUES
(2, 1, '자연과 인간의 공존', 1),
(2, 2, '과학 기술의 발전',   0),
(2, 3, '역사적 사건의 교훈', 0),
(2, 4, '경제 성장의 중요성', 0);

-- 객관식 보기 — question 3 (품사, 다중선택)
INSERT INTO qbank_question_choice (questionId, seq, choiceText, isCorrect) VALUES
(3, 1, '빠르다',   1),
(3, 2, '학교',     0),
(3, 3, '아름답다', 1),
(3, 4, '달리다',   0),
(3, 5, '높다',     1);

-- 객관식 보기 — question 5 (반의어)
INSERT INTO qbank_question_choice (questionId, seq, choiceText, isCorrect) VALUES
(5, 1, '기쁨', 0),
(5, 2, '슬픔', 1),
(5, 3, '행복', 0),
(5, 4, '설렘', 0);



-- 설문 보기 — question 6
INSERT INTO qbank_question_choice (questionId, seq, choiceText, isCorrect) VALUES
(6, 1, '매우 만족',   0),
(6, 2, '만족',         0),
(6, 3, '보통',         0),
(6, 4, '불만족',       0),
(6, 5, '매우 불만족', 0);

-- 설문 보기 — question 7
INSERT INTO qbank_question_choice (questionId, seq, choiceText, isCorrect) VALUES
(7, 1, '매우 쉬움',   0),
(7, 2, '쉬움',         0),
(7, 3, '적절함',       0),
(7, 4, '어려움',       0),
(7, 5, '매우 어려움', 0);

-- 7. 응시 세션 예제
INSERT INTO qbank_exam_session (examPaperId, examineeName, examineeNo, submittedAt, totalScore) VALUES
(1, '홍길동', '20240001', NOW(), NULL),
(1, '김영희', '20240002', NOW(), NULL);

-- 8. 답안지 예제 (홍길동 sessionId=1)
INSERT INTO qbank_answer_sheet (sessionId, questionId, answerText, selectedChoice) VALUES
(1, 1, NULL, 6),
(1, 2, NULL, 9),
(1, 4, '이 시의 주제는 자연과 인간의 조화로운 공존이다.', NULL);




