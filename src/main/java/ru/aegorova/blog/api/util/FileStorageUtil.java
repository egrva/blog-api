package ru.aegorova.blog.api.util;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.aegorova.blog.api.models.FileInfo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorageUtil {

    @Value("${storage.path}")
    private String storagePath;

    public String getStoragePath() {
        return storagePath;
    }

    // сохраняет файл на диск
    @SneakyThrows
    public void copyToStorage(MultipartFile file, String storageFileName) {
        Files.copy(file.getInputStream(), Paths.get(storagePath, storageFileName));
    }

    public long sizeOf(String filePath) {
        return new File(filePath).length();
    }

    // принимает на вход файл в формате Multipart
    // сохраняет его в БД
    public FileInfo convertFromMultipart(MultipartFile file) {
        // получаем название файла
        String originalFileName = file.getOriginalFilename();
        // вытаскиваем контент-тайп (MIME)
        String type = file.getContentType();
        // размер файла
        long size = file.getSize();
        // создаем имя файла
        String storageName = createStorageName(originalFileName);
        // получаем url файла по которому он будет доступен внутри системы
        String fileUrl = getUrlOfFile(storageName);
        return FileInfo.builder()
                .originalFileName(originalFileName)
                .storageFileName(storageName)
                .url(fileUrl)
                .size(size)
                .type(type)
                .build();
    }

    private String getUrlOfFile(String storageFileName) {
        // путь к папке с файлами на диске + название файла
        return storagePath + "/" + storageFileName;
    }

    // создает уникальное имя файла на диске с его расширением
    private String createStorageName(String originalFileName) {
        // получаем расширение файла по его имени
        String extension = FilenameUtils.getExtension(originalFileName);
        // генерируем случайную строку
        String newFileName = UUID.randomUUID().toString();
        // новое имя файла - UUID + . + расширение файла
        return newFileName + "." + extension;
    }
}