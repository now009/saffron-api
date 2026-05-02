package com.saffron.portal.mapper;

import com.saffron.portal.dto.board.BoardFileDto;
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
