package com.project.shoppingmall.utils.fileManager;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

public class S3FileNameManager {
    public static final String ROOT_DIR = Paths.get("root", "image").toString();

    public static String makeFileName(MultipartFile multipartFile, String... fileDirs) {
        String extension = extractExtension(multipartFile);

        String fileNameWithoutExtension = Paths.get(ROOT_DIR, fileDirs).toString();
        return String.format("%s.%s", fileNameWithoutExtension, extension);
    }

    public static String extractExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return FilenameUtils.getExtension(originalFilename);
    }
}
