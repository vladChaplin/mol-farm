package com.mol_ferma.web.service;


import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.models.UserEntity;


public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByPhoneNumber(String phoneNumber);
    void sendMessage(RegistrationDto registrationDto, String token);
}
