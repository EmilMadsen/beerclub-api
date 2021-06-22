package dk.thebeerclub.brewhub.controller;

import dk.thebeerclub.brewhub.model.Brew;
import dk.thebeerclub.brewhub.repository.BrewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/brew")
public class BrewController {

    @Autowired private BrewRepository brewRepository;

    @GetMapping("/check")
    public String check() {
        return "check - " + new Date();
    }

    @GetMapping("/")
    public List<Brew> getAll() {
        return brewRepository.findAll();
    }
}
