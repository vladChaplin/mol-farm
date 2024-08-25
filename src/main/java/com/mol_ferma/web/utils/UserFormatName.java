package com.mol_ferma.web.utils;

import com.mol_ferma.web.models.UserEntity;
import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class UserFormatName {

    public static String formatUsernameByEmail(UserEntity user) {
        return Stream.of(user.getEmail().substring(0, user.getEmail().indexOf("@")))
                .map(StringBuilder::new)
                .map(name -> name.append("#").append(new Random().nextInt(0, 1000)))
                .collect(Collectors.joining());
    }

}
