package com.project.shoppingmall.service;

import com.project.shoppingmall.domain.Seller;
import com.project.shoppingmall.exception.AuthenticationException;
import com.project.shoppingmall.exception.CrudException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SellerService implements UserDetailsService {
    @Autowired SellerRepository sellerRepository;
    @Autowired PasswordEncoder passwordEncoder;

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

    public void isThereEmailEqualTo(String email) throws UsernameNotFoundException {
        sellerRepository.findByEmail(email).ifPresent(user ->{
            throw new CrudException(
                    ErrorCode.ACCOUNT_ALREADY_EXISTED,
                    String.format("검색에 사용한 Email: %s", email)
            );
        });
    }

    public Seller loadUserById(Long uid) {
        return sellerRepository.findById(uid)
                .orElseThrow(() ->
                        new CrudException(
                                ErrorCode.ACCOUNT_NOT_FOUNDED,
                                String.format("조회에 사용된 ID: %s", uid)
                        )
                );
    }

    public void createUser(SellerCreateRequest request) {
        isThereEmailEqualTo(request.getEmail());
        sellerRepository.save(request.toEntity(passwordEncoder));
    }

    public SellerReadResponse readUser(Long uid) {
        return SellerReadResponse.fromEntity(loadUserById(uid));
    }

    public void updateUser(Long uid, SellerUpdateRequest request) {
        Seller entity = loadUserById(uid);
        Seller entityOverWritten = SellerUpdateRequest.overwrite(entity, request, passwordEncoder);
        sellerRepository.save(entityOverWritten);
    }

    public void deleteUser(Long uid) {
        sellerRepository.delete(loadUserById(uid));
    }

    public void updatePrincipal(SellerDto principal, SellerUpdateRequest request) {
        Seller entity = principal.toEntity();

        Seller entityOverWritten = SellerUpdateRequest.overwrite(entity, request, passwordEncoder);
        sellerRepository.save(entityOverWritten);
    }

    public void deletePrincipal(SellerDto principal) {
        sellerRepository.delete(principal.toEntity());
    }
}
