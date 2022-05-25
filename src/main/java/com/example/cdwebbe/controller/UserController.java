package com.example.cdwebbe.controller;

import com.example.cdwebbe.model.UserInfo;
import com.example.cdwebbe.repository.UserRepository;
import com.example.cdwebbe.security.CurrentUser;
import com.example.cdwebbe.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.example.cdwebbe.security.CurrentUser;
import com.example.cdwebbe.security.CustomUserDetailsService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserInfo getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserInfo userSummary = new UserInfo(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }
    //    @PostMapping("/logout")
//    @ApiOperation(value = "Logs the specified user device and clears the refresh tokens associated with it")
//    public ResponseEntity logoutUser(@CurrentUser CustomUserDetailsService customUserDetails,
//                                     @ApiParam(value = "The LogOutRequest payload") @Valid @RequestBody LogOutRequest logOutRequest) {
//        userService.logoutUser(customUserDetails, logOutRequest);
//        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
//
//        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(customUserDetails.getEmail(), credentials.toString(), logOutRequest);
//        applicationEventPublisher.publishEvent(logoutSuccessEvent);
//        return ResponseEntity.ok(new ApiResponse(true, "Log out successful"));
//    }

}
