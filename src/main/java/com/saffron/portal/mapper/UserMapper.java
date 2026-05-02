package com.saffron.portal.mapper;

import com.saffron.portal.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserDto> selectUserList(@Param("userId") String userId,
                                 @Param("userName") String userName,
                                 @Param("deptId") String deptId);

    int insertUser(UserDto userDto);

    int updateUser(UserDto userDto);

    int countUser(@Param("userId") String userId);

    int deleteUser(@Param("userId") String userId);

    String selectNextUserId();
}
