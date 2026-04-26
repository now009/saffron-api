package com.saffron.api.portal.service.user;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(String userId, String userName, String deptId);

    ApiResponse saveUser(UserDto userDto);

    ApiResponse updateUser(UserDto userDto);

    ApiResponse deleteUser(String userId);

    ApiResponse checkUserId(String userId);

    String getNextUserId();
}
