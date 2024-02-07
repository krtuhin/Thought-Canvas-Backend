package com.rootapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rootapp.payloads.ApiResponse;
import com.rootapp.payloads.PostDto;
import com.rootapp.payloads.PostResponse;
import com.rootapp.services.PostService;

@RestController
@RequestMapping("api/")
public class PostController {

    @Autowired
    private PostService postService;

    // POST -> create post
    @PostMapping("user/{userId}/category/{categoryId}/posts/")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Long userId,
            @PathVariable("categoryId") Long categoryId) {

        PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    // PUT -> update post
    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {

        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        return ResponseEntity.ok(updatedPostDto);
    }

    // GET -> get single post
    @GetMapping("posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {

        PostDto postDto = this.postService.getPostById(postId);

        return ResponseEntity.ok(postDto);
    }

    // GET -> get all posts with pagination
    @GetMapping("posts/")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "page_number", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sort_by", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sort_in", defaultValue = "asc", required = false) String sortIn) {

        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortIn);

        return ResponseEntity.ok(postResponse);
    }

    // DELETE -> delete post
    @DeleteMapping("posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {

        this.postService.deletePost(postId);

        return ResponseEntity.ok(ApiResponse.builder().message("post deleted successfully").success(true).build());
    }

    // GET -> get all posts by user with pagination
    @GetMapping("user/{userId}/posts")
    public ResponseEntity<PostResponse> getAllPostsByUser(@PathVariable Long userId,
            @RequestParam(value = "page_number", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sort_by", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sort_in", defaultValue = "asc", required = false) String sortIn) {

        PostResponse postResponse = this.postService.getAllPostsByUser(userId, pageNumber, pageSize, sortBy, sortIn);

        return ResponseEntity.ok(postResponse);
    }

    // GET -> get all posts by category with pagination
    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getAllPostsBycategory(@PathVariable Long categoryId,
            @RequestParam(value = "page_number", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sort_by", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sort_in", defaultValue = "asc", required = false) String sortIn) {

        PostResponse postrResponse = this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize, sortBy,
                sortIn);

        return ResponseEntity.ok(postrResponse);
    }

    // GET -> search posts by keyword
    @GetMapping("posts")
    public ResponseEntity<List<PostDto>> searchPostsByKeyword(@RequestParam String keyword) {

        List<PostDto> posts = this.postService.searchPosts(keyword);

        return ResponseEntity.ok(posts);
    }

}
