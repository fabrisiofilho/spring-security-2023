package com.bluty.springsecurity2023.service.impl;

import com.bluty.springsecurity2023.dto.user.UserDTO;
import com.bluty.springsecurity2023.entity.UserEntity;
import com.bluty.springsecurity2023.exception.NotFoundException;
import com.bluty.springsecurity2023.repository.UserRepository;
import com.bluty.springsecurity2023.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity create(UserDTO userDto) {
        var user = UserEntity.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .tokenRecover(null)
                .build();

        return repository.save(user);
    }

    @Override
    public UserEntity read(UUID id) {
        return repository.findById(id).orElseThrow(()-> {throw  new NotFoundException("Not Found");});
    }

    @Override
    public UserEntity update(UserDTO userDto) {
        UserEntity userEntity = read(userDto.getId()).update(userDto);
        return repository.save(userEntity);
    }

    @Override
    public UserEntity delete(UUID id) {
        UserEntity userEntity = read(id);
        repository.deleteById(id);
        return userEntity;
    }

    @Override
    public List<UserEntity> list() {
        return repository.findAll();
    }

    @Override
    public Page<UserEntity> pageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public UserEntity resetPassword(UserEntity userDto) {
        UserEntity userEntity = read(userDto.getId());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return repository.save(userEntity);
    }

    @Override
    public Boolean isEmailInUse(String email) {
        return !Objects.isNull(repository.findByEmail(email));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(()-> {throw new NotFoundException("Não foi encontrado o usuario com o email");});
    }

    @Override
    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(()-> {throw new NotFoundException("Não foi encontrado o usuario com o username");});
    }

    @Override
    public UserEntity updateName(UserDTO.UpdateName dto) {
        UserEntity entity = read(dto.getId());
        entity.setFullName(dto.getFullName());
        return repository.save(entity);
    }

    @Override
    public UserEntity updateUser(UserDTO.UpdateUser dto) {
        UserEntity entity = read(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        if (!entity.getPassword().equals(dto.getPassword())) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return repository.save(entity);
    }

    @Override
    public UserEntity updateRecoverToken(String token, UserEntity userEntity) {
        userEntity.setTokenRecover(token);
        return repository.save(userEntity);
    }

    @Override
    public UserEntity findByTokenRecover(String token) {
        return repository.findByTokenRecover(token).orElseThrow(()-> {throw new NotFoundException("Não foi encontrado o usuario com o token");});
    }

}
