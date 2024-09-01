package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.RegistrationDto;
import com.mol_ferma.web.enums.RoleName;
import com.mol_ferma.web.models.UserEntity;
import com.mol_ferma.web.models.VerificationToken;
import com.mol_ferma.web.repository.VerificationTokenRepository;
import com.mol_ferma.web.service.EmailService;
import com.mol_ferma.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    private VerificationTokenRepository verificationTokenRepository;
    private EmailService emailService;

    @Autowired
    public AuthController(UserService userService,
                          VerificationTokenRepository verificationTokenRepository,
                          EmailService emailService) {
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }



    /*@PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result, Model model) {
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        UserEntity existingUserPhoneNumber = userService.findByPhoneNumber(user.getPhoneNumber());

        if(existingUserPhoneNumber != null && existingUserPhoneNumber.getPhoneNumber() != null
                && !existingUserPhoneNumber.getPhoneNumber().isEmpty()) {
            return "redirect:/register?fail";
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        user.setRole(RoleName.USER);
        userService.saveUser(user);

        return "redirect:/login?success";
    }*/
}
