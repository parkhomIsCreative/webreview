package com.example.webrewiew.controller;

import com.example.webrewiew.dto.CommentDTO;
import com.example.webrewiew.entity.Comment;
import com.example.webrewiew.facade.CommentFacade;
import com.example.webrewiew.payload.response.MessageResponse;
import com.example.webrewiew.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentFacade commentFacade;


    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDTO commentDTO,
                                                @PathVariable("postId") String postId,
                                                Principal principal) {
        Comment comment = commentService.saveComment(Long.parseLong(postId),commentDTO,principal);
        CommentDTO createdComment = commentFacade.commentToCommentDTO(comment);

        return new ResponseEntity<>(createdComment, HttpStatus.OK);

    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<Object> getAllComments(@PathVariable("postId") String postId){
        List<CommentDTO> commentDTOList = commentService.getAllCommentsForPost(Long.parseLong(postId))
                .stream()
                .map(commentFacade :: commentToCommentDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);

    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<Object> deleteComment(@PathVariable("commentId") String commentId){
        commentService.deleteComment(Long.parseLong(commentId));

        return new ResponseEntity<>(new MessageResponse("Post was deleted"),HttpStatus.OK);
    }


}
