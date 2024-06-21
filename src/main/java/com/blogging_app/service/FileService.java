package com.blogging_app.service;

import com.blogging_app.dto.FileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public FileResponse uploadFile(String path, MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        assert fileName != null;
        String fileName1= randomID.concat(fileName.substring(fileName.lastIndexOf(".")));

        String filepath = path + File.pathSeparator + fileName1;


        File file = new File(path);
            if(!file.exists()){
                file.mkdir();
            }

        Files.copy(multipartFile.getInputStream(), Paths.get(filepath));

        return new FileResponse();

    }


    public InputStream getResource(String path, String fileName1) throws FileNotFoundException{
        String fullPath = path+ File.separator + fileName1;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}
