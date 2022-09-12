package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.CommentsDto;
import com.programming.techie.springredditclone.repository.CommentRepository;
import com.programming.techie.springredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:4200/")
public class CommentsController {
    private final CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }

    @GetMapping("/energy/{id}")
    public Long energy(@PathVariable Long id) {
        return commentRepository.energy(id);
    }
}
