package com.mogreene.security.repository;

import com.mogreene.security.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    void insertUser(UserDTO userDTO);

}
