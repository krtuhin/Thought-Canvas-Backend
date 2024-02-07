package com.rootapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rootapp.entities.Category;
import com.rootapp.entities.Post;
import com.rootapp.entities.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    // get posts page by user
    Page<Post> findByUser(User user, Pageable pageable);

    // get posts page by category
    Page<Post> findByCategory(Category category, Pageable pageable);

    // search posts page by keyword
    List<Post> findByTitleContaining(String keyword);

}
