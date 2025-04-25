package com.sqlutions.altave.service;

import com.sqlutions.altave.entity.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    void photoSave(MultipartFile foto, Long employeeId);

    Photo getPhotoById(Long photoId);
    byte[] getPhotoBytes(Long photoId);
    Resource getPhotoResource(Long photoId);

    Photo getPhotoByEmployeeId(Long employeeId);
    byte[] getPhotoBytesByEmployeeId(Long employeeId);
    Resource getPhotoResourceByEmployeeId(Long employeeId);

    void deletePhotoByEmployeeId(Long employeeId);

}
