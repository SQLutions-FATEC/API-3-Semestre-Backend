package com.sqlutions.altave.controller;

import com.sqlutions.altave.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Operation(summary = "Upload de foto do funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar a foto")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file,
                                              @RequestParam("employeeId") Long employeeId) {
        photoService.photoSave(file, employeeId);
        return ResponseEntity.ok("Foto salva com sucesso!");
    }
}
