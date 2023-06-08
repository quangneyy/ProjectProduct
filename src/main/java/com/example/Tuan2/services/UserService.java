package com.example.Tuan2.services;

import com.example.Tuan2.entity.User;
import com.example.Tuan2.repository.IRoleRepository;
import com.example.Tuan2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user){
        userRepository.save(user);
        Long userId = userRepository.getUserldByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (roleId != 0 && userId != 0) {
            userRepository.addRoleToUser(userId, roleId);
        }
    }
    public User getUserByUsername(String name){
        return userRepository.findByUsername(name);
    }

}
