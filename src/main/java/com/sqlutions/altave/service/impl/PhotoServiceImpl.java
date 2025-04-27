package com.sqlutions.altave.service.impl;

import com.sqlutions.altave.repository.PhotoRepository;
import com.sqlutions.altave.entity.Employee;
import com.sqlutions.altave.entity.Photo;
import com.sqlutions.altave.repository.EmployeeRepository;
import com.sqlutions.altave.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Photo existingPhoto = null;
        try {
            existingPhoto = photoRepository.findByEmployeeId(employeeId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar foto existente: " + e.getMessage());
        }

        try {
            if (existingPhoto != null) {
                Files.deleteIfExists(Paths.get(existingPhoto.getPath()));
                photoRepository.delete(existingPhoto);
            }

            Path dir = Paths.get("src/main/resources/photos/");
            Files.createDirectories(dir);

            String ext = Objects.requireNonNull(foto.getOriginalFilename())
                    .substring(foto.getOriginalFilename().lastIndexOf('.') + 1);
            String filename = employee.getEmployeeName().replaceAll("\\s+", "_")
                    + "_" + System.currentTimeMillis() + "." + ext;
            Path filePath = dir.resolve(filename);
            Files.write(filePath, foto.getBytes());

            Photo photo = new Photo();
            photo.setEmployee(employee);
            photo.setPath(filePath.toString());
            photo.setOriginalFilename(filename);
            photo.setCreationDate(LocalDateTime.now());
            photoRepository.save(photo);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a foto", e);
        }
    }

    @Override
    public Photo getPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Foto não encontrada"));
    }

    @Override
    public byte[] getPhotoBytes(Long photoId) {
        try {
            Photo photo = getPhotoById(photoId);
            return Files.readAllBytes(Paths.get(photo.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler os bytes da foto", e);
        }
    }

    @Override
    public Resource getPhotoResource(Long photoId) {
        try {
            Photo photo = getPhotoById(photoId);
            UrlResource resource = new UrlResource(Paths.get(photo.getPath()).toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Arquivo não pode ser lido: " + photo.getOriginalFilename());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL inválida para o arquivo", e);
        }
    }

    @Override
    public Photo getPhotoByEmployeeId(Long employeeId) {
        Photo photo = photoRepository.findByEmployeeId(employeeId);
        if (photo == null) {
            throw new RuntimeException("Nenhuma foto encontrada para o funcionário ID " + employeeId);
        }
        return photo;
    }

    @Override
    public byte[] getPhotoBytesByEmployeeId(Long employeeId) {
        try {
            Photo photo = getPhotoByEmployeeId(employeeId);
            return Files.readAllBytes(Paths.get(photo.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler os bytes da foto do funcionário", e);
        }
    }

    @Override
    public Resource getPhotoResourceByEmployeeId(Long employeeId) {
        Photo existingPhoto = null;
        try {
            existingPhoto = photoRepository.findByEmployeeId(employeeId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar foto existente: " + e.getMessage());
        }

        if (existingPhoto == null) {
            throw new RuntimeException("Foto não encontrada");
        }

        try {
            Path path = Paths.get(existingPhoto.getPath());
            System.out.println("Loading photo from path: " + path);
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Arquivo não pode ser lido");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao carregar a imagem", e);
        }
    }

    @Override
    public void deletePhotoByEmployeeId(Long employeeId) {
        Photo existingPhoto = photoRepository.findByEmployeeId(employeeId);
        if (existingPhoto == null) {
            throw new RuntimeException("Nenhuma foto encontrada para o funcionário ID " + employeeId);
        }
        try {
            Files.deleteIfExists(Paths.get(existingPhoto.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar o arquivo físico da foto", e);
        }
        photoRepository.delete(existingPhoto);
    }
}
