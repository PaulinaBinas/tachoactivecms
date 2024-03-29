package com.binas.tachographcms.controllers;

import com.binas.tachographcms.model.entity.User;
import com.binas.tachographcms.model.to.UserTo;
import com.binas.tachographcms.service.EmailService;
import com.binas.tachographcms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/android")
public class AndroidController {

    private EmailService emailService;

    private UserService userService;

    public AndroidController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping(consumes = "application/octet-stream")
    public HttpStatus addNewDDDFile(@RequestHeader("X-Secret") String code, @RequestHeader("filename") String filename, @RequestBody byte[] file) {
        try {
            UserTo user = userService.getUserByCode(code);
            if(user != null) {
                emailService.sendMessageWithAttachment(emailService.getEmail(), "Nowy plik DDD od " + user.getName() +
                        " " + user.getSurname() + " " + user.getCompanyName(), "W tym mailu znajdziesz nowy plik ddd z aplikacji Android.", file, filename);
                return HttpStatus.OK;
            } else {
                return HttpStatus.FORBIDDEN;
            }
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus registerNewUser(@RequestBody UserTo user) {
        try {
            this.userService.addUser(user);
            return HttpStatus.OK;
        } catch(IllegalArgumentException e) {
            return HttpStatus.CONFLICT;
        } catch (Exception e) {
	    e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/login")
    public HttpStatus login(@RequestHeader("X-Secret") String code, @RequestHeader("serialNumber") String serialNumber) {
        UserTo user = userService.getUserByCode(code);
        if(user != null && user.getSerialNumber().equals(serialNumber)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/days")
    public Integer getDaysReminder(@RequestHeader("X-Secret") String code) {
        UserTo user = userService.getUserByCode(code);
        if(user != null) {
            return user.getDaysReminder();
        } else {
            return null;
        }
    }

    @GetMapping("/name")
    public String getNameAndSurname(@RequestHeader("X-Secret") String code) {
        UserTo user = userService.getUserByCode(code);
        if(user != null) {
            return user.getName() + " " + user.getSurname();
        } else {
            return null;
        }
    }
}
