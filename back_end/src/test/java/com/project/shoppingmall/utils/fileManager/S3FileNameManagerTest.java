package com.project.shoppingmall.utils.fileManager;

import com.project.shoppingmall.utils.SetProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SetProfile
class S3FileNameManagerTest {
    @Test
    @DisplayName("[정상 작동][extractExtension] multipartfile에서 확장자 추출.")
    public void givenMultipart_whenCallExtractExtension_thenReturnExtension() throws IOException {
        // given
        URL url = new URL("https://cdn.pixabay.com/photo/2016/01/18/19/42/fractal-1147253_960_720.jpg");

        String originalFilenameWithoutExtension = "originalFilename";
        String extensionExpected = "jpg";
        String originalFilename = String.join(".", originalFilenameWithoutExtension, extensionExpected);

        String extensionCalculated;

        //  when
        try (InputStream inputStream = url.openStream()) {
            MockMultipartFile image = new MockMultipartFile("image", originalFilename, null, inputStream);
            extensionCalculated = S3FileNameManager.extractExtension(image);
        }

        //  then
        log.debug("Expected:\t{}", extensionExpected);
        log.debug("Calculated:\t{}", extensionCalculated);
        assertThat(extensionCalculated).isEqualTo(extensionExpected);
    }

    @Test
    @DisplayName("[정상 작동][makeFileName] S3에 저장 시, 사용할 파일 이름 정하는 함수 호출.")
    public void givenMultipartAndWords_whenCallMakeFileName_thenReturnFileName() throws IOException {
        // given
        URL url = new URL("https://cdn.pixabay.com/photo/2016/01/18/19/42/fractal-1147253_960_720.jpg");
        String originalFilenameWithoutExtension = "originalFilename";
        String extensionExpected = "jpg";
        String originalFilename = String.join(".", originalFilenameWithoutExtension, extensionExpected);

        Class<Object> clazz = Object.class;
        String fileDir1 = "one";
        String fileDir2 = "two";

        String root = S3FileNameManager.ROOT_DIR;
        String fileNameExpected = String.join("/", root, clazz.getSimpleName(), fileDir1, fileDir2) + "." + extensionExpected;
        String fileNameCalculated;

        //  when
        try (InputStream inputStream = url.openStream()) {
            MockMultipartFile image = new MockMultipartFile("mock name", originalFilename, null, inputStream);
            fileNameCalculated = S3FileNameManager.makeFileName(image, clazz, fileDir1, fileDir2);
        }

        //  then
        log.debug("Expected:\t{}", fileNameExpected);
        log.debug("Calculated:\t{}", fileNameCalculated);
        assertThat(fileNameCalculated).isEqualTo(fileNameExpected);
    }
}