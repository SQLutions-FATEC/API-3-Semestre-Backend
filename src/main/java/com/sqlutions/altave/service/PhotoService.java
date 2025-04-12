package com.sqlutions.altave.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    void photoSave(MultipartFile foto, Long funcionarioId);
}
