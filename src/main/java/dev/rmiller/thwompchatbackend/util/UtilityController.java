package dev.rmiller.thwompchatbackend.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utility")
public class UtilityController {

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "Thwomp is doing well";
    }


}
