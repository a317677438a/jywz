package xft.workbench.backstage.base.action;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.ResponseEntity;

public class Response {
    private static <T> ImmutableMap<String, Object> buildData
            (boolean success, String message, T data) {

        return ImmutableMap.of(
                "success", success ? "true" : "false",
                "returnmsg", message,
                "returndata", data == null ? "" : data);
    }

    public static <T> ResponseEntity ok(String message, T data) {
        return ResponseEntity.ok(buildData(true, message, data));
    }

    public static ResponseEntity bad(String message) {
        return ResponseEntity.ok(buildData(false, message, null));
    }

    public static <T> ResponseEntity build(boolean success, String message, T data) {
        return ResponseEntity.ok(buildData(success, message, data));
    }

}
