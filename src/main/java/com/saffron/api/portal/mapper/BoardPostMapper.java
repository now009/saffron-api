package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.board.BoardPostDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardPostMapper {

    List<BoardPostDto> selectPostList(@Param("boardId") String boardId,
                                      @Param("title") String title,
                                      @Param("category") String category,
                                      @Param("useYn") String useYn,
                                      @Param("currentUser") String currentUser);

    BoardPostDto selectPost(@Param("postId") String postId,
                            @Param("currentUser") String currentUser);

    int insertPost(BoardPostDto postDto);

    int updatePost(BoardPostDto postDto);

    int deletePost(@Param("postId") String postId);

    int countPost(@Param("postId") String postId);

    int incrementViewCount(@Param("postId") String postId);

    int incrementLikeCount(@Param("postId") String postId);

    int decrementLikeCount(@Param("postId") String postId);

    int incrementCommentCount(@Param("postId") String postId);

    int decrementCommentCount(@Param("postId") String postId);

    int incrementAttachCount(@Param("postId") String postId);

    int decrementAttachCount(@Param("postId") String postId);

    String selectNextPostId();

    List<BoardPostDto> selectTopLikedPosts(@Param("boardId") String boardId,
                                           @Param("limit") int limit);
}
