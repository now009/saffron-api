package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.board.BoardMasterDto;
import com.saffron.api.portal.dto.board.BoardStatsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMasterMapper {

    List<BoardMasterDto> selectBoardList(@Param("boardName") String boardName,
                                         @Param("boardType") String boardType,
                                         @Param("useYn") String useYn);

    BoardMasterDto selectBoard(@Param("boardId") String boardId);

    int insertBoard(BoardMasterDto boardDto);

    int updateBoard(BoardMasterDto boardDto);

    int deleteBoard(@Param("boardId") String boardId);

    int countBoard(@Param("boardId") String boardId);

    String selectNextBoardId();

    List<BoardStatsDto> selectBoardStats();
}
