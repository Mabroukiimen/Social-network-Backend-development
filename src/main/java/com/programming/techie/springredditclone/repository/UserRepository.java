package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findUsersByUsername(String username);
    User findUserByEmail(String email);
  User findUserByuserId(Long id);
  //  String  findUserNameByUserId(Long id);
   // Optional<User> findByuserId(Long id);
  @Query("SELECT u.userId FROM User u where u.username=:username ")
  public Long findUserIdByUserName(String username);



}
