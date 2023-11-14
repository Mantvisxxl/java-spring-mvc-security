package com.mantvydas.demosecurity.controller;

import com.mantvydas.demosecurity.entity.PasswordChangeForm;
import com.mantvydas.demosecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PasswordChangeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {

        model.addAttribute("passwordChangeForm", new PasswordChangeForm());

        return "password-change-form";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passwordChangeForm") PasswordChangeForm form, Principal principal, Model model) {
        String username = principal.getName();

        // Validate current password
        if (!userService.isPasswordCorrect(username, form.getCurrentPassword())) {
            model.addAttribute("errorMessage", "Incorrect current password");
            return "password-change-form";
        }

        // Validate new password and confirmation
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "New passwords do not match");
            return "password-change-form";
        }

        // Update password
        System.out.println("Password MJ: " + form.getNewPassword());
        System.out.println("Encoded: " + passwordEncoder.encode(form.getNewPassword()));
        userService.updateUserPassword(username, passwordEncoder.encode(form.getNewPassword()));

        model.addAttribute("successMessage", "Password updated successfully");
        return "password-change-result";
    }
}
