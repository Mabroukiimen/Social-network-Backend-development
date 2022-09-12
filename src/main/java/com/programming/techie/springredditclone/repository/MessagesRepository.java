package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.model.Friend;
import com.programming.techie.springredditclone.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MessagesRepository  extends JpaRepository<Messages,Long> {

    @Query("SELECT m from Messages m  WHERE (m.sender.username=:username and m.receiver.userId=:userId) or (m.receiver.username=:username and m.sender.userId=:userId) and m.theme=:theme ORDER BY m.createdDate asc ")
    List<Messages> getListMessages(String username , Long userId, String theme);

     @Query("SELECT m from Messages  m where m.seen=false and m.receiver.username=:username and m.theme=:theme")
     List<Messages> getNotifMessages(@Param("username") String username, @Param("theme") String theme);

     @Query("select m from Messages m where m.seen=false and m.receiver.username=:username")
     List<Messages> getListMsgs (@Param("username") String username);

    @Query("SELECT count(m) from Messages  m where m.seen=false and m.receiver.username=:username")
    Long countMsgs(@Param("username") String username);



    @Query("SELECT count(m) from Messages  m where m.seen=false and m.receiver.username=:username1 and m.sender.username=:username2 and m.theme=:theme")
    Long countNotif(@Param("username1") String username1,@Param("username2") String username2,@Param("theme") String theme);

    @Transactional
    @Modifying
    @Query("update Messages f set f.seen = true  where f.sender.userId= :userId and f.theme=:theme")
    void seen(@Param(value = "userId") long userId,@Param(value="theme") String theme);


}
