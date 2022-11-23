package com.project.shoppingmall.service;

import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.model.UserDto;
import com.project.shoppingmall.repository.UserRepository;
import com.project.shoppingmall.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired UserRepository userRepository;

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
}
