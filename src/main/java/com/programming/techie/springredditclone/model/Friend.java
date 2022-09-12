package com.programming.techie.springredditclone.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "accepted_date")
    private LocalDate acceptedDate;



    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "first_user_id", referencedColumnName = "userId")
    User firstUser;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "second_user_id", referencedColumnName = "userId")
    User secondUser;
/*
    @Column(name = "first_user_name")
    private String firstUserName;

    @Column(name = "second_user_name")
    private String secondUserName;
*/
    @Column(name = "is_Friend")
    private Boolean isFriend;


    private Integer voteCount = 0;

    private Integer energy=0;
    private  Integer energyMatryoshka=0;

    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }
/*
    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        this.firstUserName = firstUserName;
    }

    public String getSecondUserName() {
        return secondUserName;
    }

    public void setSecondUserName(String secondUserName) {
        this.secondUserName = secondUserName;
    }
*/
    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(LocalDate acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}