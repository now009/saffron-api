package com.saffron.store.util;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM 기반 XML CRUD 유틸리티 (com.saffron.store 전용)
 *
 * - 파일 시스템 XML 파싱 / 저장
 * - Element 조작 헬퍼 (getChildElements, getText, setText, newElement)
 * - rowId auto-increment (nextRowId)
 */
public final class XmlStoreHelper {

    private XmlStoreHelper() {}

    // ── 파싱 ──────────────────────────────────────────────────

    /** 파일 시스템 경로에서 Document 파싱 */
    public static Document parseFile(Path path) throws Exception {
        try (InputStream is = Files.newInputStream(path)) {
            return buildParser().parse(is);
        }
    }

    /** 루트 엘리먼트만 가진 빈 Document 생성 */
    public static Document newDocument(String rootTag) throws Exception {
        DocumentBuilder builder = buildParser();
        Document doc = builder.newDocument();
        doc.appendChild(doc.createElement(rootTag));
        return doc;
    }

    private static DocumentBuilder buildParser() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // XXE 방어
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        return factory.newDocumentBuilder();
    }

    // ── 저장 ──────────────────────────────────────────────────

    /** Document 를 파일로 저장 (들여쓰기 포함, UTF-8) */
    public static void writeFile(Document doc, Path path) throws Exception {
        Files.createDirectories(path.getParent());
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,     "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING,   "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        try (OutputStream os = Files.newOutputStream(path)) {
            transformer.transform(new DOMSource(doc), new StreamResult(os));
        }
    }

    // ── Element 조작 ──────────────────────────────────────────

    /**
     * parent 의 직계 자식 중 tagName 에 해당하는 Element 목록 반환
     * (getElementsByTagName 은 깊이 무관 탐색이라 직계만 필요할 때 사용)
     */
    public static List<Element> getChildElements(Element parent, String tagName) {
        List<Element> result = new ArrayList<>();
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && tagName.equals(n.getNodeName())) {
                result.add((Element) n);
            }
        }
        return result;
    }

    /** 자식 태그의 텍스트 내용 반환 (없으면 빈 문자열) */
    public static String getText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) return "";
        String text = nodes.item(0).getTextContent();
        return text != null ? text.trim() : "";
    }

    /** 태그가 존재하면 텍스트 업데이트, 없으면 생성 */
    public static void setText(Document doc, Element parent, String tagName, String value) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        Element el;
        if (nodes.getLength() == 0) {
            el = doc.createElement(tagName);
            parent.appendChild(el);
        } else {
            el = (Element) nodes.item(0);
        }
        el.setTextContent(value != null ? value : "");
    }

    /** 텍스트 값을 가진 새 Element 생성 */
    public static Element newElement(Document doc, String tagName, String value) {
        Element el = doc.createElement(tagName);
        el.setTextContent(value != null ? value : "");
        return el;
    }

    // ── rowId 관리 ────────────────────────────────────────────

    /**
     * 루트 Element 의 lastRowId 속성을 읽어 +1 한 뒤 저장하고 반환
     * (auto-increment PK 생성에 사용)
     */
    public static long nextRowId(Element root) {
        String attr = root.getAttribute("lastRowId");
        long id;
        try {
            id = (attr == null || attr.isEmpty()) ? 1L : Long.parseLong(attr) + 1;
        } catch (NumberFormatException e) {
            id = 1L;
        }
        root.setAttribute("lastRowId", String.valueOf(id));
        return id;
    }

    /** 문자열 → Long 변환 (실패 시 null) */
    public static Long parseLong(String s) {
        if (s == null || s.isEmpty()) return null;
        try { return Long.parseLong(s.trim()); } catch (NumberFormatException e) { return null; }
    }
}
