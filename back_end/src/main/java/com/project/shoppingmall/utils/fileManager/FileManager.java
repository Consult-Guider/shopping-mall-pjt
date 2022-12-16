package com.project.shoppingmall.utils.fileManager;

import java.io.IOException;

public interface FileManager<T, U> {
    String saveFile(T src, U trg) throws IOException;
    void deleteFile(U trg);
}
