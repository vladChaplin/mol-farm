package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.ForgotEmailForm;
import com.mol_ferma.web.dto.ForgotPasswordForm;
import com.mol_ferma.web.models.UserEntity;
import com.mol_ferma.web.models.VerificationToken;
import com.mol_ferma.web.repository.VerificationTokenRepository;
import com.mol_ferma.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ForgotPasswordController {

    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public ForgotPasswordController(UserService userService, VerificationTokenRepository verificationTokenRepository) {
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @GetMapping("/forgotPassword")
    public String showForgotPassword(Model model) {
        model.addAttribute("forgotEmail", new ForgotEmailForm());
        return "forgot-password";
    }

    @PostMapping("/forgotPassword")
    public String sendEmailToken(@Valid @ModelAttribute("forgotEmail") ForgotEmailForm forgotEmailForm,
                                 BindingResult result,
                                 Model model) {
        if(result.hasErrors()) {
            model.addAttribute("forgotEmail", forgotEmailForm);
            return "forgot-password";
        }

        var user = userService.findByEmail(forgotEmailForm.getEmail());
        if(user == null) return "redirect:/forgotPassword?notExistUser";
        VerificationToken token = new VerificationToken(user);
        verificationTokenRepository.save(token);
        userService.sendMessageForChangePassword(user.getEmail(), token.getConfirmationToken());

        model.addAttribute("email", user.getEmail());

        return "successful-send-email-token";
    }

    @GetMapping("/changePasswordError")
    public String showErrorsChangePasswordUser() {
        return "change-password-error";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordUser(@RequestParam("token") String confirmationToken,
                                         Model model,
                                         HttpSession session) {
        var token = verificationTokenRepository.findByConfirmationToken(confirmationToken);
        var currentDateTime = LocalDateTime.now();

        if(token == null) return "redirect:/changePasswordError?tokenNotExist";

        if(!currentDateTime.isBefore(token.getExpiryDate())) return "redirect:/changePasswordError?expired";

        model.addAttribute("forgotPass", new ForgotPasswordForm());
        session.setAttribute("token", token.getConfirmationToken());

        return "change-password";
    }

    @PostMapping("/changePassword")
    public String changePasswordUser(@Valid @ModelAttribute("forgotPass") ForgotPasswordForm forgotPasswordForm,
                                     BindingResult result,
                                     Model model,
                                     HttpSession session) {

        if(result.hasErrors()) {
            model.addAttribute("forgotPass", forgotPasswordForm);
            return "change-password";
        }

        String token = (String) session.getAttribute("token");
        UserEntity user = verificationTokenRepository.findByConfirmationToken(token).getUser();

        if(user == null) return "redirect:/change-password?notExistUser";

        if(!userService.changeUserPassword(user, forgotPasswordForm.getPassword())) {
            return "redirect:/change-password?errorChangePassword";
        }

        session.removeAttribute("token");
        return "redirect:/login?successChangePassword";
    }
}

