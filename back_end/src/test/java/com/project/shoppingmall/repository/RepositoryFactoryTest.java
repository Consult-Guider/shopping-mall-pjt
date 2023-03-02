package com.project.shoppingmall.repository;

import com.project.shoppingmall.domain.LoginEntity;
import com.project.shoppingmall.type.RoleType;
import com.project.shoppingmall.utils.EnableProjectQueryDslConfiguration;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
@DisplayName("RepositoryFactory Factory Test")
@SetProfile
@EnableProjectQueryDslConfiguration
@Import({RepositoryFactoryImpl.class})
@DataJpaTest
class RepositoryFactoryTest {
    @Autowired
    RepositoryFactory repositoryFactory;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Test
    @DisplayName("[getFinderByEmail] UserRepository 호출 실험")
    void givenUserRole_whenCallGetFinderByEmail_thenDoesNotThrow() {
        // given
        RoleType role = RoleType.USER;

        // when & then
        LoginEntity entity = assertDoesNotThrow(() ->
                repositoryFactory.getFinderByEmail(role, "iksadnorth@gmail.com").get()
        );
        log.debug(entity.toString());
    }

    @Test
    @DisplayName("[getFinderByEmail] SellerRepository 호출 실험")
    void givenSellerRole_whenCallGetFinderByEmail_thenDoesNotThrow() {
        // given
        RoleType role = RoleType.SELLER;

        // when & then
        LoginEntity entity = assertDoesNotThrow(() ->
                repositoryFactory.getFinderByEmail(role, "iksadsouth@gmail.com").get()
        );
        log.debug(entity.toString());
    }
}