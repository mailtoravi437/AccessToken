package org.electronic.store.springbootlogin.service;

import org.electronic.store.springbootlogin.entity.User;
import org.electronic.store.springbootlogin.entity.VerificationToken;
import org.electronic.store.springbootlogin.model.UserModel;
import org.electronic.store.springbootlogin.repository.UserRepository;
import org.electronic.store.springbootlogin.repository.VerifcationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifcationTokenRepository verifcationTokenRepository;

    private ModelMapper modelMapper = new ModelMapper();


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {
        User user = modelMapper.map(userModel,User.class);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user,token);
        verifcationTokenRepository.save(verificationToken);
    }

    @Override
    public String confirmRegistration(String token) {
        VerificationToken verificationToken = verifcationTokenRepository.findByToken(token);
        if(verificationToken == null){
            return "Token not found";
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
            verifcationTokenRepository.delete(verificationToken);
            return "Token expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken = verifcationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(java.util.UUID.randomUUID().toString());
        verifcationTokenRepository.save(verificationToken);
        return verificationToken;
    }
}
