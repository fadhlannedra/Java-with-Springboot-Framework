package com.bootcamp.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.books.enttity.User;
import com.bootcamp.books.repository.UserRepository;
import com.bootcamp.books.security.jwt.JwtTokenProvider;
import com.bootcamp.books.util.CommonResponse;
import com.bootcamp.books.util.CommonStatus;
import com.bootcamp.books.util.JsonUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @PostMapping("/signin")
    public String signin(@RequestBody AuthenticationRequest data) {

    	try {
            String username = data.getUsername();
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            User user = (User) auth.getPrincipal();
            
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            CommonResponse<String> response = new CommonResponse<>();
            CommonStatus commonStatus = new CommonStatus();
			if (token == null) {
				commonStatus.setResponseCode("404");
				commonStatus.setResponseDesc("token not found");
				response.setResponseStatus(commonStatus);
			} else {
				commonStatus.setResponseCode("200");
				commonStatus.setResponseDesc("Success");
				response.setResponseStatus(commonStatus);
				response.setData(token);
			}
			
    		return JsonUtil.generateJson(response);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
}
}