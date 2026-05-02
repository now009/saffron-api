package com.saffron.portal.dto.role;

/**
 * 역할에 사용자를 할당/해제하기 위한 요청 DTO.
 * useYn = 'Y' 인 사용자만 user_role 테이블에 저장된다.
 */
public class RoleUserRequest {

    private String userId;
    private String useYn;

    public RoleUserRequest() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
}
