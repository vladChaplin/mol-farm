package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.ForgotPasswordForm;
import com.mol_ferma.web.models.VerificationToken;
import com.mol_ferma.web.repository.VerificationTokenRepository;
import com.mol_ferma.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("forgotPass", new ForgotPasswordForm());
        return "forgot-password";
    }

    @PostMapping("/forgotPassword")
    public String sendEmailToken(@Valid @ModelAttribute("forgotPass") ForgotPasswordForm forgotPasswordForm,
                                 BindingResult result,
                                 Model model) {
        if(result.hasErrors()) return "forgot-password";

        var user = userService.findByEmail(forgotPasswordForm.getEmail());

        if(user == null) return "redirect:/forgotPassword?notExistUser";

        System.out.println(user);

        return "successful-registration";
    }
}

