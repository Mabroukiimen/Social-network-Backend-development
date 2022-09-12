package com.programming.techie.springredditclone.mapper;

import com.programming.techie.springredditclone.dto.UserDto;
import com.programming.techie.springredditclone.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface ModelMapper {
    UserDto map(User user, Class<UserDto> userdto);
}

