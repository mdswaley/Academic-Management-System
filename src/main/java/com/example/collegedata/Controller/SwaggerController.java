package com.example.collegedata.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SwaggerController {
    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger";
    }
}
