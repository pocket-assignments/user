package com.pocket.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @GetMapping
    public String checkStatus() {
        log.info("Service is up");
        return "Success";
    }
}
