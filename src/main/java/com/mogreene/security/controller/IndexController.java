package com.mogreene.security.controller;

import com.mogreene.security.config.auth.PrincipalDetails;
import com.mogreene.security.dto.UserDTO;
import com.mogreene.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * oauth2 로그인
     * @param authentication
     * @param oAuth
     * @return
     */
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User oAuth) {
        System.out.println("/test/oauth/login ====");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication = " + oAuth2User.getAttributes());
        System.out.println("oAuth = " + oAuth);
        return "Oauth 세션 정보 확인";
    }

    /**
     * 일반 로그인
     * @param authentication
     * @param userDetails
     * @return
     */
    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails) {
        System.out.println("/test/login ====");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication.getUserDTO() = " + principalDetails.getUserDTO());
        System.out.println("userDetails = " + userDetails.getUsername());
        return "세션 정보 확인";
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    /**
     * 회원가입 페이지
     * @return
     */
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    /**
     * 회원가입 > 등록
     * @param userDTO
     * @return
     */
    @PostMapping("/join")
    public String join(UserDTO userDTO) {

        userDTO.setRole("ROLE_USER");
        String rawPassword = userDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userDTO.setPassword(encPassword);

        userRepository.insertUser(userDTO);

        return "redirect:/loginForm";
    }


    //================보통 메서드 하나에 걸때는 어노테이션으로 처리, 컨트롤러를 걸고 싶다면 SecurityConfig 설정으로..
    @Secured("ROLE_ADMIN")  //@EnableGlobalMethodSecurity 를 통해 활성화 된 어노테이션 / 하나만 걸고 싶을때 > 최근에 많이 씀
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")   //두개의 권한을 걸고 싶을때 > 예전에 많이 씀
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
