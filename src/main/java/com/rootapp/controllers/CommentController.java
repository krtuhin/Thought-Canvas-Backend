package com.rootapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rootapp.payloads.ApiResponse;
import com.rootapp.payloads.CommentDto;
import com.rootapp.services.CommentService;

@RestController
@RequestMapping("api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // POST -> create comment
    @PostMapping("user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long userId,
            @PathVariable Long postId) {

        CommentDto createdCommentDto = this.commentService.createComment(commentDto, postId, userId);

        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    // PUT -> update comment
    @PutMapping("comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId) {

        CommentDto updatedCommentDto = this.commentService.updateComment(commentDto, commentId);

        return ResponseEntity.ok(updatedCommentDto);
    }

    // DELETE -> delete comment
    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {

        this.commentService.deleteComment(commentId);

        return ResponseEntity.ok(ApiResponse.builder().message("comment deleted successfully").success(true).build());
    }

}
