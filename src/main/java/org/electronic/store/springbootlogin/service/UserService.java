package org.electronic.store.springbootlogin.service;

import org.electronic.store.springbootlogin.entity.User;
import org.electronic.store.springbootlogin.entity.VerificationToken;
import org.electronic.store.springbootlogin.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerUser(UserModel userModel);

    void createVerificationToken(User user, String token);

    String confirmRegistration(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
