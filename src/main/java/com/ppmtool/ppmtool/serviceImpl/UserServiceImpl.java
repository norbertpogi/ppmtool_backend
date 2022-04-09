package com.ppmtool.ppmtool.serviceImpl;

import com.ppmtool.ppmtool.domain.User;
import com.ppmtool.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.ppmtool.ppmtool.repositories.UserRepository;
import com.ppmtool.ppmtool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

   private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Autowired
    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User saveUser(User user) {
       try {
           user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
           //Username has to be unique (exception)

           // Make sure that password and confirmPassword match
           // We don't persist or show the confirmPassword
           return userRepository.save(user);
       } catch (Exception ex) {
           throw new UsernameAlreadyExistsException("Username: " +"'"+user.getUsername()+"' "+ "already exist");
       }
    }
}
