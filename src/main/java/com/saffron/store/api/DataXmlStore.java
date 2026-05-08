package com.saffron.store.api;

import com.saffron.store.config.XmlStoreProperties;
import com.saffron.store.model.ColumnDef;
import com.saffron.store.util.XmlStoreHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 데이터 XML CRUD 서비스
 *
 * 파일 위치: {xml-store.data-path}/{subject}-data.xml
 *
 * 데이터 XML 구조:
 * <pre>
 * {@code
 * <data subject="recipe" lastRowId="3">
 *   <row rowId="1">
 *     <name>OXIDE_ETCH_V1</name>
 *     <description>산화막 식각</description>
 *   </row>
 *   <row rowId="2">
 *     ...
 *   </row>
 * </data>
 * }
 * </pre>
 *
 * ▶ rowId : 자동 증가 PK (DataXmlStore 가 관리, 직접 지정 불가)
 * ▶ 컬럼  : MetaXmlStore 에서 읽은 ColumnDef 기준으로 저장/조회
 */
@Service
public class DataXmlStore {

    private static final Logger log = LoggerFactory.getLogger(DataXmlStore.class);

    private static final String TAG_ROW    = "row";
    private static final String ATTR_ROWID = "rowId";

    private final XmlStoreProperties props;
    private final MetaXmlStore       metaStore;

    public DataXmlStore(XmlStoreProperties props, MetaXmlStore metaStore) {
        this.props     = props;
        this.metaStore = metaStore;
    }

    // ── 경로 헬퍼 ─────────────────────────────────────────────

    private Path dataPath(String subject) {
        return Paths.get(props.getDataPath()).resolve(subject + "-data.xml");
    }

    // ── 데이터 파일 초기화 (최초 삽입 시 자동 생성) ───────────

    private Document loadOrCreate(String subject) throws Exception {
        Path path = dataPath(subject);
        if (Files.exists(path)) {
            return XmlStoreHelper.parseFile(path);
        }
        Document doc  = XmlStoreHelper.newDocument("data");
        Element  root = doc.getDocumentElement();
        root.setAttribute("subject",   subject);
        root.setAttribute("lastRowId", "0");
        return doc;
    }

    // ── INSERT ────────────────────────────────────────────────

    /**
     * 데이터 행 삽입
     *
     * @param subject subject 명
     * @param values  컬럼명 → 값 맵 (메타에 없는 컬럼은 무시)
     * @return 삽입된 행의 rowId
     */
    public synchronized long insert(String subject, Map<String, String> values) throws Exception {
        List<ColumnDef> cols = metaStore.getColumns(subject);
        if (cols.isEmpty()) throw new IllegalStateException("메타가 존재하지 않습니다: " + subject);

        // 필수 컬럼 검증
        for (ColumnDef col : cols) {
            if (col.isRequired()) {
                String v = values.get(col.getName());
                if (v == null || v.isBlank()) {
                    throw new IllegalArgumentException("필수 컬럼 누락: " + col.getName());
                }
            }
        }

        Document doc  = loadOrCreate(subject);
        Element  root = doc.getDocumentElement();
        long     rowId = XmlStoreHelper.nextRowId(root);

        Element rowEl = doc.createElement(TAG_ROW);
        rowEl.setAttribute(ATTR_ROWID, String.valueOf(rowId));

        for (ColumnDef col : cols) {
            String val = values.getOrDefault(col.getName(),
                         col.getDefaultValue() != null ? col.getDefaultValue() : "");
            rowEl.appendChild(XmlStoreHelper.newElement(doc, col.getName(), val));
        }

        root.appendChild(rowEl);
        XmlStoreHelper.writeFile(doc, dataPath(subject));
        log.debug("[DataXmlStore] INSERT subject={} rowId={}", subject, rowId);
        return rowId;
    }

    // ── SELECT ALL ────────────────────────────────────────────

    /**
     * 전체 행 조회
     *
     * @param subject subject 명
     * @return rowId 포함 전체 행 목록
     */
    public synchronized List<Map<String, String>> findAll(String subject) throws Exception {
        Path path = dataPath(subject);
        if (!Files.exists(path)) return new ArrayList<>();

        Document        doc  = XmlStoreHelper.parseFile(path);
        Element         root = doc.getDocumentElement();
        List<ColumnDef> cols = metaStore.getColumns(subject);

        List<Map<String, String>> result = new ArrayList<>();
        for (Element rowEl : XmlStoreHelper.getChildElements(root, TAG_ROW)) {
            result.add(toMap(rowEl, cols));
        }
        return result;
    }

    // ── SELECT BY rowId ───────────────────────────────────────

