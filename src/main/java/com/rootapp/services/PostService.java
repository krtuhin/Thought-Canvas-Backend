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

    // get all post
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);

    // get post by user id
    PostResponse getAllPostsByUser(Long userId, Integer pageNumber, Integer pageSize);

    // get all post by category id
    PostResponse getAllPostsByCategory(Long categoryId, Integer pageNumber, Integer pageSize);

    // delete
    void deletePost(long postId);

    // search post
    List<PostDto> searchPosts(String keyword);

}
