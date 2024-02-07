package com.rootapp.services.impls;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rootapp.entities.Category;
import com.rootapp.entities.Post;
import com.rootapp.entities.User;
import com.rootapp.exceptions.ResourceNotFoundException;
import com.rootapp.payloads.PostDto;
import com.rootapp.repositories.CategoryRepository;
import com.rootapp.repositories.PostRepository;
import com.rootapp.repositories.UserRepository;
import com.rootapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

        @Autowired
        private PostRepository postRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private ModelMapper modelMapper;

        // create post
        @Override
        public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

                User user = this.userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

                Category category = this.categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

                Post post = this.modelMapper.map(postDto, Post.class);

                if (postDto.getImageName() == null || postDto.getImageName().trim().isEmpty()) {

                        post.setImageName("default.png");
                }

                post.setAddedDate(new Date());
                post.setCategory(category);
                post.setUser(user);

                Post createdPost = this.postRepository.save(post);

                return this.modelMapper.map(createdPost, PostDto.class);
        }

        // update post
        @Override
        public PostDto updatePost(PostDto postDto, Long postId) {

                Post post = this.postRepository.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                post.setImageName(postDto.getImageName());

                Post updatedPost = this.postRepository.save(post);

                return this.modelMapper.map(updatedPost, PostDto.class);
        }

        // get single post by id
        @Override
        public PostDto getPostById(Long postId) {

                Post post = this.postRepository.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

                return this.modelMapper.map(post, PostDto.class);
        }

        // get all post
        @Override
        public List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize) {

                Pageable pageable = PageRequest.of(pageNumber, pageSize);

                Page<Post> page = this.postRepository.findAll(pageable);

                List<Post> posts = page.getContent();

                List<PostDto> postDtos = posts.stream()
                                .map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

                return postDtos;
        }

        // delete post
        @Override
        public void deletePost(long postId) {

                Post post = this.postRepository.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

                this.postRepository.delete(post);
        }

        // get all posts by user
        @Override
        public List<PostDto> getAllPostsByUser(Long userId) {

                User user = this.userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

                List<PostDto> posts = this.postRepository.findByUser(user).stream()
                                .map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

                return posts;
        }

        // get all posts by category
        @Override
        public List<PostDto> getAllPostsByCategory(Long categoryId) {

                Category category = this.categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

                List<PostDto> posts = this.postRepository.findByCategory(category).stream()
                                .map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

                return posts;
        }

        // search posts by keywords
        @Override
        public List<PostDto> searchPosts(String keyword) {

                List<PostDto> posts = this.postRepository.findByTitleContaining(keyword).stream()
                                .map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

                return posts;
        }

}
