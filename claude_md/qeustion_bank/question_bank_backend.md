2. [DB 테이블 설계](#2-db-테이블-설계)
3. [DDL 및 예제 데이터 SQL](#3-ddl-및-예제-데이터-sql)
4. [Backend API 엔드포인트](#4-backend-api-엔드포인트)
5. 
## 1. Backend API 엔드포인트

> **Prefix: `/api/qbank`**

### 관리자 API

| Method | URL | 설명 |
|--------|-----|------|
| `GET` | `/api/qbank/admin/exam-types` | 시험종류 목록 조회 |
| `POST` | `/api/qbank/admin/exam-types` | 시험종류 등록 |
| `PUT` | `/api/qbank/admin/exam-types/{id}` | 시험종류 수정 |
| `DELETE` | `/api/qbank/admin/exam-types/{id}` | 시험종류 삭제 |
| `GET` | `/api/qbank/admin/exam-subjects` | 시험대상 목록 조회 |
| `POST` | `/api/qbank/admin/exam-subjects` | 시험대상 등록 |
| `PUT` | `/api/qbank/admin/exam-subjects/{id}` | 시험대상 수정 |
| `DELETE` | `/api/qbank/admin/exam-subjects/{id}` | 시험대상 삭제 |
| `GET` | `/api/qbank/admin/papers` | 시험지 목록 조회 |
| `POST` | `/api/qbank/admin/papers` | 시험지 생성 |
| `PUT` | `/api/qbank/admin/papers/{id}` | 시험지 수정 |
| `DELETE` | `/api/qbank/admin/papers/{id}` | 시험지 삭제 |
| `GET` | `/api/qbank/admin/papers/{paperId}/questions` | 문항 목록 조회 |
| `POST` | `/api/qbank/admin/papers/{paperId}/questions` | 문항 등록 (이미지 업로드 포함) |
| `PUT` | `/api/qbank/admin/questions/{id}` | 문항 수정 |
| `DELETE` | `/api/qbank/admin/questions/{id}` | 문항 삭제 |
| `POST` | `/api/qbank/admin/questions/{id}/choices` | 보기 등록 |
| `PUT` | `/api/qbank/admin/choices/{id}` | 보기 수정 |
| `DELETE` | `/api/qbank/admin/choices/{id}` | 보기 삭제 |
| `GET` | `/api/qbank/admin/sessions` | 응시 세션 목록 조회 |
| `GET` | `/api/qbank/admin/sessions/{id}/answers` | 답안지 조회 |
| `POST` | `/api/qbank/admin/sessions/{id}/grade` | 채점 (자동+수동) |

### 응시자 API

| Method | URL | 설명 |
|--------|-----|------|
| `GET` | `/api/qbank/exam/types` | 시험종류 목록 (응시용) |
| `GET` | `/api/qbank/exam/subjects` | 시험대상 목록 (응시용) |
| `GET` | `/api/qbank/exam/papers` | 시험지 조회 (`?typeId=&subjectId=`) |
| `POST` | `/api/qbank/exam/sessions` | 응시 시작 (이름/사번 입력) |
| `POST` | `/api/qbank/exam/sessions/{id}/submit` | 답안 제출 |

### 주요 Request/Response 예시

```json
// POST /api/qbank/exam/sessions — 응시 시작
Request:
{
  "examPaperId": 1,
  "examineeName": "홍길동",
  "examineeNo": "20240001"
}
Response:
{
  "sessionId": 1,
  "title": "1학년 국어 중간고사",
  "timeLimitMin": 50,
  "questions": [
    {
      "id": 1,
      "seq": 1,
      "qType": "single",
      "questionText": "다음 중 맞춤법이 올바른 것은?",
      "imageUrl": null,
      "score": 2,
      "choices": [
        { "id": 5, "seq": 1, "choiceText": "안되요", "imageUrl": null },
        { "id": 6, "seq": 2, "choiceText": "안돼요", "imageUrl": null }
      ]
    }
  ]
}
// ※ is_correct는 응시자 응답에 절대 포함하지 않음

// POST /api/qbank/exam/sessions/{id}/submit — 답안 제출
Request:
{
  "answers": [
    { "questionId": 1, "selectedChoiceId": 6 },
    { "questionId": 4, "answerText": "자연과 인간의 조화로운 공존이다." }
  ]
}

// POST /api/qbank/admin/sessions/{id}/grade — 채점
Request:
{
  "autoGrade": true,
  "manualGrades": [
    { "questionId": 4, "isCorrect": true }
  ]
}
