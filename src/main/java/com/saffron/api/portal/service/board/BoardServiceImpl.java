package com.saffron.api.portal.service.board;

import com.saffron.api.portal.dto.board.*;
import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.mapper.BoardCommentMapper;
import com.saffron.api.portal.mapper.BoardFileMapper;
import com.saffron.api.portal.mapper.BoardLikeMapper;
import com.saffron.api.portal.mapper.BoardMasterMapper;
import com.saffron.api.portal.mapper.BoardPostMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMasterMapper boardMasterMapper;
    private final BoardPostMapper boardPostMapper;
    private final BoardFileMapper boardFileMapper;
    private final BoardCommentMapper boardCommentMapper;
    private final BoardLikeMapper boardLikeMapper;
    private final String filePath;

    public BoardServiceImpl(BoardMasterMapper boardMasterMapper,
                            BoardPostMapper boardPostMapper,
                            BoardFileMapper boardFileMapper,
                            BoardCommentMapper boardCommentMapper,
                            BoardLikeMapper boardLikeMapper,
                            @Value("${board.filePath}") String filePath) {
        this.boardMasterMapper = boardMasterMapper;
        this.boardPostMapper = boardPostMapper;
        this.boardFileMapper = boardFileMapper;
        this.boardCommentMapper = boardCommentMapper;
        this.boardLikeMapper = boardLikeMapper;
        this.filePath = filePath;
    }

    // ===================== board_master =====================
    @Override
    public List<BoardMasterDto> getBoards(String boardName, String boardType, String useYn) {
        return boardMasterMapper.selectBoardList(boardName, boardType, useYn);
    }

    @Override
    public BoardMasterDto getBoard(String boardId) {
        return boardMasterMapper.selectBoard(boardId);
    }

    @Override
    public ApiResponse saveBoard(BoardMasterDto boardDto) {
        if (boardMasterMapper.countBoard(boardDto.getBoardId()) > 0) {
            return ApiResponse.fail("이미 존재하는 게시판ID입니다");
        }
        try {
            boardMasterMapper.insertBoard(boardDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateBoard(BoardMasterDto boardDto) {
        if (boardMasterMapper.countBoard(boardDto.getBoardId()) == 0) {
            return ApiResponse.fail("게시판이 존재하지 않습니다");
        }
        try {
            boardMasterMapper.updateBoard(boardDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteBoard(String boardId) {
        if (boardMasterMapper.countBoard(boardId) == 0) {
            return ApiResponse.fail("게시판이 존재하지 않습니다");
        }
        boardMasterMapper.deleteBoard(boardId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public String getNextBoardId() {
        return boardMasterMapper.selectNextBoardId();
    }

    @Override
    public List<BoardStatsDto> getBoardStats() {
        return boardMasterMapper.selectBoardStats();
    }

    // ===================== board_post =====================
    @Override
    public List<BoardPostDto> getPosts(String boardId, String title, String category, String useYn, String currentUser) {
        return boardPostMapper.selectPostList(boardId, title, category, useYn, currentUser);
    }

    @Override
    public BoardPostDto getPost(String postId, String currentUser) {
        BoardPostDto post = boardPostMapper.selectPost(postId, currentUser);
        if (post != null) {
            boardPostMapper.incrementViewCount(postId);
        }
        return post;
    }

    @Override
    public ApiResponse savePost(BoardPostDto postDto) {
        if (boardPostMapper.countPost(postDto.getPostId()) > 0) {
            return ApiResponse.fail("이미 존재하는 게시글ID입니다");
        }
        try {
            boardPostMapper.insertPost(postDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updatePost(BoardPostDto postDto) {
        if (boardPostMapper.countPost(postDto.getPostId()) == 0) {
            return ApiResponse.fail("게시글이 존재하지 않습니다");
        }
        try {
            boardPostMapper.updatePost(postDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deletePost(String postId) {
        if (boardPostMapper.countPost(postId) == 0) {
            return ApiResponse.fail("게시글이 존재하지 않습니다");
        }
        boardFileMapper.deleteFilesByPost(postId);
        boardPostMapper.deletePost(postId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public String getNextPostId() {
        return boardPostMapper.selectNextPostId();
    }

    @Override
    public List<BoardPostDto> getTopLikedPosts(String boardId, int limit) {
        return boardPostMapper.selectTopLikedPosts(boardId, limit);
    }

    // ===================== board_file =====================
    @Override
    public List<BoardFileDto> getFilesByPost(String postId) {
        return boardFileMapper.selectFilesByPost(postId);
    }

    @Override
    public BoardFileDto getFile(String fileId) {
        return boardFileMapper.selectFile(fileId);
    }

    @Override
    public List<BoardFileDto> uploadFiles(String postId, String boardId, String createdUser, MultipartFile[] files) {
        List<BoardFileDto> saved = new ArrayList<>();
        if (files == null || files.length == 0) return saved;

        String yyyyMM = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String relativeDir = boardId + "/" + yyyyMM + "/";
        Path dir = Paths.get(filePath, boardId, yyyyMM);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 디렉토리 생성 실패: " + e.getMessage(), e);
        }

        int sortOrder = 1;
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) continue;
            String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown";
            String ext = "";
            int dotIdx = original.lastIndexOf('.');
            if (dotIdx > -1 && dotIdx < original.length() - 1) {
                ext = original.substring(dotIdx + 1).toLowerCase();
            }
            String savedName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
            Path target = dir.resolve(savedName);
            try {
                file.transferTo(target.toFile());
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
            }

            BoardFileDto dto = new BoardFileDto();
            dto.setFileId(boardFileMapper.selectNextFileId());
            dto.setPostId(postId);
            dto.setBoardId(boardId);
            dto.setOriginalName(original);
            dto.setSavedName(savedName);
            dto.setFilePath(relativeDir);
            dto.setFileSize(file.getSize());
            dto.setFileExt(ext);
            dto.setSortOrder(sortOrder++);
            dto.setCreatedUser(createdUser);

            boardFileMapper.insertFile(dto);
            boardPostMapper.incrementAttachCount(postId);
            saved.add(dto);
        }
        return saved;
    }

    @Override
    public ApiResponse deleteFile(String fileId) {
        BoardFileDto file = boardFileMapper.selectFile(fileId);
        if (file == null) {
            return ApiResponse.fail("파일이 존재하지 않습니다");
        }
        try {
            Path path = Paths.get(filePath, file.getFilePath(), file.getSavedName()).normalize();
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
        boardFileMapper.deleteFile(fileId);
        boardPostMapper.decrementAttachCount(file.getPostId());
        return ApiResponse.success("삭제되었습니다");
    }

    // ===================== board_comment =====================
    @Override
    public List<BoardCommentDto> getComments(String postId, String currentUser) {
        return boardCommentMapper.selectCommentList(postId, currentUser);
    }

    @Override
    public ApiResponse saveComment(BoardCommentDto commentDto) {
        if (boardCommentMapper.countComment(commentDto.getCommentId()) > 0) {
            return ApiResponse.fail("이미 존재하는 댓글ID입니다");
        }
        try {
            boardCommentMapper.insertComment(commentDto);
            boardPostMapper.incrementCommentCount(commentDto.getPostId());
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateComment(BoardCommentDto commentDto) {
        if (boardCommentMapper.countComment(commentDto.getCommentId()) == 0) {
            return ApiResponse.fail("댓글이 존재하지 않습니다");
        }
        try {
            boardCommentMapper.updateComment(commentDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteComment(String commentId) {
        BoardCommentDto comment = boardCommentMapper.selectComment(commentId);
        if (comment == null) {
            return ApiResponse.fail("댓글이 존재하지 않습니다");
        }
        boardCommentMapper.deleteComment(commentId);
        boardPostMapper.decrementCommentCount(comment.getPostId());
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public String getNextCommentId() {
        return boardCommentMapper.selectNextCommentId();
    }

    // ===================== board_like =====================
    @Override
    public boolean isLiked(String targetType, String targetId, String createdUser) {
        return boardLikeMapper.countLike(targetType, targetId, createdUser) > 0;
    }

    @Override
    public Map<String, Object> toggleLike(BoardLikeDto likeDto) {
        Map<String, Object> result = new LinkedHashMap<>();
        boolean liked = boardLikeMapper.countLike(
                likeDto.getTargetType(), likeDto.getTargetId(), likeDto.getCreatedUser()) > 0;

        if (liked) {
            boardLikeMapper.deleteLike(
                    likeDto.getTargetType(), likeDto.getTargetId(), likeDto.getCreatedUser());
            decrementLikeTargetCount(likeDto);
            result.put("isLiked", "N");
            result.put("message", "좋아요가 취소되었습니다");
        } else {
            likeDto.setLikeId(boardLikeMapper.selectNextLikeId());
            boardLikeMapper.insertLike(likeDto);
            incrementLikeTargetCount(likeDto);
            result.put("isLiked", "Y");
            result.put("likeId", likeDto.getLikeId());
            result.put("message", "좋아요가 등록되었습니다");
        }
        return result;
    }

    private void incrementLikeTargetCount(BoardLikeDto likeDto) {
        if ("POST".equals(likeDto.getTargetType())) {
            boardPostMapper.incrementLikeCount(likeDto.getTargetId());
        } else if ("COMMENT".equals(likeDto.getTargetType())) {
            boardCommentMapper.incrementLikeCount(likeDto.getTargetId());
        }
    }

    private void decrementLikeTargetCount(BoardLikeDto likeDto) {
        if ("POST".equals(likeDto.getTargetType())) {
            boardPostMapper.decrementLikeCount(likeDto.getTargetId());
        } else if ("COMMENT".equals(likeDto.getTargetType())) {
            boardCommentMapper.decrementLikeCount(likeDto.getTargetId());
        }
    }
}
