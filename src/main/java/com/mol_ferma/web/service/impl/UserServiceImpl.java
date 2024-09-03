package com.mol_ferma.web.service.impl;

import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.enums.RoleName;
import com.mol_ferma.web.models.Role;
import com.mol_ferma.web.models.UserEntity;
import com.mol_ferma.web.repository.RoleRepository;
import com.mol_ferma.web.repository.UserRepository;
import com.mol_ferma.web.service.EmailService;
import com.mol_ferma.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mol_ferma.web.utils.UserFormatName.formatUsernameByEmail;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserEntity saveUser(RegistrationDto registrationDto, RoleName roleName) {
        UserEntity user = new UserEntity();

        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhoneNumber(registrationDto.getPhoneNumber());

        user.setUsername(formatUsernameByEmail(user));

        Role role = roleRepository.findByName(roleName);
        user.setRoles(Arrays.asList(role));

        return userRepository.save(user);
    }

    @Override
    public void saveUser(UserEntity user) {
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

    @Override
    public void sendMessage(RegistrationDto registrationDto, String token) {
        if(!StringUtils.isEmpty(registrationDto.getEmail())) {
            String message = String.format(
                    "Чтобы завершить регистрацию! " +
                            "Подтвердите аккаунт, нажав на ссылку..." +
                            "http://%s/activate?token=%s",
                    hostname,
                    token
            );

            emailService.sendEmailMessage(registrationDto.getEmail(),
                    "Активация аккаунта",
                    message);
        }
    }
}
