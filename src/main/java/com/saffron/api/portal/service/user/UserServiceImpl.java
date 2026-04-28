package com.saffron.api.portal.service.user;

import com.saffron.api.portal.dto.common.ApiResponse;
import com.saffron.api.portal.dto.user.UserDto;
import com.saffron.api.portal.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getUsers(String userId, String userName, String deptId) {
        return userMapper.selectUserList(userId, userName, deptId);
    }

    @Override
    public ApiResponse saveUser(UserDto userDto) {
        if (userMapper.countUser(userDto.getUserId()) > 0) {
            return ApiResponse.fail("이미 존재하는 사용자ID입니다");
        }
        try {
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userMapper.insertUser(userDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateUser(UserDto userDto) {
        if (userMapper.countUser(userDto.getUserId()) == 0) {
            return ApiResponse.fail("사용자가 존재하지 않습니다");
        }
        try {
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            } else {
                userDto.setPassword(null);
            }
            userMapper.updateUser(userDto);
            return ApiResponse.success("저장되었습니다");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @Override
    public ApiResponse deleteUser(String userId) {
        if (userMapper.countUser(userId) == 0) {
            return ApiResponse.fail("사용자가 존재하지 않습니다");
        }
        userMapper.deleteUser(userId);
        return ApiResponse.success("삭제되었습니다");
    }

    @Override
    public ApiResponse checkUserId(String userId) {
        if (userMapper.countUser(userId) > 0) {
            return ApiResponse.fail("이미 사용중인 ID입니다");
        }
        return ApiResponse.success("사용 가능한 ID입니다");
    }

    @Override
    public String getNextUserId() {
        return userMapper.selectNextUserId();
    }
}
