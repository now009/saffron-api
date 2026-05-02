package com.saffron.portal.mapper;

import com.saffron.portal.dto.board.BoardCommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardCommentMapper {

    List<BoardCommentDto> selectCommentList(@Param("postId") String postId,
                                            @Param("currentUser") String currentUser);

    BoardCommentDto selectComment(@Param("commentId") String commentId);

    int insertComment(BoardCommentDto commentDto);

    int updateComment(BoardCommentDto commentDto);

    int deleteComment(@Param("commentId") String commentId);

    int countComment(@Param("commentId") String commentId);

    int incrementLikeCount(@Param("commentId") String commentId);

    int decrementLikeCount(@Param("commentId") String commentId);

    String selectNextCommentId();
}
