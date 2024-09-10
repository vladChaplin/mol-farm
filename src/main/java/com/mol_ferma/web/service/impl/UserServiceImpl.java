package com.mol_ferma.web.service.impl;

import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.enums.RoleName;
import com.mol_ferma.web.models.Role;
import com.mol_ferma.web.models.UserEntity;
import com.mol_ferma.web.repository.RoleRepository;
import com.mol_ferma.web.repository.UserRepository;
import com.mol_ferma.web.service.EmailService;
import com.mol_ferma.web.service.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
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

    @Value("${custom.protocol.name}")
    private String protocolProject;

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
    public void sendMessage(String email, String token) {
        if(email != null && !email.isEmpty()) {
            String message = String.format(
                    "Чтобы завершить регистрацию! " +
                            "Подтвердите аккаунт, нажав на ссылку: " +
                            "%s://%s/activate?token=%s",
                    protocolProject,
                    hostname,
                    token
            );

            emailService.sendEmailMessage(email,
                    "Активация аккаунта",
                    message);
        }
    }

    @Override
    public void sendMessageForChangePassword(String email, String token) {
        if(email != null && !email.isEmpty()) {
            String message = String.format(
                    "Чтобы сбросить пароль, нажмите на ссылку: " +
                    "%s://%s/changePassword?token=%s",
                    protocolProject,
                    hostname,
                    token
            );

            emailService.sendEmailMessage(email, "Сброс пароля", message);
        }

    }

    @Override
    public boolean changeUserPassword(UserEntity user, String password) {
        if(password == null) return false;
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user) != null;
    }

    @Override
    public String saveUserByOAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof OAuth2AuthenticationToken oauthToken) {
            OAuth2User oAuth2User = oauthToken.getPrincipal();
            if(oAuth2User != null) {
                Map<String, Object> attributes = oAuth2User.getAttributes();
                var email = (String) attributes.get("email");
                Optional<UserEntity> optionalUser = userRepository.findByEmailOptional(email);

                if(optionalUser.isEmpty()) {
                    UserEntity user = new UserEntity();
                    user.setId(Long.parseLong((String) attributes.get("sub")));
                    user.setEnabled(true);
                    user.setEmail(email);
                    user.setFirstName((String) attributes.get("given_name"));
                    user.setLastName((String) attributes.get("family_name"));
                    user.setPhoneNumber((String) attributes.get("phone"));
                    user.setPassword(passwordEncoder.encode((CharSequence) attributes.get("token")));
                    var role = roleRepository.findByName(RoleName.USER);
                    user.setRoles(Arrays.asList(role));

                }
            }
        }
//        TODO: ДОПИЛИТЬ метод
        return "";
    }
}
