package com.ppmtool.ppmtool.serviceImpl;

import com.ppmtool.ppmtool.repositories.UserRepository;
import com.ppmtool.ppmtool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

   private UserRepository userRepository;

   @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
