package com.programming.techie.springredditclone.service;


import com.programming.techie.springredditclone.dto.MessageDTO;
import com.programming.techie.springredditclone.dto.MessageGroupDTO;
import com.programming.techie.springredditclone.model.Friend;
import com.programming.techie.springredditclone.model.Messages;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.MessagesRepository;
import com.programming.techie.springredditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthService securityService;

    @Autowired
    private MessagesRepository messagesRepository;

    public Messages send(Messages messages) {
        return messagesRepository.save(messages);
    }
    public Messages sendM(Messages messages,Long toIdUser)   {
       // Messages messages = new Messages();
        User currentUser = securityService.getCurrentUser();
        messages.setSender(userRepository.findUserByuserId(currentUser.getUserId()));
        messages.setReceiver(userRepository.findUserByuserId(toIdUser));
        //  friend.setFirstUserName(currentUser.getUsername());
        //     friend.setSecondUserName(userRepository.findUserNameByUserId(friendId));
        messages.setCreatedDate(LocalDateTime.now());
        messages.setTheme(messages.getTheme());
        messages.setMessageContent(messages.getMessageContent());
        messages.setSeen(false);




        return messagesRepository.save(messages);
    }


    public void sendMessage(String to, MessageDTO message) {

        jdbcTemplate.update("insert into messages (message_text,message_from,message_to,created_datetime) " +
                "values (?,?,?,current_time )",message.getMessage(),message.getFromLogin(),to);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);

    }

    public List<Map<String,Object>> getListMessage(@PathVariable("from") Integer from, @PathVariable("to") Integer to){
        return jdbcTemplate.queryForList("select * from messages where (message_from=? and message_to=?) " +
                "or (message_to=? and message_from=?) order by created_datetime asc",from,to,from,to);
    }


    public List<Map<String,Object>> getListMessageGroups(@PathVariable("groupid") Integer groupid){
        return jdbcTemplate.queryForList("select gm.*,us.name as name from group_messages gm " +
                "join users us on us.id=gm.user_id " +
                "where gm.group_id=? order by created_datetime asc",groupid);
    }


    public void sendMessageGroup(Integer to, MessageGroupDTO message) {

        jdbcTemplate.update("INSERT INTO `group_messages`(`group_id`, `user_id`, `messages`, `created_datetime`) " +
                "VALUES (?,?,?,current_timestamp )",to,message.getFromLogin(),message.getMessage());
        message.setGroupId(to);
        simpMessagingTemplate.convertAndSend("/topic/messages/group/" + to, message);

    }
}