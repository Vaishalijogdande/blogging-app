package com.blogging_app.controller;

import com.blogging_app.dto.CommentDto;
import com.blogging_app.entity.Comment;
import com.blogging_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto, @PathVariable Long postId){
        return new ResponseEntity<>(commentService.addComment(commentDto, postId), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable Long commentId){
        return new ResponseEntity<>(commentService.updateComment(comment,commentId),HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(commentId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        return new ResponseEntity<>(commentService.getAllComments(),HttpStatus.OK);

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        return new ResponseEntity<>(commentService.deleteComment(commentId),HttpStatus.NOT_FOUND);
    }

}
