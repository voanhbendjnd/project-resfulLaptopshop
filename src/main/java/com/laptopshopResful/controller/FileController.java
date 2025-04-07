package com.laptopshopResful.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laptopshopResful.domain.response.file.ResUploadFileDTO;
import com.laptopshopResful.service.FileService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.StorageException;

@RestController
public class FileController {
    private final FileService fileService;

    @Value("${djnd.upload-file.base-uri}")
    private String baseURI;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    @ApiMessage("Upload single file")
    public ResponseEntity<ResUploadFileDTO> upload(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException, StorageException {
        if (file.isEmpty() || file == null) {
            throw new StorageException(">>> File is empty <<<");
        }
        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "docx"); //
        boolean isValid = allowedExtensions.stream()
                .anyMatch((x -> fileName.toLowerCase().endsWith(x)));
        if (!isValid) {
            throw new StorageException("Invalid file extension. only allow " + allowedExtensions.toString());
        }
        // create new folder
        this.fileService.createDirectory(baseURI + folder);
        //
        String uploadFile = this.fileService.store(file, folder);
        ResUploadFileDTO res = new ResUploadFileDTO(uploadFile, Instant.now());
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/files")
    @ApiMessage("Download a file")
    public ResponseEntity<Resource> download(
            @RequestParam("fileName") String fileName,
            @RequestParam("folder") String folder)
            throws StorageException, URISyntaxException, FileNotFoundException {
        if (fileName == null || folder == null) {
            throw new StorageException("Missing required params");
        }
        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0) {
            throw new StorageException("File with name =  " + fileName + " not found!!!");
        }
        InputStreamResource resource = this.fileService.getResource(fileName, folder);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
