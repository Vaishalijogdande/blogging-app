package com.blogging_app.service;

import com.blogging_app.dto.PostDto;
import com.blogging_app.entity.Category;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.PostResponse;
import com.blogging_app.entity.User;
import com.blogging_app.exception.ResourceNotFoundException;
import com.blogging_app.repository.CategoryRepository;
import com.blogging_app.repository.PostRepository;
import com.blogging_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public PostDto createPost(PostDto postDto,Long userId, Long categoryId) {

        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user", " userId ", userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("category", " categoryId ", categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);

        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post addPost = this.postRepository.save(post);
        return this.modelMapper.map(addPost, PostDto.class);
    }

    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post ", "postId", postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepository.save(post);

        return this.modelMapper.map(updatedPost, PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post ", "postId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
//        if(sortDir.equalsIgnoreCase("asc")){
//            sort= Sort.by(sortBy).ascending();
//        }else {
//            sort=Sort.by(sortBy).descending();
//        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort); // PageRequest page index starts at 0
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> allPosts = page.getContent();


        List<PostDto> post1=  allPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(post1);
        postResponse.setPageSize(page.getSize());
        postResponse.setPageNumber(page.getNumber());
        postResponse.setTotalElements(page.getNumberOfElements());
        postResponse.setTotalPages(page.getTotalPages());

        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }

    public String deletePost(Long postId){

        Post post = this.postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post ", "postId", postId));
        this.postRepository.deleteById(postId);
        return "Post deleted Successfully!!!!";
    }


    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategory(category);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }


    public List<PostDto> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Post> posts = postRepository.findByUser(user);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    public List<PostDto> SearchPostsByTitle(String postTitle){
      List<Post> posts=  this.postRepository.searchByTitle(postTitle);
      List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();
      return postDtos;
    }

}
