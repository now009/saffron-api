package com.saffron.store.model;

/**
 * XML Store 표준 응답 래퍼
 *
 * {
 *   "success": "success" | "fail",
 *   "message": "...",
 *   "data": [ ... ] | { ... } | null
 * }
 */
public class StoreResponse {

    private String success;   // "success" or "fail"
    private String message;
    private Object data;      // List<Map> or Map or null

    private StoreResponse() {}

    // ── 정적 팩토리 ─────────────────────────────────────────

    public static StoreResponse ok(Object data) {
        return of("success", "success", data);
    }

    public static StoreResponse ok(String message, Object data) {
        return of("success", message, data);
    }

    public static StoreResponse fail(String message) {
        return of("fail", message, null);
    }

    private static StoreResponse of(String success, String message, Object data) {
        StoreResponse r = new StoreResponse();
        r.success = success;
        r.message = message;
        r.data    = data;
        return r;
    }

    // ── Getter ───────────────────────────────────────────────

    public String getSuccess() { return success; }
    public String getMessage() { return message; }
    public Object getData()    { return data; }
}
