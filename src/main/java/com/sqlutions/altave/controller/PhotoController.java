package com.sqlutions.altave.controller;

import com.sqlutions.altave.entity.Photo;
import com.sqlutions.altave.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar a foto")
    })

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Endpoint para fazer upload da foto de um funcionário")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeId") Long employeeId) {
        try {
            photoService.photoSave(file, employeeId);
            return ResponseEntity.ok("Foto salva com sucesso!");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagem retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao ler os bytes da foto")
    })

    @GetMapping(
            value = "/bytes/employee/{employeeId}",
            produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    @Operation(summary = "Endpoint para obter a foto do funcionário em formato de bytes (uso em mobile e APIs)")
    public @ResponseBody byte[] servePhotoBytes(@PathVariable Long employeeId) {
        try {
            return photoService.getPhotoBytesByEmployeeId(employeeId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagem retornada como Resource"),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao carregar o arquivo")
    })
    @GetMapping(
            value = "/file/employee/{employeeId}",
            produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    )
    @Operation(summary = "Endpoint para obter a foto do funcionário como recurso (uso em aplicações web)")
    public ResponseEntity<Resource> servePhotoAsResource(@PathVariable Long employeeId) {
        Resource resource = photoService.getPhotoResourceByEmployeeId(employeeId);
        Photo photo = photoService.getPhotoByEmployeeId(employeeId);

        try {
            MediaType mediaType = photo.getOriginalFilename().toLowerCase().endsWith(".png")
                    ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

            return ResponseEntity.ok()
                    //.cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                    .cacheControl(CacheControl.noStore())
                    .header(HttpHeaders.PRAGMA, "no-cache")
                    .header(HttpHeaders.EXPIRES, "0")
                    .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, max-age=0")
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + photo.getOriginalFilename() + "\"")
                    .body(resource);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada")
    })
    @DeleteMapping("/delete/employee/{employeeId}")
    @Operation(summary = "Endpoint para deletar a foto de um funcionário pelo ID")
    public ResponseEntity<String> deletePhoto(@PathVariable Long employeeId) {
        try {
            photoService.deletePhotoByEmployeeId(employeeId);
            return ResponseEntity.ok("Foto deletada com sucesso!");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
