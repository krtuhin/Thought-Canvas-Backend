package com.rootapp.services;

import com.rootapp.payloads.CommentDto;

public interface CommentService {

    // create comment
    CommentDto createComment(CommentDto commentDto, Long postId, Long userId);

    // update comment
    CommentDto updateComment(CommentDto commentDto, Long commentId);

    // delete comment
    void deleteComment(Long commentId);

}
