package com.saffron.portal.controller.board;

import com.saffron.portal.dto.board.*;
import com.saffron.portal.dto.common.ApiResponse;
import com.saffron.portal.service.board.BoardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/portal/boards")
public class BoardController {

    private final BoardService boardService;
    private final String filePath;

    public BoardController(BoardService boardService,
                           @Value("${board.filePath}") String filePath) {
        this.boardService = boardService;
        this.filePath = filePath;
    }

    // ===================== board_master =====================
    @PostMapping("/list")
    public ResponseEntity<List<BoardMasterDto>> boardList(@RequestBody(required = false) Map<String, String> params) {
        String boardName = params != null ? params.get("boardName") : null;
        String boardType = params != null ? params.get("boardType") : null;
        String useYn     = params != null ? params.get("useYn")     : null;
        return ResponseEntity.ok(boardService.getBoards(boardName, boardType, useYn));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardMasterDto> boardDetail(@PathVariable String boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> boardSave(@RequestBody BoardMasterDto boardDto) {
        ApiResponse result = boardService.saveBoard(boardDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(boardService.getBoards(null, null, null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> boardUpdate(@RequestBody BoardMasterDto boardDto) {
        ApiResponse result = boardService.updateBoard(boardDto);
        if ("fail".equals(result.getMessageCode())) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(boardService.getBoards(null, null, null));
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity<ApiResponse> boardDelete(@PathVariable String boardId) {
        return ResponseEntity.ok(boardService.deleteBoard(boardId));
    }

    @GetMapping("/next-id")
    public ResponseEntity<Map<String, String>> boardNextId() {
        return ResponseEntity.ok(Map.of("boardId", boardService.getNextBoardId()));
    }

    @GetMapping("/stats")
    public ResponseEntity<List<BoardStatsDto>> boardStats() {
        return ResponseEntity.ok(boardService.getBoardStats());
    }

    // ===================== board_post =====================
    @PostMapping("/posts/list")
    public ResponseEntity<List<BoardPostDto>> postList(@RequestBody(required = false) Map<String, String> params) {
        String boardId     = params != null ? params.get("boardId")     : null;
        String title       = params != null ? params.get("title")       : null;
        String category    = params != null ? params.get("category")    : null;
        String useYn       = params != null ? params.get("useYn")       : null;
        String currentUser = params != null ? params.get("currentUser") : null;
        return ResponseEntity.ok(boardService.getPosts(boardId, title, category, useYn, currentUser));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<BoardPostDto> postDetail(@PathVariable String postId,
                                                   @RequestParam(required = false) String currentUser) {
        return ResponseEntity.ok(boardService.getPost(postId, currentUser));
    }

    @PostMapping("/posts/save")
    public ResponseEntity<?> postSave(@RequestBody BoardPostDto postDto) {
        return ResponseEntity.ok(boardService.savePost(postDto));
    }

    @PostMapping("/posts/update")
    public ResponseEntity<?> postUpdate(@RequestBody BoardPostDto postDto) {
        return ResponseEntity.ok(boardService.updatePost(postDto));
    }

    @PostMapping("/posts/delete/{postId}")
    public ResponseEntity<ApiResponse> postDelete(@PathVariable String postId) {
        return ResponseEntity.ok(boardService.deletePost(postId));
    }

    @GetMapping("/posts/next-id")
    public ResponseEntity<Map<String, String>> postNextId() {
        return ResponseEntity.ok(Map.of("postId", boardService.getNextPostId()));
    }

    @GetMapping("/posts/top-likes")
    public ResponseEntity<List<BoardPostDto>> postTopLikes(
            @RequestParam(required = false) String boardId,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(boardService.getTopLikedPosts(boardId, limit));
    }

    // ===================== board_file =====================
    @GetMapping("/files/post/{postId}")
    public ResponseEntity<List<BoardFileDto>> fileListByPost(@PathVariable String postId) {
        return ResponseEntity.ok(boardService.getFilesByPost(postId));
    }

    @PostMapping("/files/upload")
    public ResponseEntity<List<BoardFileDto>> fileUpload(
            @RequestParam("postId") String postId,
            @RequestParam("boardId") String boardId,
            @RequestParam(value = "createdUser", required = false, defaultValue = "system") String createdUser,
            @RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(boardService.uploadFiles(postId, boardId, createdUser, files));
    }

    @PostMapping("/files/delete/{fileId}")
    public ResponseEntity<ApiResponse> fileDelete(@PathVariable String fileId) {
        return ResponseEntity.ok(boardService.deleteFile(fileId));
    }

    @GetMapping("/files/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable String fileId) {
        BoardFileDto file = boardService.getFile(fileId);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        Path path = Paths.get(filePath, file.getFilePath(), file.getSavedName()).normalize();
        FileSystemResource resource = new FileSystemResource(path);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String encodedName;
        try {
            encodedName = URLEncoder.encode(file.getOriginalName(), StandardCharsets.UTF_8.name())
                    .replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            encodedName = file.getOriginalName();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.getFileSize())
                .body(resource);
    }

    // ===================== board_comment =====================
    @PostMapping("/comments/list")
    public ResponseEntity<List<BoardCommentDto>> commentList(@RequestBody Map<String, String> params) {
        String postId      = params.get("postId");
        String currentUser = params.get("currentUser");
        return ResponseEntity.ok(boardService.getComments(postId, currentUser));
    }

    @PostMapping("/comments/save")
    public ResponseEntity<?> commentSave(@RequestBody BoardCommentDto commentDto) {
        return ResponseEntity.ok(boardService.saveComment(commentDto));
    }

    @PostMapping("/comments/update")
    public ResponseEntity<?> commentUpdate(@RequestBody BoardCommentDto commentDto) {
        return ResponseEntity.ok(boardService.updateComment(commentDto));
    }

    @PostMapping("/comments/delete/{commentId}")
    public ResponseEntity<ApiResponse> commentDelete(@PathVariable String commentId) {
        return ResponseEntity.ok(boardService.deleteComment(commentId));
    }

    @GetMapping("/comments/next-id")
    public ResponseEntity<Map<String, String>> commentNextId() {
        return ResponseEntity.ok(Map.of("commentId", boardService.getNextCommentId()));
    }

    // ===================== board_like =====================
    @PostMapping("/likes/check")
    public ResponseEntity<Map<String, Object>> likeCheck(@RequestBody Map<String, String> params) {
        String targetType  = params.get("targetType");
        String targetId    = params.get("targetId");
        String createdUser = params.get("createdUser");
        boolean liked = boardService.isLiked(targetType, targetId, createdUser);
        return ResponseEntity.ok(Map.of("isLiked", liked ? "Y" : "N"));
    }

    @PostMapping("/likes/toggle")
    public ResponseEntity<Map<String, Object>> likeToggle(@RequestBody BoardLikeDto likeDto) {
        return ResponseEntity.ok(boardService.toggleLike(likeDto));
    }
}
