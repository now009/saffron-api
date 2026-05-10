package com.saffron.qbank.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QbankImageStore {

    private final QbankProperties props;

    public record SaveResult(String fileName, String imageUrl) {}

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(props.getImageDir()));
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 폴더 생성 실패: " + props.getImageDir(), e);
        }
    }

    /**
     * base64 데이터 URI(data:image/...;base64,...)면 파일로 저장하고 SaveResult를 반환한다.
     *   - fileName : 저장된 파일명 (uuid.ext)
     *   - imageUrl : HTTP 서빙 경로 (/qbank-images/uuid.ext)
     * base64가 아니면(이미 저장된 경로·null) null을 반환 → 호출부에서 기존 값 유지.
     */
    public SaveResult save(String input) {
        if (input == null || !input.startsWith("data:image/")) {
            return null;
        }

        String[] parts = input.split(",", 2);
        String ext = extractExt(parts[0]);
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(parts[1]);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("이미지 base64 디코딩 실패", e);
        }

        String fileName = UUID.randomUUID() + "." + ext;
        Path dir = Paths.get(props.getImageDir());
        try {
            Files.createDirectories(dir);
            Files.write(dir.resolve(fileName), bytes);
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패: " + e.getMessage(), e);
        }

        return new SaveResult(fileName, props.getImagePath() + "/" + fileName);
    }

    /**
     * MultipartFile을 UUID 파일명으로 저장한다.
     * 원본 파일명(한글 포함)에 관계없이 UUID로 저장하므로 인코딩 문제 없음.
     */
    public SaveResult saveMultipart(MultipartFile file) {
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + (ext != null ? "." + ext : "");
        Path dir = Paths.get(props.getImageDir());
        try {
            Files.createDirectories(dir);
            Files.write(dir.resolve(fileName), file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패: " + e.getMessage(), e);
        }
        return new SaveResult(fileName, props.getImagePath() + "/" + fileName);
    }

    /** imageFileName → HTTP 서빙 경로 (imageUrl 저장용) */
    public String toFilePath(String imageFileName) {
        if (imageFileName == null) return null;
        return props.getImagePath() + "/" + imageFileName;
    }

    // "data:image/png;base64" → "png"
    private String extractExt(String meta) {
        return meta.replaceAll("data:image/([^;]+);.*", "$1");
    }
}
