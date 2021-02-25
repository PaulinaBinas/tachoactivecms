package com.binas.tachographcms.controllers;

import com.binas.tachographcms.model.to.UserTo;
import com.binas.tachographcms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tacho")
public class TachographController {

    private UserService userService;

    public TachographController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/secured")
    public String securedPage() {
        return "home";
    }

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }

    @GetMapping("/users")
    public List<UserTo> getAllUsers() {
        return this.userService.getAllUsers();
    }

}
