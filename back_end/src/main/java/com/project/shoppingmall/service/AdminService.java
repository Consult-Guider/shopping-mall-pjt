package com.project.shoppingmall.service;

import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.request.AdminCreateRequest;
import com.project.shoppingmall.model.request.AdminUpdateRequest;
import com.project.shoppingmall.model.response.AdminReadResponse;
import com.project.shoppingmall.repository.AdminRepository;
import com.project.shoppingmall.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService implements UserDetailsService {
    @Autowired AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByEmail(username)
                .map(AdminDto::fromEntity)
                .orElseThrow(() ->
                        new AuthenticationException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("검색에 사용한 Email: %s", username)
                        )
                );
    }

    public void createUser(AdminCreateRequest request) {

    }

    public AdminReadResponse readUser(Long uid) {
        return null;
    }

    public void updateUser(Long uid, AdminUpdateRequest request) {

    }

    public void deleteUser(Long uid) {

    }

    public void updatePrincipal(AdminDto principal, AdminUpdateRequest request) {

    }

    public void deletePrincipal(AdminDto principal) {

    }
}
