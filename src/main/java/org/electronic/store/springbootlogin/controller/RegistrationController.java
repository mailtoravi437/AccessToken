package org.electronic.store.springbootlogin.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.electronic.store.springbootlogin.entity.User;
import org.electronic.store.springbootlogin.entity.VerificationToken;
import org.electronic.store.springbootlogin.event.RegistrationCompleteEvent;
import org.electronic.store.springbootlogin.model.UserModel;
import org.electronic.store.springbootlogin.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;


    @Autowired
    private ApplicationEventPublisher publisher ;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return new ResponseEntity<>("Registered successfully", HttpStatus.OK);
    }

    private String applicationUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();

    }
    }

    @PostMapping("/registrationConfirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        String result = userService.confirmRegistration(token);
        if(result.equalsIgnoreCase("User registered successfully")){
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Verified", HttpStatus.NO_CONTENT);
    }


    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        publisher.publishEvent(new RegistrationCompleteEvent(userService.getUser(),applicationUrl(request)));
        return "Verification token has been resent";
}
