package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.VoteDto;
import com.programming.techie.springredditclone.repository.VoteRepository;
import com.programming.techie.springredditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:4200/")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    private VoteRepository voteRepository;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {

        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/energy/{id}")
    public Long energy(@PathVariable Long id) {
        return voteRepository.energy(id);
    }
}