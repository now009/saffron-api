package com.saffron.store;

import com.saffron.store.api.DataXmlStore;
import com.saffron.store.api.MetaXmlStore;
import com.saffron.store.model.ColumnDef;
import com.saffron.store.model.StoreResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * XML Store 파사드 서비스
 *
 * com.saffron.* 패키지에서 XML Store 를 사용할 때 이 클래스만 주입받아 사용합니다.
 * MetaXmlStore / DataXmlStore 를 직접 사용하지 않아도 됩니다.
 *
 * <pre>
 * &#64;Autowired XmlStoreService xmlStore;
 *
 * // 1. 메타 정의
 * xmlStore.defineMeta("recipe", List.of(
 *     new ColumnDef("name",        "String", "레시피명", true, null),
 *     new ColumnDef("description", "String", "설명")
 * ));
 *
 * // 2. 데이터 삽입
 * xmlStore.insert("recipe", Map.of("name", "OXIDE_V1", "description", "산화막"));
 *
 * // 3. 전체 조회
 * StoreResponse res = xmlStore.findAll("recipe");
 *
 * // 4. 컬럼 검색
 * StoreResponse res = xmlStore.findBy("recipe", "name", "OXIDE");
 * </pre>
 *
 * 모든 메서드는 StoreResponse 를 반환합니다:
 * <pre>
 * { "success": "success", "message": "...", "data": [...] }
 * </pre>
 */
@Service
public class XmlStoreService {

    private static final Logger log = LoggerFactory.getLogger(XmlStoreService.class);

    private final MetaXmlStore metaStore;
    private final DataXmlStore dataStore;

    public XmlStoreService(MetaXmlStore metaStore, DataXmlStore dataStore) {
        this.metaStore = metaStore;
        this.dataStore = dataStore;
    }

    // ── 메타 관리 ─────────────────────────────────────────────

    /**
     * 메타 정의 생성 (존재하면 덮어씀)
     *
     * @param subject subject 명 (예: "recipe")
     * @param columns 컬럼 정의 목록
     */
    public StoreResponse defineMeta(String subject, List<ColumnDef> columns) {
        try {
            metaStore.createMeta(subject, columns);
            return StoreResponse.ok("메타 생성 완료: " + subject, null);
        } catch (IllegalArgumentException e) {
            return StoreResponse.fail(e.getMessage());
        } catch (Exception e) {
            log.error("[XmlStoreService] defineMeta 오류: subject={}", subject, e);
            return StoreResponse.fail("메타 생성 실패: " + e.getMessage());
        }
    }

    /**
     * 메타 컬럼 정의 조회
     *
     * @param subject subject 명
     */
    public StoreResponse getMetaInfo(String subject) {
        try {
            List<ColumnDef> cols = metaStore.getColumns(subject);
            if (cols.isEmpty() && !metaStore.exists(subject)) {
                return StoreResponse.fail("메타가 존재하지 않습니다: " + subject);
            }
            return StoreResponse.ok("메타 조회 성공", cols);
        } catch (Exception e) {
            log.error("[XmlStoreService] getMetaInfo 오류: subject={}", subject, e);
            return StoreResponse.fail("메타 조회 실패: " + e.getMessage());
        }
    }

    /**
     * 메타 삭제
     *
     * @param subject subject 명
     */
    public StoreResponse deleteMeta(String subject) {
        try {
            boolean deleted = metaStore.deleteMeta(subject);
            return deleted
                ? StoreResponse.ok("메타 삭제 완료: " + subject, null)
                : StoreResponse.fail("메타가 존재하지 않습니다: " + subject);
        } catch (Exception e) {
            log.error("[XmlStoreService] deleteMeta 오류: subject={}", subject, e);
            return StoreResponse.fail("메타 삭제 실패: " + e.getMessage());
        }
    }

    // ── 데이터 CRUD ───────────────────────────────────────────

