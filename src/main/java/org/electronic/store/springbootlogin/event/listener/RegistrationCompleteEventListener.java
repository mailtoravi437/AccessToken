package org.electronic.store.springbootlogin.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.electronic.store.springbootlogin.entity.User;
import org.electronic.store.springbootlogin.event.RegistrationCompleteEvent;
import org.electronic.store.springbootlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.logging.Logger;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    public UserService userService;

    Logger logger = Logger.getLogger(RegistrationCompleteEventListener.class.getName());

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create verification token for the user
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user,token);
        //Send Mail to user.
        String url = event.getApplicationUrl() + "/registrationConfirm?token=" + token;
        String message = "Please click the link below to confirm your registration";
        logger.info("Sending mail to user with url: "+url);


    }
}
