package com.rootapp.services.impls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rootapp.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    // upload image to server
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // getting file name
        String name = file.getOriginalFilename();

        // generating random number
        String randomId = UUID.randomUUID().toString();

        // creating random file name
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        // full path
        String filePath = path + File.separator + fileName;

        // create folder if not created
        File f = new File(path);

        if (!f.exists()) {

            f.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    // get image from server
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        // creating full path
        String fullPath = path + File.separator + fileName;

        // getting file input stream from server
        InputStream is = new FileInputStream(fullPath);

        return is;
    }

}
