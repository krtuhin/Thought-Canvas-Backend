package com.rootapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rootapp.entities.Category;
import com.rootapp.entities.Post;
import com.rootapp.entities.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    // get posts by user
    List<Post> findByUser(User user);

    // get posts by category
    List<Post> findByCategory(Category category);

    // search posts by keyword
    List<Post> findByTitleContaining(String keyword);

}
