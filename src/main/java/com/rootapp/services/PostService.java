package com.rootapp.services;

import java.util.List;

import com.rootapp.payloads.PostDto;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    // update
    PostDto updatePost(PostDto postDto, Long postId);

    // get single post
    PostDto getPostById(Long postId);

    // get all post
    List<PostDto> getAllPosts();

    // get post by user id
    List<PostDto> getAllPostsByUser(Long userId);

    // get all post by category id
    List<PostDto> getAllPostsByCategory(Long categoryId);

    // delete
    void deletePost(long postId);

    // search post
    List<PostDto> searchPosts(String keyword);

}
