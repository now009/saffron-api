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
import java.util.ArrayList;
import java.util.List;

/**
 * 메타 XML 관리 서비스
 *
 * 파일 위치: {xml-store.meta-path}/{subject}-meta.xml
 *
 * 메타 XML 구조:
 * <pre>
 * {@code
 * <meta subject="recipe">
 *   <columns>
 *     <column name="name"  type="String"  label="레시피명" required="true" />
 *     <column name="price" type="Long"    label="가격"     defaultValue="0" />
 *   </columns>
 * </meta>
 * }
 * </pre>
 *
 * ※ rowId (PK) 는 DataXmlStore 가 자동 관리하므로 메타에 포함하지 않습니다.
 */
@Service
public class MetaXmlStore {

    private static final Logger log = LoggerFactory.getLogger(MetaXmlStore.class);

    private final XmlStoreProperties props;

    public MetaXmlStore(XmlStoreProperties props) {
        this.props = props;
    }

    // ── 경로 헬퍼 ─────────────────────────────────────────────

    public Path metaPath(String subject) {
        return Paths.get(props.getMetaPath()).resolve(subject + "-meta.xml");
    }

    public boolean exists(String subject) {
        return Files.exists(metaPath(subject));
    }

    // ── 메타 생성 / 수정 ──────────────────────────────────────

    /**
     * 메타 XML 생성 (이미 존재하면 덮어씀)
     *
     * @param subject subject 명 (예: "recipe")
     * @param columns 컬럼 정의 목록
     */
    public synchronized void createMeta(String subject, List<ColumnDef> columns) throws Exception {
        if (subject == null || subject.isBlank()) throw new IllegalArgumentException("subject 는 필수입니다.");
        if (columns == null || columns.isEmpty())  throw new IllegalArgumentException("columns 는 1개 이상이어야 합니다.");

        Document doc  = XmlStoreHelper.newDocument("meta");
        Element  root = doc.getDocumentElement();
        root.setAttribute("subject", subject);

        Element columnsEl = doc.createElement("columns");
        root.appendChild(columnsEl);

        for (ColumnDef col : columns) {
            if (col.getName() == null || col.getName().isBlank()) continue;
            Element colEl = doc.createElement("column");
            colEl.setAttribute("name",  col.getName());
            colEl.setAttribute("type",  col.getType()  != null ? col.getType()  : "String");
            colEl.setAttribute("label", col.getLabel() != null ? col.getLabel() : col.getName());
            if (col.isRequired())                            colEl.setAttribute("required",     "true");
            if (col.getDefaultValue() != null)               colEl.setAttribute("defaultValue", col.getDefaultValue());
            columnsEl.appendChild(colEl);
        }

        XmlStoreHelper.writeFile(doc, metaPath(subject));
        log.info("[MetaXmlStore] 메타 생성: subject={}, columns={}", subject, columns.size());
    }

    // ── 메타 조회 ─────────────────────────────────────────────

    /**
     * 메타 XML 에서 컬럼 정의 목록 읽기
     *
     * @param subject subject 명
     * @return 컬럼 정의 목록 (없으면 빈 리스트)
     */
    public synchronized List<ColumnDef> getColumns(String subject) throws Exception {
        Path path = metaPath(subject);
        if (!Files.exists(path)) {
            log.warn("[MetaXmlStore] 메타 파일 없음: {}", path);
            return new ArrayList<>();
        }

        Document doc     = XmlStoreHelper.parseFile(path);
        Element  root    = doc.getDocumentElement();
        Element  colsEl  = (Element) root.getElementsByTagName("columns").item(0);
        if (colsEl == null) return new ArrayList<>();

        List<ColumnDef> result = new ArrayList<>();
        for (Element colEl : XmlStoreHelper.getChildElements(colsEl, "column")) {
            ColumnDef def = new ColumnDef();
            def.setName(colEl.getAttribute("name"));
            def.setType(colEl.getAttribute("type"));
            def.setLabel(colEl.getAttribute("label"));
            def.setRequired("true".equalsIgnoreCase(colEl.getAttribute("required")));
            String dv = colEl.getAttribute("defaultValue");
            def.setDefaultValue(dv.isEmpty() ? null : dv);
            result.add(def);
        }
        return result;
    }

    // ── 메타 삭제 ─────────────────────────────────────────────

    /**
     * 메타 XML 삭제
     *
     * @param subject subject 명
     * @return 삭제 성공 여부
     */
    public synchronized boolean deleteMeta(String subject) throws Exception {
        Path path = metaPath(subject);
        if (!Files.exists(path)) return false;
        Files.delete(path);
        log.info("[MetaXmlStore] 메타 삭제: subject={}", subject);
        return true;
    }
}
