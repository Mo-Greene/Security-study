package com.mogreene.security.config.auth;

//security 가 /login 주소로 로그인 진행
//로그인 완료가 되면 시큐리티 session 만듬(Security ContextHolder)
//세션에 들어가는 오브젝트는 정해져있다. => Authentication 객체
//Authentication 안에 User 정보 존재
//User 오브젝트 타입 => UserDetails 타입 객체

import com.mogreene.security.dto.UserDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//SecuritySession안에 => Authentication => UserDetails 타입(PrincipalDetails)
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public PrincipalDetails(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    //해당 User의 권한을 리턴(role)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //1년 동안 접속을 하지 않았다면 휴면계정으로 할 필요가 있음
        //그럴때 사용
        return true;
    }
}
