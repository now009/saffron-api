package com.saffron.store.model;

/**
 * 메타 XML 의 컬럼 정의
 *
 * <column name="name" type="String" label="레시피명" required="true" />
 */
public class ColumnDef {

    private String  name;          // 컬럼 키 (XML 태그명으로 사용)
    private String  type;          // 데이터 타입 힌트 (String / Long / Boolean / Date 등)
    private String  label;         // UI 표시명
    private boolean required;      // 필수 여부
    private String  defaultValue;  // 기본값 (선택)

    public ColumnDef() {}

    public ColumnDef(String name, String type, String label) {
        this.name  = name;
        this.type  = type;
        this.label = label;
    }

    public ColumnDef(String name, String type, String label, boolean required, String defaultValue) {
        this.name         = name;
        this.type         = type;
        this.label        = label;
        this.required     = required;
        this.defaultValue = defaultValue;
    }

    public String  getName()                       { return name; }
    public void    setName(String name)             { this.name = name; }

    public String  getType()                       { return type; }
    public void    setType(String type)             { this.type = type; }

    public String  getLabel()                      { return label; }
    public void    setLabel(String label)           { this.label = label; }

    public boolean isRequired()                    { return required; }
    public void    setRequired(boolean required)    { this.required = required; }

    public String  getDefaultValue()               { return defaultValue; }
    public void    setDefaultValue(String v)        { this.defaultValue = v; }

    @Override
    public String toString() {
        return "ColumnDef{name='" + name + "', type='" + type + "', label='" + label + "'}";
    }
}
