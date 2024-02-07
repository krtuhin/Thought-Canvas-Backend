package com.rootapp.services;

import java.util.List;

import com.rootapp.payloads.PostDto;
import com.rootapp.payloads.PostResponse;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    // update
    PostDto updatePost(PostDto postDto, Long postId);

    // get single post
    PostDto getPostById(Long postId);

    // get all post with sort and pagination
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortIn);

    // get post by user id with sort and pagination
    PostResponse getAllPostsByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortIn);

    // get all post by category id with sort and pagination
    PostResponse getAllPostsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortIn);

    // delete
    void deletePost(long postId);

    // search post
    List<PostDto> searchPosts(String keyword);

}