    /**
     * rowId 로 단건 조회
     *
     * @param subject subject 명
     * @param rowId   PK
     * @return 행 데이터 맵 (없으면 empty Optional)
     */
    public synchronized Optional<Map<String, String>> findById(String subject, long rowId) throws Exception {
        Path path = dataPath(subject);
        if (!Files.exists(path)) return Optional.empty();

        Document        doc  = XmlStoreHelper.parseFile(path);
        Element         root = doc.getDocumentElement();
        List<ColumnDef> cols = metaStore.getColumns(subject);

        for (Element rowEl : XmlStoreHelper.getChildElements(root, TAG_ROW)) {
            if (rowId == parseRowId(rowEl)) {
                return Optional.of(toMap(rowEl, cols));
            }
        }
        return Optional.empty();
    }

    // ── SELECT BY 컬럼 값 (검색) ──────────────────────────────

    /**
     * 특정 컬럼 값으로 검색 (부분 일치)
     *
     * @param subject    subject 명
     * @param columnName 검색할 컬럼명
     * @param keyword    검색 키워드 (null 또는 빈 문자열이면 전체 반환)
     * @return 매칭된 행 목록
     */
    public synchronized List<Map<String, String>> findBy(String subject,
                                                         String columnName,
                                                         String keyword) throws Exception {
        List<Map<String, String>> all = findAll(subject);
        if (keyword == null || keyword.isBlank()) return all;

        String lower = keyword.toLowerCase();
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> row : all) {
            String val = row.getOrDefault(columnName, "");
            if (val.toLowerCase().contains(lower)) {
                result.add(row);
            }
        }
        return result;
    }

    /**
     * 복수 조건으로 검색 (AND 조건, 모두 부분 일치)
     *
     * @param subject  subject 명
     * @param criteria 컬럼명 → 키워드 맵
     * @return 매칭된 행 목록
     */
    public synchronized List<Map<String, String>> findByMultiple(String subject,
                                                                  Map<String, String> criteria) throws Exception {
        List<Map<String, String>> all = findAll(subject);
        if (criteria == null || criteria.isEmpty()) return all;

        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> row : all) {
            boolean match = true;
            for (Map.Entry<String, String> entry : criteria.entrySet()) {
                String keyword = entry.getValue();
                if (keyword == null || keyword.isBlank()) continue;
                String val = row.getOrDefault(entry.getKey(), "");
                if (!val.toLowerCase().contains(keyword.toLowerCase())) {
                    match = false;
                    break;
                }
            }
            if (match) result.add(row);
        }
        return result;
    }

    // ── UPDATE ────────────────────────────────────────────────

    /**
     * rowId 로 행 수정 (존재하는 컬럼만 업데이트)
     *
     * @param subject subject 명
     * @param rowId   PK
     * @param values  수정할 컬럼명 → 값 맵
     * @return 수정 성공 여부
     */
    public synchronized boolean update(String subject, long rowId,
                                       Map<String, String> values) throws Exception {
        Path path = dataPath(subject);
        if (!Files.exists(path)) return false;

        List<ColumnDef> cols = metaStore.getColumns(subject);
        Document        doc  = XmlStoreHelper.parseFile(path);
        Element         root = doc.getDocumentElement();

        for (Element rowEl : XmlStoreHelper.getChildElements(root, TAG_ROW)) {
            if (rowId == parseRowId(rowEl)) {
                for (ColumnDef col : cols) {
                    if (values.containsKey(col.getName())) {
                        XmlStoreHelper.setText(doc, rowEl, col.getName(), values.get(col.getName()));
                    }
                }
                XmlStoreHelper.writeFile(doc, path);
                log.debug("[DataXmlStore] UPDATE subject={} rowId={}", subject, rowId);
                return true;
            }
        }
        return false;
    }

    // ── DELETE ────────────────────────────────────────────────

    /**
     * rowId 로 행 삭제
     *
     * @param subject subject 명
     * @param rowId   PK
     * @return 삭제 성공 여부
     */
    public synchronized boolean delete(String subject, long rowId) throws Exception {
        Path path = dataPath(subject);
        if (!Files.exists(path)) return false;

        Document doc  = XmlStoreHelper.parseFile(path);
        Element  root = doc.getDocumentElement();

        for (Element rowEl : XmlStoreHelper.getChildElements(root, TAG_ROW)) {
            if (rowId == parseRowId(rowEl)) {
                root.removeChild(rowEl);
                XmlStoreHelper.writeFile(doc, path);
                log.debug("[DataXmlStore] DELETE subject={} rowId={}", subject, rowId);
                return true;
            }
        }
        return false;
    }

    // ── 내부 헬퍼 ─────────────────────────────────────────────

    /** Element → Map 변환 (rowId 포함) */
    private Map<String, String> toMap(Element rowEl, List<ColumnDef> cols) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(ATTR_ROWID, rowEl.getAttribute(ATTR_ROWID));
        for (ColumnDef col : cols) {
            map.put(col.getName(), XmlStoreHelper.getText(rowEl, col.getName()));
        }
        return map;
    }

    /** row Element 의 rowId 속성 파싱 */
    private long parseRowId(Element rowEl) {
        Long id = XmlStoreHelper.parseLong(rowEl.getAttribute(ATTR_ROWID));
        return id != null ? id : -1L;
    }
}
