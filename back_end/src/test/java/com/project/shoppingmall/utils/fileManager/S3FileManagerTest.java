package com.project.shoppingmall.utils.fileManager;

import com.project.shoppingmall.utils.EnableProjectAmazonS3Configuration;
import com.project.shoppingmall.utils.EnableProjectQueryDslConfiguration;
import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("S3FileManager text - AWS S3 Bucket test")
@EnableProjectAmazonS3Configuration
@SetProfile
// 아래는 Configuration들을 테스트에서 사용하기 위한 장치들이다.
@DataJpaTest
@EnableProjectQueryDslConfiguration
class S3FileManagerTest {
    @Autowired
    FileManager<MultipartFile, String> fileManager;

    @Test
    @DisplayName("[정상 작동][saveFile & deleteFile] S3 파일 저장, 삭제 확인")
    public void givenUrlAndTrgPath_whenCallSaveFileAndDeleteFile_thenExistRelatedUrlInS3Bucket() throws IOException {
        // given
        URL url = new URL("https://cdn.pixabay.com/photo/2016/01/18/19/42/fractal-1147253_960_720.jpg");
        String trgPath = "root/test.jpg";
        String urlImage;

        //  when
        try (InputStream inputStream = url.openStream()) {
            MockMultipartFile image = new MockMultipartFile("image", inputStream);
            urlImage = fileManager.saveFile(image, trgPath);
            fileManager.deleteFile(trgPath);
        }

        //  then
        log.debug(urlImage);
        assertThat(urlImage.contains(trgPath)).isEqualTo(true);
    }
}