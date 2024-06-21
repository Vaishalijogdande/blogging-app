package com.blogging_app.controller;


import com.blogging_app.config.AppConstants;
import com.blogging_app.dto.FileResponse;
import com.blogging_app.dto.PostDto;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.PostResponse;
import com.blogging_app.service.CategoryService;
import com.blogging_app.service.FileService;
import com.blogging_app.service.PostService;
import com.blogging_app.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("add/{id}/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable  Long id,@PathVariable Long categoryId){
        return new ResponseEntity<>(postService.createPost(postDto, id,categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updateDto(@RequestBody PostDto postDto, @PathVariable Long postId){
        return new ResponseEntity<>(postService.updatePost(postDto,postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

                                                     )
    {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId){
        return new ResponseEntity<>(postService.deletePost(postId), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory( @PathVariable Long categoryId){
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId){
        return new ResponseEntity<>(postService.getPostsByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/search/{postTitle}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String postTitle){
        return new ResponseEntity<>(postService.SearchPostsByTitle(postTitle),HttpStatus.OK);
    }

    @PostMapping("/upload/image/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
        @PathVariable Long postId) throws IOException {

        PostDto postDto = postService.getPostById(postId);
        String filename = String.valueOf(fileService.uploadFile(path, image));
        postDto.setImageName(filename);
        PostDto updatedPost = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);


    }

//    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
//        InputStream resource = fileService.getResource(path, imageName);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource, response.getOutputStream());
//
//    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        response.getOutputStream().flush();
        resource.close();
    }


}
