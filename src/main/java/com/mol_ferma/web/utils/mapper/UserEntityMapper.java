package com.mol_ferma.web.utils.mapper;

import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.models.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class UserEntityMapper {

    public static UserEntity mapToUserEntity(RegistrationDto registrationDto) {
        return UserEntity.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .password(registrationDto.getPassword())
                .phoneNumber(registrationDto.getPhoneNumber())
                .roles(Arrays.asList(registrationDto.getRole()))
                .enabled(registrationDto.getEnabled())
                .build();
    }
}
