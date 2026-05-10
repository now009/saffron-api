package com.saffron.qbank.controller;

import com.saffron.qbank.config.QbankProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class QbankImageController {

    private final QbankProperties props;

    // @PathVariable이 URL 디코딩을 자동 처리 → 한글 파일명 정상 조회
    @GetMapping("${qbank.image-path}/{fileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) {
        Path filePath = Paths.get(props.getImageDir()).resolve(fileName).normalize();

        // 디렉토리 탈출 방지 (path traversal)
        if (!filePath.startsWith(Paths.get(props.getImageDir()).normalize())) {
            return ResponseEntity.badRequest().build();
        }

        Resource resource = new FileSystemResource(filePath);
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }
}
