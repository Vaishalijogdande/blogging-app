package com.blogging_app.controller;

import com.blogging_app.dto.FileResponse;
import com.blogging_app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

//    @PostMapping("/upload")
//    public ResponseEntity<FileResponse> uploadFile(@RequestParam("image") MultipartFile image) throws IOException {
//        String fileName = null;
//        try {
//            fileName = String.valueOf(fileService.uploadFile(path, image));
//        } catch (IOException e) {
//
//            return new ResponseEntity<>(new FileResponse(null, "File not uploaded due to some error"),HttpStatus.OK);
//
//        }
//
//        return new ResponseEntity<>(new FileResponse(fileName, "File uploaded successfully!!"),HttpStatus.OK);
//    }
}
