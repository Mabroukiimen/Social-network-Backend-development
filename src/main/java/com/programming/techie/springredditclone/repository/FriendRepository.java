package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.model.Friend;
import com.programming.techie.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Integer> {

    boolean existsByFirstUserAndSecondUser(User first, User second);
    Friend findById(Long id);

    List<Friend> findByFirstUser(User user);
    List<Friend> findBySecondUser(User user);

    @Transactional
    @Modifying
    @Query("update Friend f set f.isFriend =true, f.energy=100 ,f.acceptedDate=  CURRENT_TIMESTAMP  where f.firstUser.userId = :id")
    void accepted(@Param(value = "id") long id);

    Friend getById(Long id);

    @Query("SELECT f from Friend f  WHERE f.isFriend= false and f.secondUser.username=:username ")
    List<Friend> getRequests(String username);

    @Query("SELECT f  from Friend f where f.voteCount in (select MAX (f.voteCount) from Friend f ) ")
    List<Friend> getHF();

    @Query("SELECT u FROM User u  where u.username  not in (select f.firstUser.username from Friend f ) and u.username  not in (select f.secondUser.username from Friend f )  and u.username=:username ")
    List<User> getUserNF(String username);
    @Query("SELECT f.voteCount FROM Friend f  where ( f.firstUser.username=:username1 and f.secondUser.username=:username2) or( f.firstUser.username=:username2 and f.secondUser.username=:username2)  ")
    Integer getvoteCount(String username1,String username2);


    @Transactional
    @Modifying
    @Query("update Friend f set f.voteCount = f.voteCount +1, f.energyMatryoshka= f.energyMatryoshka+50 where f.id = :id and f.isFriend=true")
    void plusVote(@Param(value = "id") long id);

    @Transactional
    @Modifying
    @Query("update Friend f set f.voteCount = f.voteCount - 1  where f.id = :id and f.isFriend=true")
    void lessVote(@Param(value = "id") long id);

    @Query("SELECT sum(f.energy+ f.energyMatryoshka) FROM Friend f where f.firstUser.userId=:id or f.secondUser.userId=:id ")
    public Long energy(Long id);

}