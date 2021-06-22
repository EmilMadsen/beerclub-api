package dk.thebeerclub.brewhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index - " + new Date();
    }
}
