package com.project.shoppingmall.service;

import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.model.SellerDto;
import com.project.shoppingmall.model.request.SellerCreateRequest;
import com.project.shoppingmall.model.request.SellerUpdateRequest;
import com.project.shoppingmall.model.response.SellerReadResponse;
import com.project.shoppingmall.repository.SellerRepository;
import com.project.shoppingmall.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SellerService implements UserDetailsService {
    @Autowired SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sellerRepository.findByEmail(username)
                .map(SellerDto::fromEntity)
                .orElseThrow(() ->
                        new AuthenticationException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("검색에 사용한 Email: %s", username)
                        )
                );
    }

    public void createUser(SellerCreateRequest request) {

    }

    public SellerReadResponse readUser(Long uid) {
        return null;
    }

    public void updateUser(Long uid, SellerUpdateRequest request) {

    }

    public void deleteUser(Long uid) {

    }

    public void updatePrincipal(SellerDto principal, SellerUpdateRequest request) {

    }

    public void deletePrincipal(SellerDto principal) {

    }
}
