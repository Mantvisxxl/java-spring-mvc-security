package com.mantvydas.demosecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Controller
public class MyCustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public MyCustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        // Get the error attributes
        Map<String, Object> errorAttributesMap = getErrorAttributes(request);

        // Add specific error details to the model
        model.addAttribute("status", errorAttributesMap.get("status"));
        model.addAttribute("error", errorAttributesMap.get("error"));
        model.addAttribute("message", errorAttributesMap.get("message"));

        return "error";
    }

    // Extract error attributes from the request
    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request), ErrorAttributeOptions.defaults());
    }


}
