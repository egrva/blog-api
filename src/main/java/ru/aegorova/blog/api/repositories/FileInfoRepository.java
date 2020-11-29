package ru.aegorova.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aegorova.blog.api.models.FileInfo;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    Optional<FileInfo> findFirstByStorageFileName(String storageFileName);
}
