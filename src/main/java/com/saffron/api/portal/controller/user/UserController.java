package com.saffron.api.portal.controller.user;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.user.UserDto;
import com.saffron.api.portal.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<UserDto>> list(@RequestBody(required = false) Map<String, String> params) {
        String userId   = params != null ? params.get("userId")   : null;
        String userName = params != null ? params.get("userName") : null;
        String deptId   = params != null ? params.get("deptId")   : null;
        return ResponseEntity.ok(userService.getUsers(userId, userName, deptId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        ApiResponse result = userService.saveUser(userDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(userService.getUsers(null, null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        ApiResponse result = userService.updateUser(userDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(userService.getUsers(null, null, null));
    }

    @PostMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @GetMapping("/check-id/{userId}")
    public ResponseEntity<ApiResponse> checkId(@PathVariable String userId) {
        return ResponseEntity.ok(userService.checkUserId(userId));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("userId", userService.getNextUserId()));
    }
}
