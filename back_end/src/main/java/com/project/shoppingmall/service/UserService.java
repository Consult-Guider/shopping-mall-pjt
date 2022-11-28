package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.User;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.model.request.UserCreateRequest;
import com.project.shoppingmall.model.request.UserUpdateRequest;
import com.project.shoppingmall.model.response.UserReadResponse;
import com.project.shoppingmall.repository.UserRepository;
import com.project.shoppingmall.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserDto::fromEntity)
                .orElseThrow(() ->
                        new AuthenticationException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("검색에 사용한 Email: %s", username)
                        )
                );
    }

    public void isThereEmailEqualTo(String email) throws UsernameNotFoundException {
        userRepository.findByEmail(email).ifPresent(user ->{
            throw new CrudException(
                    ErrorCode.ACCOUNT_ALREADY_EXISTED,
                    String.format("검색에 사용한 Email: %s", email)
                );
        });
    }

    public User loadUserById(Long uid) {
        return userRepository.findById(uid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("조회에 사용된 ID: %s", uid)
                        )
                );
    }

    public void createUser(UserCreateRequest request) {
        isThereEmailEqualTo(request.getEmail());
        userRepository.save(request.toEntity(passwordEncoder));
    }

    public UserReadResponse readUser(Long uid) {
        return UserReadResponse.fromEntity(loadUserById(uid));
    }

    public void updateUser(Long uid, UserUpdateRequest request) {
        User entity = loadUserById(uid);
        User entityOverWritten = UserUpdateRequest.overwrite(entity, request, passwordEncoder);
        userRepository.save(entityOverWritten);
    }

    public void deleteUser(Long uid) {
        User entity = loadUserById(uid);
        userRepository.delete(entity);
    }

    public void updatePrincipal(UserDto principal, UserUpdateRequest request) {
        User entity = principal.toEntity();

        User entityOverWritten = UserUpdateRequest.overwrite(entity, request, passwordEncoder);
        userRepository.save(entityOverWritten);
    }

    public void deletePrincipal(UserDto principal) {
        User entity = principal.toEntity();
        userRepository.delete(entity);
    }
}
