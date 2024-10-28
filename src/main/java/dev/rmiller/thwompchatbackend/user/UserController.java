package dev.rmiller.thwompchatbackend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = new User();
        user.setUsername(authentication.getName());

        return ResponseEntity.ok(user);
    }

}
