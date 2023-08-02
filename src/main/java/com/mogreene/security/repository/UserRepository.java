package com.mogreene.security.repository;

import com.mogreene.security.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    //유저 등록
    void insertUser(UserDTO userDTO);

    //loadByUsername
    UserDTO findByUsername(String username);
}
