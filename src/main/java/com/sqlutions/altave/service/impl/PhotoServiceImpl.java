package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.entity.Photo;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.repository.PhotoRepository;
import com.sqlutions.altave.service.PhotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void photoSave(MultipartFile foto, Long employeeId) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

            Photo existingPhoto = photoRepository.findByEmployee_EmployeeId(employeeId);
            if (existingPhoto != null) {
                Path oldPath = Paths.get(existingPhoto.getPath());
                Files.deleteIfExists(oldPath);

                photoRepository.delete(existingPhoto);
            }

            String baseDir = "src/main/resources/photos/";
            Path path = Paths.get(baseDir);
            Files.createDirectories(path);

            String extension = Objects.requireNonNull(foto.getOriginalFilename())
                    .substring(foto.getOriginalFilename().lastIndexOf('.') + 1);

            String filename = employee.getEmployeeName().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + "." + extension;
            Path filePath = path.resolve(filename);
            Files.write(filePath, foto.getBytes());

            Photo photo = new Photo();
            photo.setPath(filePath.toString());
            photo.setOriginalFilename(filename);
            photo.setCreationDate(LocalDateTime.now());
            photo.setEmployee(employee);

            photoRepository.save(photo);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a foto", e);
        }
    }

}
