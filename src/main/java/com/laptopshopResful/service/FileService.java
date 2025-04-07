package com.laptopshopResful.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    @Value("${djnd.upload-file.base-uri}")
    private String baseURI;

    public void createDirectory(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        // change to folder
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectories(tmpDir.toPath());
                System.out.println("*** CREATE NEW DIRECTTORY SUCCESSFUL WITH PATH = " + tmpDir.toPath() + "***");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("*** SKIP MAKING DIRECTORY...***");
        }
    }

    // save file function
    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {
        // create new name for file with time
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        return finalName;
    }

    public Long getFileLength(String fileName, String folder) throws URISyntaxException {
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.exists() || tmpDir.isDirectory()) {
            return 0L;
        }
        return tmpDir.length();
    }

    public InputStreamResource getResource(String fileName, String folder)
            throws URISyntaxException, FileNotFoundException {
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        File file = new File(path.toString());
        return new InputStreamResource(new FileInputStream(file));
    }
}
