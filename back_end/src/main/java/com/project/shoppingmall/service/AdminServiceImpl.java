package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Admin;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.exception.CrudException;
import com.project.shoppingmall.model.AdminDto;
import com.project.shoppingmall.model.request.AdminCreateRequest;
import com.project.shoppingmall.model.request.AdminUpdateRequest;
import com.project.shoppingmall.model.response.AdminReadResponse;
import com.project.shoppingmall.repository.AdminRepository;
import com.project.shoppingmall.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired AdminRepository adminRepository;
    @Autowired PasswordEncoder passwordEncoder;

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

    public void isThereEmailEqualTo(String email) throws UsernameNotFoundException {
        adminRepository.findByEmail(email).ifPresent(user ->{
            throw new CrudException(
                    ErrorCode.ACCOUNT_ALREADY_EXISTED,
                    String.format("검색에 사용한 Email: %s", email)
            );
        });
    }

    public Admin loadUserById(Long uid) {
        return adminRepository.findById(uid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("조회에 사용된 ID: %s", uid)
                        )
                );
    }

    public void create(AdminCreateRequest request) {
        isThereEmailEqualTo(request.getEmail());
        adminRepository.save(request.toEntity(passwordEncoder));
    }

    public AdminReadResponse read(Long uid) {
        return AdminReadResponse.fromEntity(loadUserById(uid));
    }

    public void update(Long uid, AdminUpdateRequest request) {
        Admin entity = loadUserById(uid);
        Admin entityOverWritten = AdminUpdateRequest.overwrite(entity, request, passwordEncoder);
        adminRepository.save(entityOverWritten);
    }

    public void delete(Long uid) {
        adminRepository.delete(loadUserById(uid));
    }

    public void updatePrincipal(AdminDto principal, AdminUpdateRequest request) {
        Admin entity = principal.toEntity();

        Admin entityOverWritten = AdminUpdateRequest.overwrite(entity, request, passwordEncoder);
        adminRepository.save(entityOverWritten);
    }

    public void deletePrincipal(AdminDto principal) {
        adminRepository.delete(principal.toEntity());
    }
}
