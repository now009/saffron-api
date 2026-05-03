package com.saffron.eai.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EaiDbAdapterDef {

    private Long id;
    private String dbAdapterId;
    private String dbType;
    private String dbName;
    private String dbIp;
    private String dbPort;
    private String dbId;
    private String dbPw;
    private String direction;
}
