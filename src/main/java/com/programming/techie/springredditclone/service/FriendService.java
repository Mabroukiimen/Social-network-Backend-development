package com.programming.techie.springredditclone.service;


import com.programming.techie.springredditclone.mapper.ModelMapper;
import com.programming.techie.springredditclone.model.Friend;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.FriendRepository;
import com.programming.techie.springredditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    ModelMapper modelMapper;


    @Autowired
    private AuthService securityService;

    public Friend add(Friend friend) {
        return friendRepository.save(friend);
    }

    public void saveFriend(User userDto1, Long id) throws NullPointerException{


        User userDto2 = userRepository.findUserByuserId(id);

       // UserDto userDto2 = modelMapper.map(user,UserDto.class);


        Friend friend = new Friend();
        User user1 = userRepository.findUserByEmail(userDto1.getEmail());
        User user2 = userRepository.findUserByEmail(userDto2.getEmail());
        User firstuser = user1;
        User seconduser = user2;
       /* if(user1.getUserId() > user2.getUserId()){
            firstuser = user2;
            seconduser = user1;
        }*/
        if( !(friendRepository.existsByFirstUserAndSecondUser(firstuser,seconduser)) ){
            friend.setCreatedDate(LocalDate.now());
            friend.setFirstUser(firstuser);
            friend.setSecondUser(seconduser);
            friendRepository.save(friend);
        }
    }


    public List<User> getFriends() {

        User currentUserDto = securityService.getCurrentUser();
        User currentUser = userRepository.findUserByEmail(currentUserDto.getEmail());
        List<Friend> friendsByFirstUser = friendRepository.findByFirstUser(currentUser);
        List<Friend> friendsBySecondUser = friendRepository.findBySecondUser(currentUser);
        List<User> friendUsers = new ArrayList<>();


        /*
            suppose there are 3 users with id 1,2,3.
            if user1 add user2 as friend database record will be first user = user1 second user = user2
            if user3 add user2 as friend database record will be first user = user2 second user = user3
            it is because of lexicographical order
            while calling get friends of user 2 we need to check as a both first user and the second user
         */



            for (Friend friend : friendsByFirstUser) {
                if (friend.getFriend()==true){
                    friendUsers.add(userRepository.findUserByuserId(friend.getSecondUser().getUserId()));



                }
            }
            for (Friend friend : friendsBySecondUser) {
                if (friend.getFriend()==true){
                friendUsers.add(userRepository.findUserByuserId(friend.getFirstUser().getUserId()));}
            }


        return friendUsers;
    }
    public List<Friend> getListFriends() {

        User currentUserDto = securityService.getCurrentUser();
        User currentUser = userRepository.findUserByEmail(currentUserDto.getEmail());
        List<Friend> friendsByFirstUser = friendRepository.findByFirstUser(currentUser);
        List<Friend> friendsBySecondUser = friendRepository.findBySecondUser(currentUser);
        List<Friend> friendUsers = new ArrayList<>();


        /*
            suppose there are 3 users with id 1,2,3.
            if user1 add user2 as friend database record will be first user = user1 second user = user2
            if user3 add user2 as friend database record will be first user = user2 second user = user3
            it is because of lexicographical order
            while calling get friends of user 2 we need to check as a both first user and the second user
         */



        for (Friend friend : friendsByFirstUser) {
            if (friend.getFriend()==true){
                friendUsers.add( friendRepository.findById(friend.getId()));



            }
        }
        for (Friend friend : friendsBySecondUser) {
            if (friend.getFriend()==true){
                friendUsers.add(friendRepository.findById(friend.getId()));}
        }


        return friendUsers;
    }
    public List<User> getaddedFriends() {

        User currentUserDto = securityService.getCurrentUser();
        User currentUser = userRepository.findUserByEmail(currentUserDto.getEmail());
        List<Friend> friendsByFirstUser = friendRepository.findByFirstUser(currentUser);
        List<Friend> friendsBySecondUser = friendRepository.findBySecondUser(currentUser);
        List<User> friendUsers = new ArrayList<>();

        /*
            suppose there are 3 users with id 1,2,3.
            if user1 add user2 as friend database record will be first user = user1 second user = user2
            if user3 add user2 as friend database record will be first user = user2 second user = user3
            it is because of lexicographical order
            while calling get friends of user 2 we need to check as a both first user and the second user
         */



        for (Friend friend : friendsByFirstUser) {
            if (friend.getFriend()==false){
                friendUsers.add(userRepository.findUserByuserId(friend.getSecondUser().getUserId()));


            }
        }
        for (Friend friend : friendsBySecondUser) {
            if (friend.getFriend()==false){
                friendUsers.add(userRepository.findUserByuserId(friend.getFirstUser().getUserId()));}
        }


        return friendUsers;
    }
    @Transactional(readOnly = true)
    public List<Friend> getAll() {
        return friendRepository.findAll()
                .stream()
                .collect(toList());
    }
    public boolean FriendExist(Long id) {

        User currentUserDto = securityService.getCurrentUser();
        User currentUser = userRepository.findUserByEmail(currentUserDto.getEmail());
        List<Friend> friendsByFirstUser = friendRepository.findByFirstUser(currentUser);
        List<Friend> friendsBySecondUser = friendRepository.findBySecondUser(currentUser);
        List<User> friendUsers = new ArrayList<>();


        /*
            suppose there are 3 users with id 1,2,3.
            if user1 add user2 as friend database record will be first user = user1 second user = user2
            if user3 add user2 as friend database record will be first user = user2 second user = user3
            it is because of lexicographical order
            while calling get friends of user 2 we need to check as a both first user and the second user
         */



        for (Friend friend : friendsByFirstUser) {
            if (friend.getFriend()==true){
                friendUsers.add(userRepository.findUserByuserId(friend.getSecondUser().getUserId()));



            }
        }
        for (Friend friend : friendsBySecondUser) {
            if (friend.getFriend()==true){
                friendUsers.add(userRepository.findUserByuserId(friend.getFirstUser().getUserId()));}
        }


        for (User user :friendUsers){
            if(user.getUserId()==id){
                return true;

            }

        }
         //friendUsers;
           return false;
    }

}