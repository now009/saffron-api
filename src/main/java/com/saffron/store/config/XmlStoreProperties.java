package com.saffron.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * XML Store 경로 설정
 *
 * application.yml:
 *   xml-store:
 *     meta-path: C:/xmlStore/meta   # {subject}-meta.xml 저장 폴더
 *     data-path: C:/xmlStore/data   # {subject}-data.xml 저장 폴더
 */
@Component
@ConfigurationProperties(prefix = "xml-store")
public class XmlStoreProperties {

    /** 메타 XML 저장 폴더 — {metaPath}/{subject}-meta.xml */
    private String metaPath = "C:/xmlStore/meta";

    /** 데이터 XML 저장 폴더 — {dataPath}/{subject}-data.xml */
    private String dataPath = "C:/xmlStore/data";

    public String getMetaPath() { return metaPath; }
    public void   setMetaPath(String metaPath) { this.metaPath = metaPath; }

    public String getDataPath() { return dataPath; }
    public void   setDataPath(String dataPath) { this.dataPath = dataPath; }
}
