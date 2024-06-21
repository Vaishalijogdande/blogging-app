package com.blogging_app.repository;

import com.blogging_app.dto.PostDto;
import com.blogging_app.entity.Category;
import com.blogging_app.entity.Post;
import com.blogging_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);


    @Query("SELECT p FROM Post p WHERE p.postTitle LIKE %:key%")
    List<Post> searchByTitle(@Param("key") String postTitle);

}
