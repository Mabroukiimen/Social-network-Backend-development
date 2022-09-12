package com.programming.techie.springredditclone.controller;


import com.programming.techie.springredditclone.dto.MessageDTO;
import com.programming.techie.springredditclone.dto.MessageGroupDTO;
import com.programming.techie.springredditclone.model.Friend;
import com.programming.techie.springredditclone.model.Messages;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.MessagesRepository;
import com.programming.techie.springredditclone.repository.UserRepository;
import com.programming.techie.springredditclone.service.AuthService;
import com.programming.techie.springredditclone.service.MessageService;
import com.programming.techie.springredditclone.service.UserAndGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class MessagesController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    MessageService messageService;

    @Autowired
    private AuthService securityService;

    @Autowired
    UserAndGroupService userAndGroupService;

    @Autowired
    MessagesRepository messagesRepository;

    @MessageMapping("/chat/{to}")
    public void sendMessagePersonal(@DestinationVariable String to, MessageDTO message) {

        messageService.sendMessage(to,message);

    }
    @PostMapping("/chat/{toIdUser}")
    public Messages sendMessage(@PathVariable Long toIdUser) {
        Messages messages = new Messages();
        User currentUser = securityService.getCurrentUser();
        messages.setSender(userRepository.findUserByuserId(currentUser.getUserId()));
        messages.setReceiver(userRepository.findUserByuserId(toIdUser));
        //  friend.setFirstUserName(currentUser.getUsername());
        //     friend.setSecondUserName(userRepository.findUserNameByUserId(friendId));
       // messages.setCreatedDate(LocalDateTime.now());
        messages.setTheme(messages.getTheme());
        messages.setMessageContent(messages.getMessageContent());

        return messageService.send(messages);

       // messageService.sendMessage(to,message);

    }
    @PostMapping({"/sendMessages/{toIdUser}"})
    public Messages sendM(@RequestBody Messages messages,@PathVariable Long toIdUser) {
        return messageService.sendM(messages,toIdUser);
    }
    @GetMapping("/getListMessages/{username}/{userId}/{theme}")
    public List<Messages> getListMessages(@PathVariable("username") String username, @PathVariable("userId") Long userId, @PathVariable("theme") String theme){
        return messagesRepository.getListMessages(username, userId,theme);
    }
    @RequestMapping(value="/getNotifMessages/{username}/{theme}",method=RequestMethod.GET)
   // @GetMapping(value="/getNotifMessages/{userId}/{theme}")
    public List<Messages> getNotifMessages(@PathVariable("username") String  username, @PathVariable("theme") String theme){
        return messagesRepository.getNotifMessages(username,theme);
    }
    @RequestMapping(value="/getListMsgs/{username}",method=RequestMethod.GET)
    public  List<Messages> getListMsgs(@PathVariable("username") String username){
        return messagesRepository.getListMsgs(username);
    }

   @ResponseBody
   @PutMapping("/seen/{userId}/{theme}")
    public void seen(@PathVariable("userId") Long userId,@PathVariable("theme") String  theme){
       messagesRepository.seen(userId,theme);
    }

    @GetMapping("/countNotif/{username1}/{username2}/{theme}")
    public Long countNotif(@PathVariable("username1") String username1, @PathVariable("username2") String username2, @PathVariable("theme") String theme) {
        return messagesRepository.countNotif(username1, username2,theme);
    }
    @GetMapping("/countMsgs/{username}")
    public Long countMsgs( @PathVariable("username") String username) {
        return messagesRepository.countMsgs(username);
    }

    @GetMapping("listmessage/{from}/{to}")
    public List<Map<String,Object>> getListMessageChat(@PathVariable("from") Integer from, @PathVariable("to") Integer to){
        return messageService.getListMessage(from, to);
    }

    @MessageMapping("/chat/group/{to}")
    public void sendMessageToGroup(@DestinationVariable Integer to, MessageGroupDTO message) {
        messageService.sendMessageGroup(to,message);

    }

    @GetMapping("listmessage/group/{groupid}")
    public List<Map<String,Object>> getListMessageGroupChat(@PathVariable("groupid") Integer groupid){
        return messageService.getListMessageGroups(groupid);
    }

    @GetMapping("/fetchAllUsers/{myId}")
    public List<Map<String,Object>> fetchAll(@PathVariable("myId") String myId) {
        return userAndGroupService.fetchAll(myId);

    }

    @GetMapping("/fetchAllGroups/{groupid}")
    public List<Map<String,Object>> fetchAllGroup(@PathVariable("groupid") String groupId) {
        return  userAndGroupService.fetchAllGroup(groupId);
    }

}