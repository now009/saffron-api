package com.saffron.eai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EaiDbAdapterDefDto {

    private Long id;

    @NotBlank
    private String dbAdapterId;

    @NotBlank
    private String dbType;

    @NotBlank
    private String dbName;

    @NotBlank
    private String dbIp;

    @NotBlank
    private String dbPort;

    @NotBlank
    private String dbId;

    private String dbPw;

    @NotBlank
    private String direction;
}
