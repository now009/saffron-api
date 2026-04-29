package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.board.BoardLikeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardLikeMapper {

    int countLike(@Param("targetType") String targetType,
                  @Param("targetId") String targetId,
                  @Param("createdUser") String createdUser);

    int insertLike(BoardLikeDto likeDto);

    int deleteLike(@Param("targetType") String targetType,
                   @Param("targetId") String targetId,
                   @Param("createdUser") String createdUser);

    String selectNextLikeId();
}
