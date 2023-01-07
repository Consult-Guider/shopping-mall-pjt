package com.project.shoppingmall.utils.fileManager;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class S3FileNameManager {
    public static final String ROOT_DIR = Paths.get("root", "image").toString();

    public static <T> String makeFileName(MultipartFile multipartFile, Class<T> clazz, String... fileDirs) {
        List<String> dirs = new ArrayList<>();

        // 루트 경로.
        dirs.add(ROOT_DIR);

        // 도메인 이름 경로.
        dirs.add(clazz.getSimpleName());

        // 나머지 경로.
        dirs.addAll(List.of(fileDirs));

        String extension = extractExtension(multipartFile);
        String separator = File.separator;

        String fileNameWithoutExtension = String.join(separator, dirs);
        return String.format("%s.%s", fileNameWithoutExtension, extension);
    }

    public static String extractExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        return FilenameUtils.getExtension(originalFilename);
    }
}
