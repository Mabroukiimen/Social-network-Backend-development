package com.programming.techie.springredditclone.controller;


import com.programming.techie.springredditclone.dto.VoteDto;
import com.programming.techie.springredditclone.model.Friend;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.FriendRepository;
import com.programming.techie.springredditclone.repository.UserRepository;
import com.programming.techie.springredditclone.service.AuthService;
import com.programming.techie.springredditclone.service.FriendService;
import lombok.AllArgsConstructor;
import nonapi.io.github.classgraph.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.time.LocalDate;
import java.util.*;


import static org.springframework.http.ResponseEntity.status;;

@RestController
@RequestMapping("/api/friend")
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:4200/")
public class FriendController {


    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private FriendService friendService;
    @Autowired
        private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService securityService;
    @GetMapping("/addFriends/{friendId}")
    public Friend addFriend(@PathVariable("friendId")Long friendId) {
        Friend friend = new Friend();
        User currentUser = securityService.getCurrentUser();
        friend.setFirstUser(userRepository.findUserByuserId(currentUser.getUserId()));
        friend.setSecondUser(userRepository.findUserByuserId(friendId));
        //  friend.setFirstUserName(currentUser.getUsername());
        //     friend.setSecondUserName(userRepository.findUserNameByUserId(friendId));
        friend.setCreatedDate(LocalDate.now());
        friend.setFriend(false);

        return friendService.add(friend);
    }
    @PostMapping("/addF/{friendId}")
    public Friend addF(@PathVariable("friendId")Long friendId) {
        Friend friend = new Friend();
        User currentUser = securityService.getCurrentUser();
        friend.setFirstUser(userRepository.findUserByuserId(currentUser.getUserId()));
        friend.setSecondUser(userRepository.findUserByuserId(friendId));
        //  friend.setFirstUserName(currentUser.getUsername());
        //     friend.setSecondUserName(userRepository.findUserNameByUserId(friendId));
        friend.setCreatedDate(LocalDate.now());
        friend.setFriend(false);

        return friendService.add(friend);
    }
    @GetMapping("voteCount/{username1}/{username2}")
    public Integer getvoteCount(@PathVariable String username1,@PathVariable String username2){
        return friendRepository.getvoteCount(username1,username2);
    }


    @GetMapping
    public ResponseEntity<List<Friend>> getAll() {

        return status(HttpStatus.OK).body(friendService.getAll());
    }
    @ResponseBody
    @PutMapping("/accepted/{id}")
    public  void updateF(@PathVariable Long id) {
        friendRepository.accepted(id);
      //  return ResponseEntity.ok("Friend accepted successfully");
    }
    @GetMapping("addFriend")
    public void addUser( @RequestParam Long friendId) throws NullPointerException{
        User currentUser = securityService.getCurrentUser();
        friendService.saveFriend(currentUser, friendId);
       // return ResponseEntity.ok("Friend added successfully");
    }
    @GetMapping("ISFriend")
    public List<User> isFriend(){
        List<User> myFriends = friendService.getaddedFriends();
        List<User> user = authService.getAllUsers();
        //List<User> r = ListUtils.subtract(user,myFriends);
        //List<String> result = new ArrayList<>(a);
        List<User> usertab = new ArrayList<>(user);
        List<User> friendtab = new ArrayList<>(myFriends);

        usertab.removeAll(myFriends);
      //differences.removeAll(myFriends);
        return usertab;
    }

   /* public List<User> isFriend(@PathVariable String username){
        List<User> myFriends = friendService.getFriends();
        List<User> user = authService.getAllUsers();
        List<User> currentUser = userRepository.findUsersByUsername(username);
        //List<User> r = ListUtils.subtract(user,myFriends);
        //List<String> result = new ArrayList<>(a);
        user.removeAll(myFriends);
        user.removeAll(currentUser);
        return user;

    }*/

    @GetMapping("listFriends")
    public ResponseEntity<List<User>> getFriends() {
        List<User> myFriends = friendService.getFriends();
        return new ResponseEntity<>(myFriends, HttpStatus.OK);
    }
    @GetMapping("getListFriends")
    public ResponseEntity<List<Friend>> getListFriends() {
        List<Friend> myFriends = friendService.getListFriends();
        return new ResponseEntity<>(myFriends, HttpStatus.OK);
    }
    @GetMapping("listInvitations")
    public ResponseEntity<List<User>> getaddedFriends() {
        List<User> myFriends = friendService.getaddedFriends();
        return new ResponseEntity<>(myFriends, HttpStatus.OK);
    }
    @PutMapping("plusVote/{id}")
    public  ResponseEntity<String> plusVote(@PathVariable Long id) {
        friendRepository.plusVote(id);
        return ResponseEntity.ok("Vote + 1");
    }
    @PutMapping("lessVote/{id}")
    public  ResponseEntity<String> lessVote(@PathVariable Long id) {
        friendRepository.lessVote(id);
        return ResponseEntity.ok("Vote - 1");
    }
    @GetMapping("/requests/{username}")
    public List<Friend> getRequests(@PathVariable String username) {

        return friendRepository.getRequests(username) ;
    }
    @GetMapping("/userNF/{username}")
    public List<User> getUserNF(@PathVariable String username) {

        return friendRepository.getUserNF(username) ;
    }
    @GetMapping("/HF")
    public List<Friend> getHF(){
        return  friendRepository.getHF();
    }

    @GetMapping("/FriendExist/{id}")
    public boolean FriendExist(@PathVariable Long id){
        return friendService.FriendExist((id));
    }

    @GetMapping("/energy/{id}")
    public Long energy(@PathVariable Long id) {
        return friendRepository.energy(id);
    }
}
