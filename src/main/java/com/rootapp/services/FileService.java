package com.rootapp.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // upload image
    String uploadImage(String path, MultipartFile file) throws IOException;

    // get image
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
