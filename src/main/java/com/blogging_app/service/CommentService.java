package com.blogging_app.service;

import com.blogging_app.dto.CommentDto;
import com.blogging_app.entity.Comment;
import com.blogging_app.entity.Post;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.CommentRepository;
import com.blogging_app.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CommentDto addComment(CommentDto commentDto, Long postId){

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("post ", "postId", postId));

        Comment comment=this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setAddedDate(new Date());
        Comment savedComment = this.commentRepository.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);

    }

    public Comment updateComment(Comment comment,Long commentId){
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        existingComment.setComment(comment.getComment());
        existingComment.setAddedDate(new Date());

        return commentRepository.save(existingComment);

    }

    public Comment getCommentById(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("comment", "commentId", commentId));
    }

    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    public String deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("comment", "commentId", commentId));

         commentRepository.deleteById(commentId);
        return "Comment deleted successfully!!!";
    }

}
