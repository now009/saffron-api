package com.saffron.portal.controller.notice;

import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.dto.notice.NoticeDto;
import com.saffron.portal.service.notice.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<NoticeDto>> list(@RequestBody(required = false) Map<String, String> params) {
        String title      = params != null ? params.get("title")      : null;
        String noticeType = params != null ? params.get("noticeType") : null;
        String useYn      = params != null ? params.get("useYn")      : null;
        return ResponseEntity.ok(noticeService.getNotices(title, noticeType, useYn));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDto> detail(@PathVariable String noticeId) {
        return ResponseEntity.ok(noticeService.getNotice(noticeId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody NoticeDto noticeDto) {
        ApiResponse result = noticeService.saveNotice(noticeDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(noticeService.getNotices(null, null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody NoticeDto noticeDto) {
        ApiResponse result = noticeService.updateNotice(noticeDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(noticeService.getNotices(null, null, null));
    }

    @PostMapping("/delete/{noticeId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> nextId() {
        return ResponseEntity.ok(Map.of("noticeId", noticeService.getNextNoticeId()));
    }
}
