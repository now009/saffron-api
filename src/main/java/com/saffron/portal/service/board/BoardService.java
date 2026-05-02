package com.saffron.portal.service.board;

import com.saffron.portal.dto.board.*;
import com.saffron.portal.dto.common.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    // board_master
    List<BoardMasterDto> getBoards(String boardName, String boardType, String useYn);
    BoardMasterDto getBoard(String boardId);
    ApiResponse saveBoard(BoardMasterDto boardDto);
    ApiResponse updateBoard(BoardMasterDto boardDto);
    ApiResponse deleteBoard(String boardId);
    String getNextBoardId();
    List<BoardStatsDto> getBoardStats();

    // board_post
    List<BoardPostDto> getPosts(String boardId, String title, String category, String useYn, String currentUser);
    BoardPostDto getPost(String postId, String currentUser);
    ApiResponse savePost(BoardPostDto postDto);
    ApiResponse updatePost(BoardPostDto postDto);
    ApiResponse deletePost(String postId);
    String getNextPostId();
    List<BoardPostDto> getTopLikedPosts(String boardId, int limit);

    // board_file
    List<BoardFileDto> getFilesByPost(String postId);
    BoardFileDto getFile(String fileId);
    List<BoardFileDto> uploadFiles(String postId, String boardId, String createdUser, MultipartFile[] files);
    ApiResponse deleteFile(String fileId);

    // board_comment
    List<BoardCommentDto> getComments(String postId, String currentUser);
    ApiResponse saveComment(BoardCommentDto commentDto);
    ApiResponse updateComment(BoardCommentDto commentDto);
    ApiResponse deleteComment(String commentId);
    String getNextCommentId();

    // board_like
    boolean isLiked(String targetType, String targetId, String createdUser);
    Map<String, Object> toggleLike(BoardLikeDto likeDto);
}
