package dev.rmiller.thwompchatbackend.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public String getUser() {
        return "hit user!";
    }

}
