package com.rootapp.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rootapp.entities.Comment;
import com.rootapp.entities.Post;
import com.rootapp.entities.User;
import com.rootapp.exceptions.ResourceNotFoundException;
import com.rootapp.payloads.CommentDto;
import com.rootapp.repositories.CommentRepository;
import com.rootapp.repositories.PostRepository;
import com.rootapp.repositories.UserRepository;
import com.rootapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // create comment
    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {

        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment createdComment = this.commentRepository.save(comment);

        return this.modelMapper.map(createdComment, CommentDto.class);
    }

    // update comment
    @Override
    public CommentDto updateComment(CommentDto commentDto, Long commentId) {

        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        comment.setMessage(commentDto.getMessage());

        Comment updatedComment = this.commentRepository.save(comment);

        return this.modelMapper.map(updatedComment, CommentDto.class);
    }

    // delete comment
    @Override
    public void deleteComment(Long commentId) {

        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        this.commentRepository.delete(comment);
    }

}
