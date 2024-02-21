package com.rootapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rootapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
