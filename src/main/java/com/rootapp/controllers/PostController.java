package com.rootapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rootapp.config.AppConstants;
import com.rootapp.payloads.ApiResponse;
import com.rootapp.payloads.PostDto;
import com.rootapp.payloads.PostResponse;
import com.rootapp.services.FileService;
import com.rootapp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
            @RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = AppConstants.SORT_IN, defaultValue = AppConstants.DEFAULT_SORT_IN, required = false) String sortIn) {

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
            @RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = AppConstants.SORT_IN, defaultValue = AppConstants.DEFAULT_SORT_IN, required = false) String sortIn) {

        PostResponse postResponse = this.postService.getAllPostsByUser(userId, pageNumber, pageSize, sortBy, sortIn);

        return ResponseEntity.ok(postResponse);
    }

    // GET -> get all posts by category with pagination
    @GetMapping("category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getAllPostsBycategory(@PathVariable Long categoryId,
            @RequestParam(value = AppConstants.PAGE_NUMBER, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = AppConstants.PAGE_SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = AppConstants.SORT_BY, defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = AppConstants.SORT_IN, defaultValue = AppConstants.DEFAULT_SORT_IN, required = false) String sortIn) {

        PostResponse postrResponse = this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize, sortBy,
                sortIn);

        return ResponseEntity.ok(postrResponse);
    }

    // GET -> search posts by keyword
    @GetMapping("posts/search")
    public ResponseEntity<List<PostDto>> searchPostsByKeyword(
            @RequestParam(value = "title", required = true) String keyword) {

        List<PostDto> posts = this.postService.searchPosts(keyword);

        return ResponseEntity.ok(posts);
    }

    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@PathVariable Long postId,
            @RequestParam(value = AppConstants.IMAGE, required = true) MultipartFile image) throws IOException {

        // get post by post id
        PostDto postDto = this.postService.getPostById(postId);

        // save image on server
        String imageName = this.fileService.uploadImage(this.path, image);

        // update image name in database
        postDto.setImageName(imageName);

        // update post
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        return ResponseEntity.ok(updatedPostDto);
    }

    // get image from database
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response)
            throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }

}
