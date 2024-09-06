package com.mol_ferma.web.service;


import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.enums.RoleName;
import com.mol_ferma.web.models.UserEntity;


public interface UserService {
    UserEntity saveUser(RegistrationDto registrationDto, RoleName roleName);
    void saveUser(UserEntity user);
    UserEntity findByEmail(String email);
    UserEntity findByPhoneNumber(String phoneNumber);
    void sendMessage(String email, String token);
    void sendMessageForChangePassword(String email, String token);

    boolean changeUserPassword(UserEntity user, String password);
}
