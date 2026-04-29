package com.saffron.api.portal.mapper;

import com.saffron.api.portal.dto.board.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardFileMapper {

    List<BoardFileDto> selectFilesByPost(@Param("postId") String postId);

    BoardFileDto selectFile(@Param("fileId") String fileId);

    int insertFile(BoardFileDto fileDto);

    int deleteFile(@Param("fileId") String fileId);

    int deleteFilesByPost(@Param("postId") String postId);

    int countFile(@Param("fileId") String fileId);

    String selectNextFileId();
}
