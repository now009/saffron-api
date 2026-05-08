# XML Store 사용 가이드

`com.saffron.store` 패키지는 XML 파일 기반의 경량 데이터 저장소입니다.  
DB 없이 메타 정의 → 데이터 CRUD 를 파일로 관리할 수 있습니다.

---

## 1. 파일 구조

```
C:/xmlStore/
  meta/
    recipe-meta.xml      ← 컬럼 정의 (스키마)
    product-meta.xml
  data/
    recipe-data.xml      ← 실제 데이터 (자동 생성)
    product-data.xml
```

### 메타 XML 형식
```xml
<meta subject="recipe">
  <columns>
    <column name="name"        type="String"  label="레시피명" required="true" />
    <column name="description" type="String"  label="설명" />
    <column name="price"       type="Long"    label="가격" defaultValue="0" />
    <column name="active"      type="Boolean" label="활성" defaultValue="true" />
  </columns>
</meta>
```

### 데이터 XML 형식 (자동 생성)
```xml
<data subject="recipe" lastRowId="3">
  <row rowId="1">
    <name>OXIDE_ETCH_V1</name>
    <description>산화막 식각 레시피</description>
    <price>0</price>
    <active>true</active>
  </row>
</data>
```

> **rowId** 는 자동 증가 PK 로, 직접 지정하지 않습니다.

---

## 2. YML 설정

```yaml
# application.yml (공통 기본값)
xml-store:
  meta-path: C:/xmlStore/meta   # {subject}-meta.xml 저장 폴더
  data-path: C:/xmlStore/data   # {subject}-data.xml 저장 폴더

# application-local.yml (로컬 환경 오버라이드)
xml-store:
  meta-path: C:/xmlStore/meta
  data-path: C:/xmlStore/data
```

---

## 3. 주입 방법

`XmlStoreService` 하나만 주입받으면 됩니다.

```java
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final XmlStoreService xmlStore;

    public RecipeController(XmlStoreService xmlStore) {
        this.xmlStore = xmlStore;
    }
}
```

---

## 4. 메타 관리

### 4-1. 메타 생성

```java
List<ColumnDef> columns = List.of(
    new ColumnDef("name",        "String",  "레시피명", true, null),
    new ColumnDef("description", "String",  "설명",     false, null),
    new ColumnDef("active",      "Boolean", "활성여부", false, "true")
);

StoreResponse res = xmlStore.defineMeta("recipe", columns);
```

**ColumnDef 생성자:**
```
ColumnDef(String name, String type, String label)
ColumnDef(String name, String type, String label, boolean required, String defaultValue)
```

| type 값    | 의미       |
|------------|------------|
| `String`   | 문자열     |
| `Long`     | 정수       |
| `Boolean`  | 참/거짓    |
| `Date`     | 날짜 문자열 |

### 4-2. 메타 조회

```java
StoreResponse res = xmlStore.getMetaInfo("recipe");
// res.getData() → List<ColumnDef>
```

### 4-3. 메타 삭제

```java
StoreResponse res = xmlStore.deleteMeta("recipe");
```

---

## 5. 데이터 CRUD

### 5-1. INSERT

```java
Map<String, String> values = new LinkedHashMap<>();
values.put("name",        "OXIDE_ETCH_V1");
values.put("description", "산화막 식각 레시피");
values.put("active",      "true");

StoreResponse res = xmlStore.insert("recipe", values);
// res.getData() → { "rowId": 1 }
```

### 5-2. SELECT ALL

```java
StoreResponse res = xmlStore.findAll("recipe");
// res.getData() → List<Map<String,String>>
// 각 Map 에는 rowId + 모든 컬럼 포함
```

**응답 예시:**
```json
{
  "success": "success",
  "message": "조회 성공",
  "data": [
    { "rowId": "1", "name": "OXIDE_ETCH_V1", "description": "산화막 식각", "active": "true" },
    { "rowId": "2", "name": "NITRIDE_DEP_V1", "description": "질화막 증착", "active": "false" }
  ]
}
```

### 5-3. SELECT BY rowId

```java
StoreResponse res = xmlStore.findById("recipe", 1L);
// res.getData() → Map<String,String> (단건)
```

### 5-4. 컬럼 검색 (부분 일치)

```java
// 단일 컬럼 검색
StoreResponse res = xmlStore.findBy("recipe", "name", "OXIDE");

// 복수 컬럼 AND 검색
Map<String, String> criteria = Map.of(
    "name",   "OXIDE",
    "active", "true"
);
StoreResponse res = xmlStore.findByMultiple("recipe", criteria);
```

### 5-5. UPDATE

```java
Map<String, String> values = Map.of(
    "description", "수정된 설명",
    "active",      "false"
);
StoreResponse res = xmlStore.update("recipe", 1L, values);
// 지정한 컬럼만 업데이트, 나머지는 유지
```

### 5-6. DELETE

```java
StoreResponse res = xmlStore.delete("recipe", 1L);
```

---

## 6. 표준 응답 구조 (StoreResponse)

모든 메서드는 `StoreResponse` 를 반환합니다.

```json
{
  "success": "success",
  "message": "조회 성공",
  "data": [ ... ]
}
```

| 필드      | 값                    | 설명               |
|-----------|-----------------------|--------------------|
| `success` | `"success"` / `"fail"` | 처리 성공 여부     |
| `message` | 문자열                | 처리 결과 메시지   |
| `data`    | List / Map / null     | 반환 데이터        |

**컨트롤러 활용 예시:**

```java
@GetMapping
public ResponseEntity<StoreResponse> list() {
    return ResponseEntity.ok(xmlStore.findAll("recipe"));
}

@PostMapping
public ResponseEntity<StoreResponse> create(@RequestBody Map<String, String> body) {
    StoreResponse res = xmlStore.insert("recipe", body);
    int status = "success".equals(res.getSuccess()) ? 200 : 400;
    return ResponseEntity.status(status).body(res);
}

@GetMapping("/search")
public ResponseEntity<StoreResponse> search(
        @RequestParam String column,
        @RequestParam String keyword) {
    return ResponseEntity.ok(xmlStore.findBy("recipe", column, keyword));
}
```

---

## 7. 클래스 구조

```
com.saffron.store/
  XmlStoreService.java          ← 파사드 (이것만 사용)
  config/
    XmlStoreProperties.java     ← yml 바인딩 (xml-store.*)
  model/
    ColumnDef.java              ← 컬럼 정의
    StoreResponse.java          ← 표준 응답 래퍼
  util/
    XmlStoreHelper.java         ← DOM XML 유틸 (내부 사용)
  api/
    MetaXmlStore.java           ← 메타 XML 관리 (내부)
    DataXmlStore.java           ← 데이터 XML CRUD (내부)
```

---

## 8. 주의사항

- `rowId` 는 자동 증가하며, 삭제 후 재사용되지 않습니다.
- 메타가 없는 subject 에 INSERT 를 시도하면 `IllegalStateException` 이 발생합니다.
- 동시성: 각 서비스 메서드는 `synchronized` 로 스레드 안전하게 처리됩니다.
- 데이터 XML 은 INSERT 시 파일이 없으면 자동으로 생성됩니다.
- 모든 값은 `String` 으로 저장되며, type 은 UI/클라이언트 표시용 힌트입니다.
