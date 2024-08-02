package com.example.ecommercemini.services.impl;

import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.UserDto;
import com.example.ecommercemini.dtos.UserResponseDto;
import com.example.ecommercemini.entities.User;
import com.example.ecommercemini.exception.ConstraintViolationException;
import com.example.ecommercemini.exception.ResourceNotFoundException;
import com.example.ecommercemini.repositories.RoleRepository;
import com.example.ecommercemini.repositories.UserRepository;
import com.example.ecommercemini.services.UserService;
import com.example.ecommercemini.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserResponseDto getUserById(int userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found!"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user found!"));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public PageableResponse<UserResponseDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserResponseDto> response = Helper.getPageableResponse(page,UserResponseDto.class );
        return response;
    }

    @Override
    public PageableResponse<UserResponseDto> searchUser(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? (Sort.by(sortBy).ascending()) : (Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<User> page = userRepository.findByFirstNameContaining(keyword,pageable);
        PageableResponse<UserResponseDto> response = Helper.getPageableResponse(page,UserResponseDto.class );
        return response;
    }

    @Override
    public UserResponseDto createUser(UserDto userDto) {
        User emailExists = userRepository.findUserByEmailIgnoreCase(userDto.getEmail());
        if (emailExists != null){
            throw new ConstraintViolationException("Email is already registered");
        }
        return null;
    }

    @Override
    public UserResponseDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return this.userRepository.findByEmail(email);
    }
}