    /**
     * 데이터 삽입
     *
     * @param subject subject 명
     * @param values  컬럼명 → 값 맵
     * @return 생성된 rowId 포함 응답
     */
    public StoreResponse insert(String subject, Map<String, String> values) {
        try {
            long rowId = dataStore.insert(subject, values);
            Map<String, Object> result = Map.of("rowId", rowId);
            return StoreResponse.ok("삽입 완료", result);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return StoreResponse.fail(e.getMessage());
        } catch (Exception e) {
            log.error("[XmlStoreService] insert 오류: subject={}", subject, e);
            return StoreResponse.fail("삽입 실패: " + e.getMessage());
        }
    }

    /**
     * 전체 행 조회
     *
     * @param subject subject 명
     */
    public StoreResponse findAll(String subject) {
        try {
            List<Map<String, String>> rows = dataStore.findAll(subject);
            return StoreResponse.ok("조회 성공", rows);
        } catch (Exception e) {
            log.error("[XmlStoreService] findAll 오류: subject={}", subject, e);
            return StoreResponse.fail("조회 실패: " + e.getMessage());
        }
    }

    /**
     * rowId 로 단건 조회
     *
     * @param subject subject 명
     * @param rowId   PK
     */
    public StoreResponse findById(String subject, long rowId) {
        try {
            Optional<Map<String, String>> row = dataStore.findById(subject, rowId);
            return row.isPresent()
                ? StoreResponse.ok("조회 성공", row.get())
                : StoreResponse.fail("데이터가 존재하지 않습니다: rowId=" + rowId);
        } catch (Exception e) {
            log.error("[XmlStoreService] findById 오류: subject={} rowId={}", subject, rowId, e);
            return StoreResponse.fail("조회 실패: " + e.getMessage());
        }
    }

    /**
     * 단일 컬럼 키워드 검색 (부분 일치)
     *
     * @param subject    subject 명
     * @param columnName 검색할 컬럼명
     * @param keyword    검색 키워드
     */
    public StoreResponse findBy(String subject, String columnName, String keyword) {
        try {
            List<Map<String, String>> rows = dataStore.findBy(subject, columnName, keyword);
            return StoreResponse.ok("검색 성공 (" + rows.size() + "건)", rows);
        } catch (Exception e) {
            log.error("[XmlStoreService] findBy 오류: subject={}", subject, e);
            return StoreResponse.fail("검색 실패: " + e.getMessage());
        }
    }

    /**
     * 복수 컬럼 AND 검색 (부분 일치)
     *
     * @param subject  subject 명
     * @param criteria 컬럼명 → 키워드 맵
     */
    public StoreResponse findByMultiple(String subject, Map<String, String> criteria) {
        try {
            List<Map<String, String>> rows = dataStore.findByMultiple(subject, criteria);
            return StoreResponse.ok("검색 성공 (" + rows.size() + "건)", rows);
        } catch (Exception e) {
            log.error("[XmlStoreService] findByMultiple 오류: subject={}", subject, e);
            return StoreResponse.fail("검색 실패: " + e.getMessage());
        }
    }

    /**
     * 행 수정
     *
     * @param subject subject 명
     * @param rowId   PK
     * @param values  수정할 컬럼명 → 값 맵
     */
    public StoreResponse update(String subject, long rowId, Map<String, String> values) {
        try {
            boolean updated = dataStore.update(subject, rowId, values);
            return updated
                ? StoreResponse.ok("수정 완료", null)
                : StoreResponse.fail("데이터가 존재하지 않습니다: rowId=" + rowId);
        } catch (Exception e) {
            log.error("[XmlStoreService] update 오류: subject={} rowId={}", subject, rowId, e);
            return StoreResponse.fail("수정 실패: " + e.getMessage());
        }
    }

    /**
     * 행 삭제
     *
     * @param subject subject 명
     * @param rowId   PK
     */
    public StoreResponse delete(String subject, long rowId) {
        try {
            boolean deleted = dataStore.delete(subject, rowId);
            return deleted
                ? StoreResponse.ok("삭제 완료", null)
                : StoreResponse.fail("데이터가 존재하지 않습니다: rowId=" + rowId);
        } catch (Exception e) {
            log.error("[XmlStoreService] delete 오류: subject={} rowId={}", subject, rowId, e);
            return StoreResponse.fail("삭제 실패: " + e.getMessage());
        }
    }
}
