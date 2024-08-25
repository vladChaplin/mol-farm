package com.mol_ferma.web.service.impl;

import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.enums.RoleName;
import com.mol_ferma.web.models.Role;
import com.mol_ferma.web.models.UserEntity;
import com.mol_ferma.web.repository.RoleRepository;
import com.mol_ferma.web.repository.UserRepository;
import com.mol_ferma.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        String userName = Stream.of(registrationDto.getEmail().substring(0, 6))
                .map(StringBuilder::new)
                .map(str -> str.append(registrationDto.getLastName()))
                .map(str -> str.append(new Random().nextInt())).toString();

        user.setUsername(userName);
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(registrationDto.getPassword());
        user.setPhoneNumber(registrationDto.getPhoneNumber());

        Role role = roleRepository.findByName(RoleName.ADMIN).get();
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
