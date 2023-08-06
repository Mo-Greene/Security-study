package com.mogreene.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String role;

    private String provider;    //oauth 일시
    private String providerId;  //oauth PK
}

// google oauth2
// username = google_sub(pk)
// password = 암호화(겟인데어)
// email = google email
// role = ROLE_USER

// provider = google
// providerId = googlePK
