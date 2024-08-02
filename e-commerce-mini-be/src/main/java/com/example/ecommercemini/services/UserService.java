package com.example.ecommercemini.services;

import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.UserDto;
import com.example.ecommercemini.dtos.UserResponseDto;
import com.example.ecommercemini.entities.User;

import java.util.Optional;

public interface UserService {
    UserResponseDto getUserById(int userId);

    UserResponseDto getUserByEmail(String email);

    PageableResponse<UserResponseDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<UserResponseDto> searchUser(String keyword,int pageNumber, int pageSize, String sortBy,
                                                 String sortDir);

    UserResponseDto createUser(UserDto userDto);

    UserResponseDto updateUser(UserDto userDto, String userId);

    Optional<User> findUserByEmailOptional(String email);

}
