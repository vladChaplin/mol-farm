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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.mol_ferma.web.utils.mapper.UserEntityMapper.mapToUserEntity;

@Controller
public class AuthController {
    private UserService userService;

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public AuthController(UserService userService,
                          VerificationTokenRepository verificationTokenRepository) {
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterForm(ModelAndView modelAndView, RegistrationDto registrationDto) {
        modelAndView.addObject("user", registrationDto);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerUser(Model model, RegistrationDto registrationDto, BindingResult result) {
        UserEntity existingUser = userService.findByEmail(registrationDto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        UserEntity existingUserPhoneNumber = userService.findByPhoneNumber(registrationDto.getPhoneNumber());

        if (existingUserPhoneNumber != null && existingUserPhoneNumber.getPhoneNumber() != null
                && !existingUserPhoneNumber.getPhoneNumber().isEmpty()) {
            return "redirect:/register?fail";
        }

        if(result.hasErrors()) {
            model.addAttribute("user", registrationDto);
            return "register";
        }

        var userEntity = userService.saveUser(registrationDto, RoleName.USER);
        VerificationToken verificationToken = new VerificationToken(userEntity);
        verificationTokenRepository.save(verificationToken);

        userService.sendMessage(registrationDto.getEmail(), verificationToken.getConfirmationToken());

        model.addAttribute("emailName", registrationDto.getEmail());

        return "successful-registration";
    }

//    TODO: UPDATE method check expired token datetime
    @GetMapping("/activate")
    public String confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByConfirmationToken(confirmationToken);

        if(verificationToken != null) {
            UserEntity user = userService.findByEmail(verificationToken.getUser().getEmail());
            user.setEnabled(true);
            userService.saveUser(user);
            return "redirect:/login?success";
        } else {
            return "redirect:/register?fail";
        }
    }


}
